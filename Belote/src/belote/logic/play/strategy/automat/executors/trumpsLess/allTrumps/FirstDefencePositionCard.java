/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.play.strategy.automat.executors.trumpsLess.allTrumps;

import belote.bean.Game;
import belote.logic.play.strategy.automat.base.executor.PlayCardExecutor;
import belote.logic.play.strategy.automat.executors.trumpsLess.NeedlessCard;
import belote.logic.play.strategy.automat.methods.MaxSuitLeftCard;
import belote.logic.play.strategy.automat.methods.MinAboveCard;
import belote.logic.play.strategy.automat.methods.MinSuitCard;
import belote.logic.play.strategy.automat.methods.trumpsLess.allTrump.AllTrumpSuitHookCard;

/**
 * AllTrumpFirstDefencePositionCard executor. Used in AllTrumpPlayStategy getFirstDefencePositionCard().
 * @author Dimitar Karamanov
 */
public final class FirstDefencePositionCard extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game a BelotGame instance.
     */
    public FirstDefencePositionCard(final Game game) {
        super(game);
        // Register play card methods.
        register(new AllTrumpSuitHookCard(game));
        register(new MaxSuitLeftCard(game));
        register(new MinAboveCard(game));
        register(new MinSuitCard(game));
        register(new NeedlessCard(game));
    }
}
