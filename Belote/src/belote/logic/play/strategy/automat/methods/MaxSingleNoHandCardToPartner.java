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
import belote.logic.play.strategy.automat.base.PlayCardMethod;
import belote.logic.play.strategy.automat.methods.base.BaseMethod;

/**
 * SingleCardToPartner class.
 * PlayCardMethod which implements the logic of playing single card to partner if he is the best card player
 * and have played the maximum left suit card.
 * @author Dimitar Karamanov
 */
public final class MaxSingleNoHandCardToPartner extends BaseMethod {

    /**
     * Play card helper method.
     */
    private final PlayCardMethod maxSingleNoHandCard;

    /**
     * Constructor.
     * @param game BelotGame instance class.
     */
    public MaxSingleNoHandCardToPartner(final Game game) {
        super(game);

        maxSingleNoHandCard = new MaxSingleNoHandCard(game);
    }

    /**
     * Returns player's card.
     * @param player who is on turn.
     * @return Card object instance or null.
     */
    public Card getPlayMethodCard(final Player player) {
        final Card handAttackSuitCard = game.getTrickCards().getHandAttackSuitCard();
        if (handAttackSuitCard != null) {
            final Player partner = player.getPartner();
            final Player handPlayer = game.getPlayerByCard(handAttackSuitCard);
            if (handPlayer != null) {
                if (handPlayer.equals(partner) && isMaxSuitCardLeft(handAttackSuitCard, false)) {
                    return maxSingleNoHandCard.getPlayerCard(player);
                }
            }
        }
        return null;
    }
}