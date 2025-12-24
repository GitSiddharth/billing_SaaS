package com.s1.billing.auth;

import com.s1.billing.auth.dto.AuthResponse;
import com.s1.billing.auth.dto.LoginRequest;
import com.s1.billing.auth.dto.RegisterRequest;
import com.s1.billing.user.User;
import org.springframework.web.bind.annotation.*;
import com.s1.billing.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) {
        authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        User user = authService.authenticate(request);
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

}
