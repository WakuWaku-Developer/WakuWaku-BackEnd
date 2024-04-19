package dev.backend.wakuwaku.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 기본페이지 요청
    @GetMapping("/")
    public String index(){
        return "index"; // => index.~
    }

}
