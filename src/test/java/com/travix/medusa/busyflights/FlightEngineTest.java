package com.travix.medusa.busyflights;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.ClientResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.engine.FlightEngine;
import com.travix.medusa.busyflights.external.AirLineClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

public class FlightEngineTest {

    @Mock
    private AirLineClient airLineClient;

    @InjectMocks
    private FlightEngine flightEngine;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void zeroReturnTest() {
        BusyFlightsRequest request = new BusyFlightsRequest("LHR", "AMS", "2019-04-23T00:00:00Z","2019-05-01T00:00:00Z",1);

        Mockito.doReturn(Optional.empty()).when(airLineClient).queryCrazyAir(any(CrazyAirRequest.class));
        Mockito.doReturn(Optional.empty()).when(airLineClient).queryToughJet(any(ToughJetRequest.class));

        BusyFlightsResponse response = flightEngine.getBestFlight(request);

        assertTrue(response.getSearchResult().isEmpty());
    }


    @Test
    public void oneReturnTest() {
        BusyFlightsRequest request = new BusyFlightsRequest("LHR", "AMS", "2019-04-23T00:00:00Z","2019-05-01T00:00:00Z",1);

        ClientResponse crazyAirResponse = new ClientResponse("CrazyAir", "CrazyAir", BigDecimal.valueOf(111.3), "LHR", "AMS", "2019-04-23T01:10:00Z", "2019-05-01T10:50:00Z");

        Mockito.doReturn(Optional.of(crazyAirResponse)).when(airLineClient).queryCrazyAir(any(CrazyAirRequest.class));
        Mockito.doReturn(Optional.empty()).when(airLineClient).queryToughJet(any(ToughJetRequest.class));
        //given:
        BusyFlightsResponse response = flightEngine.getBestFlight(request);
        //then:
        List<ClientResponse> results = response.getSearchResult();
        assertEquals(1, results.size());
        assertEquals("CrazyAir", results.get(0).getSupplier());
    }

    @Test
    public void bothReturnTest() {
        BusyFlightsRequest request = new BusyFlightsRequest("LHR", "AMS", "2019-04-23T00:00:00Z","2019-05-01T00:00:00Z",1);

        ClientResponse crazyAirResponse = new ClientResponse("CrazyAir", "CrazyAir", BigDecimal.valueOf(131.3), "LHR", "AMS", "2019-04-23T01:10:00Z", "2019-05-01T10:50:00Z");

        ClientResponse toughJetResponse = new ClientResponse("ToughJet", "ToughJet", BigDecimal.valueOf(129.9), "LHR", "AMS", "2019-04-23T03:55:00Z", "2019-05-01T10:00:00Z");

        Mockito.doReturn(Optional.of(crazyAirResponse)).when(airLineClient).queryCrazyAir(any(CrazyAirRequest.class));
        Mockito.doReturn(Optional.of(toughJetResponse)).when(airLineClient).queryToughJet(any(ToughJetRequest.class));
        //given:
        BusyFlightsResponse response = flightEngine.getBestFlight(request);
        //then:
        List<ClientResponse> results = response.getSearchResult();
        assertEquals(2, results.size());
        assertEquals("ToughJet", results.get(0).getSupplier());
        assertEquals("CrazyAir", results.get(1).getSupplier());
    }

}