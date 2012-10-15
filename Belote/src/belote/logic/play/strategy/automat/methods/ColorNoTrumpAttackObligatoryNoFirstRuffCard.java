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
 * ColorNoTrumpAttackObligatoryNoFirstRuffCard class. PlayCardMethod which implements the logic of playing a obligatory bigger trump card in not trump attack
 * color game.
 * @author Dimitar Karamanov
 */
public final class ColorNoTrumpAttackObligatoryNoFirstRuffCard extends BaseTrumpMethod {

    /**
     * Constructor.
     * @param game BelotGame instance class.
     */
    public ColorNoTrumpAttackObligatoryNoFirstRuffCard(final Game game) {
        super(game);
    }

    /**
     * Returns player's card.
     * @param player who is on turn.
     * @param trump suit.
     * @return Card object instance or null.
     */
    public Card getPlayMethodCard(final Player player, final Suit trump) {
        final Card attackCard = game.getTrickCards().getAttackCard();
        final Card maxTrumpCard = game.getTrickCards().findMaxSuitCard(trump);

        if (attackCard != null && !attackCard.getSuit().equals(trump) && maxTrumpCard != null) {
            final Player trumpPlayer = game.getPlayerByCard(maxTrumpCard);
            if (trumpPlayer != null && !trumpPlayer.getTeam().equals(player.getTeam())) {
                final Card card = player.getCards().findMinAboveCard(maxTrumpCard);
                if (card != null) {
                    return player.getCards().getMaxSequenceCardAfter(card);
                }
            }
        }
        return null;
    }
}