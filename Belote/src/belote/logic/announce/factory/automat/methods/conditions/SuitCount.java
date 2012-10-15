/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.announce.factory.automat.methods.conditions;

import belote.bean.Player;
import belote.bean.pack.card.suit.Suit;
import belote.logic.announce.factory.automat.methods.conditions.base.AnnounceCondition;
import belote.logic.announce.factory.automat.methods.suitDeterminators.base.SuitDeterminator;

/**
 * SuitCount class.
 * Returns if the announce player has ant minimum cards from dynamically determined suit.
 * @author Dimitar Karamanov
 */
public class SuitCount implements AnnounceCondition {

    /**
     * Suit determinator.
     */
    private final SuitDeterminator suitDeterminator;

    /**
     * Minimum needed suit cards count.
     */
    private final int count;

    /**
     * Constructor.
     * @param suitDeterminator used to determine dynamically the suit.
     * @param count the minimum needed.
     */
    public SuitCount(final SuitDeterminator suitDeterminator, final int count) {
        this.suitDeterminator = suitDeterminator;
        this.count = count;
    }

    /**
     * The method which returns the result of condition.
     * @param player which has to declare next game announce.
     * @return boolean true if the condition fits, false otherwise.
     */
    public boolean process(final Player player) {
        final Suit suit = suitDeterminator.determinateSuit(player);

        if (suit != null) {
            return player.getCards().getSuitCount(suit) >= count;
        }

        return false;
    }
}
