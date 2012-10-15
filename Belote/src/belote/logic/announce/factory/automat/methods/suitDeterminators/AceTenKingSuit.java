/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.announce.factory.automat.methods.suitDeterminators;

import belote.bean.pack.card.rank.Rank;
import belote.logic.announce.factory.automat.methods.suitDeterminators.base.RankSuitDeterminator;

/**
 * Returns suit of which the player has Ace, Ten and King cards or null.
 * @author Dimitar Karamanov
 */
public final class AceTenKingSuit extends RankSuitDeterminator {

	public AceTenKingSuit() {
	    addRank(Rank.Ace);
	    addRank(Rank.Ten);
	    addRank(Rank.King);
	}
}