package com.lineplus.featurestore.statistics.controller;

import com.lineplus.featurestore.statistics.domain.dto.request.FeatureStatisticsRequestDto;
import com.lineplus.featurestore.statistics.domain.dto.response.StatisticsResponseDto;
import com.lineplus.featurestore.statistics.service.StatisticsService;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/statistics")
@EnableBatchProcessing
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/featureStatisticsList")
    private List<StatisticsResponseDto> getFeatureStatisticsList(FeatureStatisticsRequestDto featureStatisticsRequestDTO, Pageable pageable){
        List<StatisticsResponseDto> result =  this.statisticsService.getFeatureStatisticsList(featureStatisticsRequestDTO, pageable);
        return result;
    }

    @GetMapping("/sparkSubmitTest")
    private void executeSpark(){
        Process process;
        String result;
        try{
            String[] cmd = {"spark-submit","--master", "spark://lab.bigle.ai:7077","--deploy-mode", "client", "/Users/user/dxproject/line/pyspark/hello-world-module.py"};
            process = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while((result = bufferedReader.readLine()) != null){
                System.out.println(result);
            }
            process.waitFor();
            System.out.println("exit: " + process.exitValue());
            process.destroy();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
