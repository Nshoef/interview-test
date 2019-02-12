package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.util.AirportCode;
import com.travix.medusa.busyflights.engine.FlightEngine;
import com.travix.medusa.busyflights.util.ValidationException;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class ApiController {
    private static final Logger LOGGER = Logger.getLogger(ApiController.class);

    @Autowired
    private FlightEngine flightEngine;


    @PostMapping("search-flight")
    public BusyFlightsResponse getFlight(@RequestBody BusyFlightsRequest request) {
        validateRequest(request);
        return flightEngine.getBestFlight(request);
    }

    private void validateRequest(BusyFlightsRequest request) {
        try {
            AirportCode.valueOf(request.getOrigin());
            AirportCode.valueOf(request.getDestination());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ValidationException("Airport code is incorrect");
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date now = new Date();
            Date departureDate = format.parse(request.getDepartureDate());
            Date returnDate = format.parse(request.getReturnDate());
            if(departureDate.before(now) || returnDate.before(now) || returnDate.before(departureDate)) {
                throw new ValidationException("dates are in the past or return before departure ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("failed to convert dates from request. Formant should be: yyyy-MM-dd'T'HH:mm:ss'Z'");
            throw new ValidationException("Error in validating dates, check format");
        }

        if(request.getNumberOfPassengers() < 1) throw new ValidationException("Number of passengers should be at least 1");
    }
}
