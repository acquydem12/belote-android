/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.play.strategy.automat.executors.trumps;

import belote.bean.Game;
import belote.logic.play.strategy.automat.base.executor.PlayCardExecutor;
import belote.logic.play.strategy.automat.methods.MaxSingleNoHandCardToPartner;
import belote.logic.play.strategy.automat.methods.MinimumOfAllCard;
import belote.logic.play.strategy.automat.methods.trumps.needless.MinimumOfAllNoTrumpCard;
import belote.logic.play.strategy.automat.methods.trumps.needless.MinimumOfAllMastersCards;
import belote.logic.play.strategy.automat.methods.trumps.needless.ClearCard;
import belote.logic.play.strategy.automat.methods.trumps.needless.ClearSingleSuitCard;
import belote.logic.play.strategy.automat.methods.trumps.needless.TrumpCard;

/**
 * ColorNeedlessCard executor. Implements the logic to play no needed card and is called after obligatory rules. Used in ColorAttackCard and ColorDefenceCard
 * executors.
 * @author Dimitar Karamanov
 */
public final class NeedlessCard extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game a BelotGame instance.
     */
    public NeedlessCard(final Game game) {
        super(game);
        // Register play card methods.
        register(new MaxSingleNoHandCardToPartner(game));
        register(new MinimumOfAllMastersCards(game));
        register(new ClearSingleSuitCard(game));
        register(new ClearCard(game));
        register(new MinimumOfAllNoTrumpCard(game));
        register(new TrumpCard(game));
        register(new MinimumOfAllCard(game));
    }
}
