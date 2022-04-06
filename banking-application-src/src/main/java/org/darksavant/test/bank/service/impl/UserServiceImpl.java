package org.darksavant.test.bank.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.darksavant.test.bank.api.dto.UserDto;
import org.darksavant.test.bank.api.dto.request.CreateUserRequest;
import org.darksavant.test.bank.api.enums.UserStatus;
import org.darksavant.test.bank.domain.model.Role;
import org.darksavant.test.bank.domain.model.User;
import org.darksavant.test.bank.error.BadRequestException;
import org.darksavant.test.bank.repository.UserRepository;
import org.darksavant.test.bank.service.RoleService;
import org.darksavant.test.bank.service.UserService;
import org.darksavant.test.bank.util.DtoMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of UserService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DtoMapper mapper;
    private final RoleService roleService;

    @Override
    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("User with this username already exists");
        }

        User user = User.builder()
                .status(UserStatus.ACTIVE)
                .accounts(new ArrayList<>())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .roles(List.of(roleService.findRoleByName("ROLE_CLIENT")))
                .build();
        user = userRepository.save(user);
        return mapper.userToDto(user);
    }

    @Override
    @Transactional
    public UserDto blockUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setStatus(UserStatus.BLOCKED);
        user = userRepository.save(user);
        return mapper.userToDto(user);
    }

    @Override
    @Transactional
    public UserDto deleteUser(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setStatus(UserStatus.DELETED);
        user = userRepository.save(user);
        return mapper.userToDto(user);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    @Transactional
    public UserDto findDtoByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return mapper.userToDto(user);
    }

    @Override
    @Transactional
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return mapper.userToDto(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));

    }

    @Override
    @Transactional
    public List<UserDto> findAllByIds(Collection<Long> ids) {
        return userRepository.findAllById(ids).stream()
                .map(mapper::userToDto)
                .collect(Collectors.toList());
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
