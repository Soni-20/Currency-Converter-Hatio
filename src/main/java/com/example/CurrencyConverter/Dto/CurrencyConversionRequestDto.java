package com.example.CurrencyConverter.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyConversionRequestDto {
    private String from;
    private String to;
    private double amount;
}
