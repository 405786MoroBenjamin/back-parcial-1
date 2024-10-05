package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryGetDto;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import jakarta.inject.Inject;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CountryServiceTest {

    @InjectMocks
    CountryService sut;

    @Mock
    CountryRepository countryRepository;

    @Mock
    RestTemplate restTemplate;

    @Mock
    private List<Country> countries;

    @Mock
    private List<CountryGetDto> countriesDto;

    @Mock
    List<Map<String, Object>> responseRest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Country country = new Country();
        country.setName("Argentina");
        country.setPopulation(10000);
        country.setArea(1000);
        country.setCode("ARG");
        country.setRegion("Americas");
        country.setBorders(List.of("BRZ", "URU", "CHL"));
        country.setLanguages(Map.of("SPA", "Spanish"));

        Country country2 = new Country();
        country2.setName("Brazil");
        country2.setPopulation(20000);
        country2.setArea(2000);
        country2.setCode("BRZ");
        country2.setRegion("Americas");
        country2.setBorders(List.of("ARG", "URU"));
        country2.setLanguages(Map.of("POR", "Portuguese"));

        Country country3 = new Country();
        country3.setName("Uruguay");
        country3.setPopulation(30000);
        country3.setArea(3000);
        country3.setCode("URU");
        country3.setRegion("Americas");
        country3.setBorders(List.of("ARG", "BRZ"));
        country3.setLanguages(Map.of("SPA", "Spanish"));

        countries = List.of(country, country2, country3);

        CountryGetDto countryGetDto = new CountryGetDto();
        countryGetDto.setName("Argentina");
        countryGetDto.setCode("ARG");

        CountryGetDto countryGetDto2 = new CountryGetDto();
        countryGetDto2.setName("Brazil");
        countryGetDto2.setCode("BRZ");

        CountryGetDto countryGetDto3 = new CountryGetDto();
        countryGetDto3.setName("Uruguay");
        countryGetDto3.setCode("URU");

        countriesDto = List.of(countryGetDto, countryGetDto2, countryGetDto3);

        responseRest = List.of(Map.of("name", Map.of("common", "Argentina"), "population", 10000, "area", 1000, "cca3", "ARG", "region", "Americas", "borders", List.of("BRZ", "URU", "CHL"), "languages", Map.of("SPA", "Spanish")),
                                Map.of("name", Map.of("common", "Brazil"), "population", 20000, "area", 2000, "cca3", "BRZ", "region", "Americas", "borders", List.of("ARG", "URU"), "languages", Map.of("POR", "Portuguese")),
                                Map.of("name", Map.of("common", "Uruguay"), "population", 30000, "area", 3000, "cca3", "URU", "region", "Americas", "borders", List.of("ARG", "BRZ"), "languages", Map.of("SPA", "Spanish")));

    }

    @Test
    void getAllCountries() {
        when(restTemplate.getForObject("https://restcountries.com/v3.1/all", List.class)).thenReturn(responseRest);

        List<Country> result = sut.getAllCountries();

        assertEquals(countries.size(), result.size());
        assertEquals(countries.get(0).getName(), result.get(0).getName());
        verify(restTemplate).getForObject("https://restcountries.com/v3.1/all", List.class);
    }
    @Test
    void getAllCountriesDto() {
        when(restTemplate.getForObject("https://restcountries.com/v3.1/all", List.class)).thenReturn(responseRest);

        List<CountryGetDto> result = sut.getAllCountriesDto();

        assertEquals(countriesDto.size(), result.size());
        assertEquals(countriesDto.get(0).getName(), result.get(0).getName());
        verify(restTemplate).getForObject("https://restcountries.com/v3.1/all", List.class);
    }

    @Test
    void getAllCountriesDtoForCodeOrName() {
        when(restTemplate.getForObject("https://restcountries.com/v3.1/all", List.class)).thenReturn(responseRest);

        List<CountryGetDto> result = sut.getAllCountriesDtoForCodeOrName("Argentina");

        assertEquals(1, result.size());
        assertEquals("Argentina", result.get(0).getName());
        verify(restTemplate).getForObject("https://restcountries.com/v3.1/all", List.class);
    }

    @Test
    void getAllForContinent() {
        when(restTemplate.getForObject("https://restcountries.com/v3.1/all", List.class)).thenReturn(responseRest);

        List<CountryGetDto> result = sut.getAllForContinent("Americas");

        assertEquals(3, result.size());
        assertEquals("Argentina", result.get(0).getName());
        assertEquals("Brazil", result.get(1).getName());
        assertEquals("Uruguay", result.get(2).getName());
        verify(restTemplate).getForObject("https://restcountries.com/v3.1/all", List.class);
    }

    @Test
    void getAllForLenguage() {
        when(restTemplate.getForObject("https://restcountries.com/v3.1/all", List.class)).thenReturn(responseRest);

        List<CountryGetDto> result = sut.getAllForLenguage("Spanish");

        assertEquals(2, result.size());
        assertEquals("Argentina", result.get(0).getName());
        assertEquals("Uruguay", result.get(1).getName());
        verify(restTemplate).getForObject("https://restcountries.com/v3.1/all", List.class);
    }

    @Test
    void getMostBorders() {
    }

    @Test
    void postCountryForCantity() {

    }
}