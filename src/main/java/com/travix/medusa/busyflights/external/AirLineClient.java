package com.travix.medusa.busyflights.external;

import com.travix.medusa.busyflights.util.ResponseMapper;
import com.travix.medusa.busyflights.domain.busyflights.ClientResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class AirLineClient {
    private static final Logger LOGGER = Logger.getLogger(AirLineClient.class);

    @Autowired
    private static RestTemplate restTemplate;


    public Optional<ClientResponse> queryCrazyAir(CrazyAirRequest request) {
        try {
            CrazyAirResponse response = restTemplate.postForObject("url", request, CrazyAirResponse.class);
            ClientResponse clientResponse = ResponseMapper.fromCrazyAirResponse(response);
            return Optional.of(clientResponse);
        } catch (Exception e) {
            LOGGER.warn("Failed to get a response from CrazyAir for request: "+ request);
            return Optional.empty();
        }
    }

    public Optional<ClientResponse> queryToughJet(ToughJetRequest request) {
        try {
            ToughJetResponse response = restTemplate.postForObject("url", request, ToughJetResponse.class);
            ClientResponse clientResponse = ResponseMapper.fromToughJetResponse(response);
            return Optional.of(clientResponse);
        } catch (Exception e) {
            LOGGER.warn("Failed to get a response from ToughJet for request: "+ request);
            return Optional.empty();
        }

    }
}
