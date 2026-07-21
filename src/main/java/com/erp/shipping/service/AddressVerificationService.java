package com.erp.shipping.service;

import com.erp.common.exception.ResourceNotFoundException;
import com.erp.shipping.dto.AddressVerificationResponse;
import com.erp.shipping.dto.ZippopotamResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressVerificationService {

    private static final String ZIPPOPOTAM_BASE_URL = "https://api.zippopotam.us";

    private final RestTemplate restTemplate;

    public AddressVerificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Verilen ülke kodu + posta koduyla Zippopotam.us üzerinden adres doğrulaması yapar.
     * Posta kodu geçerliyse şehir/eyalet/koordinat bilgisiyle döner.
     *
     * @param countryCode ISO 3166-1 alpha-2 ülke kodu (örn. "us", "de", "fr")
     * @param postalCode  doğrulanacak posta kodu (örn. "90210")
     */
    public AddressVerificationResponse verifyPostalCode(String countryCode, String postalCode) {
        String url = String.format("%s/%s/%s",
                ZIPPOPOTAM_BASE_URL,
                countryCode.trim().toLowerCase(),
                postalCode.trim());

        try {
            ZippopotamResponse raw = restTemplate.getForObject(url, ZippopotamResponse.class);

            if (raw == null || raw.getPlaces() == null || raw.getPlaces().isEmpty()) {
                throw new ResourceNotFoundException(
                        "Posta kodu doğrulanamadı: " + countryCode + "/" + postalCode);
            }

            return AddressVerificationResponse.from(raw);

        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException(
                    "Geçersiz posta kodu veya ülke kodu: " + countryCode + "/" + postalCode);
        } catch (RestClientException e) {
            throw new IllegalStateException(
                    "Adres doğrulama servisine ulaşılamadı, lütfen daha sonra tekrar deneyin.");
        }
    }
}