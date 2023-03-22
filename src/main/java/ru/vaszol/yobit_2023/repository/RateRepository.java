package ru.vaszol.yobit_2023.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vaszol.yobit_2023.model.Rate;

public interface RateRepository extends CrudRepository<Rate, Long> {
}
