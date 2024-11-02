package com.example.demo.Repository;

import com.example.demo.Entities.IpStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IStatisticsRepository extends JpaRepository<IpStatistics, Long> {
    public List<IpStatistics> findMaxDistance();
    public List<IpStatistics> findMinDistance();
    public List<Double> findAverageDistance();
}
