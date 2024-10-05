package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryGetDto;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CountryController {

    @Autowired
    private final CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountryGetDto>> getAllCountries(@RequestParam(value = "codeOrName", required = false) String codeOrName) {
        if(codeOrName != null){
            return ResponseEntity.ok( countryService.getAllCountriesDtoForCodeOrName(codeOrName));
        }else{
            return ResponseEntity.ok( countryService.getAllCountriesDto());
        }
    }

    @GetMapping("/countries/{continent}/continent")
    public ResponseEntity<List<CountryGetDto>> getAllCountriesForContinent(@PathVariable String continent) {
        List<String> continents = List.of("Africa", "Americas", "Asia", "Europe", "Oceania", "Antarctic");
        if(continents.stream().noneMatch(continent::equalsIgnoreCase)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(countryService.getAllForContinent(continent));
    }

    @GetMapping("/countries/{lenguage}/lenguage")
    public ResponseEntity<List<CountryGetDto>> getAllCountriesForLenguage(@PathVariable String lenguage) {
        List<String> lenguages = List.of("Spanish", "English", "French", "German", "Portuguese", "Chinese", "Hindi", "Arabic", "Swahili");
        if(lenguages.stream().noneMatch(lenguage::equalsIgnoreCase)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(countryService.getAllForLenguage(lenguage));
    }

    @GetMapping("/countries/most-borders")
    public ResponseEntity<CountryGetDto> getAllCountriesMostBorders() {
        return ResponseEntity.ok(countryService.getMostBorders());
    }

    @PostMapping("/countries")
    public ResponseEntity<List<CountryGetDto>> postCountry(@RequestBody Long amountOfCountryToSave) {
        return ResponseEntity.ok(countryService.postCountryForCantity(amountOfCountryToSave));
    }

}