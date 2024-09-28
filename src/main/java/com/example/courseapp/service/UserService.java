package com.example.courseapp.service;

import com.example.courseapp.dto.RegistrationRequest;
import com.example.courseapp.exception.InvalidOtpException;
import com.example.courseapp.exception.RegistrationException;
import com.example.courseapp.model.*;
import com.example.courseapp.repository.EnrollRepository;
import com.example.courseapp.repository.RoleRepository;
import com.example.courseapp.repository.UserRepository;
import com.example.courseapp.specification.UserSpecification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EnrollRepository enrollRepository;
    private final RoleRepository roleRepository;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);  // Generates a 6-digit OTP
        return String.valueOf(otp);
    }

    public String registerUser(RegistrationRequest request) throws MessagingException {
        // Check if the user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "User already registered with this email!";
        }
        if (request.getFirstname() == null || request.getFirstname().isEmpty()) {
            throw new RegistrationException("First name cannot be null or empty!");
        }
        if (request.getSchoolname() == null || request.getSchoolname().isEmpty()) {
            throw new RegistrationException("School name cannot be null or empty!");
        }
        if (request.getMobile() == null || request.getMobile().isEmpty()) {
            throw new RegistrationException("Mobile number cannot be null or empty!");
        }
        if (request.getRollno() == null || request.getRollno().isEmpty()) {
            throw new RegistrationException("Roll number cannot be null or empty!");
        }
        if (request.getLastname() == null || request.getLastname().isEmpty()) {
            throw new RegistrationException("Last name cannot be null or empty!");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new RegistrationException("Email cannot be null or empty!");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new RegistrationException("Password cannot be null or empty!");
        }
        if (request.getConfirmPassword() == null || request.getConfirmPassword().isEmpty()) {
            throw new RegistrationException("Confirm Password cannot be null or empty!");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RegistrationException("Password and Confirm Password do not match!");
        }


        Optional<Role> roleOpt = roleRepository.findByName(request.getRole().toUpperCase());
        if (roleOpt.isEmpty()){
            return "Role not found";
        }


        String otp = generateOtp();
        String encodedPassword = passwordEncoder.encode(request.getPassword());


        // Create a new user first
        User user = new User(
                null,
                request.getEmail(),
                encodedPassword,
                otp,
                false,
                request.getRollno(),
                request.getFirstname(),
                request.getLastname(),
                request.getSchoolname(),
                request.getMobile(),
                new HashSet<>() // Initialize with an empty set for addresses
        );
        user.getRoles().add(roleOpt.get());
        userRepository.save(user);


        userRepository.save(user);

        // Now add addresses, setting the user for each address
        Set<Address> addresses = request.getAddresses();
        if (addresses != null) {
            for (Address address : addresses) {
                address.setUser(user); // Link the address to the user
            }
            user.setAddresses(addresses); // Set the addresses in the user object
        }

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
            user.setIsVerified(true);
            userRepository.save(user);  // Mark the user as verified
            return "OTP verified successfully!";
        } else {
            throw new InvalidOtpException("Invalid OTP!");  // Throw custom exception for invalid OTP
        }
    }
    throw new InvalidOtpException("User not found!");  // Throw custom exception for user not found
}


    public String loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return "User not found!";
        }

        User user = userOptional.get();

        // Check if the user is verified
        if (!user.getIsVerified()) {
            return "User has not verified their email!";
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid password!";
        }

        return "Login successful!";
    }

    public List<User> filterUsers(String email, String firstName, String lastName) {
        Specification<User> spec = Specification.where(null);
        if (email != null && !email.isEmpty()) {
            spec = spec.and(UserSpecification.hasEmail(email));
        }
        if (firstName != null && !firstName.isEmpty()) {
            spec = spec.and(UserSpecification.hasFirstName(firstName));
        }
        if (lastName != null && !lastName.isEmpty()) {
            spec = spec.and(UserSpecification.hasLastName(lastName));
        }
        return userRepository.findAll(spec);
    }

    public List<Course> getCoursesForUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null){
            throw new IllegalArgumentException("User not found");
        }

        List<UserCourses> courses = enrollRepository.findByUser(user);
        return courses.stream().map(UserCourses::getCourse)
                .collect(Collectors.toList());
    }


}

