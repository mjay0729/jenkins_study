package com.lineplus.featurestore.feature.controller;


import com.lineplus.featurestore.feature.domain.dto.response.AttributeResponseDto;
import com.lineplus.featurestore.feature.domain.dto.response.BaseFeatureGroupResponseDto;
import com.lineplus.featurestore.feature.service.BaseFeatureService;
import com.lineplus.featurestore.global.reponse.FeatureStoreResponse;
import com.lineplus.featurestore.global.reponse.ResponseCodes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@Tag(name = "BaseFeature", description = "BaseFeature API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/baseFeature")
public class BaseFeatureController {

    @Autowired
    private BaseFeatureService baseFeatureService;

    @Operation(summary = "get baseFeatureGroup and baseFeature", description = "baseFeatureGroup 과 거기에 속한 baseFeature 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = BaseFeatureGroupResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @Parameters()
    @GetMapping("/group")
    public ResponseEntity<FeatureStoreResponse<?>> getBaseFeatureGroup(){
        FeatureStoreResponse response = new FeatureStoreResponse(ResponseCodes.DEFAULT, this.baseFeatureService.getBaseFeatureGroupDto());
        return new ResponseEntity<FeatureStoreResponse<?>>(response, HttpStatus.OK);
    }

    @Operation(summary = "get attribute", description = "선택된 baseFeature 에 속해 있는 label 목록 또는 continuous 값 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AttributeResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @Parameters({
            @Parameter(name = "featuresId",description = "baseFeature 의 id",example = "1")
    })
    @GetMapping("/feature/{featuresId}")
    public ResponseEntity<FeatureStoreResponse<?>> getBaseFeatureLabel(@PathVariable("featuresId") long featuresId){
        FeatureStoreResponse response = new FeatureStoreResponse(ResponseCodes.DEFAULT, this.baseFeatureService.getAttribute(featuresId));;
        return new ResponseEntity<FeatureStoreResponse<?>>(response, HttpStatus.OK);
    }

}
