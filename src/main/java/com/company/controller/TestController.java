package com.company.controller;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/testtt")
    public void test(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/message");
        System.out.println(messageSource.getMessage("email.exist",null,new Locale("en")));
        System.out.println(messageSource.getMessage("email.exist",null,new Locale("ru")));
        System.out.println(messageSource.getMessage("email.exist",null,new Locale("uz")));
    }

}
