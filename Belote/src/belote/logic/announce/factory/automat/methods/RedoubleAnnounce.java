/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.announce.factory.automat.methods;

import belote.bean.Game;
import belote.bean.Player;
import belote.bean.announce.Announce;
import belote.bean.announce.suit.AnnounceSuit;
import belote.logic.announce.factory.adviser.AllTrumpRedoubleAdviser;
import belote.logic.announce.factory.adviser.ColorRedoubleAdviser;
import belote.logic.announce.factory.adviser.NotTrumpRedoubleAdviser;
import belote.logic.announce.factory.automat.methods.base.ConditionListMethod;
import belote.logic.announce.factory.automat.methods.conditions.OppositeTeamDoubleAnnounce;

/**
 * RedoubleAnnounce class.
 * Announce factory method which creates a redouble announce.
 * @author Dimitar Karamanov
 */
public final class RedoubleAnnounce extends ConditionListMethod {

    /**
     * Not trump redouble adviser.
     */
    private final NotTrumpRedoubleAdviser ntRedoubleAdviser;

    /**
     * All trump redouble adviser.
     */
    private final AllTrumpRedoubleAdviser atRedoubleAdviser;

    /**
     * Color suit redouble adviser.
     */
    private final ColorRedoubleAdviser clRedoubleAdviser;

    /**
     * Constructor.
     * @param game BelotGame instance class.
     */
    public RedoubleAnnounce(final Game game) {
        super(game);
        ntRedoubleAdviser = new NotTrumpRedoubleAdviser(game);
        atRedoubleAdviser = new AllTrumpRedoubleAdviser(game);
        clRedoubleAdviser = new ColorRedoubleAdviser(game);

        addAnnounceCondition(new OppositeTeamDoubleAnnounce(game));
    }

    /**
     * Returns the proper Announce when conditions match.
     * @param player who is on turn.
     * @return an Announce instance.
     */
    protected Announce createAnnounce(Player player) {
        final Announce announce = game.getAnnounceList().getContractAnnounce();

        if (announce != null) {
            if (announce.getAnnounceSuit().equals(AnnounceSuit.NotTrump)) {
                return ntRedoubleAdviser.getAnnounce(player);
            }

            if (announce.getAnnounceSuit().equals(AnnounceSuit.AllTrump)) {
                return atRedoubleAdviser.getAnnounce(player);
            }

            return clRedoubleAdviser.getAnnounce(player);
        }

        return null;
    }
}
