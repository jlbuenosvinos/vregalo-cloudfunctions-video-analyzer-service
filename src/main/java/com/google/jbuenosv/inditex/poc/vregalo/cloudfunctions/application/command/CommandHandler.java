package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.command;

/**
 * Created by jbuenosv@google.com
 */
public interface CommandHandler {
    /**
     * Executes the command
     */
    void execute(Command command) ;
}
