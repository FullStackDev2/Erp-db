package com.erp.shipping.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * https://api.zippopotam.us/{country}/{postalCode} ham JSON yanıtının
 * doğrudan karşılığı. Alan adları API'de boşluklu olduğu için
 * @JsonProperty ile eşleştiriliyor.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZippopotamResponse {

    @JsonProperty("post code")
    private String postCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("country abbreviation")
    private String countryAbbreviation;

    @JsonProperty("places")
    private List<Place> places;

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryAbbreviation() {
        return countryAbbreviation;
    }

    public void setCountryAbbreviation(String countryAbbreviation) {
        this.countryAbbreviation = countryAbbreviation;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Place {

        @JsonProperty("place name")
        private String placeName;

        @JsonProperty("state")
        private String state;

        @JsonProperty("state abbreviation")
        private String stateAbbreviation;

        @JsonProperty("latitude")
        private String latitude;

        @JsonProperty("longitude")
        private String longitude;

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStateAbbreviation() {
            return stateAbbreviation;
        }

        public void setStateAbbreviation(String stateAbbreviation) {
            this.stateAbbreviation = stateAbbreviation;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}