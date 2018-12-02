package net.mert.reportingapi.model.response.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Agent {

    private long id;
    private String customerIp, customerUserAgent;
    private String merchantIp, merchantUserAgent;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at, updated_at, deleted_at;
}
