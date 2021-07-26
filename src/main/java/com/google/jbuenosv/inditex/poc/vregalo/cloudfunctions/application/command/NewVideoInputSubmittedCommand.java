package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.command;

import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain.VideoInput;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain.VideoOutputResult;

/**
 * Created by jbuenosv@google.com
 */
public class NewVideoInputSubmittedCommand implements Command {

    private VideoInput videoInput;

    public VideoInput getVideoInput() {
        return videoInput;
    }

    public void setVideoInput(VideoInput videoInput) {
        this.videoInput = videoInput;
    }

}
