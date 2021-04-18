package com.codegym.airbnb.controller;

import com.codegym.airbnb.model.*;
import com.codegym.airbnb.security.JwtUtil;
import com.codegym.airbnb.services.RoleService;
import com.codegym.airbnb.services.UserService;
import com.codegym.airbnb.services.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        if (userService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setActive(true);
        user.setCreatedDate(LocalDateTime.now());
        user.setModifiedDate(LocalDateTime.now());
        user.setName(user.getUsername());
        Set<Role> roles = new HashSet<>();
        user.getRoles().forEach(role -> {
            if (role.getName().equals("admin")) {
                Role adminRole = roleService.findByName("ROLE_ADMIN").get();
                roles.add(adminRole);
            } else {
                Role userRole = roleService.findByName("ROLE_USER").get();
                roles.add(userRole);
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("login")
    public Response createAuthenticationToken(@RequestBody LoginForm loginForm) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
        } catch (BadCredentialsException e) {
            return new Response(null, "Wrong user or password", HttpStatus.FORBIDDEN);
        }
        // Trả về jwt cho người dùng.
        String jwt = jwtUtil.generateToken(loginForm.getUsername());
        Optional<UserModel> user = userService.findByUserName(loginForm.getUsername());
        UserDetails userDetails = userService.loadUserByUsername(loginForm.getUsername());
        return new Response(new JwtResponse(jwt, user.get().getId(), user.get().getUsername(), userDetails.getAuthorities()), "success", HttpStatus.OK);
    }

    @PostMapping("login-with-google")
    public Response loginWithGoogle(@RequestBody LoginGoogle loginGoogle) {
        Optional<UserModel> userModel = userService.findByEmail(loginGoogle.getEmail());
        // Trả về jwt cho người dùng.
        if (!userModel.isPresent()) {
            UserModel user = new UserModel();
            user.setName(loginGoogle.getName());
            user.setUsername(extractUsername(loginGoogle.getEmail()));
            user.setPassword(generatingRandomPassword());
            user.setEmail(loginGoogle.getEmail());
            user.setCreatedDate(LocalDateTime.now());
            user.setModifiedDate(LocalDateTime.now());
            user.setActive(true);
            UserModel newUser = userService.save(user);
            System.out.println(newUser.toString());
            String jwt = jwtUtil.generateToken(newUser.getUsername());
            UserDetails userDetails = userService.loadUserByUsername(newUser.getUsername());
            return new Response(new JwtResponse(jwt, newUser.getId(), newUser.getUsername(), userDetails.getAuthorities()), "success", HttpStatus.OK);
        }
        String jwt = jwtUtil.generateToken(userModel.get().getUsername());
        UserDetails userDetails = userService.loadUserByUsername(userModel.get().getUsername());
        System.out.println(1);
        return new Response(new JwtResponse(jwt, userModel.get().getId(), userModel.get().getUsername(), userDetails.getAuthorities()), "success", HttpStatus.OK);
    }

    private String extractUsername(String email) {
        StringTokenizer st = new StringTokenizer(email, "@");
        return st.nextToken();
    }

    private String generatingRandomPassword() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println(generatedString);
        return generatedString;
    }
}
