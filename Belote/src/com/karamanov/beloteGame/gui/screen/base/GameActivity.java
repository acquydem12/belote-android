package com.karamanov.beloteGame.gui.screen.base;

/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
import android.app.Activity;
import android.os.Bundle;

import com.karamanov.beloteGame.gui.screen.base.message.MessageQueue;
import com.karamanov.beloteGame.gui.screen.base.message.MessageTypeRegister;
import com.karamanov.beloteGame.gui.screen.base.message.Messageable;
import com.karamanov.beloteGame.gui.screen.base.message.UserMessage;
import com.karamanov.beloteGame.gui.screen.base.message.UserMessageType;

/**
 * GameActivity class.
 * @author Dimitar Karamanov
 */
public class GameActivity extends Activity implements Runnable {

    /**
     * Used to indicate if the working thread was interrupted.
     */
    private boolean interrupted;

    /**
     * Working thread.
     */
    protected final Thread thread;

    /**
     * Message queue.
     */
    private final MessageQueue messageQueue = new MessageQueue();

    /**
     * Constructor.
     * @param suppressKeyEvents indicates if the key events are suppressed.
     */
    public GameActivity() {
        super();
        thread = new Thread(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thread.start();
    }

    /**
     * Initializes user message type.
     * @param messageTypeID id of the message type.
     * @return UserMessageType instance.
     */
    protected final UserMessageType initUserMessageType(String messageTypeID) {
        UserMessageType result = MessageTypeRegister.getRegister().getUserMessageType(messageTypeID);

        if (result == null) {
            result = MessageTypeRegister.getRegister().registerUserMessageType(messageTypeID);
        }

        return result;
    }

    /**
     * Stops the main game loop.
     */
    public final void stop() {
        this.interrupted = true;
    }

    /**
     * The canvas is being displayed. Stop the event handling and animation thread.
     */
    protected void onResume() {
        super.onResume();
    }

    /**
     * The canvas is being removed from the screen. Stop the event handling and animation thread.
     */
    protected void onPause() {
        super.onPause();
    }

    /**
     * Working thread run method.
     */
    public final void run() {
        try {
            final Thread mythread = Thread.currentThread();

            while (!interrupted && mythread == thread) {
                messageQueue.processMessage();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sleeps for provided millisecond.
     * @param ms provided millisecond.
     */
    public final void sleep(final long ms) {
        if (ms > 0) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ex) {
                // ex.printStackTrace();
            }
        }
    }

    /**
     * Adds user message to the end of the queue.
     * @param message new message.
     */
    public final void triggerMessage(final UserMessage message) {
        messageQueue.addMessage(message);
    }

    /**
     * Adds message listener for the concrete message type.
     * @param messageType concrete user message type.
     * @param messageable message listener.
     */
    public final void addMessageListener(final UserMessageType messageType, final Messageable messageable) {
        messageQueue.addMessageListener(messageType, messageable);
    }

    /**
     * Removes message listener for the concrete message type.
     * @param messageType concrete user message type.
     */
    public final void removeMessageListener(final UserMessageType messageType) {
        messageQueue.removeMessageListener(messageType);
    }
}
