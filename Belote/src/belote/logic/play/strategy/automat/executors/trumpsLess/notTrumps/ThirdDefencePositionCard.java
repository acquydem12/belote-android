/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.play.strategy.automat.executors.trumpsLess.notTrumps;

import belote.bean.Game;
import belote.logic.play.strategy.automat.base.executor.PlayCardExecutor;
import belote.logic.play.strategy.automat.executors.trumpsLess.NeedlessCard;
import belote.logic.play.strategy.automat.methods.MinSuitCard;
import belote.logic.play.strategy.automat.methods.trumpsLess.notTrump.NotTrumpSuitHookCard;

/**
 * NotTrumpThirdDefencePositionCard executor. Used in NotTrumpPlayStategy getThirdDefencePositionCard().
 * @author Dimitar Karamanov
 */
public final class ThirdDefencePositionCard extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game a BelotGame instance.
     */
    public ThirdDefencePositionCard(final Game game) {
        super(game);
        // Register play card methods.
        register(new NotTrumpSuitHookCard(game));
        register(new MinSuitCard(game));
        register(new NeedlessCard(game));
    }
}
