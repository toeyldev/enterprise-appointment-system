package edu.sjsu.cmpe172.starterdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // default login page
    @GetMapping("/")
    public String login() {
        return "login";
    }

    // customer dashboard
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // instructor dashboard
    @GetMapping("/instructor")
    public String instructor() {
        return "instructor";
    }

    // admin dashboard
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    // page for viewing available classes
    @GetMapping("/class-schedule")
    public String showSchedulePage() {
        return "class-schedule";
    }

    // page showing customer reservations
    @GetMapping("/reservations")
    public String reservations() {
        return "reservations";
    }

    // page for viewing and buying credit packages
    @GetMapping("/credit-package")
    public String creditPackage() {
        return "credit-package";
    }

    // cancel reservation page
    @GetMapping("/cancel-reservation")
    public String cancelReservation() {
        return "cancel-reservation";
    }

    // customer credit purchase history page
    @GetMapping("/credit-history")
    public String creditHistory() {
        return "credit-history";
    }

    // confirmation page after class reservation
    @GetMapping("/confirm-reservation")
    public String confirmReservation() {
        return "confirm-reservation.html";
    }
}