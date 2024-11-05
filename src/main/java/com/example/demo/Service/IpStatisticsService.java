package com.example.demo.Service;

import com.example.demo.Entities.IpStatistics;
import com.example.demo.Repository.IIPStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpStatisticsService implements IIpStatisticsService{

    @Autowired
    private IIPStatisticsRepository statisticsRepository;

    @Override
    public List<IpStatistics> findMaxDistance() {
        return statisticsRepository.findMaxDistance();
    }

    @Override
    public List<IpStatistics> findMinDistance() {
        return statisticsRepository.findMinDistance();
    }

    @Override
    public List<Double> findAverageDistance() {
        return statisticsRepository.findAverageDistance();
    }
}
