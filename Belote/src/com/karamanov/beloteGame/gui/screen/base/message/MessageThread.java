/*
 * Copyright (c) i:FAO AG. All Rights Reserved.
 * MessageThread.java
 * cytric mobile application.
 *
 * Created by mobile team Aug 17, 2010
 */
package com.karamanov.beloteGame.gui.screen.base.message;


/**
 * @author Dimitar Karamanov
 */
public final class MessageThread extends Thread {

    /**
     * Suspend indicator.
     */
    private boolean suspended;

    /**
     * Message queue.
     */
    private final MessageQueue messageQueue;

    /**
     * Constructor.
     *
     * @param messageQueue which the thread will process.
     */
    public MessageThread(MessageQueue messageQueue) {
        setSuspended(false);
        setDaemon(true);
        this.messageQueue = messageQueue;
    }

    /**
     * Thread business method.
     */
    public void run() {
        final Thread mythread = Thread.currentThread();
        if (mythread == this) {
            while (!suspended) {
                messageQueue.processMessage();
            }
        }
    }

    /**
     * @param suspended the suspended to set
     */
    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }
}
