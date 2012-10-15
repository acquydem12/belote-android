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
import belote.logic.announce.factory.automat.methods.base.ConditionListMethod;
import belote.logic.announce.factory.automat.methods.conditions.DealAttackPlayer;
import belote.logic.announce.factory.automat.methods.conditions.HasCard;
import belote.logic.announce.factory.automat.methods.conditions.SuitCount;
import belote.logic.announce.factory.automat.methods.conditions.base.MultipleAndCondition;
import belote.logic.announce.factory.automat.methods.suitDeterminators.DominantSuit;
import belote.logic.announce.factory.automat.methods.suitDeterminators.base.SuitDeterminator;

/**
 * RegGameNagNotTrumpWhenFirstAndHasAceSuitAnnounce class.
 * Announce factory method which creates nag NT announce on ace suit and player is attack one.
 * @author Dimitar Karamanov
 */
public final class RegGameNagNotTrumpWhenFirstAndHasAceSuitAnnounce extends ConditionListMethod {

    /**
     * Constructor.
     * @param game BelotGame instance class.
     */
    public RegGameNagNotTrumpWhenFirstAndHasAceSuitAnnounce(final Game game) {
        super(game);
        final SuitDeterminator suitDeterminator = new DominantSuit();
        addAnnounceCondition(new MultipleAndCondition(new SuitCount(suitDeterminator, 5), new HasCard(Rank.Ace, suitDeterminator), new DealAttackPlayer(game)));
    }

    /**
     * Returns the proper Announce when conditions match.
     * @param player who is on turn.
     * @return an Announce instance.
     */
    protected Announce createAnnounce(Player player) {
        return Announce.createNTNormalAnnounce(player);
    }
}