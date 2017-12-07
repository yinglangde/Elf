package com.tools.elf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @create 2017-11-13 11:11
 **/
@Controller
public class TestController {
    @RequestMapping(value = "/hello" ,method = RequestMethod.GET)
    @ResponseBody
    public Object Hello(){
        return "Hello Spring Boot !!!";
    }
}
