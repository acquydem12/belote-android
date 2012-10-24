/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package com.karamanov.beloteGame.gui.screen.base.message;

/**
 * UserMessageType class used in SystemMessage class as type.
 * @author Dimitar Karamanov
 */
public final class UserMessageType extends MessageType {
    
    public final static UserMessageType MT_KEY_PRESSED = new UserMessageType("MT_KEY_PRESSED");

    public final static UserMessageType MT_TOUCH_EVENT = new UserMessageType("MT_TOUCH_EVENT");

    public final static UserMessageType MT_EXIT_EVENT = new UserMessageType("MT_EXIT_EVENT");

    public final static UserMessageType MT_PAINT_EVENT = new UserMessageType("MT_PAINT_EVENT");
    
    public final static UserMessageType MT_CLOSE_END_GAME = new UserMessageType("MT_CLOSE_END_GAME");
    
    /**
     * Constructor package private.
     * @param type
     */
    private UserMessageType(final String type) {
        super(type);
    }

    /**
     * The method checks if this MessageType and specified object (MessageType) are equal.
     * @param obj specified object.
     * @return true if this MessageType is equal to specified object and false otherwise.
     */
    public boolean equals(final Object obj) {
        if (obj instanceof UserMessageType) {
            final UserMessageType messageType = (UserMessageType) obj;
            return type.equals(messageType.type);
        }
        return false;
    }

    /**
     * Returns hash code generated on message type ID value.
     * @return int hash code.
     */
    public int hashCode() {
        return hashCode(2);
    }
}
