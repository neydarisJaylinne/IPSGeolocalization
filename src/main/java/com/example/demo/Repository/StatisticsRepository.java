package com.example.demo.Repository;

import com.example.demo.Entities.Statistics;

import java.util.ArrayList;

public class StatisticsRepository {
    static ArrayList<Statistics> nombreLista = new ArrayList<>();
    public static void save(Statistics statistics){
        nombreLista.add(statistics);
    }
}
