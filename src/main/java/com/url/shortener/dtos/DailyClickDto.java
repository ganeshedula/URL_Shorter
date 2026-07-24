package com.url.shortener.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class DailyClickDto {
    private final LocalDate date;
    private final long count;
}
