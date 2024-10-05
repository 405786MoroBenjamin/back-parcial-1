package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryGetDto;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CountryControllerTest {

    @InjectMocks
    CountryController controller;

    @Mock
    CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAllCountriesWithoutParams() {
        List<CountryGetDto> countries = List.of(
                new CountryGetDto("Argentina", "ARG"),
                new CountryGetDto("Brazil", "BRZ"),
                new CountryGetDto("Uruguay", "URU")
        );

        when(countryService.getAllCountriesDto()).thenReturn(countries);

        List<CountryGetDto> result = controller.getAllCountries(null).getBody();

        assertEquals(countries, result);
        verify(countryService).getAllCountriesDto();
    }

    @Test
    void getAllCountriesWithName() {
        List<CountryGetDto> countries = List.of(
                new CountryGetDto("Argentina", "ARG")
        );

        when(countryService.getAllCountriesDtoForCodeOrName("Argentina")).thenReturn(countries);

        List<CountryGetDto> result = controller.getAllCountries("Argentina").getBody();

        assertEquals(countries, result);
        verify(countryService).getAllCountriesDtoForCodeOrName("Argentina");
    }

    @Test
    void getAllCountriesWithCode() {
        List<CountryGetDto> countries = List.of(
                new CountryGetDto("Argentina", "ARG")
        );

        when(countryService.getAllCountriesDtoForCodeOrName("ARG")).thenReturn(countries);

        List<CountryGetDto> result = controller.getAllCountries("ARG").getBody();

        assertEquals(countries, result);
        verify(countryService).getAllCountriesDtoForCodeOrName("ARG");
    }


    @Test
    void getAllCountriesForContinent() {
        List<CountryGetDto> countries = List.of(
                new CountryGetDto("Argentina", "ARG"),
                new CountryGetDto("Brazil", "BRZ"),
                new CountryGetDto("Uruguay", "URU")
        );

        when(countryService.getAllForContinent("Americas")).thenReturn(countries);

        List<CountryGetDto> result = controller.getAllCountriesForContinent("Americas").getBody();

        assertEquals(countries, result);
        verify(countryService).getAllForContinent("Americas");
    }

    @Test
    void getAllCountriesForLenguage() {
        List<CountryGetDto> countries = List.of(
                new CountryGetDto("Argentina", "ARG"),
                new CountryGetDto("Uruguay", "URU")
        );

        when(countryService.getAllForLenguage("Spanish")).thenReturn(countries);

        List<CountryGetDto> result = controller.getAllCountriesForLenguage("Spanish").getBody();

        assertEquals(countries, result);
        verify(countryService).getAllForLenguage("Spanish");
    }

    @Test
    void getAllCountriesMostBorders() {
        CountryGetDto country = new CountryGetDto("Argentina", "ARG");

        when(countryService.getMostBorders()).thenReturn(country);

        CountryGetDto result = controller.getAllCountriesMostBorders().getBody();

        assertEquals(country, result);
        verify(countryService).getMostBorders();
    }

    @Test
    void postCountry() {
        List<CountryGetDto> countries = List.of(
                new CountryGetDto("Argentina", "ARG"),
                new CountryGetDto("Brazil", "BRZ"),
                new CountryGetDto("Uruguay", "URU")
        );

        when(countryService.postCountryForCantity(3L)).thenReturn(countries);

        List<CountryGetDto> result = controller.postCountry(3L).getBody();

        assertEquals(countries, result);
        verify(countryService).postCountryForCantity(3L);
    }
}