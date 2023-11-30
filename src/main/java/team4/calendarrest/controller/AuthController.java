package team4.calendarrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import team4.calendarrest.Repositories.EventRepository;
import team4.calendarrest.Repositories.UserRepository;
import team4.calendarrest.enteties.User;
import team4.calendarrest.requests.SignInRequest;
import team4.calendarrest.requests.SignUpRequest;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody SignInRequest signInReq) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                signInReq.getUsernameOrEmail(), signInReq.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Sign in successful!", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpReq){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpReq.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpReq.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setName(signUpReq.getName());
        user.setUsername(signUpReq.getUsername());
        user.setEmail(signUpReq.getEmail());
        user.setPassword(passwordEncoder.encode(signUpReq.getPassword()));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}