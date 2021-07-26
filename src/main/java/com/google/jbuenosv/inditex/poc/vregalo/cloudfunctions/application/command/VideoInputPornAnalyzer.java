package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.command;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.videointelligence.v1.*;

import com.google.cloud.videointelligence.v1.VideoIntelligenceServiceClient;

import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.exception.VideoAnalyzerServiceException;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain.*;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class VideoInputPornAnalyzer {

    public static final Logger logger = Logger.getLogger(VideoInputPornAnalyzer.class.getName());

    private VideoIntelligenceServiceClient client;

    public void init() {
        try {
            client = VideoIntelligenceServiceClient.create();
        } catch (IOException e) {
            logger.severe("Unable to create a VideoIntelligenceServiceClient client due to [" + e.getMessage() + "]");
            throw new VideoAnalyzerServiceException(e);
        }
    }

    /**
     * Performs an porn detection analysis
     * @param videoInput
     * @return VideoPornAnalystResult
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    public VideoPornAnalystResult analyzeVideoInput(VideoInput videoInput) throws ExecutionException, InterruptedException, Exception {
        Instant start = null;
        Instant finish = null;
        List<VideoAnnotationResults> results = null;
        VideoPornAnalystResult videoPornAnalystResult = new VideoPornAnalystResult();
        VideoPornAnalystMetricsResult videoPornAnalystMetricsResult = new VideoPornAnalystMetricsResult();
        VideoPornFramesAnalystResult videoPornFramesAnalystResult = new VideoPornFramesAnalystResult();

        try {

            logger.info("Video GSUtil URI [" + videoInput.getVideoGSUtilURI() + "].");

            videoPornAnalystResult.setId(videoInput.getId());
            videoPornAnalystResult.setInputFileName(videoInput.getName());
            videoPornAnalystResult.setOutputFileName(videoInput.getName().substring(0,videoInput.getName().indexOf(".")) + ".json");
            videoPornAnalystResult.setVideoInputGSUtilURI(videoInput.getVideoGSUtilURI());

            AnnotateVideoRequest request =
                    AnnotateVideoRequest.newBuilder()
                            .setInputUri(videoInput.getVideoGSUtilURI())
                            .addFeatures(Feature.EXPLICIT_CONTENT_DETECTION)
                            .build();

            start = Instant.now();
            OperationFuture<AnnotateVideoResponse, AnnotateVideoProgress> response = client.annotateVideoAsync(request);
            results = response.get().getAnnotationResultsList();
            finish = Instant.now();

            videoPornAnalystMetricsResult.setTimeElapsedMillis(Duration.between(start,finish).toMillis());
            videoPornAnalystMetricsResult.setTimeElapsedNanos(Duration.between(start,finish).toNanos());
            videoPornAnalystMetricsResult.setTimeElapsedSeconds(Duration.between(start,finish).toSeconds());
            videoPornAnalystResult.setVideoPornAnalystMetricsResult(videoPornAnalystMetricsResult);

            videoPornAnalystResult.setPorn(Boolean.FALSE);

            if (results != null) {

                videoPornAnalystResult.setAnalyzed(Boolean.TRUE);

                if (results.isEmpty()) {

                    logger.info("No results.");
                    videoPornAnalystResult.setPorn(Boolean.FALSE);
                    // No results
                    videoPornAnalystResult.setHasResults(Boolean.FALSE);
                    videoPornAnalystResult.setHasFramesAnalysisResults(Boolean.FALSE);

                } else {

                    // frames analyst

                    if (results.get(0).getExplicitAnnotation().getFramesCount() == 0) {
                        logger.info("No pornography has been detected :-)");
                        videoPornAnalystResult.setPorn(Boolean.FALSE);
                        videoPornAnalystResult.setHasFramesAnalysisResults(Boolean.FALSE);
                    }
                    else {

                        logger.info("Frames Analysis Frames count [" + results.get(0).getExplicitAnnotation().getFramesCount() + "]");

                        ExplicitContentFrame currentExplicitFrame = null;
                        Likelihood currentLikelihood = null;

                        for (int i = 0 ; i < results.get(0).getExplicitAnnotation().getFramesCount() ; i ++) {

                            currentExplicitFrame = results.get(0).getExplicitAnnotation().getFrames(i);
                            currentLikelihood = currentExplicitFrame.getPornographyLikelihood();

                            if (currentLikelihood.name().equalsIgnoreCase(Likelihood.VERY_LIKELY.name()) ||
                                    currentLikelihood.name().equalsIgnoreCase(Likelihood.LIKELY.name())) {

                                videoPornAnalystResult.setPorn(Boolean.TRUE);
                                videoPornAnalystResult.setHasFramesAnalysisResults(Boolean.TRUE);

                                Double frameDuration = currentExplicitFrame.getTimeOffset().getSeconds() + currentExplicitFrame.getTimeOffset().getNanos() / 1e9;
                                logger.info("Frame [" + i + "," + frameDuration + "," + currentLikelihood.name() + "]");

                                videoPornAnalystResult.addFrame(new VideoPornFrame(i,currentLikelihood.name(),frameDuration));

                            }
                            else {
                                logger.info("Frame [" + i + "," + currentLikelihood.name() + "]");
                            }

                        } // end for

                        if (videoPornAnalystResult.getPorn()) {
                            logger.info("The video [" + videoInput.getName() + "] has porn :-(");
                        }
                        else {
                            logger.info("The video [" + videoInput.getName() + "] has No porn :-(");
                        }
                        // end frames analyst

                    }

                }

            }
            else {
                videoPornAnalystResult.setAnalyzed(Boolean.FALSE);
            }

        } finally {
        }

        return videoPornAnalystResult;
    }

    public void destroy() {
        if (client != null) {
            client.close();
            logger.info("VideoIntelligenceServiceClient client has been closed.");
        }
    }

}
