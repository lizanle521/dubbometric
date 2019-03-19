package com.lzl.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lizanle
 * @data 2019/3/18 9:15 PM
 */
@RestController
public class TpsController {

    @RequestMapping("/tps")
    @ResponseBody
    public String tps(){
        return "tps";
    }
}
