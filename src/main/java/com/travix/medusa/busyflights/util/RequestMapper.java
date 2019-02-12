package com.travix.medusa.busyflights.util;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;

public class RequestMapper {


    public static CrazyAirRequest toCrazyAirRequest(BusyFlightsRequest request) {
        CrazyAirRequest crazyAirRequest = new CrazyAirRequest();
        crazyAirRequest.setOrigin(request.getOrigin());
        crazyAirRequest.setDestination(request.getDestination());
        crazyAirRequest.setDepartureDate(request.getDepartureDate());
        crazyAirRequest.setReturnDate(request.getReturnDate());
        crazyAirRequest.setPassengerCount(request.getNumberOfPassengers());
        return crazyAirRequest;
    }


    public static ToughJetRequest toToughJetRequest(BusyFlightsRequest request) {
        ToughJetRequest toughJetRequest = new ToughJetRequest();
        toughJetRequest.setFrom(request.getOrigin());
        toughJetRequest.setTo(request.getDestination());
        toughJetRequest.setInboundDate(request.getDepartureDate());
        toughJetRequest.setOutboundDate(request.getReturnDate());
        toughJetRequest.setNumberOfAdults(request.getNumberOfPassengers());
        return toughJetRequest;
    }
}
