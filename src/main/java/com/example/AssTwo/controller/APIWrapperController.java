package com.example.AssTwo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class APIWrapperController {
    private final Logger log = LoggerFactory.getLogger(APIWrapperController.class);

    @Autowired
    @Qualifier(value = "restTemplate")
    RestTemplate restTemplate;
}
