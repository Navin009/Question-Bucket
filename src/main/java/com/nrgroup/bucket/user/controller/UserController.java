package com.nrgroup.bucket.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nrgroup.bucket.config.SecurityRule;
import com.nrgroup.bucket.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // @Autowired
    // private OTPService otpService;

    // @Autowired
    // private JWTUtils jwtUtils;

    @PreAuthorize(SecurityRule.PERMIT_ALL)
    @PostMapping("/v1/password/update")
    public ResponseEntity<?> updateUserPassword(@ModelAttribute("password") String password) {
        // UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/login");
        // HttpHeaders header = new HttpHeaders();
        // try {
        //     String isUserVarified = session.getAttribute("user").toString();
        //     if (isUserVarified.equals("verified")) {
        //         boolean isPasswordUpdated = userService.updatePassword(session.getAttribute("email").toString(),
        //                 password);
        //         if (isPasswordUpdated) {
        //             uriComponentsBuilder.queryParam("error", "Password Updated Successfully!");
        //             header.add(HttpHeaders.LOCATION, uriComponentsBuilder.build().toUriString());
        //         } else {
        //             uriComponentsBuilder.queryParam("error", "Error in Updating password");
        //             header.add(HttpHeaders.LOCATION, uriComponentsBuilder.build().toUriString());
        //         }
        //         return ResponseEntity.status(HttpStatus.FOUND)
        //                 .headers(header)
        //                 .build();
        //     }
        // } catch (Exception e) {
        //     log.error("Exception in UserController updateUserPassword", e);
        // }
        // uriComponentsBuilder.queryParam("error", "Request is not Valid!");
        // header.add(HttpHeaders.LOCATION, uriComponentsBuilder.build().toUriString());
        // return ResponseEntity.status(HttpStatus.FOUND)
        //         .headers(header)
        //         .build();
        return null;
    }

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@ModelAttribute LoginRequest loginRequest,
    // HttpServletResponse response) {
    // ErrorResponse errorResponse = new ErrorResponse();

    // if (loginRequest.getEmail().isBlank() ||
    // loginRequest.getPassword().isBlank()) {
    // errorResponse.setError("Username and password are required");
    // URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
    // .path("/login")
    // .queryParam("error", errorResponse.getError()).build().toUri();
    // return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    // }

    // try {
    // Authentication authentication =
    // userService.validateUser(loginRequest.getEmail(),
    // loginRequest.getPassword());
    // String token = jwtUtils.generateJWT(authentication);
    // LoginResponse loginResponse = new LoginResponse();
    // loginResponse.setUsername(loginRequest.getEmail());
    // loginResponse.setToken(token);
    // Cookie cookie = new Cookie("0Auth", token);
    // cookie.setHttpOnly(true);
    // cookie.setPath("/");
    // response.addCookie(cookie);
    // URI uri =
    // ServletUriComponentsBuilder.fromCurrentContextPath().path("/").build().toUri();
    // return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    // } catch (Exception e) {
    // errorResponse.setError("Invalid username or password");
    // URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
    // .path("/login")
    // .queryParam("error", errorResponse.getError()).build().toUri();
    // return ResponseEntity.status(HttpStatus.FOUND).location(uri).build(); }

    // }

    // @PostMapping("/register")
    // public ResponseEntity<?> register(@ModelAttribute @Validated RegisterRequest
    // registerRequest) {
    // String otp = otpService.generateOTP(registerRequest.getEmail());
    // boolean isEmailSend = otpService.sendEmail(registerRequest.getEmail(), otp);
    // boolean isUserRegistered = userService.registerUser(registerRequest);
    // if (isEmailSend && isUserRegistered) {
    // return ResponseEntity.ok().build();
    // } else {
    // ErrorResponse errorResponse = new ErrorResponse();
    // errorResponse.setError("Invalid register request");
    // return ResponseEntity.badRequest().body(errorResponse);

    // }
    // }

}
