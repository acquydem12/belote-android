/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package com.karamanov.beloteGame.gui.screen.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import belote.bean.Game;
import belote.logic.HumanBeloteGame;

import com.karamanov.beloteGame.R;
import com.karamanov.beloteGame.gui.screen.base.GameActivity;
import com.karamanov.beloteGame.gui.screen.base.message.Message;
import com.karamanov.beloteGame.gui.screen.base.message.Messageable;
import com.karamanov.beloteGame.gui.screen.base.message.UserMessage;
import com.karamanov.beloteGame.gui.screen.base.message.UserMessageType;
import com.karamanov.beloteGame.gui.screen.pref.BelotePreferencesActivity;
import com.karamanov.beloteGame.gui.screen.score.ScoreActivity;
import com.karamanov.beloteGame.gui.screen.tricks.TricksActivity;

/**
 * BelotGameCanvas class.
 * 
 * @author Dimitar Karamanov
 */
public final class BeloteActivity extends GameActivity implements OnSharedPreferenceChangeListener {

	private static final String BELOTE_GAME = "BeloteGame";

	/**
	 * Belote game facade (The enter point of game AI).
	 */
	private final HumanBeloteGame beloteGame;

	/**
	 * BELOTE_DAT constant.
	 */
	private static final String BELOTE_DAT = "belote.dat";

	/**
	 * GAME_RESUME_CODE constant.
	 */
	public static final int GAME_RESUME_CODE = 2011;

	/**
	 * User message type used from message which runs game.
	 */
	public final UserMessageType MT_KEY_PRESSED;

	/**
	 * User message type used from message which runs game.
	 */
	public final UserMessageType MT_TOUCH_EVENT;

	/**
	 * User message type exit.
	 */
	public final UserMessageType MT_EXIT_EVENT;

	/**
	 * User message type exit.
	 */
	public final UserMessageType MT_PAINT_EVENT;

	public static final int NAV_PRESS = -1;
	public static final int NAV_LEFT = -2;
	public static final int NAV_RIGHT = -3;

	private static final int GAME_NEW_INDEX = 1;
	private static final int PLAYED_CARDS_INDEX = 2;
	private static final int GAME_SCORE_INDEX = 3;
	private static final int PREF_INDEX = 4;

	private static final int RESET_ANNOUNCE_INDEX = 5;

	private Dealer dealer;

	private RelativeLayout buttons;

	private BeloteView beloteView;

	private RelativeLayout relative;

	private boolean needRefresh = false;

	/**
	 * Constructor.
	 */
	public BeloteActivity() {
		super();

		beloteGame = new HumanBeloteGame();

		MT_KEY_PRESSED = initUserMessageType("MT_KEY_PRESSED");
		addMessageListener(MT_KEY_PRESSED, new KeyPressedListener());

		MT_TOUCH_EVENT = initUserMessageType("MT_TOUCH_EVENT");
		addMessageListener(MT_TOUCH_EVENT, new TouchListener());

		MT_EXIT_EVENT = initUserMessageType("MT_EXIT_EVENT");
		addMessageListener(MT_EXIT_EVENT, new ExitListener());

		MT_PAINT_EVENT = initUserMessageType("MT_PAINT_EVENT");
		addMessageListener(MT_PAINT_EVENT, new PaintListener());
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Serializable serializable = getBundleData(savedInstanceState, BELOTE_GAME);
		if (serializable instanceof Game) {
			Game game = (Game) serializable;
			beloteGame.setGame(game);
		} else {
			if (!loadGame()) {
				beloteGame.newGame();
			}
		}

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String key = getString(R.string.prefBlackRedOrder);
		boolean blackRedOrder = preferences.getBoolean(key, Boolean.FALSE);
		beloteGame.setBlackRedCardOrder(blackRedOrder);

		buttons = new RelativeLayout(this);
		buttons.setId(1);
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		buttons.setLayoutParams(rlp);

		ImageButton left = new ImageButton(this);
		left.setBackgroundResource(R.drawable.btn_left);
		rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		left.setLayoutParams(rlp);
		left.setOnClickListener(new ButtonPressListener(Integer.valueOf(NAV_LEFT)));
		left.setSoundEffectsEnabled(false);
		buttons.addView(left);

		ImageButton play = new ImageButton(this);
		play.setBackgroundResource(R.drawable.btn_play);
		rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		play.setLayoutParams(rlp);
		play.setOnClickListener(new ButtonPressListener(Integer.valueOf(NAV_PRESS)));
		play.setSoundEffectsEnabled(false);
		buttons.addView(play);

		ImageButton right = new ImageButton(this);
		right.setBackgroundResource(R.drawable.btn_right);
		rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		right.setLayoutParams(rlp);
		right.setOnClickListener(new ButtonPressListener(Integer.valueOf(NAV_RIGHT)));
		right.setSoundEffectsEnabled(false);
		buttons.addView(right);

		relative = new RelativeLayout(this);
		relative.addView(buttons);
		
		beloteView = new BeloteView(this);
		dealer = new Dealer(this, beloteGame, beloteView, buttons);
		rlp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		rlp.addRule(RelativeLayout.ABOVE, buttons.getId());
		beloteView.setLayoutParams(rlp);
		relative.addView(beloteView);

		boolean showBtns = preferences.getBoolean(getString(R.string.prefShowBtns), Boolean.TRUE);
		buttons.setVisibility(showBtns ? View.VISIBLE : View.GONE);

		setContentView(relative);

		//dealer.invalidateGame();

		preferences.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putSerializable(BELOTE_GAME, beloteGame.getGame());
	}

	protected final Serializable getBundleData(Bundle savedInstanceState, String key) {
		if (savedInstanceState != null) {
			Serializable data = savedInstanceState.getSerializable(key);
			return data;
		}
		return null;
	}

	protected void onResume() {
		super.onResume();

		if (needRefresh) {
			try {
				if (relative != null && beloteView != null) {
					relative.removeView(beloteView);
					relative.addView(beloteView);
				}
			} finally {
				needRefresh = false;
			}
		}
	}

	private void resetGame() {
		beloteGame.setGame(new Game());
		beloteGame.newGame();
		deleteFile(BELOTE_DAT);

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String key = getString(R.string.prefBlackRedOrder);
		boolean blackRedOrder = preferences.getBoolean(key, Boolean.FALSE);
		beloteGame.setBlackRedCardOrder(blackRedOrder);
	}

	private boolean loadGame() {

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String key = getString(R.string.prefStoreGame);
		boolean storeGame = preferences.getBoolean(key, Boolean.FALSE);

		if (storeGame) {
			try {
				FileInputStream fis = openFileInput(BELOTE_DAT);
				try {
					ObjectInputStream ois = new ObjectInputStream(fis);
					try {
						Object object = ois.readObject();
						if (object instanceof Game) {
							Game game = (Game) object;
							beloteGame.setGame(game);
							return true;
						}
					} finally {
						ois.close();
					}
				} finally {
					fis.close();
				}
			} catch (FileNotFoundException e) {
			} catch (StreamCorruptedException e) {
			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
			}
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		preferences.unregisterOnSharedPreferenceChangeListener(this);
		super.onDestroy();
	}

	/**
	 * Initialize the contents of the Activity's standard options menu. You
	 * should place your menu items in to menu.
	 * 
	 * @param menu - The options menu in which you place your items.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		int base = Menu.CATEGORY_SECONDARY;

		MenuItem newMenu = menu.add(base, base + GAME_NEW_INDEX, base + GAME_NEW_INDEX, getString(R.string.New));
		newMenu.setIcon(android.R.drawable.ic_menu_rotate);

		MenuItem scoreMenu = menu.add(base, base + GAME_SCORE_INDEX, base + GAME_SCORE_INDEX, getString(R.string.Score));
		scoreMenu.setIcon(android.R.drawable.ic_menu_info_details);

		MenuItem prefMenu = menu.add(base, base + PREF_INDEX, base + PREF_INDEX, getString(R.string.PREFERENCES));
		prefMenu.setIcon(android.R.drawable.ic_menu_manage);

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);

		int base = Menu.CATEGORY_SECONDARY;

		boolean showTricks = !beloteGame.getGame().getTrickList().isEmpty();

		MenuItem historyMenu = menu.findItem(base + PLAYED_CARDS_INDEX);
		if (showTricks) {
			if (historyMenu == null) {
				historyMenu = menu.add(base, base + PLAYED_CARDS_INDEX, base + PLAYED_CARDS_INDEX, getString(R.string.PastTricks));
				historyMenu.setIcon(R.drawable.ic_menu_tricks);
			}
		} else {
			if (historyMenu != null) {
				menu.removeItem(base + PLAYED_CARDS_INDEX);
			}
		}

		MenuItem resetAnnounceMenu = menu.findItem(base + RESET_ANNOUNCE_INDEX);
		if (beloteGame.getGame().isAnnounceGameMode() && beloteGame.getGame().getAnnounceList().getCount() > 0) {
			if (resetAnnounceMenu == null) {
				resetAnnounceMenu = menu.add(base, base + RESET_ANNOUNCE_INDEX, base + RESET_ANNOUNCE_INDEX, getString(R.string.ResetAnnounce));
				resetAnnounceMenu.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
			}
		} else {
			if (resetAnnounceMenu != null) {
				menu.removeItem(base + RESET_ANNOUNCE_INDEX);
			}
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = super.onOptionsItemSelected(item);

		int base = Menu.CATEGORY_SECONDARY;

		if (item.getItemId() == base + GAME_NEW_INDEX) {
			AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
			myAlertDialog.setTitle(getString(R.string.Confirm));
			myAlertDialog.setMessage(getString(R.string.NewEraseQuestion));
			myAlertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					resetGame();
					repaint();
				}
			});
			myAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					//
				}
			});
			myAlertDialog.show();
		}

		if (item.getItemId() == base + PLAYED_CARDS_INDEX) {
			Intent intent = new Intent(this, TricksActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(TricksActivity.BELOTE, beloteGame.getGame());
			startActivity(intent);
		}

		if (item.getItemId() == base + GAME_SCORE_INDEX) {
			Intent intent = new Intent(this, ScoreActivity.class);
			intent.putExtra(ScoreActivity.BELOTE, beloteGame.getGame());
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}

		if (item.getItemId() == base + PREF_INDEX) {
			Intent intent = new Intent(this, BelotePreferencesActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}

		if (item.getItemId() == base + RESET_ANNOUNCE_INDEX) {
			beloteGame.getGame().getAnnounceList().clear();
			repaint();
		}

		return result;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == GAME_RESUME_CODE) {
			dealer.setEndGameActivity(false);
		}
	}

	private class KeyPressedListener implements Messageable {

		@Override
		public void performMessage(Message message) {
			if (message.getData() instanceof Integer) {
				Integer integer = (Integer) message.getData();
				dealer.checkKeyPressed(integer.intValue());
			}
		}
	}

	private class TouchListener implements Messageable {

		@Override
		public void performMessage(Message message) {
			if (message.getData() instanceof PointF) {
				PointF pointF = (PointF) message.getData();
				dealer.checkPointerPressed(pointF.x, pointF.y);
			}
		}
	}

	private class ExitListener implements Messageable {

		@Override
		public void performMessage(Message message) {
			onTerminate();
			dealer.onExit();
		}
	}

	private class PaintListener implements Messageable {

		@Override
		public void performMessage(Message message) {
			dealer.invalidateGame();
		}
	}

	public void onTerminate() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String key = getString(R.string.prefStoreGame);
		boolean storeGame = preferences.getBoolean(key, Boolean.FALSE);

		if (storeGame) {
			try {
				FileOutputStream fos = openFileOutput(BELOTE_DAT, Context.MODE_PRIVATE);
				try {
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					try {
						oos.writeObject(beloteGame.getGame());
					} finally {
						oos.close();
					}
				} finally {
					fos.close();
				}
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		}
	}

	@Override
	public void onBackPressed() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String key = getString(R.string.prefAlertOnQuit);
		boolean alertOnQuit = preferences.getBoolean(key, Boolean.TRUE);

		if (alertOnQuit) {
			AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
			myAlertDialog.setTitle(getString(R.string.Confirm));
			myAlertDialog.setMessage(getString(R.string.ExitQuestion));
			myAlertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					UserMessage tMessage = new UserMessage(MT_EXIT_EVENT);
					triggerMessage(tMessage);
				}
			});
			myAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					//
				}
			});
			myAlertDialog.show();
		} else {
			UserMessage tMessage = new UserMessage(MT_EXIT_EVENT);
			triggerMessage(tMessage);
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(getString(R.string.prefShowBtns))) {
			boolean showBtns = sharedPreferences.getBoolean(key, Boolean.TRUE);
			needRefresh = true;

			if (buttons != null) {
				buttons.setVisibility(showBtns ? View.VISIBLE : View.GONE);
				repaint();
				if (beloteView != null && relative != null) {
					beloteView.invalidate(); // Work fine.
				}
			}
		}

		if (key.equals(getString(R.string.prefBlackRedOrder))) {
			boolean blackRedOrder = sharedPreferences.getBoolean(key, Boolean.FALSE);
			beloteGame.setBlackRedCardOrder(blackRedOrder);
			beloteGame.arrangePlayersCards();
			if (beloteView != null) {
				repaint();
				beloteView.invalidate(); // Work fine.
			}
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public void onSurfaceChanged() {
		dealer.onSurfaceChanged();
	}

	public void repaint() {
		UserMessage tMessage = new UserMessage(MT_PAINT_EVENT);
		triggerMessage(tMessage);
	}

	private class ButtonPressListener implements OnClickListener {

		private final Integer i;

		public ButtonPressListener(Integer i) {
			this.i = i;
		}

		@Override
		public void onClick(View view) {
			UserMessage tMessage = new UserMessage(MT_KEY_PRESSED, i);
			triggerMessage(tMessage);
		}
	}
}
