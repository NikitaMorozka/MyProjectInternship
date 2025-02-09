package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.core.DateTimeService;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeServiceTest {
    DateTimeService dateTimeService = new DateTimeService();
    @Test
    public void checkingPositiveNumber()  {
        LocalDateTime testDateFromm = LocalDateTime.of(2025, 1,11,0,0,0);
        LocalDateTime testDateToo = LocalDateTime.of(2025, 2,11,0,0,0);
        long price = dateTimeService.calculateAgreementPrice(testDateFromm, testDateToo);
        assertEquals(price, 31L);
    }

    @Test
    public void checkingNegativeNumber() {
        LocalDateTime testDateFromm = LocalDateTime.of(2025, 2,11,0,0,0);
        LocalDateTime testDateToo = LocalDateTime.of(2025, 1,11,0,0,0);
        long price = dateTimeService.calculateAgreementPrice(testDateFromm, testDateToo);
        assertEquals(price, -31L);
    }

    @Test
    public void checkingZeroNumber() {
        LocalDateTime testDateFromm = LocalDateTime.of(2025, 1,11,0,0,0);
        LocalDateTime testDateToo = LocalDateTime.of(2025, 1,11,0,0,0);
        long price = dateTimeService.calculateAgreementPrice(testDateFromm, testDateToo);
        assertEquals(price, 0);
    }
}
