package org.darksavant.test.bank.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.darksavant.test.bank.api.dto.JwtResponse;
import org.darksavant.test.bank.api.dto.UserDto;
import org.darksavant.test.bank.api.dto.request.CreateUserRequest;
import org.darksavant.test.bank.api.dto.request.JwtRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "AUTH", description = "Set of endpoints to register and authorize users")
public interface AuthApi {

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest request);

    @Operation(summary = "Authorize user")
    @PostMapping()
    ResponseEntity<JwtResponse> authorize(@RequestBody JwtRequest jwtRequest);
}
