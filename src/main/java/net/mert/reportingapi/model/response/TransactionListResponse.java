package net.mert.reportingapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionListResponse extends ResponseTemplate{

    private int per_page, current_page;

    @Max(256)
    private String next_page_url, prev_page_url;

    private Integer from, to;

    private List<TransactionListItem> data;
}
