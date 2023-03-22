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

    @RequestMapping(value = "getRate", method = RequestMethod.GET)
    public Rate getRate(@RequestParam String pair) {
        try {
            Rate rate = yoBitAPI.getRate(pair);
            return rate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "updatewRate", method = RequestMethod.GET)
    public Rate updatewRate(@RequestParam String pair) {
        return yoBitService.updatewRate(pair);
    }
}
