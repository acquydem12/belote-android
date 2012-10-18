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
import belote.logic.play.strategy.automat.executors.PossiblePartnerSuitCard;
import belote.logic.play.strategy.automat.methods.MeterSuitCard;
import belote.logic.play.strategy.automat.methods.trumps.ColorDominantNoTrumpSuitCard;
import belote.logic.play.strategy.automat.methods.trumps.ColorDominantTrumpCard;
import belote.logic.play.strategy.automat.methods.trumps.ColorNoTrumpHandCard;
import belote.logic.play.strategy.automat.methods.trumps.ColorPartnerTrumpCard;
import belote.logic.play.strategy.automat.methods.trumps.ColorTeamSuitPartnerCard;

/**
 * ColorAttackCard executor. Used in ColorPlayStategy getAttackCard().
 * @author Dimitar Karamanov
 */
public final class AttackCard extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game a BelotGame instance.
     */
    public AttackCard(final Game game) {
        super(game);
        // Register play card methods.
        register(new ColorTeamSuitPartnerCard(game));
        register(new ColorDominantTrumpCard(game));
        register(new ColorPartnerTrumpCard(game));
        register(new ColorNoTrumpHandCard(game));
        register(new MeterSuitCard(game));
        register(new PossiblePartnerSuitCard(game));
        register(new ColorDominantNoTrumpSuitCard(game));
        register(new NeedlessCard(game));
    }
}
