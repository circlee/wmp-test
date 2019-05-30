package com.circlee.wmp.dto;

import com.circlee.wmp.common.enums.AlphaNumericCondition;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlphaNumericReqDTO {

    private String targetUrl;

    private AlphaNumericCondition condition;

    private BigDecimal mok;
}
