package org.javaguru.travel.insurance.core;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class DateTimeService {
    Long calculateAgreementPrice(LocalDateTime dateFrom, LocalDateTime dateTo ){
        return ChronoUnit.DAYS.between(dateFrom, dateTo);
    }
}
