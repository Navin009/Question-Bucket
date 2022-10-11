package com.nrgroup.bucket.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nrgroup.bucket.config.SecurityRule;
import com.nrgroup.bucket.config.ServerCookie;
import com.nrgroup.bucket.entity.User;
import com.nrgroup.bucket.enums.Status;
import com.nrgroup.bucket.exception.ServerMessage;
import com.nrgroup.bucket.security.BCryptEncoder;
import com.nrgroup.bucket.user.model.request.LoginRequest;
import com.nrgroup.bucket.user.service.UserService;
import com.nrgroup.bucket.utils.JwtUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // @Autowired
    // private OTPService otpService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private BCryptEncoder bCryptEncoder;

    @Autowired
    private ServerCookie serverCookie;

    @PreAuthorize(SecurityRule.PERMIT_ALL)
    @PostMapping("/v1/password/update")
    public ResponseEntity<?> updateUserPassword(@ModelAttribute("password") String password) {
        // UriComponentsBuilder uriComponentsBuilder =
        // UriComponentsBuilder.fromPath("/login");
        // HttpHeaders header = new HttpHeaders();
        // try {
        // String isUserVarified = session.getAttribute("user").toString();
        // if (isUserVarified.equals("verified")) {
        // boolean isPasswordUpdated =
        // userService.updatePassword(session.getAttribute("email").toString(),
        // password);
        // if (isPasswordUpdated) {
        // uriComponentsBuilder.queryParam("error", "Password Updated Successfully!");
        // header.add(HttpHeaders.LOCATION, uriComponentsBuilder.build().toUriString());
        // } else {
        // uriComponentsBuilder.queryParam("error", "Error in Updating password");
        // header.add(HttpHeaders.LOCATION, uriComponentsBuilder.build().toUriString());
        // }
        // return ResponseEntity.status(HttpStatus.FOUND)
        // .headers(header)
        // .build();
        // }
        // } catch (Exception e) {
        // log.error("Exception in UserController updateUserPassword", e);
        // }
        // uriComponentsBuilder.queryParam("error", "Request is not Valid!");
        // header.add(HttpHeaders.LOCATION, uriComponentsBuilder.build().toUriString());
        // return ResponseEntity.status(HttpStatus.FOUND)
        // .headers(header)
        // .build();
        return null;
    }

    @PostMapping("/login")
    @PreAuthorize(SecurityRule.ANONYMOUS)
    public Mono<ResponseEntity<ServerMessage>> login(@RequestBody LoginRequest request) {
        return Mono.fromCallable(() -> {
            ServerMessage message;
            log.info("Login function log");
            if (request.getEmail() == null) {
                message = new ServerMessage("Email is not empty!", Status.FIELD_REQUIRED);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
            if (request.getPassword() == null) {
                message = new ServerMessage("Password is not empty!", Status.FIELD_REQUIRED);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
            User user = userService.findUser(request.getEmail());
            if (user != null) {
                String encodedPassword = bCryptEncoder.encode(request.getPassword());
                Boolean isMatched = bCryptEncoder.matches(request.getPassword(), encodedPassword);
                if (isMatched) {
                    String token = jwtUtils.generateToken(user);
                    ResponseCookie cookie = ResponseCookie.from(serverCookie.getName(), token)
                            .secure(serverCookie.getSecure())
                            .httpOnly(serverCookie.getHttpOnly())
                            .sameSite(serverCookie.getSameSite())
                            .path(serverCookie.getPath())
                            .build();

                    message = new ServerMessage("Login Success", Status.SUCCESS);
                    return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(message);
                } else {
                    message = new ServerMessage("Invalid Credentials!", Status.UNAUTHENTICATED);
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
                }
            }
            message = new ServerMessage("User not Found!", Status.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }).subscribeOn(Schedulers.parallel());
    }

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
