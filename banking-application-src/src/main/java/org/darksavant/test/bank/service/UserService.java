package org.darksavant.test.bank.service;

import org.darksavant.test.bank.api.dto.UserDto;
import org.darksavant.test.bank.api.dto.request.CreateUserRequest;
import org.darksavant.test.bank.domain.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * Service for managing users
 */
public interface UserService extends UserDetailsService {

    /**
     * Creating user
     * @param request users ID
     * @return User entity
     */
    UserDto createUser(CreateUserRequest request);

    /**
     * Blocking user in system
     * @param userId users ID
     * @return User entity
     */
    UserDto blockUser(Long userId);

    /**
     * Deleting user in system
     * @param userId users ID
     * @return User entity
     */
    UserDto deleteUser(Long userId);

    /**
     * Find user in system by username
     * @param username username to find
     * @return Optional of User entity
     */
    UserDto findDtoByUsername(String username);

    /**
     * Find user in system by username
     * @param username username to find
     * @return Optional of User entity
     */
    User findByUsername(String username);

    /**
     * Find user in system by username
     * @param id user ID to find
     * @return Optional of User entity
     */
    UserDto findById(Long id);

    /**
     * Find user in system by username
     * Override of UserDetailsService to use in auth process
     * @param username username to find
     * @return Optional of User entity
     */
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Find user in system by username
     * @param ids list of user IDs to find
     * @return List of User entities
     */
    List<UserDto> findAllByIds(Collection<Long> ids);
}
