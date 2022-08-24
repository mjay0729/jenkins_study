package com.lineplus.featurestore.setoperation.controller;


import com.lineplus.featurestore.setoperation.service.SetOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/setOperation")
public class setOperationController {

    @Autowired
    private final SetOperationService setOperationService;
}

