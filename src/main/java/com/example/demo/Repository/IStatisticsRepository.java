package com.example.demo.Repository;

import com.example.demo.Entities.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStatisticsRepository extends JpaRepository<Statistics, Long> {

}
