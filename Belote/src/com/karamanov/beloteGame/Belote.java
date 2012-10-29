package com.karamanov.beloteGame;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.karamanov.beloteGame.message.MessageProcessor;

public class Belote extends Application {

    private Object data;

    private final MessageProcessor messageProcessor;

    public Belote() {
        super();

        messageProcessor = new MessageProcessor();

        // BelotExceptionHamdler handler = new BelotExceptionHamdler();
        // Thread.setDefaultUncaughtExceptionHandler(handler);

        /*
         * Thread t = new Thread(new Runnable() {
         * 
         * @Override public void run() { BelotLogicTest.test(); } }); t.start();
         */
    }

    @Override
    public void onCreate() {
        super.onCreate();
        messageProcessor.start();
    }

    public final MessageProcessor getMessageProcessor() {
        return messageProcessor;
    }

    /*
     * public static void _saveLog(ArrayList<String> log) { try { File root = Environment.getExternalStorageDirectory(); if (root.canWrite()){ File file = new
     * File(root, "belotLog.txt"); file.createNewFile(); FileWriter fileWriter = new FileWriter(file); BufferedWriter out = new BufferedWriter(fileWriter); for
     * (String text : log) { out.write(text + "\n"); } out.close(); } } catch (IOException e) { //D.N. } }
     */

    public static int fromPixelToDip(Context context, int pixels) {
        Resources resources = context.getResources();
        if (pixels == 1) {
            return Math.max(1, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, resources.getDisplayMetrics())));
        } else {
            return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, resources.getDisplayMetrics()));
        }
    }

    public static float fromPixelToDipF(Context context, float pixels) {
        Resources resources = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, resources.getDisplayMetrics());
    }

    public final void setData(Object data) {
        this.data = data;
    }

    public final Object getData() {
        return data;
    }
}

/*
 * class BelotExceptionHandler implements UncaughtExceptionHandler {
 * 
 * @Override public void uncaughtException(Thread thread, Throwable ex) { Context context = Belote.getContext(); if (context != null) { AlertDialog alertDialog
 * = new AlertDialog.Builder(context).create(); alertDialog.setTitle("Error"); alertDialog.setMessage(ex.toString()); } } }
 */