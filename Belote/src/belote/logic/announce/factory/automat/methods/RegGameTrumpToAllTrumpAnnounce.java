package belote.logic.announce.factory.automat.methods;

import belote.bean.Game;
import belote.bean.Player;
import belote.bean.announce.Announce;
import belote.logic.announce.factory.automat.base.AnnounceMethod;

public final class RegGameTrumpToAllTrumpAnnounce implements AnnounceMethod {
    
    private final Game game;

    /**
     * Constructor.
     * @param game BelotGame instance class.
     */
    public RegGameTrumpToAllTrumpAnnounce(final Game game) {
        this.game = game;
    }

    @Override
    public Announce getAnnounce(final Player player) {
        Player partner = player.getPartner();
        
        Announce playerAnnounce = game.getAnnounceList().getContractAnnounce(player);
        Announce partnerAnnounce = game.getAnnounceList().getContractAnnounce(partner);
        
        if (playerAnnounce != null && partnerAnnounce != null && playerAnnounce.isTrumpAnnounce() && partnerAnnounce.isTrumpAnnounce()) {
            return Announce.createATNormalAnnounce(player);
        }

        return null;
    }
}