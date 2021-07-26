package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.infrastructure;

import java.util.Map;

/**
 * Created by jbuenosv@google.com
 */
public class PubSubEvent {

    private String data;
    private Map<String, String> attributes;
    private String messageId;
    private String publishTime;

    public PubSubEvent() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "PubSubEvent {" +
                "data='" + data + '\'' +
                ", attributes=" + attributes +
                ", messageId='" + messageId + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }

}
