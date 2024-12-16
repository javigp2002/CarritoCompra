package com.dss.carritocompra.controller;

import com.dss.carritocompra.security.JwtTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    public Map<String, String> loginAdmin(@RequestParam String username, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role=  userDetails.getAuthorities().iterator().next().getAuthority();
            System.out.println(role);

            if (role.equals("ROLE_USER")) {
                Map<String, String> response = new HashMap<>();
                response.put("token", "");
                return response;
            }
            String token = jwtTokenService.generateToken(userDetails.getUsername(), Map.of("roles", role));

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        } catch (AuthenticationException e) {
            Map<String, String> response = new HashMap<>();
            response.put("token", "");
            return response;
        }
    }

    // want to check that the user is authenticated
    @GetMapping("/check")
    public Map<String, String> check() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User is authenticated");
        return response;
    }
}
