package com.project.microservice.currencyconversionservice.controller;

import com.project.microservice.currencyconversionservice.beans.CurrencyConversion;

import com.project.microservice.currencyconversionservice.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
                                                          @PathVariable BigDecimal quantity) {

        //Make a Call to the other rest API
        // Note that not all of the elements will be converted
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class
                , uriVariables);

        CurrencyConversion currencyConversion = responseEntity.getBody();
        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmmount(currencyConversion.getQuantity().multiply(currencyConversion.getConversionMultiple()));
        currencyConversion.setEnvironment(currencyConversion.getEnvironment() + " " + "rest template");

        return currencyConversion;
    }

    //Method using the Proxy connection
    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,
                                                          @PathVariable BigDecimal quantity) {

        //Make a Call to the other rest API
        // Note that not all of the elements will be converted

        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);

        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmmount(currencyConversion.getQuantity().multiply(currencyConversion.getConversionMultiple()));

        currencyConversion.setEnvironment(currencyConversion.getEnvironment() + " " + "feign");
        return currencyConversion;
    }
}
