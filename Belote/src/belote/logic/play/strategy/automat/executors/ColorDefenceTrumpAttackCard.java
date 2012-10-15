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
import belote.logic.play.strategy.automat.methods.ColorTrumpAttackMaxTrumpCard;
import belote.logic.play.strategy.automat.methods.ColorTrumpAttackMinAboveCard;
import belote.logic.play.strategy.automat.methods.ColorTrumpAttackMinTrumpCard;

/**
 * ColorSuitCard executor.
 * Implements the obligatory rules for defense player when the attack card is from trump suit.
 * Used in ColorDefenceCard executor.
 * @author Dimitar Karamanov
 */
public final class ColorDefenceTrumpAttackCard extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game a BelotGame instance.
     */
    public ColorDefenceTrumpAttackCard(final Game game) {
        super(game);
        //Register play card methods.
        register(new ColorTrumpAttackMaxTrumpCard(game));
        register(new ColorTrumpAttackMinAboveCard(game));
        register(new ColorTrumpAttackMinTrumpCard(game));
    }
}
