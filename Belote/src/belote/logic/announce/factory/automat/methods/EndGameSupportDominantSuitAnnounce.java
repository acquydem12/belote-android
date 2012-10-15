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
import belote.logic.announce.factory.automat.methods.conditions.HasCard;
import belote.logic.announce.factory.automat.methods.conditions.SuitCount;
import belote.logic.announce.factory.automat.methods.conditions.base.MultipleAndCondition;
import belote.logic.announce.factory.automat.methods.suitDeterminators.DominantSuit;
import belote.logic.announce.factory.automat.methods.suitDeterminators.base.SuitDeterminator;

/**
 * EndGameSupportDominantSuitAnnounce class. Announce factory method which creates support dominant suit announce.
 * @author Dimitar Karamanov
 */
public final class EndGameSupportDominantSuitAnnounce extends ConditionListMethod {

    private final SuitDeterminator suitDeterminator;

    /**
     * Constructor.
     * @param game BelotGame instance class.
     */
    public EndGameSupportDominantSuitAnnounce(final Game game) {
        super(game);
        suitDeterminator = new DominantSuit();
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 3), new HasCard(Rank.Jack, suitDeterminator), new HasCard(Rank.Nine,
                suitDeterminator)));
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 4), new HasCard(Rank.Jack, suitDeterminator)));
    }

    /**
     * Returns the proper Announce when conditions match.
     * @param player who is on turn.
     * @return an Announce instance.
     */
    protected Announce createAnnounce(Player player) {
        final Suit suit = suitDeterminator.determinateSuit(player);
        return Announce.createSuitNormalAnnounce(player, suit);
    }
}
