package com.example.MultiChat.auth;

import com.example.MultiChat.user.User;
import com.example.MultiChat.user.UserRepository;
import com.example.MultiChat.uploads.CloudinaryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AuthViewController {

    @Autowired private UserRepository userRepo;
    @Autowired private CloudinaryService cloudinaryService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(HttpServletRequest request,
                          @RequestParam String username,
                          @RequestParam String password,
                          Model model) {

        User user = userRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            request.getSession().setAttribute("user", user);
            return "redirect:/chat";
        } else {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute AuthRequest authRequest) throws Exception {

        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(authRequest.getPassword());
        user.setEmail(authRequest.getEmail());

        userRepo.save(user);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
