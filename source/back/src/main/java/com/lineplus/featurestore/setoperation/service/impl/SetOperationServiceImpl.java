package com.lineplus.featurestore.setoperation.service.impl;

import com.lineplus.featurestore.setoperation.repository.OperatedSetRepository;
import com.lineplus.featurestore.setoperation.repository.OperatedSetTagRepository;
import com.lineplus.featurestore.setoperation.service.SetOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetOperationServiceImpl implements SetOperationService {

    @Autowired
    OperatedSetRepository operatedSetRepository;

    @Autowired
    OperatedSetTagRepository operatedSetTagRepository;


}
