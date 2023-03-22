package ru.vaszol.yobit_2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaszol.yobit_2023.api.YoBitAPI;
import ru.vaszol.yobit_2023.model.Rate;
import ru.vaszol.yobit_2023.repository.RateRepository;

import java.io.IOException;

@Service
public class YoBitService {

    @Autowired
    private YoBitAPI yoBitAPI;

    @Autowired
    private RateRepository rateRepository;

    /**
     * запрос актуального спроса пары
     * созранение спроса пары в БД
     * @param pair - пара, например "usd_rur"
     * @return спрос
     */
    public Rate updatewRate(String pair) {
        try {
            Rate rate = yoBitAPI.getRate(pair);
            rateRepository.save(rate);
            return rate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
