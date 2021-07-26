package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by jbuenosv@google.com
 */
public class VideoPornFrame implements Serializable {

    public static final Logger logger = Logger.getLogger(VideoPornFrame.class.getName());

    private Integer id;
    private String frameType;
    private Double duration;

    public VideoPornFrame() {
    }

    public VideoPornFrame(Integer id, String frameType, Double duration) {
        this.id = id;
        this.frameType = frameType;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "VideoPornFrame {" +
                "id=" + id +
                ", frameType='" + frameType + '\'' +
                ", duration=" + duration +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrameType() {
        return frameType;
    }

    public void setFrameType(String frameType) {
        this.frameType = frameType;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

}
