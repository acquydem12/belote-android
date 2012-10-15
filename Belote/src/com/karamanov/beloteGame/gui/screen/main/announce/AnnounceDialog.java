/*
 * Copyright (c) Dimitar Karamanov 2008-2010. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package com.karamanov.beloteGame.gui.screen.main.announce;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import belote.bean.Player;
import belote.bean.announce.Announce;
import belote.bean.announce.suit.AnnounceSuit;
import belote.bean.announce.type.AnnounceType;
import belote.logic.BeloteGame;

import com.karamanov.beloteGame.Belote;
import com.karamanov.beloteGame.R;
import com.karamanov.beloteGame.gui.screen.base.BooleanFlag;
import com.karamanov.beloteGame.text.TextDecorator;

/**
 * AnnouncePanel class.
 * @author Dimitar Karamanov
 */
public class AnnounceDialog extends Dialog {
	
	private final LinearLayout vertical;

    /**
     * Pass button.
     */
    private final AnnounceButtonField jrbPass;

    /**
     * Clubs button.
     */
    private final AnnounceButtonField jrbClubs;

    /**
     * Diamond button.
     */
    private final AnnounceButtonField jrbDiamonds;

    /**
     * Hearts button.
     */
    private final AnnounceButtonField jrbHearts;

    /**
     * Spades button.
     */
    private final AnnounceButtonField jrbSpades;

    /**
     * Not trump button.
     */
    private final AnnounceButtonField jrbNotTrump;

    /**
     * All trump button.
     */
    private final AnnounceButtonField jrbAllTrump;

    /**
     * Double button.
     */
    private final AnnounceButtonField jrbDouble;

    /**
     * Redouble button.
     */
    private final AnnounceButtonField jrbRedouble;

    /**
     * All aces picture.
     */
    private final ImageView pAllAces;

    /**
     * All jacks picture.
     */
    private final ImageView pAllJacks;

    /**
     * Double picture.
     */
    private final ImageView pDouble;

    /**
     * Redouble picture.
     */
    private final ImageView pRedouble;

    /**
     * Club picture.
     */
    private final ImageView pClub;

    /**
     * Diamond picture.
     */
    private final ImageView pDiamond;

    /**
     * Heart picture.
     */
    private final ImageView pHeart;

    /**
     * Spade picture.
     */
    private final ImageView pSpade;

    /**
     * Announce label.
     */
    private final TextView announceLabel;

    /**
     * Belot game object.
     */
    private final BeloteGame game;

    /**
     * Text decorator of game beans object (Suit, Rank, Announce ...)
     */
    private final TextDecorator decorator;
    
    private final BooleanFlag wait;
    
    /**
     * Constructor.
     * @param game a BelotGame instance.
     * @param parent component.
     */
    public AnnounceDialog(Context context, final BeloteGame game) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setBackgroundDrawableResource(R.drawable.announce_dlg);
        
        
        wait = new BooleanFlag();
        int dip5 = Belote.fromPixelToDip(context, 5);
        int dip15 = Belote.fromPixelToDip(context, 15);
                
        vertical = new LinearLayout(context);
        vertical.setOrientation(LinearLayout.VERTICAL);
        vertical.setPadding(dip5, dip5, dip5, dip5);        
        
        announceLabel = new TextView(context);
        announceLabel.setId(1);
        announceLabel.setTextColor(Color.rgb(255, 99, 71));
        announceLabel.setGravity(Gravity.CENTER_HORIZONTAL);
        vertical.addView(announceLabel);
        
        TableLayout tl = new TableLayout(context);

        this.game = game;
        decorator = new TextDecorator(context);

        MyFieldChangeListener mfl = new MyFieldChangeListener();

        //First Row
        //Second Row
        TableRow row = new TableRow(context);
        //Left
        jrbClubs = new AnnounceButtonField(context, context.getString(R.string.ClubsAnnounce));
        jrbClubs.setOnClickListener(mfl);
        
        pClub = new ImageView(context);
        pClub.setImageResource(R.drawable.club);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.rightMargin = dip5;
        pClub.setLayoutParams(lp);
        
        LinearLayout relative = new LinearLayout(context);
        
        relative.addView(pClub);
        relative.addView(jrbClubs);
        
        TableRow.LayoutParams trlp = new TableRow.LayoutParams();
        trlp.weight = 0.5f;
        relative.setLayoutParams(trlp);
        row.addView(relative);
        //Right
        jrbNotTrump = new AnnounceButtonField(context, context.getString(R.string.NotTrumpsAnnounce));
        jrbNotTrump.setOnClickListener(mfl);
        
        pAllAces = new ImageView(context);
        pAllAces.setImageResource(R.drawable.all_aces);
        
        relative = new LinearLayout(context);
        lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.leftMargin = dip15;
        pAllAces.setLayoutParams(lp);
        relative.addView(pAllAces);
        relative.addView(jrbNotTrump);

        trlp = new TableRow.LayoutParams();
        trlp.weight = 0.5f;
        relative.setLayoutParams(trlp);
        row.addView(relative);
        
        tl.addView(row);
        
        //Third Row
        row = new TableRow(context);
        //Left
        jrbDiamonds = new AnnounceButtonField(context, context.getString(R.string.DiamondsAnnounce));
        jrbDiamonds.setOnClickListener(mfl);
        
        pDiamond = new ImageView(context);
        pDiamond.setImageResource(R.drawable.diamond) ;
        lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.rightMargin = dip5;
        pDiamond.setLayoutParams(lp);
        
        relative = new LinearLayout(context);
        relative.addView(pDiamond);
        relative.addView(jrbDiamonds);
        
        trlp = new TableRow.LayoutParams();
        trlp.weight = 0.5f;
        relative.setLayoutParams(trlp);
        row.addView(relative);

        //Right
        jrbAllTrump = new AnnounceButtonField(context, context.getString(R.string.AllTrumpsAnnounce));
        jrbAllTrump.setOnClickListener(mfl);
        
        pAllJacks = new ImageView(context);
        pAllJacks.setImageResource(R.drawable.all_jacks);
        lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.leftMargin = dip15;
        pAllJacks.setLayoutParams(lp);
        
        relative = new LinearLayout(context);
        relative.addView(pAllJacks);
        relative.addView(jrbAllTrump);
        
        trlp = new TableRow.LayoutParams();
        trlp.weight = 0.5f;
        relative.setLayoutParams(trlp);
        row.addView(relative);
        
        tl.addView(row);
        
        //Fourth Row
        row = new TableRow(context);
        //Left
        jrbHearts = new AnnounceButtonField(context, context.getString(R.string.HeartsAnnounce));
        jrbHearts.setOnClickListener(mfl);
        
        pHeart = new ImageView(context);
        pHeart.setImageResource(R.drawable.heart) ;
        lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.rightMargin = dip5;
        lp.gravity = Gravity.CENTER;
        pHeart.setLayoutParams(lp);
        
        relative = new LinearLayout(context);
        relative.addView(pHeart);
        relative.addView(jrbHearts);
                        
        trlp = new TableRow.LayoutParams();
        trlp.weight = 0.5f;
        relative.setLayoutParams(trlp);
        row.addView(relative);
        
        //Right
        jrbDouble = new AnnounceButtonField(context, context.getString(R.string.DoubleAnnounce));
        jrbDouble.setOnClickListener(mfl);
                
        pDouble = new ImageView(context);
        pDouble.setImageResource(R.drawable.double_c);
        lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.leftMargin = dip15;
        pDouble.setLayoutParams(lp);
        
        relative = new LinearLayout(context);
        relative.addView(pDouble);
        relative.addView(jrbDouble);
        
        trlp = new TableRow.LayoutParams();
        trlp.weight = 0.5f;
        relative.setLayoutParams(trlp);
        row.addView(relative);
        
        tl.addView(row);
        
        //Sixth Row
        row = new TableRow(context);
        jrbSpades = new AnnounceButtonField(context, context.getString(R.string.SpadesAnnounce));
        jrbSpades.setOnClickListener(mfl);
        
        pSpade = new ImageView(context);
        pSpade.setImageResource(R.drawable.spade);
        lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.rightMargin = dip5;
        pSpade.setLayoutParams(lp);
        
        relative = new LinearLayout(context);
        relative.addView(pSpade);
        relative.addView(jrbSpades);
        
        trlp = new TableRow.LayoutParams();
        trlp.weight = 0.5f;
        relative.setLayoutParams(trlp);
        row.addView(relative);
        
        //Right
        jrbRedouble = new AnnounceButtonField(context, context.getString(R.string.RedoubleAnnounce));
        jrbRedouble.setOnClickListener(mfl);
        
        pRedouble = new ImageView(context);
        pRedouble.setImageResource(R.drawable.redouble);
        lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.leftMargin = dip15;
        pRedouble.setLayoutParams(lp);
        
        relative = new LinearLayout(context);
        relative.addView(pRedouble);
        relative.addView(jrbRedouble);
        
        trlp = new TableRow.LayoutParams();
        trlp.weight = 0.5f;
        relative.setLayoutParams(trlp);
        row.addView(relative);
        
        tl.addView(row);
        
        row = new TableRow(context);
        jrbPass = new AnnounceButtonField(context, context.getString(R.string.PassAnnounce));
        jrbPass.setOnClickListener(mfl);
        trlp = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        trlp.span = 2;
        trlp.gravity = Gravity.CENTER_HORIZONTAL;
        
        relative = new LinearLayout(context);
        relative.addView(jrbPass);
        
        relative.setLayoutParams(trlp);
        row.addView(relative);
    
        tl.addView(row);
       
        vertical.addView(tl);
            
        setContentView(vertical);
    }
    
    public void onBackPressed () {
    	//DN
    }


    /**
     * ButtonKeyPressedAdapter class.
     * Implements KeyEventListener.
     */
    private class MyFieldChangeListener implements View.OnClickListener {
    	
		@Override
		public void onClick(View view) {
			game.getGame().getAnnounceList().add(generatePlayerAnnounce(view));
            AnnounceDialog.this.dismiss();
		}
    }

    /**
     * Init method.
     */
    public void init() {
        initButtonsByAnnounces();
    }
    
    public boolean getValue() {
    	return wait.getValue();
    }
    
    public void setTrue() {
    	wait.setTrue();
    }
    
    protected void onStop () {
    	wait.setFalse();
	}

    /**
     * Buttons initialisation depending current announce status.
     */
    private void initButtonsByAnnounces() {
        jrbDouble.setEnabled(false);
        jrbRedouble.setEnabled(false);
        pDouble.setEnabled(false);
        pRedouble.setEnabled(false);
        jrbDouble.setEnabled(false);
        jrbRedouble.setEnabled(false);


        jrbClubs.setEnabled(true);
        pClub.setEnabled(true);
        jrbDiamonds.setEnabled(true);
        pDiamond.setEnabled(true);
        jrbHearts.setEnabled(true);
        pHeart.setEnabled(true);
        jrbSpades.setEnabled(true);
        pSpade.setEnabled(true);
        jrbNotTrump.setEnabled(true);
        pAllAces.setEnabled(true);
        jrbAllTrump.setEnabled(true);
        pAllJacks.setEnabled(true);

        Announce last = game.getGame().getAnnounceList().getContractAnnounce();

        if (last != null) {
            Player lastAnnouncePlayer = last.getPlayer();
            Player announcePlayer = game.getNextAnnouncePlayer();

            boolean sameTeam = announcePlayer.getTeam() == lastAnnouncePlayer.getTeam();

            if (!sameTeam && last.getType().equals(AnnounceType.Normal)) {
                jrbDouble.setEnabled(true);
                pDouble.setEnabled(true);
            }

            if (!sameTeam && last.getType().equals(AnnounceType.Double)) {
                jrbRedouble.setEnabled(true);
                pRedouble.setEnabled(true);
            }

            if (last.getAnnounceSuit().compareTo(AnnounceSuit.Club) >= 0) {
                jrbClubs.setEnabled(false);
                pClub.setEnabled(false);
            }

            if (last.getAnnounceSuit().compareTo(AnnounceSuit.Diamond) >= 0) {
                jrbDiamonds.setEnabled(false);
                pDiamond.setEnabled(false);
            }

            if (last.getAnnounceSuit().compareTo(AnnounceSuit.Heart) >= 0) {
                jrbHearts.setEnabled(false);
                pHeart.setEnabled(false);
            }

            if (last.getAnnounceSuit().compareTo(AnnounceSuit.Spade) >= 0) {
                jrbSpades.setEnabled(false);
                pSpade.setEnabled(false);
            }

            if (last.getAnnounceSuit().compareTo(AnnounceSuit.NotTrump) >= 0) {
                jrbNotTrump.setEnabled(false);
                pAllAces.setEnabled(false);
            }

            if (last.getAnnounceSuit().compareTo(AnnounceSuit.AllTrump) >= 0) {
                jrbAllTrump.setEnabled(false);
                pAllJacks.setEnabled(false);
            }
        }

        if (last == null) {
        	if (announceLabel.getVisibility() != View.GONE) {
        		announceLabel.setVisibility(View.GONE);
        	}
        } else {
            announceLabel.setText(decorator.getAnnounceTextEx(game.getGame().getAnnounceList()));
        	if (announceLabel.getVisibility() != View.VISIBLE) {
        		announceLabel.setVisibility(View.VISIBLE);
        	}            
        }
    }

    /**
     * Generates player announce by the receiver component.
     * @param receiver - the button which was pressed.
     * @return Announce instance.
     */
    private Announce generatePlayerAnnounce(final View receiver) {

        final Player player = game.getNextAnnouncePlayer();

        if (receiver == jrbPass) {
            return Announce.createPassAnnounce(player);
        }
        if (receiver == jrbClubs) {
            return Announce.createSuitNormalAnnounce(player, AnnounceSuit.Club);
        }
        if (receiver == jrbDiamonds) {
            return Announce.createSuitNormalAnnounce(player, AnnounceSuit.Diamond);
        }
        if (receiver == jrbHearts) {
            return Announce.createSuitNormalAnnounce(player, AnnounceSuit.Heart);
        }
        if (receiver == jrbSpades) {
            return Announce.createSuitNormalAnnounce(player, AnnounceSuit.Spade);
        }

        if (receiver == jrbNotTrump) {
            return Announce.createNTNormalAnnounce(player);
        }

        if (receiver == jrbAllTrump) {
            return Announce.createATNormalAnnounce(player);
        }

        if (receiver == jrbDouble) {
            Announce announce = game.getGame().getAnnounceList().getContractAnnounce();
            if (announce != null) {
                return Announce.createDoubleAnnounce(announce, player);
            }
        }

        if (receiver == jrbRedouble) {
            Announce announce = game.getGame().getAnnounceList().getContractAnnounce();
            if (announce != null) {
                return Announce.createRedoubleAnnounce(announce, player);
            }
        }

        return Announce.createPassAnnounce(player);
    }
}