package com.example.CurrencyConverter.Dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyConversionResponseDto {
    private String from;
    private String to;
    private double amount;
    private double convertedAmount;
}
