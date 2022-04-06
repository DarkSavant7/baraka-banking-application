package org.darksavant.test.bank.bean;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_HEADER_STARTS = "Bearer ";
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTH_HEADER);
        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith(AUTH_HEADER_STARTS)) {
            jwt = authHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwt);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,
                        null,
                        jwtTokenUtil.getRolesFromToken(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                SecurityContextHolder.getContext().setAuthentication(token);
            } catch (ExpiredJwtException | MalformedJwtException e) {
                // this catch block is optional of course
                // but I've done it for correct returning HTTP code then token expired
                StringBuilder sb = new StringBuilder();
                sb.append("{ ");
                sb.append("\"error\": \"Unauthorized\", ");
                sb.append("\"message\": \"Token expired or invalid\", ");
                sb.append("\"path\": \"")
                        .append(request.getRequestURL())
                        .append("\"");
                sb.append("} ");

                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(sb.toString());
            }
        }
        filterChain.doFilter(request, response);
    }
}
