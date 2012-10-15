/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.play.strategy.automat.methods;

import belote.bean.Game;
import belote.bean.Player;
import belote.bean.pack.card.Card;
import belote.bean.pack.card.suit.Suit;
import belote.logic.play.strategy.automat.methods.base.BaseTrumpMethod;

/**
 * ColorObligatorySomeTrumpCard class.
 * PlayCardMethod which implements the logic of playing a trump card to fit the obligatory mode.
 * @author Dimitar Karamanov
 */
public final class ColorObligatorySomeTrumpCard extends BaseTrumpMethod {

    /**
     * Constructor.
     * @param game BelotGame instance.
     */
    public ColorObligatorySomeTrumpCard(final Game game) {
        super(game);
    }

    /**
     * Returns player's card.
     * @param player who is on turn.
     * @param trump suit.
     * @return Card object instance or null.
     */
    protected Card getPlayMethodCard(final Player player, final Suit trump) {
        final Card maxCard = player.getCards().findMaxSuitCard(trump);
        if (maxCard != null) {
            if (isMaxSuitCardLeft(maxCard, true)) {
                return player.getCards().findMinSuitCard(trump);
            } else {
                return maxCard;
            }
        }
        return null;
    }
}