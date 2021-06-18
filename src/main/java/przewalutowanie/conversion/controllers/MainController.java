package przewalutowanie.conversion.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import przewalutowanie.conversion.services.ConversionService;

@RestController()
@RequestMapping(path = "/")
public class MainController {

    @Autowired
    ConversionService conversionService;

    @GetMapping(path = "/convert")
    public String conversion(@RequestParam String currencyFromCode, @RequestParam String currencyToCode, @RequestParam double currencyFromValue){

        if(conversionService.validate(currencyFromCode, currencyToCode, currencyFromValue)) {
            return conversionService.convert(currencyFromCode, currencyToCode, currencyFromValue);
        }else{
            return "This currency cannot be converted or value is less then 0";
        }

    }

}
