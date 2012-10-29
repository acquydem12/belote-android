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

import com.karamanov.beloteGame.Belote;
import com.karamanov.beloteGame.gui.screen.base.message.Messageable;
import com.karamanov.beloteGame.gui.screen.base.message.UserMessage;
import com.karamanov.beloteGame.gui.screen.base.message.UserMessageType;

/**
 * GameActivity class.
 * @author Dimitar Karamanov
 */
public class GameActivity extends Activity {

    /**
     * Constructor.
     * @param suppressKeyEvents indicates if the key events are suppressed.
     */
    public GameActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
     * Adds user message to the end of the queue.
     * @param message new message.
     */
    public final void triggerMessage(final UserMessage message) {
        if (getApplication() instanceof Belote) {
            Belote belote = (Belote) getApplication();
            belote.getMessageProcessor().sendMessage(message);
        }
    }

    /**
     * Adds message listener for the concrete message type.
     * @param messageType concrete user message type.
     * @param messageable message listener.
     */
    public final void addMessageListener(final UserMessageType messageType, final Messageable messageable) {
        if (getApplication() instanceof Belote) {
            Belote belote = (Belote) getApplication();
            belote.getMessageProcessor().addMessageListener(messageType, messageable);
        }
    }

    /**
     * Removes message listener for the concrete message type.
     * @param messageType concrete user message type.
     */
    public final void removeMessageListener(final UserMessageType messageType) {
        if (getApplication() instanceof Belote) {
            Belote belote = (Belote) getApplication();
            belote.getMessageProcessor().removeMessageListener(messageType);
        }
    }
}
