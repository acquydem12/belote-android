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
import belote.bean.pack.card.rank.Rank;
import belote.bean.pack.card.suit.Suit;
import belote.logic.announce.factory.automat.methods.base.ConditionListMethod;
import belote.logic.announce.factory.automat.methods.conditions.HasSuit;
import belote.logic.announce.factory.automat.methods.conditions.RankCount;
import belote.logic.announce.factory.automat.methods.conditions.base.MultipleAndCondition;
import belote.logic.announce.factory.automat.methods.suitDeterminators.JackNineSuit;
import belote.logic.announce.factory.automat.methods.suitDeterminators.base.SuitDeterminator;

/**
 * RegGameSupportJackNineSuitAnnounce class. Announce factory method which creates support jack nine suit announce.
 * @author Dimitar Karamanov
 */
public final class RegGameSupportJackNineSuitAnnounce extends ConditionListMethod {

    private final SuitDeterminator suitDeterminator;

    /**
     * Constructor.
     * @param game BelotGame instance class.
     */
    public RegGameSupportJackNineSuitAnnounce(final Game game) {
        super(game);
        suitDeterminator = new JackNineSuit();
        addAnnounceCondition(new MultipleAndCondition(new HasSuit(suitDeterminator), new RankCount(Rank.Ace, 1)));
    }

    /**
     * Returns the proper Announce when conditions match.
     * @param player who is on turn.
     * @return an Announce instance.
     */
    protected Announce createAnnounce(Player player) {
        final Suit suit = suitDeterminator.determineSuit(player);
        return Announce.createSuitNormalAnnounce(player, suit);
    }
}