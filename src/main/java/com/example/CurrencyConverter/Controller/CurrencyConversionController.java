package com.example.CurrencyConverter.Controller;

import com.example.CurrencyConverter.Constants.ApiConstants;
import com.example.CurrencyConverter.Dto.CurrencyConversionRequestDto;
import com.example.CurrencyConverter.Dto.CurrencyConversionResponseDto;
import com.example.CurrencyConverter.Service.CurrencyConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(ApiConstants.API)
public class CurrencyConversionController {
    private final CurrencyConversionService currencyConversionService;

    public CurrencyConversionController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @PostMapping(ApiConstants.POST_API)
    public ResponseEntity<CurrencyConversionResponseDto> convertCurrency(
            @RequestBody CurrencyConversionRequestDto requestDto) {
        CurrencyConversionResponseDto responseDto = currencyConversionService.convertCurrency(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping(ApiConstants.GET_API)
    public ResponseEntity<?> getExchangeRates(@RequestParam(defaultValue = "USD") String base) {
        try {
            Map<String, Double> rates = currencyConversionService.getExchangeRates(base);
            return ResponseEntity.ok(rates);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
