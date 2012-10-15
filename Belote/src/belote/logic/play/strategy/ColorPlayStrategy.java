/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.play.strategy;

import belote.bean.Game;
import belote.bean.Player;
import belote.bean.announce.Announce;
import belote.bean.announce.AnnounceUnit;
import belote.bean.pack.card.Card;
import belote.bean.pack.card.suit.Suit;
import belote.logic.play.strategy.automat.executors.ColorAttackCard;
import belote.logic.play.strategy.automat.executors.ColorDefenceCard;
import belote.logic.play.strategy.validators.ColorGameCardValidator;

/**
 * CLPlayCardStrategy class.
 * Color suit strategy playing class.
 * @author Dimitar Karamanov
 */
public final class ColorPlayStrategy extends BasePlayStrategy {

    /**
     * Constructor.
     * @param game belote game instance.
     */
    public ColorPlayStrategy(final Game game) {
        super(game, new ColorGameCardValidator(game), new ColorAttackCard(game), new ColorDefenceCard(game));
    }

    /**
     * Returns next attack player.
     * @param attack the round attack card.
     * @return Player next attack player.
     */
    protected Player getNextAttackPlayer(final Card attack) {
        final Card maxTrumpCard = game.getTrickCards().findMaxSuitCard(getTrumpSuit());

        if (maxTrumpCard != null) {
            return game.getPlayerByCard(maxTrumpCard);
        }

        final Card maxSuitCard = game.getTrickCards().findMaxSuitCard(attack.getSuit());
        return game.getPlayerByCard(maxSuitCard);
    }

    /**
     * Returns trump suit.
     * @return Suit trump suit.
     */
    private Suit getTrumpSuit() {
        final Announce announce = game.getAnnounceList().getContractAnnounce();
        return AnnounceUnit.transformFromAnnounceSuitToSuit(announce.getAnnounceSuit());
    }
}
