package belote.logic.announce.factory.automat.methods.suitDeterminators.base;

import java.util.ArrayList;
import java.util.Iterator;

import belote.bean.Player;
import belote.bean.pack.card.rank.Rank;
import belote.bean.pack.card.suit.Suit;
import belote.bean.pack.card.suit.SuitIterator;

public abstract class RankSuitDeterminator implements SuitDeterminator {
	
	private final ArrayList<Rank> ranks = new ArrayList<Rank>();

	@Override
	public final Suit determinateSuit(Player player) {
		for (SuitIterator iterator = Suit.iterator(); iterator.hasNext();) {
            final Suit suit = iterator.next();

            if (containRanks(player, suit)) {
                return suit;
            }
        }
        return null;
	}
	
	public final void addRank(final Rank rank) {
		ranks.add(rank);
	}
	
	private boolean containRanks(Player player, Suit suit) {
		for (Iterator<Rank> iterator = ranks.iterator(); iterator.hasNext();) {
			Rank rank = iterator.next();
			if (player.getCards().findCard(rank, suit) == null) {
				return false;
			}
		}
		return true;
	}

}