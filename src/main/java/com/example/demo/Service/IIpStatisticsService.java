package com.example.demo.Service;

import com.example.demo.Entities.IpStatistics;

import java.util.List;

public interface IIpStatisticsService {

    public List<IpStatistics> findMaxDistance();
    public List<IpStatistics> findMinDistance();
    public List<Double> findAverageDistance();
}
