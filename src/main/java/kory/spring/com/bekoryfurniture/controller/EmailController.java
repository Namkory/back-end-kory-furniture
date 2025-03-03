package kory.spring.com.bekoryfurniture.controller;

import kory.spring.com.bekoryfurniture.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/send-email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping()
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text
    ) {
        emailService.sendSimpleEmail(to, subject, text);
        return "Email sent!";
    }
}
