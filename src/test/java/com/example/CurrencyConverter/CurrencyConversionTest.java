package com.example.CurrencyConverter;

import com.example.CurrencyConverter.Dto.CurrencyConversionRequestDto;
import com.example.CurrencyConverter.Dto.CurrencyConversionResponseDto;
import com.example.CurrencyConverter.Service.CurrencyConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class CurrencyConversionTest {
    @Mock

    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyConversionService currencyConversionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertCurrency_successfulConversion() {
        CurrencyConversionRequestDto requestDto = new CurrencyConversionRequestDto("USD", "INR", 100.0);

        Map<String, Object> apiResponse = new HashMap<>();
        Map<String, Object> rates = new HashMap<>();
        rates.put("INR", 83.0); // Example rate
        apiResponse.put("rates", rates);
        CurrencyConversionResponseDto response = currencyConversionService.convertCurrency(requestDto);
        assertNotNull(response);
        assertEquals("USD", response.getFrom());
        assertEquals("INR", response.getTo());
        assertEquals(100.0, response.getAmount());
        assertEquals(8300.0, response.getConvertedAmount());
    }

    @Test
    void testConvertCurrency_invalidTargetCurrency() {
        // Arrange
        CurrencyConversionRequestDto requestDto = new CurrencyConversionRequestDto("USD", "XXX", 100.0);

        Map<String, Object> apiResponse = new HashMap<>();
        Map<String, Object> rates = new HashMap<>();
        rates.put("INR", 83.0);
        apiResponse.put("rates", rates);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            currencyConversionService.convertCurrency(requestDto);
        });
        assertTrue(exception.getMessage().contains("Invalid target currency code"));
    }
    @Test
    void testConvertCurrency_apiResponseMissingRates() {
        CurrencyConversionRequestDto requestDto = new CurrencyConversionRequestDto("USD", "INR", 100.0);
        Map<String, Object> apiResponse = new HashMap<>();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            currencyConversionService.convertCurrency(requestDto);
        });
        assertTrue(exception.getMessage().contains("Invalid API response"));
    }

}
