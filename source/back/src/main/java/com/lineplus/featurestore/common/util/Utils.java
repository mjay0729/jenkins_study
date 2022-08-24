package com.lineplus.featurestore.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);
    private Utils(){

    }

    private static class InnerUtils{
        private static final Utils instance = new Utils();
    }

    public static Utils getInstance(){
        return InnerUtils.instance;
    }

    public void execSpark(List<String> parameter){
        Process process;
        String line;
        String result = "";
        try{
            String[] cmd = {"spark-submit","--master", "spark://lab.bigle.ai:7077","--deploy-mode", "client"};
            List<String> cmdList = Arrays.asList(cmd);
            cmdList.addAll(parameter);
            cmd = cmdList.toArray(new String[cmdList.size()]);
            process = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }
            process.waitFor();
            logger.info("pyspark execute message : " + result);
            logger.info("pyspark exit message: " + process.exitValue());
            process.destroy();
        }catch(Exception e){
            logger.error("pyspark error message : " + e.getMessage());
        }
    }
}
