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
import belote.logic.announce.factory.automat.methods.conditions.RankCount;
import belote.logic.announce.factory.automat.methods.conditions.SuitCount;
import belote.logic.announce.factory.automat.methods.conditions.TeamAdvance;
import belote.logic.announce.factory.automat.methods.conditions.ThreePassOpenAnnounce;
import belote.logic.announce.factory.automat.methods.conditions.base.MultipleAndCondition;
import belote.logic.announce.factory.automat.methods.suitDeterminators.DominantSuit;
import belote.logic.announce.factory.automat.methods.suitDeterminators.base.SuitDeterminator;

/**
 * RegGameNormalDominantSuitAnnounce class. Announce factory method which creates a dominant suit announce.
 * @author Dimitar Karamanov
 */
public final class RegGameNormalDominantSuitAnnounce extends ConditionListMethod {

    private final SuitDeterminator suitDeterminator;

    /**
     * Constructor.
     * @param game BelotGame instance class.
     */
    public RegGameNormalDominantSuitAnnounce(final Game game) {
        super(game);
        suitDeterminator = new DominantSuit();

        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 3), new HasCard(Rank.Jack, suitDeterminator), new HasCard(Rank.Nine,
                suitDeterminator), new ThreePassOpenAnnounce(game)));
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 3), new HasCard(Rank.Jack, suitDeterminator), new HasCard(Rank.Ace,
                suitDeterminator), new ThreePassOpenAnnounce(game)));
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 3), new HasCard(Rank.Jack, suitDeterminator), new HasCard(Rank.Nine,
                suitDeterminator), new RankCount(Rank.Ace, 1)));
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 3), new HasCard(Rank.Jack, suitDeterminator), new HasCard(Rank.Ace,
                suitDeterminator), new RankCount(Rank.Ace, 2)));
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 3), new HasCard(Rank.Jack, suitDeterminator), new TeamAdvance(game),
                new RankCount(Rank.Ace, 1)));
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 3), new HasCard(Rank.Jack, suitDeterminator), new HasCard(Rank.Ten,
                suitDeterminator), new RankCount(Rank.Ace, 1)));
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 3), new HasCard(Rank.Jack, suitDeterminator), new RankCount(Rank.Ace, 2)));
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 4), new HasCard(Rank.Nine, suitDeterminator), new HasCard(Rank.Ace,
                suitDeterminator)));
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 4), new HasCard(Rank.Nine, suitDeterminator), new HasCard(Rank.Ten,
                suitDeterminator)));
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 4), new HasCard(Rank.Nine, suitDeterminator), new HasCard(Rank.King,
                suitDeterminator), new HasCard(Rank.Queen, suitDeterminator)));
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 4), new HasCard(Rank.Jack, suitDeterminator)));
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 5), new HasCard(Rank.Nine, suitDeterminator)));
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