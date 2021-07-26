package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by jbuenosv@google.com
 */
public class VideoPornFramesAnalystResult implements Serializable {

    public static final Logger logger = Logger.getLogger(VideoPornFramesAnalystResult.class.getName());

    private List<VideoPornFrame> frames;

    public VideoPornFramesAnalystResult() {
        this.frames = new ArrayList<VideoPornFrame>();
    }

    public Integer getFramesCount() {
        return frames.size();
    }

    public List<VideoPornFrame> getFrames() {
        return frames;
    }

    public void setFrames(List<VideoPornFrame> frames) {
        this.frames = frames;
    }

    public void addFrame(int pos, VideoPornFrame frame) {
        this.frames.add(pos,frame);
    }

    public void addFrame(VideoPornFrame frame) {
        this.frames.add(frame);
    }

    public String toJson() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(" \"frames_analysis\": [" + NEW_LINE);
        for(int i = 0 ; i < getFramesCount() ; i ++) {
            result.append("{" + NEW_LINE);
            result.append("    \"id\": " + getFrames().get(i).getId() + "," + NEW_LINE);
            result.append("    \"type\": \"" + getFrames().get(i).getFrameType() + "\"," + NEW_LINE);
            result.append("    \"duration\": " + getFrames().get(i).getDuration() + NEW_LINE);
            result.append("}");
            if (i != (getFramesCount() - 1)) {
                result.append("," + NEW_LINE);
            }
            else {
                result.append(NEW_LINE);
            }
        }
        result.append("  ]");
        return result.toString();
    }

}
