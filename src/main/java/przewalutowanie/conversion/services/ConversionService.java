package przewalutowanie.conversion.services;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class ConversionService {

    final ArrayList<String> viableCurrencyList = new ArrayList<>(Arrays.asList("PLN", "EUR", "USD", "GBP"));

    @Autowired
    RestTemplate restTemplate;

    public String convert(String currencyFromName, String currencyToName, double currencyFromValue){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange("http://api.nbp.pl/api/exchangerates/rates/C/USD", HttpMethod.GET, entity, String.class).getBody();

    }

    public boolean validate(String currencyFromName, String currencyToName, double currencyFromValue){
        return viableCurrencyList.contains(currencyFromName) && viableCurrencyList.contains(currencyToName) && currencyFromValue > 0;
    }

    @Bean
    public ConversionService getConversionService(){
        return new ConversionService();
    }


}
