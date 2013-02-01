package belote.logic.play.strategy.automat.methods.trumpsLess;

import belote.bean.Game;
import belote.bean.Player;
import belote.bean.pack.PackIterator;
import belote.bean.pack.card.Card;
import belote.logic.play.strategy.automat.base.method.BaseMethod;

public class MaximumOfAllMastersCard extends BaseMethod {

    /**
     * Constructor.
     * @param game BelotGame instance class.
     */
    public MaximumOfAllMastersCard(final Game game) {
        super(game);
    }

    /**
     * Returns player's card.
     * @param player who is on turn.
     * @return Card object instance or null.
     */
    public Card getPlayMethodCard(final Player player) {
        Card result = null;
        if (isAllCardsMasters(player)) {
            for (final PackIterator iterator = player.getCards().iterator(); iterator.hasNext();) {
                final Card card = iterator.next();
                if (player.getCards().getSuitCount(card.getSuit()) > 1) {
                    if (result == null || result.compareRankTo(card) < 0) {
                        result = card;
                    }
                }
            }
        }
        
        if (result != null) {
            player.getJackAceSuits().add(result.getSuit());
        }
        return result;
    }
}