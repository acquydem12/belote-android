/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.announce.factory.automat.methods.suitDeterminators.base;

import belote.bean.Player;
import belote.bean.pack.card.suit.Suit;

/**
 * SuitDeterminator interface.
 * @author Dimitar Karamanov
 */
public interface SuitDeterminator {

    /**
     * Returns the determined suit.
     * @param player which has to declare the next announce.
     * @return Suit instance or null.
     */
    Suit determineSuit(Player player);

}
