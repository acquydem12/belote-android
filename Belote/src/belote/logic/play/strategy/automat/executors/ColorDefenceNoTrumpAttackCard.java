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
import belote.logic.play.strategy.automat.methods.ColorNoTrumpAttackEnemyTrumpCard;
import belote.logic.play.strategy.automat.methods.ColorNoTrumpAttackMaxSuitLeftCard;
import belote.logic.play.strategy.automat.methods.ColorNoTrumpAttackSuitHookCard;
import belote.logic.play.strategy.automat.methods.MinSuitCard;

/**
 * ColorDefenceSuitCard executor. Implements the obligatory rules for defence player when the attack card is not from trump suit. Used in ColorDefenceCard
 * executor.
 * @author Dimitar Karamanov
 */
public final class ColorDefenceNoTrumpAttackCard extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game a BelotGame instance.
     */
    public ColorDefenceNoTrumpAttackCard(final Game game) {
        super(game);
        // Register play card methods.
        register(new ColorNoTrumpAttackEnemyTrumpCard(game));
        register(new ColorNoTrumpAttackSuitHookCard(game));
        register(new ColorNoTrumpAttackMaxSuitLeftCard(game));
        register(new MinSuitCard(game));
        register(new ColorNoTrumpAttackTrumpCardExecutor(game));
    }
}
