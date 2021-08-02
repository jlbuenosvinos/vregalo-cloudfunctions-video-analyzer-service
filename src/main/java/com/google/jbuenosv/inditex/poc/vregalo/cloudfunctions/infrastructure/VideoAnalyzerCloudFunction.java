package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.infrastructure;

import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.exception.VideoAnalyzerServiceException;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.process.VideoInputProcessManager;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.process.VideoInputProcessManagerImpl;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain.VideoInput;

import java.util.Base64;
import java.util.logging.Logger;

/**
 * Created by jbuenosv@google.com
 */
public class VideoAnalyzerCloudFunction implements BackgroundFunction<PubSubEvent> {

    public static final Logger logger = Logger.getLogger(VideoAnalyzerCloudFunction.class.getName());

    @Override
    public void accept(PubSubEvent message, Context context) throws Exception {
        try {

            logger.info("Receive message event.");
            String jsonMessage = new String(Base64.getDecoder().decode(message.getData()));
            logger.info("Receive message data [ " + jsonMessage + "]");
            VideoInput videoInput = new VideoInput().fromJson(jsonMessage);
            VideoInputProcessManager videoInputProcessManager = new VideoInputProcessManagerImpl();
            videoInputProcessManager.processVideo(videoInput);
            logger.info("End receive message event.");
        }
        catch(Exception e) {
            logger.severe("Unable to process the event message  [" + message.toString() + "]");
            throw new VideoAnalyzerServiceException(e.getCause());
        }

    }

}