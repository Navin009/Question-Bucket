package com.nrgroup.bucket.user.controller;

import com.nrgroup.bucket.config.SecurityRule;
import com.nrgroup.bucket.email.service.EmailService;
import com.nrgroup.bucket.exception.ViewException;
import com.nrgroup.bucket.otp.service.OTPService;
import com.nrgroup.bucket.security.SecurityUtils;
import com.nrgroup.bucket.user.model.request.ForgotRequest;
import com.nrgroup.bucket.user.model.request.RegisterRequest;
import com.nrgroup.bucket.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@PreAuthorize(SecurityRule.IS_ANONYMOUS)
public class UserViewController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Value("${security.csrf.unique-val}")
    private String csrfUniqueVal;

    @PreAuthorize(SecurityRule.PERMIT_ALL)
    @GetMapping("/login")
    public String login() {
        if (SecurityUtils.getUserNo() != null) {
            return "redirect:/";
        } else {
            return "login";
        }
    }

    @GetMapping("/register")
    public String register(Model model, HttpSession session) {
        String csrfToken = SecurityUtils.generateMD5Hash(csrfUniqueVal + System.currentTimeMillis());
        model.addAttribute("csrf", csrfToken);
        session.setAttribute("csrf", csrfToken);
        return "register";
    }

    @GetMapping("forgot/userdata")
    public String forgotUserData(Model model, HttpSession session) {
        String csrfToken = SecurityUtils.generateMD5Hash(csrfUniqueVal + System.currentTimeMillis());
        model.addAttribute("csrf", csrfToken);
        session.setAttribute("csrf", csrfToken);
        return "forgot/userdata";
    }

    @GetMapping("/forgot/otp")
    public String forgotOtp() {
        return "forgot/otp";
    }

    @GetMapping("/forgot/newpassword")
    public String forgotResetPassword() {
        return "forgot/newpassword";
    }

    @GetMapping("/otp")
    public String otp() {
        return "otp";
    }

    @PostMapping("/otp/verify")
    public String otpVerify(@ModelAttribute("otp") String requestOtp, HttpSession session) {
        String otp = session.getAttribute("otp").toString();
        RegisterRequest registerRequest = (RegisterRequest) session.getAttribute("registerRequest");
        if (otp.equals(requestOtp)) {
            String userNo = userService.registerUser(registerRequest);
            if (userNo != null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(registerRequest.getEmail(),
                        registerRequest.getPassword());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return "redirect:/";
            } else {
                return "redirect:/register?error=Error while registering user";
            }
        } else {
            return "redirect:/otp?error=Invalid OTP";
        }
    }

    @PostMapping("/forgot/userdata")
    public String getForgotUserData(@Validated ForgotRequest request, HttpSession session) {
        if (!session.getAttribute("csrf").toString().equals(request.getCsrf()))
            throw new ViewException("Invalid Forgot User Data Request!");
        session.setAttribute("email", request.getEmail());
        Boolean isEmailExist = userService.isEmailExist(request.getEmail());
        if (isEmailExist) {
            String otp = otpService.generateOTP(request.getEmail());
            emailService.sendForgotOTPEmail(request, otp);
            session.setAttribute("otp", otp);
            session.setMaxInactiveInterval(60 * 5);
            return "redirect:/forgot/otp";
        } else {
            return "redirect:/forgot/userdata?error=Email not found";
        }
    }

    @PostMapping("/register")
    public String register(@Validated RegisterRequest registerRequest, HttpSession session) {
        if (!session.getAttribute("csrf").toString().equals(registerRequest.getCsrf()))
            throw new ViewException("Invalid Register User Request!");
        String otp = otpService.generateOTP(registerRequest.getEmail());
        emailService.sendRegisterOTPEmail(registerRequest, otp);
        session.setAttribute("otp", otp);
        session.setAttribute("registerRequest", registerRequest);
        session.setMaxInactiveInterval(60 * 5);
        return "redirect:/otp";
    }

    @PostMapping("/forgot/otp")
    public String getForgotOtp(@ModelAttribute("otp") String otp, HttpSession session) {
        if (otp.equals(session.getAttribute("otp"))) {
            session.setAttribute("user", "verified");
            return "redirect:/forgot/newpassword";
        } else {
            return "redirect:/forgot/otp?error=" + "OTP is not valid";
        }
    }

}
