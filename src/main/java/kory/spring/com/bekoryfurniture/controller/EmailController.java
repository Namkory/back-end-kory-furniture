package kory.spring.com.bekoryfurniture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kory.spring.com.bekoryfurniture.service.impl.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/send-email")
@Tag(name = "Email Controller")
@Slf4j
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Operation(summary = "Add email", description = "API create new email")
    @PostMapping()
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text
    ) {
        log.info("Request add email, {} {} {}",
                to, subject, text);
        emailService.sendSimpleEmail(to, subject, text);
        return "Email sent!";
    }
}
