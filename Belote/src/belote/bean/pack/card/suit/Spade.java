/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.bean.pack.card.suit;

/**
 * Suit spade class.
 * @author Dimitar Karamanov
 */
public final class Spade extends Suit {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3200954318160833662L;
	/**
     * Spade ID constant.
     */
    private static final int ID = 3;

    /**
     * Constructor.
     */
    protected Spade() {
        super(ID);
    }

    /**
     * Returns suit's color.
     * @return String suit's color.
     */
    public String getSuitColor() {
        return "black";
    }
}