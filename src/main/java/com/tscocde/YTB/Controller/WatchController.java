package com.tscocde.YTB.Controller;

import com.tscocde.YTB.Entity.Videos;
import com.tscocde.YTB.Repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WatchController {

    @Autowired
    VideoRepository videoRepository;

    @GetMapping("/watch")
    // required là dùng để cho phép giá trị đường dẫn là v được phép null không false là có true là không
    public String watch(@RequestParam(value = "v", required = false) String videoId, Model model) {
        if (videoId == null) {
            return "redirect:/home";
        }
        Videos videos = videoRepository.findByurl(videoId);
        model.addAttribute("CurrenVideo", videos);
        return "View-video-layout";
    }
}
