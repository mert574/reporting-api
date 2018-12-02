package net.mert.reportingapi.model.response.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Acquirer {
    private Long id;
    private String name, code, type;
}
