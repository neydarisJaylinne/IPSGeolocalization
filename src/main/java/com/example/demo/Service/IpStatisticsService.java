package com.example.demo.Service;

import com.example.demo.Entities.IpStatistics;
import com.example.demo.Repository.IStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpStatisticsService implements IIpStatisticsService{

    @Autowired
    private IStatisticsRepository statisticsRepository;

    @Override
    public List<IpStatistics> findMaxDistance() {
        List<IpStatistics> list;
        try {
            list = statisticsRepository.findMaxDistance();
        } catch (Exception e) {throw e;}
        return list;
    }

    @Override
    public List<IpStatistics> findMinDistance() {
        List<IpStatistics> list;
        try {
            list = statisticsRepository.findMinDistance();
        } catch (Exception e) {throw e;}
        return list;
    }

    @Override
    public List<Double> findAverageDistance() {
        List<Double> list;
        list=statisticsRepository.findAverageDistance();
        return list;
    }
}
