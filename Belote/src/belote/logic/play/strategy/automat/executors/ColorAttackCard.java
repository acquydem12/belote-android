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
import belote.logic.play.strategy.automat.methods.ColorDominantNoTrumpSuitCard;
import belote.logic.play.strategy.automat.methods.ColorDominantTrumpCard;
import belote.logic.play.strategy.automat.methods.ColorNoTrumpHandCard;
import belote.logic.play.strategy.automat.methods.ColorPartnerTrumpCard;
import belote.logic.play.strategy.automat.methods.ColorTeamSuitPartnerCard;
import belote.logic.play.strategy.automat.methods.MeterSuitCard;

/**
 * ColorAttackCard executor.
 * Used in ColorPlayStategy getAttackCard().
 * @author Dimitar Karamanov
 */
public final class ColorAttackCard extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game a BelotGame instance.
     */
    public ColorAttackCard(final Game game) {
        super(game);
        //Register play card methods.
        register(new ColorTeamSuitPartnerCard(game));
        register(new ColorDominantTrumpCard(game));
        register(new ColorPartnerTrumpCard(game));
        register(new ColorNoTrumpHandCard(game));
        register(new MeterSuitCard(game));
        register(new PartnerPossibleSuitCard(game));
        register(new ColorDominantNoTrumpSuitCard(game));
        register(new ColorNeedlessCard(game));
    }
}

