package com.karamanov.beloteGame.gui.screen.main;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import belote.base.BelotException;
import belote.bean.GameMode;
import belote.bean.Player;
import belote.bean.announce.Announce;
import belote.bean.announce.suit.AnnounceSuit;
import belote.bean.pack.card.Card;
import belote.bean.pack.card.rank.Rank;
import belote.bean.pack.sequence.Sequence;
import belote.bean.pack.sequence.SequenceIterator;
import belote.bean.pack.sequence.SequenceType;
import belote.bean.pack.square.SquareIterator;
import belote.logic.HumanBeloteFacade;

import com.karamanov.beloteGame.Belote;
import com.karamanov.beloteGame.R;
import com.karamanov.beloteGame.gui.screen.gameResume.GameResumeActivity;
import com.karamanov.beloteGame.gui.screen.main.announce.AnnounceDialog;
import com.karamanov.beloteGame.gui.screen.main.message.MessageData;
import com.karamanov.beloteGame.gui.screen.main.message.MessageScreen;
import com.karamanov.framework.BooleanFlag;
import com.karamanov.framework.MessageActivity;
import com.karamanov.framework.graphics.Rectangle;

public final class Dealer {

    private final Handler handler;

    /**
     * Standard card delay on painting (effect).
     */
    public static final int STANDARD_CARD_DELAY = 20;

    /**
     * Belote painter. (All drawing functionality is in it).
     */
    public final BelotePainter belotPainter;

    /**
     * Delay constant
     */
    private final static int PLAY_DELAY = 200;

    public static final int NAV_PRESS = -1;
    public static final int NAV_LEFT = -2;
    public static final int NAV_RIGHT = -3;

    private final MessageActivity context;

    private final BeloteView belotPanel;

    private final View buttons;

    private final AnnounceDialog announceDialog;

    private MessageScreen messageScreen;

    private HumanBeloteFacade beloteFacade;

    public Dealer(MessageActivity context, BeloteView belotPanel, View buttons) {
        this.context = context;
        this.belotPanel = belotPanel;
        this.buttons = buttons;
        this.beloteFacade = Belote.getBeloteFacade(context);

        belotPainter = new BelotePainter(context);
        announceDialog = new AnnounceDialog(context, beloteFacade);

        handler = new Handler();
    }

    /**
     * Checks pointer click.
     * @param x position.
     * @param y position.
     */
    public void checkPointerPressed(float x, float y) {
        if (beloteFacade.isAnnounceGameMode()) {
            processAnnounceDealRound();
        } else {
            checkPointerPressedPlayGameMode(x, y);
        }
    }

    /**
     * Check pointer press for play mode.
     * @param x position.
     * @param y position.
     */
    private void checkPointerPressedPlayGameMode(float x, float y) {
        boolean isPlayedCard = processPlaySelectedCard(getHumanCardUnderPointer(x, y));

        if (!isPlayedCard) {
            isPlayedCard = processSelectHumanCard(x, y);
        }

        if (!isPlayedCard || !beloteFacade.isTrickEnd()) {
            checkTrickEnd();

            if (beloteFacade.checkGameEnd()) {
                endGame();
            } else {
                processRoundPlay(x, y);
            }
        }
    }

    /**
     * Process announce deal round (Until 3 or 4 last pass announces).
     */
    private void processAnnounceDealRound() {
        if (beloteFacade.canDeal()) {
            processSingleAnnounceDeal();
        } else {
            if (beloteFacade.getGame().getAnnounceList().getContractAnnounce() == null) {
                newAnnounceDealRound();
            } else {
                newGame();
            }
        }
    }

    private Card getHumanCardUnderPointer(final float x, final float y) {
        Player player = beloteFacade.getHumanPlayer();

        Canvas canvas = belotPanel.getBufferedCanvas();
        if (canvas != null) {
            for (int i = 0; i < Rank.getRankCount(); i++) {
                if (i < player.getCards().getSize()) {

                    Rectangle rec = belotPainter.getPlayerCardRectangle(canvas, beloteFacade, i, player);
                    Card card = player.getCards().getCard(i);

                    if (rec.include((int) x, (int) y)) {
                        return card;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Checks double click card.
     */
    private boolean processPlaySelectedCard() {
        if (beloteFacade.isPlayingGameMode() && beloteFacade.isHumanTrickOrder() && beloteFacade.getHumanPlayer().getSelectedCard() != null) {
            Player player = beloteFacade.getHumanPlayer();

            if (beloteFacade.validatePlayerCard(player, player.getSelectedCard())) {
                // couples, preferred, unwanted and missed suit
                beloteFacade.processHumanPlayerCard(player, beloteFacade.getHumanPlayer().getSelectedCard());
                // repaint frame
                invalidateGame();

                ArrayList<MessageData> messages = getMessageList(player, player.getSelectedCard());

                if (messages.size() > 0) {
                    displayMessage(player, messages);
                } else {
                    sleep(PLAY_DELAY);
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Sleeps for provided millisecond.
     * @param ms provided millisecond.
     */
    public final void sleep(final long ms) {
        if (ms > 0) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ex) {
                // ex.printStackTrace();
            }
        }
    }

    /**
     * Process the selected card.
     * 
     * @param card selected one.
     */
    private void processSelectCard(final Card card) {
        final Player player = beloteFacade.getHumanPlayer();

        if (card != null && beloteFacade.validatePlayerCard(player, card)) {
            if (!card.equals(beloteFacade.getHumanPlayer().getSelectedCard())) {
                beloteFacade.getHumanPlayer().setSelectedCard(card);
                invalidateGame();
            }
        }
    }

    /**
     * Process rounds after human player.
     * 
     * @param keyCode
     * @param gameAction
     */
    private void processPlayAfterHumanPlayer(int keyCode) {
        beloteFacade.getHumanPlayer().setSelectedCard(null);
        playRepeatedSingleRoundAfterHumanPlayer();
    }

    /**
     * Process rounds till human player.
     * 
     * @param keyCode
     * @param gameAction
     */
    private void processPlayTillHumanPlayer(int keyCode) {
        if (beloteFacade.getHumanPlayer().equals(beloteFacade.getGame().getTrickAttackPlayer())) {
            selectHumanSingleCard();
            processSelectCard(keyCode);
        } else {
            playRepeatedSingleRoundTillHumanPlayer();
            selectHumanSingleCard();
        }
    }

    /**
     * End game.
     */
    private void endGame() {
        Belote belote = (Belote) context.getApplication();
        belote.getMessageProcessor().stopMessaging();

        beloteFacade.processTrickData();
        beloteFacade.calculateTeamsPoints();

        if (beloteFacade.getGame().getAnnounceList().getOpenContractAnnounce() != null) {
            handler.post(new Runnable() {
                public void run() {
                    Intent intent = new Intent(context, GameResumeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Checks for round end.
     */
    private void checkTrickEnd() {
        if (beloteFacade.isTrickEnd()) {
            beloteFacade.processTrickData();
            invalidateGame();
        }
    }

    /**
     * Process round playing.
     * 
     * @param keyCode pressed key code.
     * @param gameAction status.
     */
    private void processRoundPlaying(int keyCode) {
        if (!beloteFacade.isHumanTrickOrder()) {
            if (beloteFacade.getHumanTrickCard() == null) {
                processPlayTillHumanPlayer(keyCode);
            } else {
                processPlayAfterHumanPlayer(keyCode);
            }
        }
    }

    /**
     * Process game playing.
     * 
     * @param keyCode pressed key code.
     * @param gameAction status.
     */
    private void processGamePlaying(int keyCode) {
        checkTrickEnd();

        if (beloteFacade.checkGameEnd()) {
            endGame();
        } else {
            processRoundPlaying(keyCode);
        }
    }

    /**
     * Checks card click.
     * 
     * @param keyCode pressed key code.
     * @param gameAction status.
     */
    private void processSelectCard(int keyCode) {
        boolean keyLeftRightAction = keyCode == NAV_LEFT || keyCode == NAV_RIGHT;
        if (beloteFacade.isPlayingGameMode() && beloteFacade.isHumanTrickOrder() && keyLeftRightAction) {
            Card card = null;

            if (keyCode == NAV_LEFT) {
                card = beloteFacade.selectNextLeftCard();
            }

            if (keyCode == NAV_RIGHT) {
                card = beloteFacade.selectNextRightCard();
            }

            processSelectCard(card);
        }
    }

    /**
     * Checks key click.
     * 
     * @param keyCode pressed key code.
     * @param gameAction status.
     */
    public void checkKeyPressed(int keyCode) {
        if (beloteFacade.isAnnounceGameMode()) {
            checkKeyPressedAnnounceGameMode(keyCode);
        } else {
            checkKeyPressedPlayGameMode(keyCode);
        }
    }

    private void checkKeyPressedPlayGameMode(int keyCode) {
        if (keyCode == NAV_PRESS) {
            final boolean isPlayedCard = processPlaySelectedCard();

            // Don't process game playing after human has played and trick is
            // ended.
            if (!isPlayedCard || !beloteFacade.isTrickEnd()) {
                processGamePlaying(keyCode);
            }
        } else if (keyCode == NAV_LEFT && beloteFacade.isHumanTrickOrder()) {
            final Card card = beloteFacade.selectNextLeftCard();
            processSelectCard(card);
        } else if (keyCode == NAV_RIGHT && beloteFacade.isHumanTrickOrder()) {
            final Card card = beloteFacade.selectNextRightCard();
            processSelectCard(card);
        } else {
            processGamePlaying(keyCode);
        }
    }

    /**
     * Check key press for announce mode.
     * 
     * @param keyCode pressed key code.
     * @param gameAction status.
     */
    private void checkKeyPressedAnnounceGameMode(int keyCode) {
        processAnnounceDealRound();
    }

    /**
     * Selects human single card.
     */
    private void selectHumanSingleCard() {
        final Player player = beloteFacade.getHumanPlayer();
        if (beloteFacade.isPlayingGameMode() && beloteFacade.isHumanTrickOrder() && player.getCards().getSize() == 1) {
            final Card card = player.getCards().getFirstNoNullCard();
            processSelectCard(card);
        }
    }

    /**
     * Plays one round till human player.
     */
    private void playRepeatedSingleRoundTillHumanPlayer() {
        for (Player player = beloteFacade.getGame().getTrickAttackPlayer(); !player.equals(beloteFacade.getHumanPlayer()); player = beloteFacade
                .getPlayerAfter(player)) {
            playSingleRoundPlayerCard(player);
        }
    }

    /**
     * Plays one round after human player (Till end of round).
     */
    private void playRepeatedSingleRoundAfterHumanPlayer() {
        for (Player player = beloteFacade.getPlayerAfter(beloteFacade.getHumanPlayer()); !player.equals(beloteFacade.getGame().getTrickAttackPlayer()); player = beloteFacade
                .getPlayerAfter(player)) {
            playSingleRoundPlayerCard(player);
        }
    }

    private void playSingleRoundPlayerCard(final Player player) {
        try {
            Card card = beloteFacade.playSingleHand(player);
            invalidateGame();

            ArrayList<MessageData> messages = getMessageList(player, card);
            if (messages.size() > 0) {
                displayMessage(player, messages);
            } else {
                sleep(PLAY_DELAY);
            }

        } catch (BelotException be) {
        }
    }

    /**
     * Shows announce panel.
     */
    private void showAnnounceDialog() {
        beloteFacade.setPlayerIsAnnouncing(true);
        // announceDialog.setTrue();
        invalidateGame();
        handler.post(new Runnable() {
            public void run() {
                announceDialog.init();
                WindowManager.LayoutParams lp = announceDialog.getWindow().getAttributes();
                lp.gravity = Gravity.BOTTOM;
                lp.y = belotPainter.getFontHeight() + belotPainter.getCardHeight();
                if (buttons.getVisibility() == View.VISIBLE) {
                    lp.y = lp.y + buttons.getHeight();
                }
                announceDialog.getWindow().setAttributes(lp);
                announceDialog.show();
            }
        });

        Belote belote = (Belote) context.getApplication();
        belote.getMessageProcessor().stopMessaging();
    }

    /**
     * Process single announce deal.
     */
    private void processSingleAnnounceDeal() {
        if (beloteFacade.getNextAnnouncePlayer().equals(beloteFacade.getHumanPlayer())) {
            showAnnounceDialog();
        } else {
            beloteFacade.processNextAnnounce();
        }

        invalidateGame();
    }

    /**
     * New game.
     */
    private void newGame() {
        beloteFacade.setGameMode(GameMode.PlayGameMode);
        beloteFacade.manageRestCards();
        invalidateGame();
    }

    /**
     * New announce deal.
     * 
     * @param repaint
     */
    protected void newAnnounceDealRound() {
        beloteFacade.processTrickData();
        beloteFacade.setNextDealAttackPlayer();
        beloteFacade.newGame();
        beloteFacade.getHumanPlayer().setSelectedCard(null);

        if (context.getWindow() != null && context.getWindow().isActive()) {
            invalidateGame(STANDARD_CARD_DELAY);
        } else {
            invalidateGame();
        }
    }

    private ArrayList<MessageData> getMessageList(final Player player, final Card card) {
        ArrayList<MessageData> result = new ArrayList<MessageData>();

        if (player.equals(beloteFacade.getGame().getTrickCouplePlayer())) {
            result.add(new MessageData(belotPainter.getCoupleImage(card.getSuit()), context.getString(R.string.Belote)));
        }
        // Add equals and sequences messages on first round and when the game is
        // not NT.
        Announce announce = beloteFacade.getGame().getAnnounceList().getOpenContractAnnounce();
        if (announce != null && !announce.getAnnounceSuit().equals(AnnounceSuit.NotTrump) && beloteFacade.getGame().getTrickList().isEmpty()) {
            for (SquareIterator it = player.getCards().getSquaresList().iterator(); it.hasNext();) {
                result.add(new MessageData(belotPainter.getEqualCardsImage(it.next()), context.getString(R.string.FourCards)));
            }

            for (SequenceIterator it = player.getCards().getSequencesList().iterator(); it.hasNext();) {
                Sequence sequence = it.next();
                result.add(new MessageData(belotPainter.getSequenceTypeImage(sequence.getType()), getSequenceString(sequence.getType())));
            }
        }

        return result;
    }

    private String getSequenceString(SequenceType type) {
        if (SequenceType.Tierce.equals(type)) {
            return context.getString(R.string.Tierce);
        }

        if (SequenceType.Quarte.equals(type)) {
            return context.getString(R.string.Quarte);
        }

        if (SequenceType.Quint.equals(type)) {
            return context.getString(R.string.Quint);
        }
        return context.getString(R.string.Sequence);
    }

    /**
     * Displays a message.
     * 
     * @param player which call the message function.
     * @param card played by player.
     */
    private void displayMessage(final Player player, final ArrayList<MessageData> messages) {
        final BooleanFlag flag = new BooleanFlag();
        handler.post(new Runnable() {
            public void run() {
                messageScreen = new MessageScreen(context, player, messages, flag);
                positionMessageScreen(messageScreen, player);
                messageScreen.show();
            }
        });

        while (flag.getValue()) {
            invalidateGame();
            sleep(PLAY_DELAY);
        }
    }

    private void positionMessageScreen(MessageScreen messageScreen, Player player) {
        Canvas canvas = belotPanel.getBufferedCanvas();

        if (canvas != null) {
            Rectangle rect = belotPainter.getPlayerCardRectangle(canvas, beloteFacade, 0, player);

            switch (player.getID()) {
            case 0:
                WindowManager.LayoutParams lp = messageScreen.getWindow().getAttributes();
                lp.gravity = Gravity.TOP;
                lp.y = rect.y + rect.height;
                messageScreen.getWindow().setAttributes(lp);
                break;

            case 1:
                lp = messageScreen.getWindow().getAttributes();
                lp.gravity = Gravity.LEFT | Gravity.BOTTOM;
                lp.x = rect.x + rect.width;
                lp.y = (belotPanel.getHeight() + buttons.getHeight()) / 2;
                messageScreen.getWindow().setAttributes(lp);
                break;

            case 2:
                lp = messageScreen.getWindow().getAttributes();
                lp.gravity = Gravity.BOTTOM;
                lp.y = belotPanel.getHeight() + buttons.getHeight() - rect.y;
                messageScreen.getWindow().setAttributes(lp);
                break;

            case 3:
                messageScreen.getWindow().setGravity(Gravity.CENTER_VERTICAL);
                lp = messageScreen.getWindow().getAttributes();
                lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                lp.x = belotPanel.getWidth() - rect.x;
                lp.y = (belotPanel.getHeight() + buttons.getHeight()) / 2;
                messageScreen.getWindow().setAttributes(lp);
                break;
            }
        }
    }

    /**
     * Process rounds after human player.
     * 
     * @param keyCode
     * @param gameAction
     */
    private void processAfterHumanPlayer(float x, float y) {
        beloteFacade.getHumanPlayer().setSelectedCard(null);
        playRepeatedSingleRoundAfterHumanPlayer();
    }

    /**
     * Process rounds till human player.
     * 
     * @param keyCode
     * @param gameAction
     */
    private void processTillHumanPlayer(float x, float y) {
        if (beloteFacade.getHumanPlayer().equals(beloteFacade.getGame().getTrickAttackPlayer())) {
            selectHumanSingleCard();
            processSelectHumanCard(x, y);
        } else {
            playRepeatedSingleRoundTillHumanPlayer();
            selectHumanSingleCard();
        }
    }

    /**
     * Checks card click.
     * 
     * @param keyCode pressed key code.
     * @param gameAction status.
     * @return if a card was selected.
     */
    private boolean processSelectHumanCard(float x, float y) {
        if (beloteFacade.isPlayingGameMode() && beloteFacade.isHumanTrickOrder()) {
            Card card = getHumanCardUnderPointer(x, y);
            processSelectCard(card);
            return true;
        }
        return false;
    }

    /**
     * Checks double click card.
     */
    private boolean processPlaySelectedCard(final Card card) {
        if (beloteFacade.isPlayingGameMode() && beloteFacade.isHumanTrickOrder() && beloteFacade.getHumanPlayer().getSelectedCard() != null
                && beloteFacade.getHumanPlayer().getSelectedCard().equals(card)) {
            final Player player = beloteFacade.getHumanPlayer();

            if (beloteFacade.validatePlayerCard(player, beloteFacade.getHumanPlayer().getSelectedCard())) {
                // couples, preferred, unwanted and missed suit
                beloteFacade.processHumanPlayerCard(player, player.getSelectedCard());
                // repaint frame
                invalidateGame();
                // check for display message
                ArrayList<MessageData> messages = getMessageList(player, beloteFacade.getHumanPlayer().getSelectedCard());
                if (messages.size() > 0) {
                    displayMessage(player, messages);
                } else {
                    sleep(PLAY_DELAY);
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Process round playing.
     * @param keyCode pressed key code.
     * @param gameAction status.
     */
    private void processRoundPlay(float x, float y) {
        if (!beloteFacade.isHumanTrickOrder()) {
            if (beloteFacade.getHumanTrickCard() == null) {
                processTillHumanPlayer(x, y);
            } else {
                processAfterHumanPlayer(x, y);
            }
        }
    }

    public void invalidateGame() {
        invalidateGame(0);
    }

    public void invalidateGame(int delay) {
        Canvas canvas = belotPanel.getBufferedCanvas();
        if (canvas != null) {
            belotPainter.drawGame(canvas, beloteFacade, belotPanel, delay);
            belotPanel.refresh();
        }
    }

    public void onExit() {
        context.finish();
    }

    public void onSurfaceChanged() {
        if (messageScreen != null && messageScreen.isShowing()) {
            positionMessageScreen(messageScreen, messageScreen.getPlayer());
        }
    }

    public void onCloseEndGame() {
        sleep(PLAY_DELAY);
        newAnnounceDealRound();
        Belote belote = (Belote) context.getApplication();
        belote.getMessageProcessor().runMessaging();
    }
}