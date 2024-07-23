package com.tscocde.YTB;

import com.tscocde.YTB.Entity.Users;
import com.tscocde.YTB.Entity.Videos;
import com.tscocde.YTB.Repository.UserRepository;
import com.tscocde.YTB.Repository.VideoRepository;
import com.tscocde.YTB.Service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class HomeController {
    @Autowired
    UserRepository userquery;
    @Autowired
    SessionService session;
    @Autowired
    VideoRepository videoquery;

    @RequestMapping("home")
    public String home(Model model) {
        List<Videos> video = videoquery.findAll();
        model.addAttribute("listvideo", video);

        return "Main-Layout";
    }

    @GetMapping("/login")
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
        return "redirect:/home";
    }

    @GetMapping("/create-video")
    public String createVideo(Model model) {
        Videos video = new Videos();
        model.addAttribute("video", video);
        return "componnent/create-video";
    }

    @PostMapping("/create-save")
    public String submitVideo(@ModelAttribute Videos video, Model model) {
        if (video.getCreatedAt() == null) {
            video.setCreatedAt(LocalDateTime.now());
        }
        video.setActive(true);
        Users loggedInUser = session.get("user");
        video.setUser(loggedInUser);
        videoquery.save(video);
        model.addAttribute("report", "Create video success");
        return "redirect:/home";
    }

    @GetMapping("/watch")
    // required là dùng để cho phép giá trị đường dẫn là v được phép null không false là có true là không
    public String watch(@RequestParam(value = "v", required = false) String videoId, Model model) {
        if (videoId == null) {
            return "redirect:/home"; // xử lý nếu video = v = null
        }
        System.out.println("v = " + videoId);
        model.addAttribute("videoId", videoId);
        return "Main-Layout";
    }


}
