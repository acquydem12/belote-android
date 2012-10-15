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
import belote.bean.pack.card.Card;
import belote.logic.play.strategy.automat.executors.AllTrumpAttackCard;
import belote.logic.play.strategy.automat.executors.AllTrumpFirstDefencePositionCard;
import belote.logic.play.strategy.automat.executors.AllTrumpSecondDefencePositionCard;
import belote.logic.play.strategy.automat.executors.AllTrumpThirdDefencePositionCard;
import belote.logic.play.strategy.validators.AllTrumpCardValidator;

/**
 * AllTrumpPlayStrategy class. All trump game strategy rules.
 * @author Dimitar Karamanov
 */
public final class AllTrumpPlayStrategy extends BasePlayStrategy {

    /**
     * Constructor.
     * @param game BelotGame instance.
     */
    public AllTrumpPlayStrategy(final Game game) {
        super(game, new AllTrumpCardValidator(game), new AllTrumpAttackCard(game), new AllTrumpFirstDefencePositionCard(game),
                new AllTrumpSecondDefencePositionCard(game), new AllTrumpThirdDefencePositionCard(game));
    }

    /**
     * Returns next attack player.
     * @param attack the trick attack card.
     * @return Player next attack player.
     */
    protected final Player getNextAttackPlayer(final Card attack) {
        final Card maxSuit = game.getTrickCards().findMaxSuitCard(attack.getSuit());
        return game.getPlayerByCard(maxSuit);
    }
}
