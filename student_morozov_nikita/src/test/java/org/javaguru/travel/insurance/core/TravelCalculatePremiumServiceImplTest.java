package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class) // Подключаем расширение Mockito

class TravelCalculatePremiumServiceImplTest {
    @Mock
    private DateTimeService dateTimeService;// Создаётся mock-объект
    @Mock
    private TravelCalculatePremiumRequestValidator requestValidator;

    @InjectMocks
    private TravelCalculatePremiumServiceImpl calculate; //тестируемый объект с внедрением mock объектов

    private TravelCalculatePremiumRequest getRequest() throws ParseException {
        TravelCalculatePremiumRequest request = new TravelCalculatePremiumRequest();
        request.setPersonFirstName("Никита");
        request.setPersonLastName("Морозов");
        request.setAgreementDateTo(LocalDateTime.of(2025, 4,11,0,0,0));
        request.setAgreementDateFrom(LocalDateTime.of(2025, 5,11,0,0,0));
        return request;
    }

    @Test
    public void shouldReturnResponseMatchingRequestFieldFirstName() throws ParseException {
        var request = getRequest();
        var response = calculate.calculatePremium(request);
        assertEquals(request.getPersonFirstName(),response.getPersonFirstName());
    }

    @Test
    public void shouldReturnResponseMatchingRequestFieldLastName() throws ParseException {
        var request = getRequest();
        var response = calculate.calculatePremium(request);
        assertEquals(request.getPersonLastName(),response.getPersonLastName());
    }

    @Test
    public void shouldReturnResponseMatchingRequestFieldDateFrom() throws ParseException {
        var request = getRequest();
        var response = calculate.calculatePremium(request);
        assertEquals(request.getAgreementDateFrom(),response.getAgreementDateFrom());
    }

    @Test
    public void shouldReturnResponseMatchingRequestFieldDateTo() throws ParseException {
        var request = getRequest();

        var response = calculate.calculatePremium(request);
        assertEquals(request.getAgreementDateTo(),response.getAgreementDateTo());
    }

    @Test

    public void shouldReturnAgreementPrice() throws ParseException {
        var request = getRequest();

        var response = calculate.calculatePremium(request);
        assertNotNull(response.getAgreementPrice());
    }

    @Test
    public void shouldReturnResponseWithErrors() {
        var request = new TravelCalculatePremiumRequest();
        var validationError = new ValidationError("field", "message");
        when(requestValidator.validate(request)).thenReturn(List.of(validationError));
        var response = calculate.calculatePremium(request);
        assertTrue(response.hasErrors());
    }

    @Test
    public void shouldReturnResponseWithCountErrors() {
        var request = new TravelCalculatePremiumRequest();
        List<ValidationError> validationErrors = List.of(
                new ValidationError("field1", "message1"),
                new ValidationError("field2", "message2")
        );
        when(requestValidator.validate(request)).thenReturn(validationErrors);
        var response = calculate.calculatePremium(request);
        assertEquals(response.getErrors().size(),2);
    }

    @Test
    @DisplayName("Проверка на передачу полей из объекта validationErrors")
    public void shouldReturnResponseEqualsErrors() {
        var request = new TravelCalculatePremiumRequest();
        var validationErrors = new ValidationError("field1", "message1");
        when(requestValidator.validate(request)).thenReturn(List.of(validationErrors));
        var response = calculate.calculatePremium(request);
        assertEquals(response.getErrors().getFirst().getField(),"field1");
        assertEquals(response.getErrors().getFirst().getMessage(),"message1");
        assertNull(response.getPersonFirstName());
    }


//
    @Test
    @DisplayName("Проверка пустых полей, и вызова конструктора с ошибками")
    public void allFieldsMustBeEmptyWhenResponseContainsError() {
        var request = new TravelCalculatePremiumRequest();
        var validationError = new ValidationError("field", "message");
        when(requestValidator.validate(request)).thenReturn(List.of(validationError));
        var response = calculate.calculatePremium(request);
        assertNull(response.getPersonFirstName());
        assertNull(response.getPersonLastName());
        assertNull(response.getAgreementDateFrom());
        assertNull(response.getAgreementDateTo());
        assertNull(response.getAgreementPrice());
        assertNotNull(response.getErrors().getFirst().getField());
        assertNotNull(response.getErrors().getFirst().getMessage());

    }

}