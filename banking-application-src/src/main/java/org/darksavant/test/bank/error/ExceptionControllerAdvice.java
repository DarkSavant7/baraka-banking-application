package org.darksavant.test.bank.error;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller advice for managing errors while clients fenching our API
 */
@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<BankErrorDto> handleResourceNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        BankErrorDto err = new BankErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<BankErrorDto> handleAccessDeniedException(AccessDeniedException e) {
        // 403
        log.error(e.getMessage());
        BankErrorDto err = new BankErrorDto(HttpStatus.FORBIDDEN.value(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<BankErrorDto> handleNotEnoughPermissionsException(NotEnoughPermissionsException e) {
        // 403
        log.error(e.getMessage());
        BankErrorDto err = new BankErrorDto(HttpStatus.FORBIDDEN.value(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<BankErrorDto> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage());
        BankErrorDto err = new BankErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<BankErrorDto> handleExpiredJwtException(ExpiredJwtException e) {
        log.error(e.getMessage());
        BankErrorDto err = new BankErrorDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<BankErrorDto> handleBadDataRequestException(BadRequestException e) {
        log.error(e.getMessage());
        BankErrorDto err = new BankErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<BankErrorDto> handleValidationException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        BankErrorDto err = new BankErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
