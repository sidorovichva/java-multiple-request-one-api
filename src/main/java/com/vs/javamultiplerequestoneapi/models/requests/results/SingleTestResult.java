package com.vs.javamultiplerequestoneapi.models.requests.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(SingleTestResultCompositeKey.class)
public class SingleTestResult {

    @Id private String id;
    @Id private LocalDateTime date;
    private Integer delay;
    private Boolean notCancelled;
    private Double risk;

    public String csvHeader() {
        return "id,date,delay,notCancelled,risk";
    }

    public String toCsvRow() {
        return id + ',' + date + ',' + delay + ',' + notCancelled + ',' + risk;
    }
}
