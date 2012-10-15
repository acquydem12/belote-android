/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.bean.announce.suit;

/**
 * Pass class.
 * @author Dimitar Karamanov
 */
public final class Pass extends AnnounceSuit {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4241790610370358080L;

	/**
     * Constructor.
     */
    protected Pass() {
        super(0);
    }

    /**
     * Color suit classes are Club, Diamond, Heart and Spade. If the objects is instance of some of them the
     * result is true otherwise is false.
     * @return boolean true if is color suit false otherwise.
     */
    public boolean isColorSuit() {
        return false;
    }

        /**
     * Returns base points.
     * @return int base points.
     */
    public final int getBasePoints() {
        return 0;
    }
}
