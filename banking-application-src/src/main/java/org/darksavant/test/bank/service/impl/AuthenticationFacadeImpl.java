package org.darksavant.test.bank.service.impl;

import org.darksavant.test.bank.service.AuthenticationFacade;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class AuthenticationFacadeImpl implements AuthenticationFacade {
    @Override
    public Principal getPrincipal() {
        return (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
