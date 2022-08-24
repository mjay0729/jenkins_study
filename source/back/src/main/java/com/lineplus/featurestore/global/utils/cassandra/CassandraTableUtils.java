package com.lineplus.featurestore.global.utils.cassandra;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CassandraTableUtils {


    public static String createCassandraTableName(String src) throws NoSuchAlgorithmException {
        return hashing(src);
    }

    private static String hashing(String src) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(src.getBytes());
        StringBuilder sb = new StringBuilder();
        for(byte b : md.digest()){
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
