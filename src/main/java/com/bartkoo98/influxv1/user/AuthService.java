package com.bartkoo98.influxv1.user;

import com.bartkoo98.influxv1.exception.APIException;
import com.bartkoo98.influxv1.exception.ResourceAlreadyExistsException;
import com.bartkoo98.influxv1.security.jwt.JwtTokenProvider;
import com.bartkoo98.influxv1.user.dto.LoginDto;
import com.bartkoo98.influxv1.user.dto.RegisterDto;
import com.bartkoo98.influxv1.user.repository.RoleRepository;
import com.bartkoo98.influxv1.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
                       RoleRepository repository, PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            throw new ResourceAlreadyExistsException("Username", registerDto.getUsername());
        }
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ResourceAlreadyExistsException("Email", registerDto.getUsername());

        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully.";
    }
}
