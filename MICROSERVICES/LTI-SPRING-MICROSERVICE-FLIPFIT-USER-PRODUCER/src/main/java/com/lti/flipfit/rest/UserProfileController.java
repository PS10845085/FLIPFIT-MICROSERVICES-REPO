package com.lti.flipfit.rest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")

public class UserProfileController {

	@GetMapping("/profile")
    public String hello() {
        return "Hello, secure world!";
    }

}
