package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by jbuenosv@google.com
 */
public class VideoPornAnalystResult implements Serializable {

    public static final Logger logger = Logger.getLogger(VideoPornAnalystResult.class.getName());

    private String id;
    private String inputFileName;
    private String outputFileName;
    private String videoInputGSUtilURI;
    private Boolean isPorn;
    private Boolean isAnalyzed;
    private Boolean hasResults;
    private Boolean hasFramesAnalysisResults;
    private VideoPornAnalystMetricsResult videoPornAnalystMetricsResult;
    private VideoPornFramesAnalystResult videoPornFramesAnalystResult;

    public VideoPornAnalystResult() {
        this.videoPornAnalystMetricsResult = new VideoPornAnalystMetricsResult();
        this.videoPornFramesAnalystResult = new VideoPornFramesAnalystResult();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getVideoInputGSUtilURI() {
        return videoInputGSUtilURI;
    }

    public void setVideoInputGSUtilURI(String videoInputGSUtilURI) {
        this.videoInputGSUtilURI = videoInputGSUtilURI;
    }

    public Boolean getPorn() {
        return isPorn;
    }

    public void setPorn(Boolean porn) {
        isPorn = porn;
    }

    public Boolean getAnalyzed() {
        return isAnalyzed;
    }

    public void setAnalyzed(Boolean analyzed) {
        isAnalyzed = analyzed;
    }

    public Boolean getHasResults() {
        return hasResults;
    }

    public void setHasResults(Boolean hasResults) {
        this.hasResults = hasResults;
    }

    public Boolean getHasFramesAnalysisResults() {
        return hasFramesAnalysisResults;
    }

    public void setHasFramesAnalysisResults(Boolean hasFramesAnalysisResults) {
        this.hasFramesAnalysisResults = hasFramesAnalysisResults;
    }

    public VideoPornAnalystMetricsResult getVideoPornAnalystMetricsResult() {
        return videoPornAnalystMetricsResult;
    }

    public void setVideoPornAnalystMetricsResult(VideoPornAnalystMetricsResult videoPornAnalystMetricsResult) {
        this.videoPornAnalystMetricsResult = videoPornAnalystMetricsResult;
    }

    public VideoPornFramesAnalystResult getVideoPornFramesAnalystResult() {
        return videoPornFramesAnalystResult;
    }

    public void setVideoPornFramesAnalystResult(VideoPornFramesAnalystResult videoPornFramesAnalystResult) {
        this.videoPornFramesAnalystResult = videoPornFramesAnalystResult;
    }

    public void addFrame(VideoPornFrame frame) {
        this.videoPornFramesAnalystResult.addFrame(frame);
    }

    /**
     * Gets the JSON representation
     * @return JSON representation
     */
    public String toJson() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append("{" + NEW_LINE);
        result.append("    \"id\": \"" + getId() + "\"," + NEW_LINE);
        result.append("    \"input_file_name\": \"" + getInputFileName() + "\"," + NEW_LINE);
        result.append("    \"output_file_name\": \"" + getOutputFileName()+ "\"," + NEW_LINE);
        result.append("    \"video_input_gs_uri\": \"" + getVideoInputGSUtilURI() + "\"," + NEW_LINE);
        result.append("    \"is_porn\": " + getPorn() + "," + NEW_LINE);
        result.append(getVideoPornAnalystMetricsResult().toJson()) ;
        if (getHasFramesAnalysisResults()) {
            result.append("," + NEW_LINE);
            result.append(getVideoPornFramesAnalystResult().toJson());
        } else {
            result.append(NEW_LINE);
        }
        result.append("}");
        return result.toString();
    }

}
