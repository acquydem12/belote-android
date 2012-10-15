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
import belote.bean.pack.card.rank.Rank;
import belote.bean.pack.card.suit.Suit;
import belote.logic.announce.factory.automat.methods.conditions.base.AnnounceCondition;
import belote.logic.announce.factory.automat.methods.suitDeterminators.base.SuitDeterminator;

/**
 * PlayerCard class. Returns true if the announce player has card from provided rank and suit.
 * @author Dimitar Karamanov
 */
public final class HasCard implements AnnounceCondition {

    /**
     * Cards rank.
     */
    private final Rank rank;

    /**
     * SuitDeterminator which dynamically by provided player determinate the suit.
     */
    private final SuitDeterminator suitDeterminator;

    /**
     * Constructor.
     * @param rank of the card.
     * @param suitDeterminator used to determine the suit.
     */
    public HasCard(final Rank rank, final SuitDeterminator suitDeterminator) {
        this.rank = rank;
        this.suitDeterminator = suitDeterminator;
    }

    /**
     * The method which returns the result of condition.
     * @param player which has to declare next game announce.
     * @return boolean true if the condition fits, false otherwise.
     */
    public boolean process(final Player player) {
        final Suit suit = suitDeterminator.determinateSuit(player);

        if (suit != null) {
            return player.getCards().findCard(rank, suit) != null;
        }

        return false;
    }
}
