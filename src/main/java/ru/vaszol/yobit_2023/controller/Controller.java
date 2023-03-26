package ru.vaszol.yobit_2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vaszol.yobit_2023.api.YoBitAPI;
import ru.vaszol.yobit_2023.model.Depth;
import ru.vaszol.yobit_2023.service.YoBitService;

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

    @RequestMapping(value = "getInfo", method = RequestMethod.GET)
    public String getInfo() {
        return "pair size = " + yoBitAPI.getInfo().size();
    }

    @RequestMapping(value = "updateInfo", method = RequestMethod.GET)
    public String updateInfo() {
        return "pair size = " + yoBitService.updateInfo();
    }

    @RequestMapping(value = "getDepth", method = RequestMethod.GET)
    public Depth getDepth(@RequestParam String pair) {
        return yoBitAPI.getDepth(pair);
    }

    @RequestMapping(value = "updateDepth", method = RequestMethod.GET)
    public Depth updateDepth(@RequestParam String pair) {
        return yoBitService.updateDepth(pair);
    }
}
