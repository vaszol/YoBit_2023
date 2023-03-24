package ru.vaszol.yobit_2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vaszol.yobit_2023.api.YoBitAPI;
import ru.vaszol.yobit_2023.model.Rate;
import ru.vaszol.yobit_2023.service.YoBitService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private YoBitAPI yoBitAPI;

    @Autowired
    private YoBitService yoBitService;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String getHello() {
        return "hello test!!";
    }

    @RequestMapping(value = "getDepth", method = RequestMethod.GET)
    public Rate getDepth(@RequestParam String pair) {
        try {
            Rate rate = yoBitAPI.getDepth(pair);
            return rate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "updateDepth", method = RequestMethod.GET)
    public Rate updateDepth(@RequestParam String pair) {
        return yoBitService.updateDepth(pair);
    }

    @RequestMapping(value = "getInfo", method = RequestMethod.GET)
    public String getInfo(@RequestParam String pair) {
        //todo getInfo
//        return yoBitService.getInfo(pair);
        return "";
    }

    @RequestMapping(value = "updateInfo", method = RequestMethod.GET)
    public String updateInfo(@RequestParam String pair) {
        //todo updateInfo
//        return yoBitService.updateInfo(pair);
        return "";
    }
}
