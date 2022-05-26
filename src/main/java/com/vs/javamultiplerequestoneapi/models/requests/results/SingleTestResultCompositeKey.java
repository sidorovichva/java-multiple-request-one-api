package com.vs.javamultiplerequestoneapi.models.requests.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleTestResultCompositeKey implements Serializable {

    private String id;
    private LocalDateTime date;
}
