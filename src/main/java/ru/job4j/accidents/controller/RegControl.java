package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.AuthorityService;
import ru.job4j.accidents.service.UserService;

@Controller
@AllArgsConstructor
public class RegControl {
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final AuthorityService authorityService;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorityService.findByAuthority("ROLE_USER"));
        var optionalUser = userService.save(user);
        if (optionalUser.isEmpty()) {
            model.addAttribute("errorMessage", "Username exists. Please choose another name");
            return "reg";
        }
        return "redirect:/users/login";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}
