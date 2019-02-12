package com.travix.medusa.busyflights.engine;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.ClientResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.external.AirLineClient;
import com.travix.medusa.busyflights.util.RequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FlightEngine {

    @Autowired
    private AirLineClient client;


    public BusyFlightsResponse getBestFlight(BusyFlightsRequest request) {
        CrazyAirRequest crazyAirRequest = RequestMapper.toCrazyAirRequest(request);
        ToughJetRequest toughJetRequest = RequestMapper.toToughJetRequest(request);

        List result = new ArrayList();

        client.queryCrazyAir(crazyAirRequest).ifPresent(result::add);
        client.queryToughJet(toughJetRequest).ifPresent(result::add);

        result.sort(Comparator.comparing(ClientResponse::getFare));
        return new BusyFlightsResponse(result);
    }
}

