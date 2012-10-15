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
import belote.logic.play.strategy.automat.executors.base.PlayCardExecutor;
import belote.logic.play.strategy.automat.methods.AllTrumpSuitHookCard;
import belote.logic.play.strategy.automat.methods.MaxSuitLeftCard;
import belote.logic.play.strategy.automat.methods.MinAboveCard;
import belote.logic.play.strategy.automat.methods.MinSuitCard;
import belote.logic.play.strategy.automat.methods.PartnerAttackMaxSuitCard;

/**
 * AllTrumpFirstDefencePositionCard executor.
 * Used in AllTrumpPlayStategy getSecondDefencePositionCard().
 * @author Dimitar Karamanov
 */
public final class AllTrumpSecondDefencePositionCard extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game a BelotGame instance.
     */
    public AllTrumpSecondDefencePositionCard(final Game game) {
        super(game);
        //Register play card methods.
        register(new AllTrumpSuitHookCard(game));
        register(new MaxSuitLeftCard(game));
        register(new PartnerAttackMaxSuitCard(game));
        register(new MinAboveCard(game));
        register(new MinSuitCard(game));
        register(new ColorlessNeedlessCard(game));
    }
}
