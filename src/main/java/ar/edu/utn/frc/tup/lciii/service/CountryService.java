package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryGetDto;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.model.CountryEntity;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CountryService {

        @Autowired
        private CountryRepository countryRepository;

        @Autowired
        private RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        public List<CountryGetDto> getAllCountriesDto() {
                List<Country> countries = getAllCountries();
                List<CountryGetDto> countriesDto = new ArrayList<>();
                for(Country country : countries){
                        countriesDto.add(mapToDTO(country));
                }
                return countriesDto;
        }

        public List<CountryGetDto> getAllCountriesDtoForCodeOrName(String codeOrName) {
                List<Country> countries = getAllCountries();
                countries = countries.stream()
                        .filter(country -> country.getName().contains(codeOrName) || country.getCode().contains(codeOrName))
                        .collect(Collectors.toList());
                List<CountryGetDto> countriesDto = new ArrayList<>();
                for(Country country : countries){
                        countriesDto.add(mapToDTO(country));
                }
                return countriesDto;
        }

        public List<CountryGetDto> getAllForContinent(String continent) {
                List<Country> countries = getAllCountries();
                countries = countries.stream()
                        .filter(country -> country.getRegion().contains(continent))
                        .collect(Collectors.toList());
                List<CountryGetDto> countriesDto = new ArrayList<>();
                for(Country country : countries){
                        countriesDto.add(mapToDTO(country));
                }
                return countriesDto;
        }

        public List<CountryGetDto> getAllForLenguage(String lenguage) {
                List<Country> countries = getAllCountries();
                // todo: revisar el filtro con map
                List<Country> countriesFiltered = new ArrayList<>();
                for(Country country : countries){
                        if(country.getLanguages() != null){
                                if(country.getLanguages().containsValue(lenguage)){
                                        countriesFiltered.add(country);
                                }
                        }
                }
                List<CountryGetDto> countriesDto = new ArrayList<>();
                for(Country country : countriesFiltered){
                        countriesDto.add(mapToDTO(country));
                }
                return countriesDto;
        }

        public CountryGetDto getMostBorders() {
                List<Country> countries = getAllCountries();
                Country countryMax = new Country();
                countryMax.setBorders(new ArrayList<>());
                for(Country country : countries){
                        if(country.getBorders() != null){
                                if(country.getBorders().size() > countryMax.getBorders().size()){
                                        countryMax = country;
                                }
                        }
                }


                return mapToDTO(countryMax);
        }

        public List<CountryGetDto> postCountryForCantity(Long cant) {
                List<Country> countries = getAllCountries();
                Collections.shuffle(countries);

                List<Country> countriesRandom = new ArrayList<>();
                for(int i = 0; i < cant; i++){
                        countriesRandom.add(countries.get(i));
                }


                List<CountryEntity> countriesEntity = new ArrayList<>();
                for(Country country : countriesRandom){
                        CountryEntity countryEntity = new CountryEntity();
                        countryEntity.setName(country.getName());
                        countryEntity.setCode(country.getCode());
                        countryEntity.setPopulation(country.getPopulation());
                        countryEntity.setArea(country.getArea());
                        countriesEntity.add(countryEntity);
                }

                countryRepository.saveAll(countriesEntity);

                List<CountryGetDto> countriesDto = new ArrayList<>();
                for(Country country : countriesRandom){
                        countriesDto.add(mapToDTO(country));
                }
                return countriesDto;
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        public Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .code((String) countryData.get("cca3"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .borders((List<String>) countryData.get("borders"))
                        .build();
        }


        public CountryGetDto mapToDTO(Country country) {
                return new CountryGetDto(country.getCode(), country.getName());
        }
}