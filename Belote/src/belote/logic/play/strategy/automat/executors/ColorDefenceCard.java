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

/**
 * ColorDefenceCard executor.
 * Used in ColorPlayStategy getXXXDefencePositionCard().
 * @author Dimitar Karamanov
 */
public final class ColorDefenceCard extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game a BelotGame instance.
     */
    public ColorDefenceCard(final Game game) {
        super(game);
        //Register play card methods.
        //When attack card is trump
        register(new ColorDefenceTrumpAttackCard(game));
        //When the player has from attack suit card or have to/can play trump
        register(new ColorDefenceNoTrumpAttackCard(game));
        //When player play no needed card
        register(new ColorNeedlessCard(game));
    }
}
