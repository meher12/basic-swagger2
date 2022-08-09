package com.guru.jsonfiltering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

    // Implementing Dynamic Filtering for RESTful Service with MappingJacksonValue,
    // SimpleBeanPropertyFilter, SimpleFilterProvider
    // returning a single bean as response
    // values to send name and salary
    @RequestMapping(value = "/filtering", method = RequestMethod.GET)
    public MappingJacksonValue retrieveSomeBean() {
        SomeBean someBean = new SomeBean("Amit", "9999999999", "39000");
        // invoking static method filterOutAllExcept()
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "salary");
        // creating filter using FilterProvider class
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        // constructor of MappingJacksonValue class that has bean as constructor
        // argument
        MappingJacksonValue mapping = new MappingJacksonValue(someBean);
        // configuring filters
        mapping.setFilters(filters);
        return mapping;
    }

    // returning a list of SomeBeans as response
    // values to send name and phone
    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveListOfSomeBeans() {
        List<SomeBean> list = Arrays.asList(new SomeBean("Saurabh", "8888888888", "20000"),
                new SomeBean("Devesh", "1111111111", "34000"));
        // invoking static method filterOutAllExcept()
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "phone");
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        // constructor of MappingJacksonValue class that has list as constructor
        // argument
        MappingJacksonValue mapping = new MappingJacksonValue(list);
        // configuring filter
        mapping.setFilters(filters);
        return mapping;
    }
}
