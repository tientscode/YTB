package com.tscocde.YTB.Controller;

import com.tscocde.YTB.Entity.Users;
import com.tscocde.YTB.Entity.Videos;
import com.tscocde.YTB.Repository.UserRepository;
import com.tscocde.YTB.Repository.VideoRepository;
import com.tscocde.YTB.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    SessionService session;
    @Autowired
    VideoRepository videoquery;

    @RequestMapping("home")
    public String home(Model model) {
        List<Videos> video = videoquery.findAll();
        session.set("listvideo", video);

        return "componnent/video-homepage";
    }



    @GetMapping("/send/mail")
    public String send_mail(Model model) {
        return "auth/Sendmail_Otp";
    }
//    @PostMapping("/sendmail")
//    public String demo(Model model, @RequestParam("email") String to) throws MessagingException {
//        try {
//            session.set("email", to);
//            Random random = new Random();
//            int randomNumber = 1000 + random.nextInt(9000); // Generates a random number between 1000 and 9999
//            String body = "Your verification code is: " + randomNumber;
//            mailer.queue(to,"Quên Mật Khẩu", body);
//            System.out.println("thành công");
//            session.set("otp", randomNumber);
//        } catch (Exception e) {
//            model.addAttribute("tb", "Email not find");
//        }
//        return "auth/Sendmail_Otp";
//    }
//
//    @PostMapping("/sendotp")
//    public String handleOTP(@RequestParam("otp1") String otp1,
//                            @RequestParam("otp2") String otp2,
//                            @RequestParam("otp3") String otp3,
//                            @RequestParam("otp4") String otp4,
//                            RedirectAttributes redirectAttributes) {
//        String otpString = otp1 + otp2 + otp3 + otp4;
//
//        try {
//            int otp = Integer.parseInt(otpString);
//            int storedOtp = session.get("otp");
//            if (otp == storedOtp) {
//                String newPassword = RdPassNewUser.generateRandomPassword(8);
//                String email = session.get("email"); // Assuming email is stored in session
//                mailer.queue(email, "New Password", "Your new password is: " + newPassword);
//                redirectAttributes.addFlashAttribute("message", "OTP is correct!");
//                return "redirect:/login";
//            } else {
//                redirectAttributes.addFlashAttribute("message", "Invalid OTP!");
//                return "redirect:/send/mail";
//            }
//        } catch (NumberFormatException e) {
//            redirectAttributes.addFlashAttribute("message", "Invalid OTP format!");
//            return "redirect:/send/mail";
//        }
//    }



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





}
