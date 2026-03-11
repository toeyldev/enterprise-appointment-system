package edu.sjsu.cmpe172.starterdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String home() { return "home"; }

    @GetMapping("/class-schedule")
    public String showSchedulePage() { return "class-schedule"; }

    @GetMapping("/credits")
    public String credits() { return "credits"; }

    @GetMapping("/reservations")
    public String reservations() { return "reservations"; }
}