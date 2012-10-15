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
import belote.logic.play.strategy.automat.methods.ColorNoTrumpAttackNoObligatoryNoFirstRuffCard;
import belote.logic.play.strategy.automat.methods.ColorNoTrumpAttackObligatoryNoFirstRuffCard;

/**
 * ColorNoTrumpAttackTrumpCardExecutor class.
 * PlayCardExecutor is used to play a trump card in a color game when the attack card is not trump
 * and the player has no card from attack card suit.
 * @author Dimitar Karamanov
 */
public final class ColorNoTrumpAttackTrumpCardExecutor extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game BelotGame instance.
     */
    public ColorNoTrumpAttackTrumpCardExecutor(final Game game) {
        super(game);
        //Register play card methods.
        register(new ColorNoTrumpAttackObligatoryFirstRuffCardExecutor(game));
        register(new ColorNoTrumpAttackObligatoryNoFirstRuffCard(game));
        register(new ColorNoTrumpAttackNoObligatoryFirstRuffCardExecutor(game));
        register(new ColorNoTrumpAttackNoObligatoryNoFirstRuffCard(game));
    }
}
