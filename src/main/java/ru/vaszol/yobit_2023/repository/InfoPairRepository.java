package ru.vaszol.yobit_2023.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vaszol.yobit_2023.model.InfoPair;

public interface InfoPairRepository extends CrudRepository<InfoPair, String> {
}
