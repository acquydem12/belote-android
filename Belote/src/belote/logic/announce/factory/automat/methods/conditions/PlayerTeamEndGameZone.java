/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.announce.factory.automat.methods.conditions;

import belote.bean.Game;
import belote.bean.Player;
import belote.logic.announce.factory.automat.methods.conditions.base.AnnounceCondition;

/**
 * PlayerTeamEndGameZone class. Returns true if the announce player team is in the end game zone, false otherwise.
 * @author Dimitar Karamanov
 */
public final class PlayerTeamEndGameZone implements AnnounceCondition {

    private static final int MINIMUM_OFFSET = 13;

    /**
     * The method which returns the result of condition.
     * @param player which has to declare next game announce.
     * @return boolean true if the condition fits, false otherwise.
     */
    public boolean process(final Player player) {
        final int playerTeamPoints = player.getTeam().getPoints().getAllPoints();
        return (Game.END_GAME_POINTS - playerTeamPoints) <= MINIMUM_OFFSET;
    }
}
