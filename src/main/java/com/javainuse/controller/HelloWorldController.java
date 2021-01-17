package com.javainuse.controller;

import com.javainuse.response.HwResponse;
import com.javainuse.service.HwService;
import exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    private final HwService hwService;

    @Autowired
    public HelloWorldController(HwService hwService) {
        this.hwService = hwService;
    }

    @RequestMapping({"/hello"})
    public HwResponse firstPage(@RequestParam String messageType) throws ValidationException {
        return hwService.getMessage(messageType);
    }

}