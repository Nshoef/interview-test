package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.engine.FlightEngine;
import com.travix.medusa.busyflights.util.ValidationException;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.ClientResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

public class ApiControllerTest {

    @Mock
    private FlightEngine flightEngine;


    @InjectMocks
    private ApiController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.doReturn(getResponse()).when(flightEngine).getBestFlight(any(BusyFlightsRequest.class));
    }


    @Test
    public void getResponseTest() {
        BusyFlightsRequest request = new BusyFlightsRequest("LHR", "AMS", "2019-04-23T00:00:00Z", "2019-05-01T00:00:00Z", 1);
        BusyFlightsResponse response = controller.getFlight(request);

        ClientResponse clientResponse = response.getSearchResult().get(0);
        assertEquals("CrazyAir", clientResponse.getAirline());
        assertEquals( "CrazyAir", clientResponse.getSupplier());
        assertEquals(BigDecimal.valueOf(200.00), clientResponse.getFare());
        assertEquals("LHR", clientResponse.getDepartureAirportCode());
        assertEquals("AMS", clientResponse.getDestinationAirportCode());
        assertEquals("2019-04-23T00:00:00Z", clientResponse.getDepartureDate());
        assertEquals("2019-05-01T00:00:00Z", clientResponse.getArrivalDate());
    }

    @Test(expected = ValidationException.class)
    public void invalidAirportCodeRequestTest() {
        BusyFlightsRequest invalideCode = new BusyFlightsRequest("LGR", "AMS", "2029-04-23T00:00:00Z", "2029-05-01T00:00:00Z", 1);
        BusyFlightsResponse response = controller.getFlight(invalideCode);
    }

    @Test(expected = ValidationException.class)
    public void invalidDateRequestTest() {
        BusyFlightsRequest invalideCode = new BusyFlightsRequest("LHR", "AMS", "2016-04-23T00:00:00Z", "2029-05-01T00:00:00Z", 1);
        BusyFlightsResponse response = controller.getFlight(invalideCode);
    }

    @Test(expected = ValidationException.class)
    public void invalidDate2RequestTest() {
        BusyFlightsRequest invalideCode = new BusyFlightsRequest("LHR", "AMS", "2029-04-23T00:00:00Z", "2029-02-01T00:00:00Z", 1);
        BusyFlightsResponse response = controller.getFlight(invalideCode);
    }

    @Test(expected = ValidationException.class)
    public void invalidNumOfPassengersRequestTest() {
        BusyFlightsRequest invalideCode = new BusyFlightsRequest("LHR", "AMS", "2029-04-23T00:00:00Z", "2029-05-01T00:00:00Z", 0);
        BusyFlightsResponse response = controller.getFlight(invalideCode);
    }


    private BusyFlightsResponse getResponse() {
        ClientResponse clientResponse = new ClientResponse("CrazyAir", "CrazyAir", BigDecimal.valueOf(200.00),
                "LHR", "AMS", "2019-04-23T00:00:00Z", "2019-05-01T00:00:00Z");
        return new BusyFlightsResponse(Arrays.asList(clientResponse));
    }
}