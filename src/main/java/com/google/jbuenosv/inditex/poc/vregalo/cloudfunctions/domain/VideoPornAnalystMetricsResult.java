package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain;

import java.io.Serializable;

/**
 * Created by jbuenosv@google.com
 */
public class VideoPornAnalystMetricsResult implements Serializable {

    private Long timeElapsedMillis ;
    private Long timeElapsedSeconds ;
    private Long timeElapsedNanos ;

    public VideoPornAnalystMetricsResult() {
    }

    public VideoPornAnalystMetricsResult(Long timeElapsedMillis, Long timeElapsedSeconds, Long timeElapsedNanos) {
        this.timeElapsedMillis = timeElapsedMillis;
        this.timeElapsedSeconds = timeElapsedSeconds;
        this.timeElapsedNanos = timeElapsedNanos;
    }

    public Long getTimeElapsedMillis() {
        return timeElapsedMillis;
    }

    public void setTimeElapsedMillis(Long timeElapsedMillis) {
        this.timeElapsedMillis = timeElapsedMillis;
    }

    public Long getTimeElapsedSeconds() {
        return timeElapsedSeconds;
    }

    public void setTimeElapsedSeconds(Long timeElapsedSeconds) {
        this.timeElapsedSeconds = timeElapsedSeconds;
    }

    public Long getTimeElapsedNanos() {
        return timeElapsedNanos;
    }

    public void setTimeElapsedNanos(Long timeElapsedNanos) {
        this.timeElapsedNanos = timeElapsedNanos;
    }

    /**
     * Gets the JSON representation
     * @return Video Input JSON representation
     */
    public String toJson() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append("\"analysis_duration_time\": {" + NEW_LINE);
        result.append("    \"seconds\": " + getTimeElapsedSeconds() + "," + NEW_LINE);
        result.append("    \"milliseconds\": " + getTimeElapsedMillis() + "," + NEW_LINE);
        result.append("    \"nanoseconds\": " + getTimeElapsedNanos() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

}
