/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.play.strategy.automat.executors.trumpsLess;

import belote.bean.Game;
import belote.logic.play.strategy.automat.base.executor.PlayCardExecutor;
import belote.logic.play.strategy.automat.methods.MaxSingleNoHandCardToPartner;
import belote.logic.play.strategy.automat.methods.MinAllCard;
import belote.logic.play.strategy.automat.methods.MinMeterSuitCard;
import belote.logic.play.strategy.automat.methods.SingleNoMajorCard;
import belote.logic.play.strategy.automat.methods.SingleNoMaxCard;
import belote.logic.play.strategy.automat.methods.trumpsLess.ColorlessChooseTwoSuitsCard;
import belote.logic.play.strategy.automat.methods.trumpsLess.ColorlessClearCard;
import belote.logic.play.strategy.automat.methods.trumpsLess.ColorlessMinAllMasterCards;

/**
 * ColorlessNeedlessCard class. Implements the logic to play no needed card and is called after obligatory rules. Used in AllTrumptXXX and NotTrumpXXX
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
        register(new ColorlessMinAllMasterCards(game));
        register(new SingleNoMajorCard(game));
        register(new ColorlessClearCard(game));
        register(new SingleNoMaxCard(game));
        register(new MinMeterSuitCard(game));
        register(new ColorlessChooseTwoSuitsCard(game));
        register(new MinAllCard(game));
    }
}
