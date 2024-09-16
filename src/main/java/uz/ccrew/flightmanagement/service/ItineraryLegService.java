package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.entity.ItineraryReservation;

public interface ItineraryLegService {
    void addItineraryLegs(ItineraryReservation reservation, Long... flightNumber);
}
