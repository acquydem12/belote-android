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
 * AllTrumpAttackCard executor.
 * Used in AllTrumpPlayStategy getAttackCard().
 * @author Dimitar Karamanov
 */
public final class AllTrumpAttackCard extends PlayCardExecutor {

    /**
     * Constructor.
     * @param game a BelotGame instance.
     */
    public AllTrumpAttackCard(final Game game) {
        super(game);
        //Register play card methods.
        register(new PartnerDeclaredAllTrumpAttackCard(game));
        register(new StandardAllTrumpAttackCard(game));
    }
}
