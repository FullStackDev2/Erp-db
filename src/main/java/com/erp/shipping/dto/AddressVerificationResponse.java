package com.erp.shipping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressVerificationResponse {

    private String postalCode;
    private String country;
    private String countryCode;
    private String city;
    private String state;
    private String stateAbbreviation;
    private Double latitude;
    private Double longitude;

    public static AddressVerificationResponse from(ZippopotamResponse raw) {
        AddressVerificationResponse response = new AddressVerificationResponse();
        response.setPostalCode(raw.getPostCode());
        response.setCountry(raw.getCountry());
        response.setCountryCode(raw.getCountryAbbreviation());

        if (raw.getPlaces() != null && !raw.getPlaces().isEmpty()) {
            ZippopotamResponse.Place place = raw.getPlaces().get(0);
            response.setCity(place.getPlaceName());
            response.setState(place.getState());
            response.setStateAbbreviation(place.getStateAbbreviation());
            response.setLatitude(parseOrNull(place.getLatitude()));
            response.setLongitude(parseOrNull(place.getLongitude()));
        }

        return response;
    }

    private static Double parseOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}