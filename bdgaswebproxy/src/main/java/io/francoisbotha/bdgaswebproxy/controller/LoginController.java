package io.francoisbotha.bdgaswebproxy.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping({"/login"})
public class LoginController {

    @GetMapping
    //@PostMapping
    public String ShowVuePage(Model model) {
        model.addAttribute("message", "A message from JAVA");
        return "index";
    }


}
