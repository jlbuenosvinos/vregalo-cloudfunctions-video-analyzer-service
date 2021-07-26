package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.command;

import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.exception.VideoAnalyzerServiceException;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain.VideoInput;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain.VideoPornAnalystResult;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.infrastructure.store.VideoAnalysisResultTopicPublisher;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.infrastructure.util.UUIDGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.logging.Logger;

/**
 * Created by jbuenosv@google.com
 */
public class NewVideoInputSubmittedCommandHandler implements  CommandHandler {

    public static final Logger logger = Logger.getLogger(NewVideoInputSubmittedCommandHandler.class.getName());

    private UUIDGenerator uuidGenerator;
    private VideoAnalysisResultTopicPublisher topicPublisher;

    /**
     * Default constructor
     */
    public NewVideoInputSubmittedCommandHandler() {
        this.uuidGenerator = new UUIDGenerator();
        this.topicPublisher = new VideoAnalysisResultTopicPublisher();
    }

    /**
     * Executes the command
     * @param command command to be executed
     */
    @Override
    public void execute(Command command) {
        NewVideoInputSubmittedCommand videoInputSubmittedCommand = (NewVideoInputSubmittedCommand)command;
        VideoInput video = videoInputSubmittedCommand.getVideoInput();

        try {
            logger.info("Ready to process the video input.");
            ApplicationContext beansContext = new ClassPathXmlApplicationContext("beans.xml");
            VideoInputPornAnalyzer  videoInputPornAnalyzer = (VideoInputPornAnalyzer)beansContext.getBean("pornAnalyzer");
            // video analysis
            VideoPornAnalystResult result = videoInputPornAnalyzer.analyzeVideoInput(video);
            logger.info("The video has been analyzed.");

            logger.info("VideoPornAnalystResult [" + result.toJson() + "]");

            VideoAnalysisResultTopicPublisher publisher = (VideoAnalysisResultTopicPublisher)beansContext.getBean("pornAnalyzerResultPublisher");
            String messageId = publisher.publish(result);

            logger.info("VideoPornAnalystResult has been published having [" + messageId + "] as message id in the topic.");
        }
        catch(Exception e) {
            logger.severe("Unable to process the video input due to [" + e.getMessage()  +  "]");
            throw new VideoAnalyzerServiceException(e);
        }

    }

}
