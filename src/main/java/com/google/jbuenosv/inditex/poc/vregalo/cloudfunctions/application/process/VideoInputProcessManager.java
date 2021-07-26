package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.process;

import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain.VideoInput;
import com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain.VideoOutputResult;

/**
 * Created by jbuenosv@google.com
 */
public interface VideoInputProcessManager {

    void processVideo(VideoInput videoInputResult);

}
