package com.karamanov.beloteGame.gui.screen.gameResume;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import belote.bean.Game;
import belote.bean.Player;
import belote.bean.announce.Announce;
import belote.bean.announce.suit.AnnounceSuit;
import belote.bean.pack.card.suit.Suit;
import belote.bean.pack.card.suit.SuitIterator;
import belote.bean.pack.sequence.Sequence;
import belote.bean.pack.sequence.SequenceIterator;
import belote.bean.pack.sequence.SequenceList;
import belote.bean.pack.square.Square;
import belote.bean.pack.square.SquareIterator;
import belote.bean.pack.square.SquareList;
import belote.logic.HumanBeloteFacade;

import com.karamanov.beloteGame.Belote;
import com.karamanov.beloteGame.R;
import com.karamanov.beloteGame.gui.graphics.PictureDecorator;
import com.karamanov.beloteGame.text.PlayerNameDecorator;
import com.karamanov.beloteGame.text.TextDecorator;
import com.karamanov.framework.graphics.ImageUtil;
import com.karamanov.framework.message.Message;

public final class GameResumeActivity extends Activity {

    private boolean showWinner = false;
    
    private boolean isShowWinner = false;

    private final String showWinnerStr = "SHOW_WINNER";

    public GameResumeActivity() {
        super();
    }

    private Game game;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSavedValues(savedInstanceState);
        
        game = Belote.getBeloteFacade(this).getGame();

        if (game != null) {
            if (isShowWinner && game.getWinnerTeam() != null) {
                setFaceView();
                showWinner = false;
            } else {
                isShowWinner = false;
                showWinner = game.getWinnerTeam() != null;

                int dip2 = Belote.fromPixelToDip(this, 2);
                int dip5 = Belote.fromPixelToDip(this, 5);

                ScrollView scroll = new ScrollView(this);
                LinearLayout vertical = new LinearLayout(this);
                vertical.setOrientation(LinearLayout.VERTICAL);
                RelativeLayout relative = new RelativeLayout(this);

                TextDecorator textDecorator = new TextDecorator(this);
                final ArrayList<String> announceText = textDecorator.getAnnounceText(game.getAnnounceList());

                LinearLayout score = new LinearLayout(this);
                score.setOrientation(LinearLayout.VERTICAL);
                score.setId(123);

                TextView scoreT = new TextView(this);

                scoreT.setText(getString(R.string.GameContract));
                scoreT.setTypeface(Typeface.SERIF, Typeface.BOLD);
                scoreT.setTextColor(Color.YELLOW);
                scoreT.setGravity(Gravity.CENTER_HORIZONTAL);
                scoreT.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                score.addView(scoreT);

                for (String s : announceText) {
                    TextView scoreV = new TextView(this);
                    scoreV.setText(s);
                    scoreV.setTypeface(Typeface.SERIF, Typeface.BOLD);
                    scoreV.setTextColor(Color.YELLOW);
                    scoreV.setGravity(Gravity.CENTER_HORIZONTAL);
                    scoreV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    score.addView(scoreV);
                }

                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                rp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                rp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                rp.topMargin = dip2;
                score.setLayoutParams(rp);
                relative.addView(score);

                TableLayout table = getGameInfo(game, textDecorator);
                if (table != null) {
                    rp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    rp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    rp.addRule(RelativeLayout.CENTER_IN_PARENT);
                    rp.addRule(RelativeLayout.BELOW, score.getId());
                    rp.topMargin = dip5;
                    table.setLayoutParams(rp);
                    relative.addView(table);
                }

                vertical.addView(relative);
                scroll.addView(vertical);
                scroll.setBackgroundResource(R.drawable.score_bkg);
                setContentView(scroll);
            }
        }
    }

    private void loadSavedValues(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Boolean bool = savedInstanceState.getBoolean(showWinnerStr);
            if (bool != null) {
                isShowWinner = bool.booleanValue();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(showWinnerStr, isShowWinner);
    }

    private TableLayout getGameInfo(Game game, TextDecorator textDecorator) {
        int dip2 = Belote.fromPixelToDip(this, 2);
        int dip4 = Belote.fromPixelToDip(this, 4);
        int dip5 = Belote.fromPixelToDip(this, 5);
        int dip10 = Belote.fromPixelToDip(this, 10);

        final Announce announce = game.getAnnounceList().getOpenContractAnnounce();
        if (announce != null) {
            final boolean ntAnnounce = announce.getAnnounceSuit().equals(AnnounceSuit.NotTrump);

            TableLayout table = new TableLayout(this);
            // Draw team player' sequences.
            PictureDecorator pictureDecorator = new PictureDecorator(this);

            for (int i = 0; i < game.getTeamsCount(); i++) {
                if (i == 1) {
                    TableRow fake = new TableRow(this);
                    TextView empty = new TextView(this);
                    TableRow.LayoutParams trlp = new TableRow.LayoutParams();
                    trlp.span = 2;
                    empty.setLayoutParams(trlp);
                    fake.addView(empty);

                    TableLayout.LayoutParams tlp = new TableLayout.LayoutParams();
                    tlp.bottomMargin = dip4;
                    tlp.topMargin = dip4;
                    fake.setLayoutParams(tlp);
                    table.addView(fake);
                }

                TableRow caption = new TableRow(this);
                TextView captionText = new TextView(this);
                TableRow.LayoutParams tp = new TableRow.LayoutParams();
                tp.span = 2;
                tp.weight = 1;
                captionText.setLayoutParams(tp);
                captionText.setSingleLine();

                PlayerNameDecorator player0 = new PlayerNameDecorator(game.getTeam(i).getPlayer(0));
                PlayerNameDecorator player1 = new PlayerNameDecorator(game.getTeam(i).getPlayer(1));

                captionText.setText(player0.decorate(this) + " - " + player1.decorate(this));
                captionText.setGravity(Gravity.CENTER_HORIZONTAL);
                captionText.setTextColor(Color.BLACK);
                captionText.setTypeface(Typeface.DEFAULT_BOLD);
                caption.addView(captionText);

                TableLayout.LayoutParams tap = new TableLayout.LayoutParams();
                tap.bottomMargin = dip2;
                caption.setLayoutParams(tap);
                caption.setBackgroundColor(Color.LTGRAY);
                table.addView(caption);

                TableRow row = new TableRow(this);
                TextView name = new TextView(this);
                name.setText(getString(R.string.Cards));
                name.setTextColor(Color.WHITE);
                tp = new TableRow.LayoutParams();
                name.setLayoutParams(tp);
                row.addView(name);

                TextView value = new TextView(this);
                value.setText(String.valueOf(game.getTeam(i).getPointsInfo().getCardPoints()));
                value.setTextColor(Color.WHITE);
                tp = new TableRow.LayoutParams();
                tp.weight = 1;
                tp.leftMargin = dip10;
                value.setLayoutParams(tp);
                row.addView(value);

                tap = new TableLayout.LayoutParams();
                tap.bottomMargin = dip2;
                row.setLayoutParams(tap);
                row.setBackgroundColor(Color.DKGRAY);
                row.setPadding(dip2, 0, dip2, 0);
                table.addView(row);

                if (game.getTeam(i).getAnnouncePoints() > 0 && !ntAnnounce) {
                    final String announcePoints = String.valueOf(game.getTeam(i).getPointsInfo().getAnnouncePoints());
                    row = new TableRow(this);

                    name = new TextView(this);
                    name.setText(getString(R.string.Announce));
                    name.setTextColor(Color.WHITE);
                    tp = new TableRow.LayoutParams();
                    // tp.weight = 0.3f;
                    name.setLayoutParams(tp);
                    row.addView(name);

                    LinearLayout linear = new LinearLayout(this);
                    linear.setOrientation(LinearLayout.VERTICAL);

                    value = new TextView(this);
                    value.setText(announcePoints);
                    value.setTextColor(Color.WHITE);

                    linear.addView(value);

                    for (int j = 0; j < game.getTeam(i).getPlayersCount(); j++) {

                        SquareList equalCardsList = game.getTeam(i).getPlayer(j).getCards().getSquaresList();
                        for (SquareIterator iterator = equalCardsList.iterator(); iterator.hasNext();) {
                            Square square = iterator.next();
                            TextView squareV = new TextView(this);
                            squareV.setText(textDecorator.getSquareText(square));
                            linear.addView(squareV);
                        }
                    }

                    for (int j = 0; j < game.getTeam(i).getPlayersCount(); j++) {
                        SequenceList sequencesList = game.getTeam(i).getPlayer(j).getCards().getSequencesList();
                        for (SequenceIterator iterator = sequencesList.iterator(); iterator.hasNext();) {
                            Sequence sequence = iterator.next();
                            Suit suit = sequence.getMaxCard().getSuit();

                            String strLeft = String.valueOf(sequence.getType().getSequencePoints());
                            strLeft += " (" + textDecorator.getRankSign(sequence.getMaxCard().getRank());
                            String strRight = ")";

                            Bitmap suitImage = game.getTeam(i).getPointsInfo().getAnnouncePoints() == 0 ? ImageUtil.transformToDisabledImage(pictureDecorator
                                    .getSuitImage(suit)) : pictureDecorator.getSuitImage(suit);

                            LinearLayout h = new LinearLayout(this);
                            h.setOrientation(LinearLayout.HORIZONTAL);

                            TextView leftV = new TextView(this);
                            leftV.setText(strLeft);
                            leftV.setTextColor(game.getTeam(i).getPointsInfo().getAnnouncePoints() == 0 ? Color.LTGRAY : Color.WHITE);
                            h.addView(leftV);

                            ImageView image = new ImageView(this);
                            image.setImageBitmap(suitImage);
                            h.addView(image);

                            TextView rightV = new TextView(this);
                            rightV.setText(strRight);
                            rightV.setTextColor(game.getTeam(i).getPointsInfo().getAnnouncePoints() == 0 ? Color.LTGRAY : Color.WHITE);
                            h.addView(rightV);

                            linear.addView(h);
                        }
                    }

                    tp = new TableRow.LayoutParams();
                    tp.weight = 1;
                    tp.leftMargin = dip10;
                    linear.setLayoutParams(tp);
                    row.addView(linear);

                    tap = new TableLayout.LayoutParams();
                    tap.bottomMargin = dip2;
                    row.setLayoutParams(tap);
                    row.setBackgroundColor(Color.DKGRAY);
                    row.setPadding(dip2, 0, dip2, 0);
                    table.addView(row);
                }

                if (game.getTeam(i).getPointsInfo().getCouplesPoints() > 0) {
                    final String couplePoints = String.valueOf(game.getTeam(i).getPointsInfo().getCouplesPoints());

                    row = new TableRow(this);

                    name = new TextView(this);
                    name.setText(getString(R.string.Couples));
                    name.setTextColor(Color.WHITE);
                    tp = new TableRow.LayoutParams();
                    // tp.weight = 0.3f;
                    name.setLayoutParams(tp);
                    row.addView(name);

                    LinearLayout linear = new LinearLayout(this);
                    linear.setOrientation(LinearLayout.HORIZONTAL);

                    value = new TextView(this);
                    value.setText(couplePoints);
                    value.setTextColor(Color.WHITE);
                    LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    llp.rightMargin = dip10;
                    value.setLayoutParams(llp);
                    linear.addView(value);

                    for (SuitIterator iterator = Suit.iterator(); iterator.hasNext();) {
                        Suit suit = iterator.next();
                        if (game.getTeam(i).getCouples().hasCouple(suit)) {
                            Bitmap suitImage = pictureDecorator.getSuitImage(suit);
                            ImageView iv = new ImageView(this);
                            iv.setImageBitmap(suitImage);
                            linear.addView(iv);
                        }
                    }

                    tp = new TableRow.LayoutParams();
                    tp.weight = 1;
                    tp.leftMargin = dip10;
                    linear.setLayoutParams(tp);
                    row.addView(linear);

                    tap = new TableLayout.LayoutParams();
                    tap.bottomMargin = dip2;
                    row.setLayoutParams(tap);
                    row.setBackgroundColor(Color.DKGRAY);
                    row.setPadding(dip2, 0, dip2, 0);
                    table.addView(row);
                }

                if (game.getTeam(i).getPointsInfo().getLastHand() > 0) {
                    final String lastHandPoints = String.valueOf(game.getTeam(i).getPointsInfo().getLastHand());

                    row = new TableRow(this);

                    name = new TextView(this);
                    name.setText(getString(R.string.LastHand));
                    name.setTextColor(Color.WHITE);
                    tp = new TableRow.LayoutParams();
                    // tp.weight = 0.3f;
                    name.setLayoutParams(tp);
                    row.addView(name);

                    value = new TextView(this);
                    value.setText(lastHandPoints);
                    value.setTextColor(Color.WHITE);

                    tp = new TableRow.LayoutParams();
                    tp.weight = 1;
                    tp.leftMargin = dip10;
                    value.setLayoutParams(tp);
                    row.addView(value);

                    tap = new TableLayout.LayoutParams();
                    tap.bottomMargin = dip2;
                    row.setLayoutParams(tap);
                    row.setBackgroundColor(Color.DKGRAY);
                    row.setPadding(dip2, 0, dip2, 0);
                    table.addView(row);
                }

                if (game.getTeam(i).getPointsInfo().getTotalPoints() >= 0) {
                    int totalRound = game.getTeam(i).getPointsInfo().getTotalTrickPoints();
                    int capotRound = game.getTeam(i).getPointsInfo().getCapotPoints() / 10;
                    final String totalRoundPoints = String.valueOf(totalRound - capotRound);

                    int total = game.getTeam(i).getPointsInfo().getTotalPoints();
                    int capot = game.getTeam(i).getPointsInfo().getCapotPoints();
                    final String totalPoints = String.valueOf(total - capot);

                    row = new TableRow(this);
                    name = new TextView(this);
                    name.setText(getText(R.string.Total));// + " " +
                                                          // getText(R.string.Points).toString().toLowerCase());
                    name.setTextColor(Color.WHITE);
                    tp = new TableRow.LayoutParams();
                    // tp.weight = 0.3f;
                    name.setLayoutParams(tp);
                    row.addView(name);

                    value = new TextView(this);
                    value.setText(totalRoundPoints + " (" + totalPoints + ")");
                    value.setTextColor(Color.WHITE);
                    tp = new TableRow.LayoutParams();
                    tp.weight = 1;
                    tp.leftMargin = dip10;
                    value.setLayoutParams(tp);
                    row.addView(value);

                    tap = new TableLayout.LayoutParams();
                    tap.bottomMargin = dip2;
                    row.setLayoutParams(tap);
                    row.setBackgroundColor(Color.DKGRAY);
                    row.setPadding(dip2, 0, dip2, 0);
                    table.addView(row);
                }

                if (game.getTeam(i).getPointsInfo().getCapotPoints() > 0) {
                    final String capotPoints = String.valueOf(game.getTeam(i).getPointsInfo().getCapotPoints() / 10);

                    row = new TableRow(this);
                    name = new TextView(this);
                    name.setText(getText(R.string.Capot));
                    name.setTextColor(Color.WHITE);
                    tp = new TableRow.LayoutParams();
                    // tp.weight = 0.3f;
                    name.setLayoutParams(tp);
                    row.addView(name);

                    value = new TextView(this);
                    value.setText(capotPoints);
                    value.setTextColor(Color.WHITE);
                    tp = new TableRow.LayoutParams();
                    tp.weight = 1;
                    tp.leftMargin = dip10;
                    value.setLayoutParams(tp);
                    row.addView(value);

                    tap = new TableLayout.LayoutParams();
                    row.setLayoutParams(tap);
                    row.setBackgroundColor(Color.DKGRAY);
                    row.setPadding(dip2, 0, dip2, 0);
                    table.addView(row);

                    final String totalPoints = String.valueOf(game.getTeam(i).getPointsInfo().getTotalTrickPoints());
                    final String message = totalPoints + " (" + String.valueOf(game.getTeam(i).getPointsInfo().getTotalPoints()) + ")";

                    row = new TableRow(this);
                    name = new TextView(this);
                    name.setText(getText(R.string.Total) + " " + getText(R.string.Points).toString().toLowerCase() + "(" + getString(R.string.Capot) + ")");
                    name.setTextColor(Color.WHITE);
                    tp = new TableRow.LayoutParams();
                    // tp.weight = 0.3f;
                    name.setLayoutParams(tp);
                    row.addView(name);

                    value = new TextView(this);
                    value.setText(message);
                    value.setTextColor(Color.WHITE);
                    tp = new TableRow.LayoutParams();
                    tp.weight = 1;
                    tp.leftMargin = dip10;
                    value.setLayoutParams(tp);
                    row.addView(value);

                    tap = new TableLayout.LayoutParams();
                    tap.bottomMargin = dip2;
                    row.setLayoutParams(tap);
                    row.setBackgroundColor(Color.DKGRAY);
                    row.setPadding(dip2, 0, dip2, 0);
                    table.addView(row);
                }
            }

            TableRow fake = new TableRow(this);
            TextView empty = new TextView(this);
            TableRow.LayoutParams trlp = new TableRow.LayoutParams();
            trlp.span = 2;
            empty.setLayoutParams(trlp);
            fake.addView(empty);

            TableLayout.LayoutParams tlp = new TableLayout.LayoutParams();
            tlp.bottomMargin = dip5;
            tlp.topMargin = dip5;
            fake.setLayoutParams(tlp);
            table.addView(fake);

            TableRow caption = new TableRow(this);
            TextView captionText = new TextView(this);
            TableRow.LayoutParams tp = new TableRow.LayoutParams();
            tp.span = 2;
            tp.weight = 1;
            // tp.gravity = Gravity.CENTER_HORIZONTAL;
            captionText.setLayoutParams(tp);
            captionText.setSingleLine();
            captionText.setText(getString(R.string.PointsDistribution));
            captionText.setGravity(Gravity.CENTER_HORIZONTAL);
            captionText.setTextColor(Color.BLACK);
            captionText.setTypeface(Typeface.DEFAULT_BOLD);
            caption.addView(captionText);

            TableLayout.LayoutParams tap = new TableLayout.LayoutParams();
            tap.bottomMargin = dip2;
            caption.setLayoutParams(tap);
            caption.setBackgroundColor(Color.LTGRAY);
            table.addView(caption);

            for (int i = 0; i < game.getTeamsCount(); i++) {
                TableRow row = new TableRow(this);
                TextView name = new TextView(this);

                PlayerNameDecorator player0 = new PlayerNameDecorator(game.getTeam(i).getPlayer(0));
                PlayerNameDecorator player1 = new PlayerNameDecorator(game.getTeam(i).getPlayer(1));

                name.setText(player0.decorate(this) + " - " + player1.decorate(this));
                name.setTextColor(Color.WHITE);
                tp = new TableRow.LayoutParams();
                // tp.weight = 0.3f;
                name.setLayoutParams(tp);
                row.addView(name);

                TextView value = new TextView(this);
                int points = 0;
                int size = game.getTeam(i).getPoints().size();
                if (size > 0) {
                    points = game.getTeam(i).getPoints().getPointsAt(size - 1);
                }
                value.setText(String.valueOf(points));
                value.setTextColor(Color.WHITE);
                tp = new TableRow.LayoutParams();
                tp.weight = 1;
                tp.leftMargin = dip10;
                value.setLayoutParams(tp);
                row.addView(value);

                tap = new TableLayout.LayoutParams();
                tap.bottomMargin = dip2;
                row.setLayoutParams(tap);
                row.setBackgroundColor(Color.DKGRAY);
                row.setPadding(dip2, 0, dip2, 0);
                table.addView(row);
            }

            if (game.getHangedPoints() > 0) {
                TableRow row = new TableRow(this);
                TextView name = new TextView(this);
                name.setText(getString(R.string.HangedPoints));
                name.setTextColor(Color.WHITE);
                tp = new TableRow.LayoutParams();
                // tp.weight = 0.3f;
                name.setLayoutParams(tp);
                row.addView(name);

                TextView value = new TextView(this);
                int points = game.getHangedPoints();

                value.setText(String.valueOf(points));
                value.setTextColor(Color.WHITE);
                tp = new TableRow.LayoutParams();
                tp.weight = 1;
                tp.leftMargin = dip10;
                value.setLayoutParams(tp);
                row.addView(value);

                tap = new TableLayout.LayoutParams();
                tap.bottomMargin = dip2;
                row.setLayoutParams(tap);
                row.setBackgroundColor(Color.DKGRAY);
                row.setPadding(dip2, 0, dip2, 0);
                table.addView(row);
            }
            return table;
        }

        return null;
    }

    private void setFaceView() {
        isShowWinner = true;
        int dip10 = Belote.fromPixelToDip(this, 10);

        LinearLayout vertical = new LinearLayout(this);
        vertical.setOrientation(LinearLayout.VERTICAL);

        ImageView image = new ImageView(this);

        String message;
        Player human = game.getPlayer(HumanBeloteFacade.HUMAN_PLAYER_INDEX);
        if (human.getTeam().equals(game.getWinnerTeam())) {
            image.setBackgroundResource(R.drawable.happy);
            message = getString(R.string.TeamWinsGame);
        } else {
            image.setBackgroundResource(R.drawable.unhappy);
            message = getString(R.string.TeamLostGame);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = dip10;
        params.bottomMargin = dip10;

        image.setLayoutParams(params);

        vertical.addView(image);

        TextView name = new TextView(this);
        name.setTextColor(Color.YELLOW);
        name.setTypeface(Typeface.SERIF, Typeface.BOLD);
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        name.setText(message);

        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = dip10;
        params.bottomMargin = dip10;
        name.setLayoutParams(params);
        vertical.addView(name);

        LinearLayout horizontal = new LinearLayout(this);
        horizontal.setOrientation(LinearLayout.HORIZONTAL);

        int orange = Color.rgb(255, 128, 64);

        TextView team0 = new TextView(this);
        if (game.getWinnerTeam().equals(game.getTeam(0))) {
            team0.setTextColor(orange);
        } else {
            team0.setTextColor(Color.YELLOW);
        }
        team0.setTypeface(Typeface.SERIF, Typeface.BOLD);
        team0.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        team0.setText(String.valueOf(game.getTeam(0).getPoints().getAllPoints()));
        horizontal.addView(team0);

        TextView sep = new TextView(this);
        sep.setTextColor(Color.YELLOW);
        sep.setTypeface(Typeface.SERIF, Typeface.BOLD);
        sep.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        sep.setText(" : ");
        horizontal.addView(sep);

        TextView team1 = new TextView(this);
        if (game.getWinnerTeam().equals(game.getTeam(1))) {
            team1.setTextColor(orange);
        } else {
            team1.setTextColor(Color.YELLOW);
        }
        team1.setTypeface(Typeface.SERIF, Typeface.BOLD);
        team1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        team1.setText(String.valueOf(game.getTeam(1).getPoints().getAllPoints()));
        horizontal.addView(team1);

        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = dip10;
        params.bottomMargin = dip10;
        horizontal.setLayoutParams(params);
        vertical.addView(horizontal);

        RelativeLayout relative = new RelativeLayout(this);
        relative.setBackgroundResource(R.drawable.score_bkg);

        RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rp.addRule(RelativeLayout.CENTER_VERTICAL);
        rp.addRule(RelativeLayout.CENTER_IN_PARENT);
        vertical.setLayoutParams(rp);

        relative.addView(vertical);

        setContentView(relative);
    }

    @Override
    public void onBackPressed() {
        if (game != null && showWinner && game.getWinnerTeam() != null) {
            setFaceView();
        } else {
            if (getApplication() instanceof Belote) {
                Belote belote = (Belote) getApplication();
                Message message = new Message(Belote.MT_CLOSE_END_GAME);
                belote.getMessageProcessor().sendMessage(message, true);
            }
            super.onBackPressed();
        }

        showWinner = false;
    }
}
