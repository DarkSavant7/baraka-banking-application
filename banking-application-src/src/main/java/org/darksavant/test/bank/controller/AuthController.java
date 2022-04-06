package org.darksavant.test.bank.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.darksavant.test.bank.api.controller.AuthApi;
import org.darksavant.test.bank.api.dto.JwtResponse;
import org.darksavant.test.bank.api.dto.UserDto;
import org.darksavant.test.bank.api.dto.request.CreateUserRequest;
import org.darksavant.test.bank.api.dto.request.JwtRequest;
import org.darksavant.test.bank.bean.JwtTokenUtil;
import org.darksavant.test.bank.domain.model.User;
import org.darksavant.test.bank.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/auth")
public class AuthController implements AuthApi {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    @Override
    public ResponseEntity<UserDto> createUser(CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @Override
    public ResponseEntity<JwtResponse> authorize(JwtRequest jwtRequest) {
        User user = userService.findByUsername(jwtRequest.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                jwtRequest.getPassword()));
        String token = jwtTokenUtil.generateTokenFromUser(user);
        log.info("Success logged user {} with roles: {}", user.getUsername(), user.getRoles());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
