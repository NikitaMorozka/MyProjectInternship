package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Подключаем расширение Mockito
public class TravelCalculatePremiumRequestValidatorTest {
    @Mock
    private TravelCalculatePremiumRequest request;//подменяемый объект

    @InjectMocks
    private TravelCalculatePremiumRequestValidator requestValidator;//тестируемый объект с внедрением mock объектов


    @Test
    @DisplayName("FirstName передача null")
    public void shouldReturnErrorWhenPersonFirstNameIsNull(){
        when(request.getPersonFirstName()).thenReturn(null);
        when(request.getPersonLastName()).thenReturn(null);
        List<ValidationError> errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
        assertEquals(errors.size(), 2);
    }


    @Test
    @DisplayName("FirstName передача пустого значения")
    public void shouldReturnErrorWhenPersonFirstNameIsEmpty(){
        when(request.getPersonFirstName()).thenReturn("");
        when(request.getPersonLastName()).thenReturn("");

        List<ValidationError> errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
        assertEquals(errors.size(), 2);
    }

    @Test
    @DisplayName("FirstName передача заполненного значения")
    public void shouldReturnErrorWhenPersonFirstName(){
        when(request.getPersonFirstName()).thenReturn("Имя");
        when(request.getPersonLastName()).thenReturn("Фамилия");
        List<ValidationError> errors = requestValidator.validate(request);
        assertTrue(errors.isEmpty());
        assertEquals(errors.size(), 0);
    }

    @Test
    @DisplayName("FirstName передача null LastName не Null")
    public void shouldReturnErrorWhenPersonFirstNameIsNullAndLastNameNotNull(){
        when(request.getPersonFirstName()).thenReturn(null);
        when(request.getPersonLastName()).thenReturn("Фамилия");
        List<ValidationError> errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
        assertEquals(errors.size(), 1);
    }

    @Test
    @DisplayName("FirstName передача null LastName не Null")
    public void shouldReturnErrorWhenPersonFirstNameIsNotNullAndLastNameIsNull(){
        when(request.getPersonFirstName()).thenReturn("Имя");
        when(request.getPersonLastName()).thenReturn(null);
        List<ValidationError> errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
        assertEquals(errors.size(), 1);
    }




}
