package net.mert.reportingapi.model.response;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.Max;

@Data
public abstract class ResponseTemplate {

    @Max(64)
    private String status;

    public ResponseTemplate() {
        this.status = "APPROVED";
    }

    public ResponseEntity<?> toResponseEntity() {
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

    public ResponseEntity<?> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(this, status);
    }
}
