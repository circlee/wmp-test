package com.circlee.wmp.controller;

import com.circlee.wmp.dto.AlphaNumericReqDTO;
import com.circlee.wmp.dto.AlphaNumericResDTO;
import com.circlee.wmp.service.AlphaNumericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alphaNumeric")
public class AlphaNumericController {


    @Autowired
    AlphaNumericService alphaNumericService;

    @PostMapping
    public Mono<AlphaNumericResDTO> generateAlphanumeric(@RequestBody AlphaNumericReqDTO dto){

        return alphaNumericService.getCharStreamFromOpenURL(dto.getTargetUrl(), dto.getCondition(), dto.getMok());

    }


}
