package com.budget.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController implements ErrorController {
    
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();

        if (status == 404) {
            return "errors/404";

        } else if (status == 401){
            return "errors/unauthorized";
            
        } else {
            return "errors/error";
        }
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
