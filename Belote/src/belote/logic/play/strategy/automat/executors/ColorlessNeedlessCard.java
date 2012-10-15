/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.play.strategy.automat.executors;

import belote.bean.Game;
import belote.bean.Player;
import belote.bean.pack.card.Card;
import belote.logic.play.strategy.automat.executors.base.PlayCardExecutor;
import belote.logic.play.strategy.automat.methods.ColorlessChooseTwoSuitsCard;
import belote.logic.play.strategy.automat.methods.ColorlessClearCard;
import belote.logic.play.strategy.automat.methods.ColorlessMinAllMasterCards;
import belote.logic.play.strategy.automat.methods.MaxSingleNoHandCardToPartner;
import belote.logic.play.strategy.automat.methods.MinAllCard;
import belote.logic.play.strategy.automat.methods.MinMeterSuitCard;
import belote.logic.play.strategy.automat.methods.SingleNoMajorCard;
import belote.logic.play.strategy.automat.methods.SingleNoMaxCard;

/**
 * ColorlessNeedlessCard class.
 * Implements the logic to play no needed card and is called after obligatory rules.
 * Used in AllTrumptXXX and NotTrumpXXX executors.
 * @author Dimitar Karamanov
 */
public final class ColorlessNeedlessCard extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game a BelotGame instance.
     */
    public ColorlessNeedlessCard(final Game game) {
        super(game);
        //Register play card methods.
        register(new MaxSingleNoHandCardToPartner(game));
        register(new ColorlessMinAllMasterCards(game));
        register(new SingleNoMajorCard(game));
        register(new ColorlessClearCard(game));
        register(new SingleNoMaxCard(game));
        register(new MinMeterSuitCard(game));
        register(new ColorlessChooseTwoSuitsCard(game));
        register(new MinAllCard(game));
    }

    /**
     * Handler method providing the user to write additional code which is executed after the getPlayerCard(Player).
     * @param player for which is called the executor.
     * @param result the result of the method getPlayerCard(Player).
     */
    protected void afterExecution(final Player player, final Card result) {
        if (result != null) {
            final Card attackCard = game.getTrickCards().getAttackCard();
            if (attackCard != null && !attackCard.getSuit().equals(result.getSuit())) {
                player.getMissedSuits().add(attackCard.getSuit());
                player.getUnwantedSuits().add(result.getSuit());
            }
        }
    }
}
