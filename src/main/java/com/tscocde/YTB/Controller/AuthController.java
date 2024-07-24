package com.tscocde.YTB.Controller;

import com.tscocde.YTB.Entity.Users;
import com.tscocde.YTB.Repository.UserRepository;
import com.tscocde.YTB.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AuthController {
    @Autowired
    UserRepository userquery;
    @Autowired
    SessionService session;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "auth/register";
    }

    @PostMapping("/register/save")
    public String register_save(@ModelAttribute Users user, RedirectAttributes redirectAttributes) {
        user.setActive(true);
        if (user.getImage() == null) {
            user.setImage("user.png");
        }
        redirectAttributes.addFlashAttribute("error", "Đăng ký Thành Công");
        userquery.save(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // Kiểm tra username và password trong cơ sở dữ liệu
        Users user = userquery.findByUsername(username);
        System.out.println("là gì đây" + user);
        if (user != null && user.getPassword().equals(password)) {
            if (user.getActive()) {
                session.set("user", user);
                return "redirect:/home";
            } else {
                model.addAttribute("error", "My account is locked !");
            }
        } else {
            model.addAttribute("error", "Invalid username or password!");
        }
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout() {
        session.remove("user");
        return "redirect:/home";
    }
}
