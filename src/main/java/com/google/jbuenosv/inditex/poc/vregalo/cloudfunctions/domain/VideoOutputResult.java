package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain;

import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.exception.VideoAnalyzerServiceException;

import java.io.Serializable;
import java.util.Base64;

/**
 * Created by jbuenosv@google.com
 */
public class VideoOutputResult implements Serializable {

    private String fileName;
    private String transportResult;
    private String result;

    public VideoOutputResult(String transportResult) {
        this.transportResult = transportResult;
    }

    public void setTransportResult(String transportResult) {
        this.transportResult = transportResult;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getResult() {
        if (transportResult != null) {
            return new String(Base64.getDecoder().decode(transportResult));
        }
        else {
            throw new VideoAnalyzerServiceException("Unable to convert the video output result.");
        }
    }

    @Override
    public String toString() {
        return "VideoOutputResult {" +
                "fileName='" + fileName + '\'' +
                ", transportResult='" + transportResult + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

}
