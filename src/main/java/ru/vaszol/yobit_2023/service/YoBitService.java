package ru.vaszol.yobit_2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaszol.yobit_2023.api.YoBitAPI;
import ru.vaszol.yobit_2023.model.Depth;
import ru.vaszol.yobit_2023.model.InfoPair;
import ru.vaszol.yobit_2023.repository.DepthPairRepository;
import ru.vaszol.yobit_2023.repository.InfoPairRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class YoBitService {

    @Autowired
    private YoBitAPI yoBitAPI;

    @Autowired
    private DepthPairRepository depthPairRepository;

    @Autowired
    private InfoPairRepository infoPairRepository;

    /**
     * запрос актуального спроса пары
     * сохранение спроса пары в БД
     *
     * @param pair - пара, например "usd_rur"
     * @return спрос
     */
    public Depth updateDepth(String pair) {
        Depth depth = yoBitAPI.getDepth(pair);
        depthPairRepository.save(depth);
        return depth;
    }

    /**
     * запрос актуальных пар (info)
     * обновление пар в БД
     *
     * @return количество пар
     */
    public int updateInfo() {
        List<InfoPair> info = yoBitAPI.getInfo();
        if (!info.isEmpty()) {
            infoPairRepository.saveAll(info);
        }
        return info.size();
    }

    public String getBot() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "bot started!";
    }
}
