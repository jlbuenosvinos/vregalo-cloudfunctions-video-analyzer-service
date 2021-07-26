package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.infrastructure.store;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.exception.VideoAnalyzerServiceException;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain.VideoPornAnalystResult;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.infrastructure.util.ConfigLoader;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by jbuenosv@google.com
 */
public class VideoAnalysisResultTopicPublisher {

    public static final Logger logger = Logger.getLogger(VideoAnalysisResultTopicPublisher.class.getName());

    private Properties props;
    private String projectName;
    private String videoAnalysisOutputTopicName;
    private Publisher publisher;

    public void init() {
        projectName = ConfigLoader.getInstance().getEnv(ConfigLoader.getInstance().getProperty("gcp.project.env.name"));
        videoAnalysisOutputTopicName = ConfigLoader.getInstance().getEnv(ConfigLoader.getInstance().getProperty("video.output.topic.env.name"));
        try {
            TopicName topicName = TopicName.of(projectName,videoAnalysisOutputTopicName);
            publisher = Publisher.newBuilder(topicName).build();
        } catch (IOException e) {
            throw new VideoAnalyzerServiceException(e.getMessage());
        }
    }

    /**
     * Private constructor
     */
    public VideoAnalysisResultTopicPublisher() {
    }

    /**
     * Stores the video analysis report into a topic
     * @param videoPornAnalystResult video analysis result
     * @return Message id identifier inside the topic
     */
    public String publish(VideoPornAnalystResult videoPornAnalystResult) {
        String messageId = null;
        try {
            logger.info("The message has to be published into [" + projectName + "," + videoAnalysisOutputTopicName + "].");

            ByteString data = ByteString.copyFromUtf8(videoPornAnalystResult.toJson());
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
            messageId = messageIdFuture.get();

            logger.info("The message [" + videoPornAnalystResult.getId() + "] has been published having [" + messageId + "] id in the topic:-)");
        } catch (Exception e) {
            logger.severe("Unable to publish the message due to: " + e.getMessage());
            throw new VideoAnalyzerServiceException(e.getMessage());
        }
        return messageId;
    }

    public void destroy() throws InterruptedException {
        if (publisher != null) {
            publisher.shutdown();
            publisher.awaitTermination(3, TimeUnit.SECONDS);
        }
    }

}
