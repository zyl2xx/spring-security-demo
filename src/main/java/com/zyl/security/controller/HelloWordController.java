package com.zyl.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security/hello")
public class HelloWordController {

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('test')")
    public String hello(){
        return "Hello World";
    }
}
