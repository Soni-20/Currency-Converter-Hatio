package com.example.CurrencyConverter.Service;

import com.example.CurrencyConverter.Constants.ApplicationConstants;
import com.example.CurrencyConverter.Dto.CurrencyConversionRequestDto;
import com.example.CurrencyConverter.Dto.CurrencyConversionResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Service
public class CurrencyConversionService {
    private static final String API_URL = "https://api.exchangerate.host/latest?base={base}";
    private final RestTemplate restTemplate = new RestTemplate();

    public CurrencyConversionResponseDto convertCurrency(CurrencyConversionRequestDto request) {
        try {
            Map<String, Object> apiResponse = restTemplate.getForObject(API_URL, Map.class, request.getFrom().toUpperCase());

            if (apiResponse == null || !apiResponse.containsKey(ApplicationConstants.RATES)) {
                throw new RuntimeException(ApplicationConstants.INVALID_RESPONSE_FROM_RATE_API);
            }

            Map<String, Double> rates = (Map<String, Double>) apiResponse.get(ApplicationConstants.RATES);
            Double rate = rates.get(request.getTo().toUpperCase());

            if (rate == null) {
                throw new RuntimeException(ApplicationConstants.INVALID_TARGET_CURRENCY + request.getTo());
            }

            double convertedAmount = request.getAmount() * rate;

            return CurrencyConversionResponseDto.builder()
                    .from(request.getFrom().toUpperCase())
                    .to(request.getTo().toUpperCase())
                    .amount(request.getAmount())
                    .convertedAmount(convertedAmount)
                    .build();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ApplicationConstants.CONVERTION_FAILES + ex.getMessage());
        }
    }

    public Map<String, Double> getExchangeRates(String baseCurrency) {
        try {
            Map<String, Object> apiResponse = restTemplate.getForObject(API_URL, Map.class, baseCurrency.toUpperCase());

            if (apiResponse == null || !apiResponse.containsKey(ApplicationConstants.RATES)) {
                throw new RuntimeException(ApplicationConstants.INVALID_RESPONSE_FROM_RATE_API);
            }

            return (Map<String, Double>) apiResponse.get(ApplicationConstants.RATES);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch exchange rates: " + ex.getMessage());
        }
    }
}
