package com.example.demo.Repository;

import com.example.demo.Entities.IpStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IIPStatisticsRepository extends JpaRepository<IpStatistics, Long> {
    @Query(value = "SELECT i FROM ip_statistics i where i.distance = (SELECT MAX(distance) rev FROM ip_statistics)")
    List<IpStatistics> findMaxDistance();
    @Query(value = "SELECT i FROM ip_statistics i where i.distance = (SELECT MIN(distance) rev FROM ip_statistics)")
    List<IpStatistics> findMinDistance();
    @Query(value = "SELECT SUM(newTable.distance) / SUM(newTable.invocation) as average  from (select i.country_code, SUM(i.distance) * count(i.id) as distance, count(i.id) as invocation from ip_statistics i group by i.country_code) as newTable", nativeQuery = true)
    List<Double> findAverageDistance();
}
