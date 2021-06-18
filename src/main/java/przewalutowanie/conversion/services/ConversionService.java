package przewalutowanie.conversion.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

    public Double convert(String currencyFromName, String currencyToName, double currencyFromValue){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Double sellRate = 0.0, buyRate = 0.0;
        try {
            if(currencyFromName.equals("PLN")){
                sellRate = 1.0;
            }else {
                JSONObject fromObject = new JSONObject(restTemplate.exchange("http://api.nbp.pl/api/exchangerates/rates/C/" + currencyFromName, HttpMethod.GET, entity, String.class).getBody());
                JSONArray fromArray = fromObject.getJSONArray("rates");
                sellRate = fromArray.getJSONObject(0).getDouble("bid")*0.98;
            }
            if(currencyToName.equals("PLN")){
                buyRate = 1.0;
            }else {
                JSONObject toObject = new JSONObject(restTemplate.exchange("http://api.nbp.pl/api/exchangerates/rates/C/" + currencyToName, HttpMethod.GET, entity, String.class).getBody());
                JSONArray toArray = toObject.getJSONArray("rates");
                buyRate = toArray.getJSONObject(0).getDouble("ask")*1.02;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (sellRate*currencyFromValue)/buyRate;

    }

    public boolean validate(String currencyFromName, String currencyToName, double currencyFromValue){
        return viableCurrencyList.contains(currencyFromName) && viableCurrencyList.contains(currencyToName) && currencyFromValue > 0;
    }

    @Bean
    public ConversionService getConversionService(){
        return new ConversionService();
    }


}
