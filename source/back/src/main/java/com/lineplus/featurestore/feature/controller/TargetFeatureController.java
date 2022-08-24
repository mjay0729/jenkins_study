package com.lineplus.featurestore.feature.controller;

import com.lineplus.featurestore.feature.domain.dto.request.EditTargetFeatureSetRequestDto;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeatureSetRequestDto;
import com.lineplus.featurestore.feature.domain.dto.response.CreatedTargetFeatureSetResponseDto;
import com.lineplus.featurestore.feature.domain.dto.response.TargetFeatureSetDetailResponseDto;
import com.lineplus.featurestore.feature.domain.dto.response.TargetFeatureSetResponseDto;
import com.lineplus.featurestore.feature.service.TargetFeatureService;
import com.lineplus.featurestore.global.reponse.FeatureStoreResponse;
import com.lineplus.featurestore.global.reponse.ResponseCodes;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "TargetFeature", description = "TargetFeature API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/targetFeature")
public class TargetFeatureController {

    @Autowired
    private final TargetFeatureService targetFeatureService;

    @Operation(summary = "post targetFeatureSet", description = "타겟 피처셋 정보 저장")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CreatedTargetFeatureSetResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/set")
    public ResponseEntity<FeatureStoreResponse<?>> saveTargetFeatureSet(
            @RequestBody @Valid TargetFeatureSetRequestDto requestDto
    ) throws NoSuchAlgorithmException {
        FeatureStoreResponse response = new FeatureStoreResponse(ResponseCodes.DEFAULT, this.targetFeatureService.saveTargetFeatureSet(requestDto));
        return new ResponseEntity<FeatureStoreResponse<?>>(response, HttpStatus.CREATED);
    }


    @Operation(summary = "post targetFeatureSetOperation", description = "타겟 피처셋 오퍼레이션 정보 저장")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CreatedTargetFeatureSetResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/set/operation")
    public ResponseEntity<FeatureStoreResponse<?>> saveTargetFeatureSetOperation(
            @RequestBody @Valid TargetFeatureSetRequestDto requestDto
    ) throws NoSuchAlgorithmException {
        FeatureStoreResponse response = new FeatureStoreResponse(ResponseCodes.DEFAULT, this.targetFeatureService.saveTargetFeatureSet(requestDto));
        return new ResponseEntity<FeatureStoreResponse<?>>(response, HttpStatus.CREATED);
    }


    @Operation(summary = "get targetFeatureSet List" , description = "제목과 해시태그로 targetFeatureSet 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = TargetFeatureSetResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/set")
    public ResponseEntity<FeatureStoreResponse<?>> getTargetFeatureSetList(
            @RequestParam(value = "name",defaultValue = "") String name
            , @RequestParam(value = "tag_list",defaultValue = "") List<String> tagList
            , Pageable pageable
    ){
        FeatureStoreResponse response = new FeatureStoreResponse(ResponseCodes.DEFAULT, this.targetFeatureService.getTargetFeatureSetList(name, tagList,pageable));
        return new ResponseEntity<FeatureStoreResponse<?>>(response, HttpStatus.OK);
    }


    @Operation(summary = "get targetFeatureSet detail information", description = "targetFeatureId 를 키로 targetFeatureSet 상세 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = TargetFeatureSetDetailResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/set/{targetFeatureSetId}")
    public ResponseEntity<FeatureStoreResponse<?>> getTargetFeatureSetDetail(
            @PathVariable("targetFeatureSetId") long targetFeatureSetId
    ){
        FeatureStoreResponse response = new FeatureStoreResponse(ResponseCodes.DEFAULT, this.targetFeatureService.getTargetFeatureSetDetailList(targetFeatureSetId));
        return new ResponseEntity<FeatureStoreResponse<?>>(response, HttpStatus.OK);
    }

    @Operation(summary = "put targetFeatureSet", description = "타겟 피처셋 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/set/{targetFeatureSetId}")
    public ResponseEntity<FeatureStoreResponse<?>> editTargetFeatureSet(
            @PathVariable("targetFeatureSetId") long targetFeatureSetId,
            @RequestBody @Valid EditTargetFeatureSetRequestDto editTargetFeatureSetRequestDto
            ){
        this.targetFeatureService.editTargetFeatureSetDetail(targetFeatureSetId,editTargetFeatureSetRequestDto);
        FeatureStoreResponse response = new FeatureStoreResponse(ResponseCodes.UPDATED);
        return new ResponseEntity<FeatureStoreResponse<?>>(response, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "delete targetFeatureSet", description = "타겟 피처셋 정보 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @DeleteMapping("/set/{targetFeatureSetId}")
    public ResponseEntity<FeatureStoreResponse<?>> deleteTargetFeatureSet(
            @PathVariable("targetFeatureSetId") long targetFeatureSetId
    ){
        this.targetFeatureService.deleteTargetFeatureSetDetail(targetFeatureSetId);
        return new ResponseEntity<FeatureStoreResponse<?>>(HttpStatus.ACCEPTED);
    }
}
