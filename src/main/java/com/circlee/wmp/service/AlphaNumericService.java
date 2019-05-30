package com.circlee.wmp.service;

import com.circlee.wmp.common.enums.AlphaNumericCondition;
import com.circlee.wmp.dto.AlphaNumericResDTO;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AlphaNumericService {
    Mono<AlphaNumericResDTO> getCharStreamFromOpenURL(String url, AlphaNumericCondition condition, BigDecimal mok);
}
