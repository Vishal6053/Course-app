package com.example.courseapp.service;

import com.example.courseapp.dto.RegistrationRequest;
import com.example.courseapp.model.User;
import com.example.courseapp.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);  // Generates a 6-digit OTP
        return String.valueOf(otp);
    }

    public String registerUser(RegistrationRequest request) throws MessagingException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "User already registered with this email!";
        }

        String otp = generateOtp();
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(null,
                request.getFirstname(),
                request.getLastname(),
                request.getSchoolname(),
                request.getEmail(),
                request.getMobile(),
                encodedPassword,
                otp,
                false);
        userRepository.save(user);


        sendOtpEmail(request.getEmail(), otp);

        return "Registration successful! Check your email for the OTP.";
    }

    private void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText("<h1>Your OTP is: " + otp + "</h1>", true);
        helper.setTo(email);
        helper.setSubject("OTP for CourseApp Registration");
        helper.setFrom("vishal6053@gmail.com");
        mailSender.send(mimeMessage);
    }
    public String verifyOtp(String email, String otp) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getOtp().equals(otp)) {
                user.setVerified(true);
                userRepository.save(user);
                return "OTP verified successfully!";
            } else {
                return "Invalid OTP!";
            }
        }
        return "User not found!";
    }

    public String loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return "User not found!";
        }

        User user = userOptional.get();

        if (!user.getIsVerified()) {
            return "User has not verified their email!";
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid password!";
        }

        return "Login successful!";
    }
}
