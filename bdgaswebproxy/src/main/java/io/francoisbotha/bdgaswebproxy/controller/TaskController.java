package io.francoisbotha.bdgaswebproxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping({"/admin/task"})
public class TaskController {

    @GetMapping
    public String ShowVuePage(Model model) {
        model.addAttribute("message", "A message from JAVA");
        return "/pages/admin/task";
    }

}