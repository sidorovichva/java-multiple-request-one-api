package com.vs.javamultiplerequestoneapi.models.responses.ota;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirLineCompanyDTO {

    private String name;
    private String code;
    private String countryId;
    private String cityId;
}
