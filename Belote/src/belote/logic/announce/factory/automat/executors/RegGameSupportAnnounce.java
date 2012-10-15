/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.announce.factory.automat.executors;

import belote.bean.Game;
import belote.logic.announce.factory.automat.executors.base.AnnounceExecutor;
import belote.logic.announce.factory.automat.methods.RegGameSupportAllTrumpAnnounce;
import belote.logic.announce.factory.automat.methods.RegGameSupportNotTrumpAnnounce;
import belote.logic.announce.factory.automat.methods.conditions.PartnerColorAnnounce;

/**
 * RegGameSupportAnnounce class.
 * @author Dimitar Karamanov
 */
public final class RegGameSupportAnnounce extends AnnounceExecutor {

    /**
     * Constructor.
     * @param game BelotGame instance class.
     */
    public RegGameSupportAnnounce(final Game game) {
        super(game);
        //Pre conditions
        addPreCondition(new PartnerColorAnnounce(game));
        //Methods
        register(new RegGameSupportColorAnnounce(game));
        register(new RegGameSupportAllTrumpAnnounce(game));
        register(new RegGameSupportNotTrumpAnnounce(game));
    }
}