package org.darksavant.test.bank.service;

import org.darksavant.test.bank.domain.model.Role;

public interface RoleService {

    /**
     * Search for role by name
     * @param name name to find
     * @return Role entity
     */
    Role findRoleByName(String name);
}
