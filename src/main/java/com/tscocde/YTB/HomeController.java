package com.tscocde.YTB;

import com.tscocde.YTB.Entity.Users;
import com.tscocde.YTB.Repository.UserRepository;
import com.tscocde.YTB.Service.SessionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    UserRepository userquery;
    @Autowired
    SessionService session;

    @RequestMapping("home")

    public String home() {
        return "Main-Layout";
    }

    @GetMapping("login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletResponse response) {
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
        return "redirect:/login";
    }

}
