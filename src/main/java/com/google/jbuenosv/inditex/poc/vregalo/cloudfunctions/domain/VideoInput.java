package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain;

import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.exception.VideoAnalyzerServiceException;
import java.io.Serializable;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by jbuenosv@google.com
 */
public class VideoInput  implements Serializable {

    public static final Logger logger = Logger.getLogger(VideoInput.class.getName());

    private String id;
    private String name;
    private String bucket;
    private String generation;
    private String videoURL;
    private String videoGSUtilURI;

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getVideoGSUtilURI() {
        return videoGSUtilURI;
    }

    public void setVideoGSUtilURI(String videoGSUtilURI) {
        this.videoGSUtilURI = videoGSUtilURI;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "VideoInput {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bucket='" + bucket + '\'' +
                ", generation='" + generation + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", videoGSUtilURI='" + videoGSUtilURI + '\'' +
                '}';
    }

    /**
     * Gets the JSON representation
     * @return Video Input JSON representation
     */
    public String toJson() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append("{" + NEW_LINE);
        result.append("    \"id\": \"" + getId() + "\"," + NEW_LINE);
        result.append("    \"name\": \"" + getName() + "\"," + NEW_LINE);
        result.append("    \"bucket\": \"" + getBucket() + "\","  + NEW_LINE);
        result.append("    \"generation\": \"" + getGeneration() + "\","  + NEW_LINE);
        result.append("    \"video_url\": \"" + getVideoURL() + "\","  + NEW_LINE);
        result.append("    \"video_gs_uri\": \"" + getVideoGSUtilURI() + "\""  + NEW_LINE);
        result.append("}");
        return result.toString();
    }

    public VideoInput fromJson(String videoJson) {
        VideoInput video = new VideoInput();

        JsonNode nameNode;
        JsonNode jsonItem;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(videoJson);

            // id
            nameNode = rootNode.path("id");
            video.setId(nameNode.textValue());
            // name
            nameNode = rootNode.path("name");
            video.setName(nameNode.textValue());
            // bucket
            nameNode = rootNode.path("bucket");
            video.setBucket(nameNode.textValue());
            // generation
            nameNode = rootNode.path("generation");
            video.setGeneration(nameNode.textValue());
            // video_url
            nameNode = rootNode.path("video_url");
            video.setVideoURL(nameNode.textValue());

            video.setVideoGSUtilURI("gs://" + video.getBucket() + "/" + video.getName() );

            logger.info("Video object populated from JSON.");
        }
        catch (Exception e) {
            logger.severe(e.getMessage());
            throw new VideoAnalyzerServiceException(e) ;
        }

        return video;
    }

}
