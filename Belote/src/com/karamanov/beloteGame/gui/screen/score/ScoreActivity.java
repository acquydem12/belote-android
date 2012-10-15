package com.karamanov.beloteGame.gui.screen.score;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import belote.bean.Game;
import belote.bean.Team;

import com.karamanov.beloteGame.Belote;
import com.karamanov.beloteGame.R;
import com.karamanov.beloteGame.gui.graphics.PlayerNameDecorator;

public final class ScoreActivity extends Activity {

    public final static String BELOTE = "BELOTE";

    private Game game;

    public ScoreActivity() {
        super();
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent startingIntent = getIntent();
        if (startingIntent != null) {
            Bundle bundle = startingIntent.getExtras();
            if (bundle != null) {
                Serializable data = bundle.getSerializable(BELOTE);
                if (data instanceof Game) {
                    game = (Game) data;

                    int dip5 = Belote.fromPixelToDip(this, 5);

                    ScrollView scroll = new ScrollView(this);

                    LinearLayout relative = new LinearLayout(this);
                    relative.setOrientation(LinearLayout.VERTICAL);

                    TextView scoreView = new TextView(this);
                    scoreView.setText(getString(R.string.Score));
                    scoreView.setTypeface(Typeface.SERIF, Typeface.BOLD);
                    scoreView.setTextColor(Color.YELLOW);
                    scoreView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

                    LinearLayout.LayoutParams rp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    rp.gravity = Gravity.CENTER_HORIZONTAL;
                    rp.topMargin = dip5;
                    rp.bottomMargin = dip5;
                    scoreView.setLayoutParams(rp);
                    relative.addView(scoreView);

                    LinearLayout horizontal = new LinearLayout(this);
                    horizontal.setOrientation(LinearLayout.HORIZONTAL);
                    horizontal.setId(2);

                    int c1 = game.getTeam(0).getPoints().size();
                    int c2 = game.getTeam(1).getPoints().size();
                    int count = Math.max(c1, c2);

                    View left = getScore(game.getTeam(0), count);
                    View right = getScore(game.getTeam(1), count);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    lp.rightMargin = dip5;
                    left.setLayoutParams(lp);
                    horizontal.addView(left);
                    horizontal.addView(right);

                    rp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    rp.gravity = Gravity.CENTER_HORIZONTAL;
                    rp.topMargin = dip5;
                    horizontal.setLayoutParams(rp);
                    relative.addView(horizontal);

                    TextView hanged = new TextView(this);
                    hanged.setId(3);
                    if (game.getHangedPoints() > 0) {
                        String hp = getString(R.string.HangedPoints);
                        hanged.setText(hp + " " + game.getHangedPoints());
                    }
                    hanged.setTypeface(Typeface.SERIF, Typeface.BOLD);
                    hanged.setTextColor(Color.RED);
                    hanged.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                    rp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    rp.gravity = Gravity.CENTER_HORIZONTAL;
                    rp.topMargin = dip5;
                    hanged.setLayoutParams(rp);
                    relative.addView(hanged);

                    scroll.addView(relative);
                    scroll.setBackgroundResource(R.drawable.score_bkg);
                    setContentView(scroll);
                }
            }
        }
    }

    private String getTeamCaption(Team team) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < team.getPlayersCount(); i++) {

            PlayerNameDecorator playerDecorator = new PlayerNameDecorator(team.getPlayer(i));

            String pName = playerDecorator.decorate(this);
            if (pName.length() > 0) {
                if (sb.length() > 0) {
                    sb.append(" & ");
                }

                sb.append(pName.charAt(0));
            }
        }

        return sb.toString();
    }

    private View getScore(Team team, int count) {
        int dip3 = Belote.fromPixelToDip(this, 3);
        int dip5 = Belote.fromPixelToDip(this, 5);
        TableLayout table = new TableLayout(this);
        String sample = "0000";
        String tString = " - ";

        TableRow caption = new TableRow(this);

        LinearLayout linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);

        TextView capView = new TextView(this);
        capView.setText(getTeamCaption(team));
        capView.setGravity(Gravity.CENTER_HORIZONTAL);
        capView.setTextColor(Color.BLACK);
        capView.setTypeface(Typeface.DEFAULT_BOLD);

        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        linearParams.gravity = Gravity.CENTER_HORIZONTAL;
        capView.setLayoutParams(linearParams);
        linear.addView(capView);

        TextView bigView = new TextView(this);
        bigView.setText(String.valueOf(team.getWinBelotGames()));
        bigView.setGravity(Gravity.CENTER_HORIZONTAL);
        bigView.setTextColor(Color.RED);
        bigView.setTypeface(Typeface.DEFAULT_BOLD);

        linearParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        linearParams.gravity = Gravity.CENTER_HORIZONTAL;
        bigView.setLayoutParams(linearParams);
        linear.addView(bigView);

        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        if (count > 0) {
            tableRowParams.span = 3;
        } else {
            Rect rect = new Rect();
            String sampleTitle = sample + tString + sample;
            capView.getPaint().getTextBounds(sampleTitle, 0, sampleTitle.length(), rect);
            capView.setMinimumWidth(rect.width());
        }

        linear.setLayoutParams(tableRowParams);
        caption.setBackgroundColor(Color.LTGRAY);
        caption.addView(linear);

        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams();
        tableParams.bottomMargin = dip5;
        caption.setLayoutParams(tableParams);
        caption.setPadding(dip3, 0, dip3, 0);

        table.addView(caption);

        for (int i = 0, sum = 0, size = team.getPoints().size(); i < size; i++) {
            TableRow row = new TableRow(this);
            sum += team.getPoints().getPointsAt(i);
            TextView sumView = new TextView(this);
            sumView.setText(String.valueOf(sum));
            sumView.setGravity(Gravity.RIGHT);
            sumView.setTextColor(Color.LTGRAY);

            Rect rect = new Rect();
            sumView.getPaint().getTextBounds(sample, 0, sample.length(), rect);
            int minWidth = rect.width();

            rect = new Rect();
            sumView.getPaint().getTextBounds(tString, 0, tString.length(), rect);
            int minTextWidth = rect.width();

            sumView.setMinWidth(minWidth);
            row.addView(sumView);

            if (i + 1 < size) {
                TextView textView = new TextView(this);
                textView.setText(tString);
                row.addView(textView);
                textView.setMinWidth(minTextWidth);

                TextView pointsView = new TextView(this);
                pointsView.setText(String.valueOf(team.getPoints().getPointsAt(i + 1)));
                pointsView.setMinWidth(minWidth);
                pointsView.setGravity(Gravity.RIGHT);
                row.addView(pointsView);
            } else {
                sumView.setTextColor(Color.WHITE);
                sumView.setTypeface(Typeface.DEFAULT_BOLD);

                TextView fakeOne = new TextView(this);
                fakeOne.setMinWidth(minTextWidth);
                row.addView(fakeOne);

                TextView fakeTwo = new TextView(this);
                fakeTwo.setMinWidth(minWidth);
                row.addView(fakeTwo);
            }

            tableParams = new TableLayout.LayoutParams();
            tableParams.bottomMargin = dip5;
            row.setLayoutParams(tableParams);
            row.setBackgroundColor(Color.DKGRAY);
            row.setPadding(dip3, 0, dip3, 0);
            table.addView(row);
        }

        int dif = count - team.getPoints().size();
        for (int i = 0; i < dif; i++) {
            TableRow row = new TableRow(this);

            TextView v1 = new TextView(this);
            Rect r = new Rect();
            v1.getPaint().getTextBounds(sample, 0, sample.length(), r);
            int minWidth = r.width();

            r = new Rect();
            v1.getPaint().getTextBounds(tString, 0, tString.length(), r);
            int tWidth = r.width();

            v1.setMinWidth(minWidth);
            row.addView(v1);

            TextView v2 = new TextView(this);
            v2.setMinWidth(tWidth);
            row.addView(v2);

            TextView v3 = new TextView(this);
            v3.setMinWidth(minWidth);
            row.addView(v3);

            TableLayout.LayoutParams tlp = new TableLayout.LayoutParams();
            tlp.bottomMargin = dip5;
            row.setLayoutParams(tlp);
            row.setBackgroundColor(Color.DKGRAY);
            table.addView(row);
        }

        return table;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            finish();
        }
        return super.onTouchEvent(event);
    }
}
