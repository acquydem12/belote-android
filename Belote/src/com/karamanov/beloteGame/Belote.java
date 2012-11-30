package com.karamanov.beloteGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import belote.bean.Game;
import belote.logic.HumanBeloteFacade;

import com.karamanov.framework.MessageApplication;
import com.karamanov.framework.message.MessageType;

public class Belote extends MessageApplication {

    public final static MessageType MT_KEY_PRESSED = new MessageType("MT_KEY_PRESSED");

    public final static MessageType MT_TOUCH_EVENT = new MessageType("MT_TOUCH_EVENT");

    public final static MessageType MT_EXIT_EVENT = new MessageType("MT_EXIT_EVENT");

    public final static MessageType MT_PAINT_EVENT = new MessageType("MT_PAINT_EVENT");
    
    public final static MessageType MT_CLOSE_END_GAME = new MessageType("MT_CLOSE_END_GAME");
    
    public final static MessageType MT_SURFACE_CHANGE = new MessageType("MT_SURFACE_CHANGE");
    
    /**
     * BELOTE_DAT constant.
     */
    private static final String BELOTE_DAT = "belote.dat";
    
    /**
     * Belote game facade (The enter point of game AI).
     */
    private final HumanBeloteFacade beloteFacade;

    public Belote() {
        super();
        beloteFacade = new HumanBeloteFacade();
    }
    
    public static HumanBeloteFacade getBeloteFacade(Activity context) {
        if (context.getApplication() instanceof Belote) {
            return ((Belote) context.getApplication()).beloteFacade;
        }

        return new HumanBeloteFacade();
    }
    
    public static synchronized void initBeloteFacade(Activity context) {
        if (!loadGame(context)) {
            getBeloteFacade(context).newGame();
        }
        
        checkBlackRedCardOrder(context);
    }
    
    public static void resetGame(Activity context) {
        getBeloteFacade(context).setGame(new Game());
        getBeloteFacade(context).newGame();
        context.deleteFile(BELOTE_DAT);

        checkBlackRedCardOrder(context);
    }
    
    private static void checkBlackRedCardOrder(Activity context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getString(R.string.prefBlackRedOrder);
        boolean blackRedOrder = preferences.getBoolean(key, Boolean.FALSE);
        getBeloteFacade(context).setBlackRedCardOrder(blackRedOrder);
    }
    
    public static boolean loadGame(Activity context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getString(R.string.prefStoreGame);
        boolean storeGame = preferences.getBoolean(key, Boolean.FALSE);

        if (storeGame) {
            try {
                FileInputStream fis = context.openFileInput(BELOTE_DAT);
                try {
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    try {
                        Object object = ois.readObject();
                        if (object instanceof Game) {
                            Game game = (Game) object;
                            getBeloteFacade(context).setGame(game);
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
    
    public static void terminate(Activity context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getString(R.string.prefStoreGame);
        boolean storeGame = preferences.getBoolean(key, Boolean.FALSE);

        if (storeGame) {
            try {
                FileOutputStream fos = context.openFileOutput(BELOTE_DAT, Context.MODE_PRIVATE);
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    try {
                        oos.writeObject(getBeloteFacade(context).getGame());
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
}