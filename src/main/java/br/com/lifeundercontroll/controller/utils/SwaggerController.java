package br.com.lifeundercontroll.controller.utils;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {
	
    @RequestMapping("/docs")
    @PreAuthorize("permitAll()")
    public String home() {
        return "redirect:/swagger-ui.html";
    }
    
}