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
 * ColorNoObligatorySecondDeffenceTrumpCard class.
 * PlayCardMethod which implements the logic of playing a card when the player is on second defence position.
 * (Analyze if to play trump or not).
 * @author Dimitar Karamanov
 */
public final class ColorNoObligatorySecondDeffenceTrumpCard extends BaseTrumpMethod {

    /**
     * Constructor.
     * @param game BelotGame instance.
     */
    public ColorNoObligatorySecondDeffenceTrumpCard(final Game game) {
        super(game);
    }

    /**
     * Returns player's card.
     * @param player who is on turn.
     * @param trump suit.
     * @return Card object instance or null.
     */
    protected Card getPlayMethodCard(final Player player, final Suit trump) {
        if (trump != null && isSecondDefencePosition()) {
            final int count = player.getCards().getSuitCount(trump);
            final Card maxTrumpCard = player.getCards().findMaxSuitCard(trump);
            final Card maxAttackSuitCard = game.getTrickCards().getHandAttackSuitCard();
            final Player lastPlayer = game.getPlayerAfter(player);
            final boolean isAnnouncePlayer = lastPlayer.equals(game.getAnnounceList().getOpenContractAnnouncePlayer());

            //If the last(the 4th round position) player is the color game announcer and the attack cards is in his|her missed suit and
            //has trump cards better to skip to play trump.
            final Card attackCard = game.getTrickCards().getAttackCard();
            if (attackCard != null && isAnnouncePlayer && !lastPlayer.getMissedSuits().contains(trump) && lastPlayer.getMissedSuits().contains(attackCard.getSuit())) {
                return null;
            }

            // if the max attack card is not the max left card better to play trump
            if (maxAttackSuitCard != null && !isMaxSuitCardLeft(maxAttackSuitCard, false) && maxTrumpCard != null) {
                if (isPlayerTeamAnnounce(player)) {
                    return player.getCards().findMinSuitCard(trump);
                } else {
                    if (isMaxSuitCardLeft(maxTrumpCard, true)) {
                        if (count > SINGLE_CARD_COUNT) {
                            return player.getCards().findMinSuitCard(trump);
                        }
                    } else {
                        return maxTrumpCard;
                    }
                }
            }
        }
        return null;
    }
}