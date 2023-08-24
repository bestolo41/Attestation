package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Filter {
//    private static List<Flight> filteredFlightsByDepartureTime;
//    private static List<Flight> flightsHasSegmentWithArrivalAfterDeparture;
//    private static List<Flight> filteredFlightsByTransferTime;
    private static Map<String, List<Flight>> filteredFlightLists = new HashMap<>();

    public static void filteringByDepartureTime(List<Flight> flights, LocalDateTime time) {
        List<Flight> filteredFlightsByDepartureTime = flights.stream()
                .filter(flight -> flight.getSegments().get(0).getDepartureDate().isAfter(time))
                .collect(Collectors.toList());
        filteredFlightLists.put("filterByDepartureTime", filteredFlightsByDepartureTime);
    }

    public static void filteringFlightsHasSegmentsWithArrivalBeforeDeparture(List<Flight> flights) {
        List<Flight> flightsHasSegmentWithArrivalAfterDeparture = flights.stream()
                .filter(Filter::checkSegmentsWithArrivalBeforeDeparture)
                .collect(Collectors.toList());
        filteredFlightLists.put("flightsHasSegmentWithArrivalAfterDeparture", flightsHasSegmentWithArrivalAfterDeparture);
    }

    public static void filteringByTransferTime(List<Flight> flights, long hours) {
        List<Flight> filteredFlightsByTransferTime = flights.stream()
                .filter(flight -> checkTransferTimeMoreThan(flight, hours))
                .collect(Collectors.toList());
        filteredFlightLists.put("filterByTransferTime", filteredFlightsByTransferTime);
    }

    public static Set<Flight> getFilteredFlights() {
        Set<Flight> filteredFlights = new HashSet<>();
        if (!filteredFlightLists.values().isEmpty()) {
            Iterator<List<Flight>> iterator = filteredFlightLists.values().iterator();
            filteredFlights.addAll(iterator.next());
            while (iterator.hasNext()) {
                filteredFlights.retainAll(iterator.next());
            }
        }
        return filteredFlights;
    }

    private static boolean checkSegmentsWithArrivalBeforeDeparture(Flight flight) {
        for (Segment segment : flight.getSegments()) {
            if (segment.getDepartureDate().isAfter(segment.getArrivalDate())) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkTransferTimeMoreThan(Flight flight, long hours) {
        for (int i = 0; i < flight.getSegments().size()-1; i++) {
            if (flight.getSegments().size() == 1) return false;

            long transferTime = ChronoUnit.HOURS.between(flight.getSegments().get(i).getArrivalDate(), flight.getSegments().get(i + 1).getDepartureDate());
            if (transferTime > hours) {
                return false;
            }
        }
        return true;
    }
}
