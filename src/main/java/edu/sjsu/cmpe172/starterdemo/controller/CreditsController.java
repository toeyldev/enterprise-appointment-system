package edu.sjsu.cmpe172.starterdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreditsController {
    @GetMapping("/credits")
    public String credits() { return "credits"; }
}