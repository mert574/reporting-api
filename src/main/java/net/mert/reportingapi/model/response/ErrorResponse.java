package net.mert.reportingapi.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorResponse extends ResponseTemplate {

    private String message;
    private int code;

    public ErrorResponse(String message, String statusText) {
        this.setStatus(statusText);
        this.message = message;
        this.code = 0;
    }

    @Override
    public ResponseEntity<?> toResponseEntity() {
        return new ResponseEntity<>(this, HttpStatus.BAD_REQUEST);
    }
}
