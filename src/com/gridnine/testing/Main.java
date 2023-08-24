package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println(flights);        //Вывод изначального набора

        LocalDateTime dateTime = LocalDateTime.now();  // Дата и время, перелеты раньше которых исключаются
        long hours = 2; // Перелеты с пересадками более этого количества часов исключаются


        Filter.filteringByDepartureTime(flights, dateTime); // Исключаем перелеты, вылет которых до текущего момента времени
        System.out.println(Filter.getFilteredFlights()); // Вывод набора после первого фильтра

        Filter.filteringByTransferTime(flights, hours); // Исключаем перелеты с временем пересадок более 2х часов
        System.out.println(Filter.getFilteredFlights()); //Вывод набора после двух фильтров

        Filter.filteringFlightsHasSegmentsWithArrivalBeforeDeparture(flights); // Исключаем перелета, у которых имеются сегменты с датой прилёта раньше даты вылета
        System.out.println(Filter.getFilteredFlights()); // Вывод набора после трех фильтров

        Filter.filteringByTransferTime(flights, 5); // Изменяем второй фильтр
        System.out.println(Filter.getFilteredFlights()); // Вывод набора после изменения второго фильтра
    }
}