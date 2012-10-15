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
import belote.logic.play.strategy.automat.methods.ColorlessHandCard;
import belote.logic.play.strategy.automat.methods.DominantSuitCard;
import belote.logic.play.strategy.automat.methods.MeterSuitCard;
import belote.logic.play.strategy.automat.methods.NotTrumpMakePowerTenCard;
import belote.logic.play.strategy.automat.methods.PartnerSuitAnnounceCard;
import belote.logic.play.strategy.automat.methods.TeamSuitPartnerCard;

/**
 * Standard NotTrumpAttackCard executor. Used in NotTrumpPlayStategy getAttackCard().
 * @author Dimitar Karamanov
 */
class StandardNotTrumpAttackCard extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game a BelotGame instance.
     */
    public StandardNotTrumpAttackCard(final Game game) {
        super(game);
        // Register play card methods.
        register(new MeterSuitCard(game));
        register(new TeamSuitPartnerCard(game));
        register(new DominantSuitCard(game));
        register(new PartnerSuitAnnounceCard(game));
        register(new NotTrumpMakePowerTenCard(game));
        register(new ColorlessHandCard(game));
        register(new PartnerPossibleSuitCard(game));
        register(new ColorlessNeedlessCard(game));
    }
}
