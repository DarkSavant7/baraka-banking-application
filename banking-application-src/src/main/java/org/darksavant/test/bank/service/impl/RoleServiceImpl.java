package org.darksavant.test.bank.service.impl;

import lombok.RequiredArgsConstructor;
import org.darksavant.test.bank.domain.model.Role;
import org.darksavant.test.bank.repository.RoleRepository;
import org.darksavant.test.bank.service.RoleService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}