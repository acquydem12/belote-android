/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.play.strategy.automat.methods;

import belote.bean.Game;
import belote.bean.Player;
import belote.bean.announce.Announce;
import belote.bean.announce.AnnounceIterator;
import belote.bean.announce.AnnounceList;
import belote.bean.announce.AnnounceUnit;
import belote.bean.pack.card.Card;
import belote.bean.pack.card.suit.Suit;
import belote.logic.play.strategy.automat.methods.base.BaseMethod;

/**
 * PartnerSuitAnnounceCard class.
 * PlayCardMethod which implements the logic of playing card from suit declared by partner during game annouce.
 * @author Dimitar Karamanov
 */
public final class PartnerSuitAnnounceCard extends BaseMethod {

    /**
     * Constructor.
     * @param game BelotGame instance class.
     */
    public PartnerSuitAnnounceCard(final Game game) {
        super(game);
    }

    /**
     * Returns player's card.
     * @param player who is on turn.
     * @return Card object instance or null.
     */
    public Card getPlayMethodCard(final Player player) {
        final Player partner = player.getPartner();
        final AnnounceList partnerAnnounces = game.getAnnounceList().getPlayerAnnounces(partner);

        for (final AnnounceIterator iterator = partnerAnnounces.iterator(); iterator.hasNext();) {
            final Announce announce = iterator.next();
            if (announce.isColorAnnounce()) {
                final Suit suit = AnnounceUnit.transformFromAnnounceSuitToSuit(announce.getAnnounceSuit());

                Card result;
                result = player.getCards().findMaxSuitCard(suit);
                if (result != null && isMaxSuitCardLeft(result, false)) {
                    return result;
                }

                result = player.getCards().findMinSuitCard(suit);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}