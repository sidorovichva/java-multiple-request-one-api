package com.vs.javamultiplerequestoneapi.models.responses.ota;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoBase {

    private String id = UUID.randomUUID().toString();
    private String name;
    private Instant createdDate;
    private Instant lastModifiedDate;
    @JsonIgnore private String createdBy;
    @JsonIgnore private String lastModifiedBy;
}
