package com.travix.medusa.busyflights.domain.busyflights;

import java.util.List;

public class BusyFlightsResponse {
    private List<ClientResponse> searchResult;


    public BusyFlightsResponse() {}

    public BusyFlightsResponse(List<ClientResponse> responses) {
        this.searchResult = responses;
    }

    public List<ClientResponse> getSearchResult() {
        return searchResult;
    }
}
