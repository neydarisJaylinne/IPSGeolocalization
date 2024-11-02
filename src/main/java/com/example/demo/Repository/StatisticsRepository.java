package com.example.demo.Repository;

import com.example.demo.Entities.IpStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticsRepository implements IStatisticsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<IpStatistics> findMaxDistance() {
        String SQLQuery = "SELECT a.distance, a.country_code\n" +
                "FROM ip_statistics a where distance = (SELECT MAX(distance) rev FROM ip_statistics b)";
        return jdbcTemplate.query(SQLQuery, BeanPropertyRowMapper.newInstance(IpStatistics.class));
    }

    @Override
    public List<IpStatistics> findMinDistance() {
        String SQLQuery = "SELECT a.distance, a.country_code\n" +
                "FROM ip_statistics a where distance = (SELECT MIN(distance) rev FROM ip_statistics b)";
        return jdbcTemplate.query(SQLQuery, BeanPropertyRowMapper.newInstance(IpStatistics.class));
    }

    //average
    @Override
    public List<Double> findAverageDistance() {
        String SQLQuery = "SELECT SUM(distance) / SUM(invocation) as average from " +
                "(select country_code as country, SUM(distance) * count(id) as distance, " +
                "count(id) as invocation from ip_statistics group by country_code) as newTable";
        return jdbcTemplate.query(SQLQuery, BeanPropertyRowMapper.newInstance(Double.class));
    }
}
