package com.elviredev.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.elviredev.entities.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    // constructor
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // récupère username et pwd qui sont envoyées au format JSON avec library Jackson
        try {
            AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // récupère utilisateur authentifié
        User user = (User) authResult.getPrincipal(); // getPrincipal = user authentifié
        // déclarer une List de roles
        List<String> roles = new ArrayList<>();
        authResult.getAuthorities().forEach(auth -> {
            roles.add(auth.getAuthority());
        });

        String jwt = JWT.create()
                .withIssuer(request.getRequestURI()) // nom de l'autorité de l'application ayant généré le token
                .withSubject(user.getUsername()) // nom de l'utilisateur
                .withArrayClaim("roles", roles.toArray(new String[roles.size()])) // ajout des rôles
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityParams.EXPIRATION)) // date d'expiration
                .sign(Algorithm.HMAC256(SecurityParams.SECRET)); // signature + secret
        response.addHeader(SecurityParams.JWT_HEADER_NAME, jwt);
    }
}
