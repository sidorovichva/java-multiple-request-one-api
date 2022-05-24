package com.vs.javamultiplerequestoneapi;

import com.vs.javamultiplerequestoneapi.model.Person;
import com.vs.javamultiplerequestoneapi.model.PricingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final Fetcher fetcher;

    @GetMapping
    public Person getPerson() {
        return new Person("Adam", 33);
    }

    @GetMapping("/get")
    public PricingResponse fetchPerson() throws IOException, InterruptedException {
        return fetcher.getPerson();
    }
}
