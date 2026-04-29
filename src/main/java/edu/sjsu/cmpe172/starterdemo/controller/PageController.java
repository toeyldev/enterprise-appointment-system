package edu.sjsu.cmpe172.starterdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/instructor")
    public String instructor() {
        return "instructor";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/class-schedule")
    public String showSchedulePage() {
        return "class-schedule";
    }

    @GetMapping("/reservations")
    public String reservations() {
        return "reservations";
    }

    @GetMapping("/credit-package")
    public String creditPackage() {
        return "credit-package";
    }

    @GetMapping("/cancel-reservation")
    public String cancelReservation() {
        return "cancel-reservation";
    }

    @GetMapping("/credit-history")
    public String creditHistory() {
        return "credit-history";
    }
}