package com.travix.medusa.busyflights.util;

import com.travix.medusa.busyflights.domain.busyflights.ClientResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.util.AirportCode;

import java.math.BigDecimal;

public class ResponseMapper {


    public static ClientResponse fromCrazyAirResponse(CrazyAirResponse response) {
        return new ClientResponse(response.getAirline(),
                "CrazyAir",
                BigDecimal.valueOf(response.getPrice()).setScale(3),
                response.getDepartureAirportCode(),
                response.getDestinationAirportCode(),
                response.getDepartureDate(),
                response.getArrivalDate());
    }

    public static ClientResponse fromToughJetResponse(ToughJetResponse response) {
        // Assume here that the final price is (base+tax)-discount
        BigDecimal price = BigDecimal.valueOf(response.getBasePrice() + response.getTax() - response.getDiscount()).setScale(3);
        return new ClientResponse(response.getCarrier(),
                "ToughJet",
                price,
                AirportCode.forName(response.getDepartureAirportName()).toString(),
                AirportCode.forName(response.getArrivalAirportName()).toString(),
                response.getInboundDateTime(),
                response.getOutboundDateTime());
    }
}
