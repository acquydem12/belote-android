package com.karamanov.beloteGame.gui.screen.main.message;

import java.util.ArrayList;

import android.app.Dialog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import belote.bean.Player;

import com.karamanov.beloteGame.Belote;
import com.karamanov.beloteGame.R;
import com.karamanov.framework.MessageActivity;

public class MessageScreen extends Dialog {

    private final Player player;
    
    private final MessageActivity activity;

    public MessageScreen(MessageActivity context, Player player, ArrayList<MessageData> messages) {
        super(context);
        
        activity = context;
        this.player = player;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setBackgroundDrawableResource(R.drawable.message_shape);

        MessagePanel messagePanel = new MessagePanel(context, player, messages);
        setContentView(messagePanel);
    }

    public final Player getPlayer() {
        return player;
    }

    protected void onStop() {
        Belote belote = (Belote) activity.getApplication();
        belote.getMessageProcessor().unlock();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        dismiss();
        return true;
    }

    /**
     * Invoked when the navigational action is selected.
     * @param status - Bitfield of values defined by KeypadListener.
     * @param time - Number of milliseconds since the device was turned on.
     */
    public boolean onTouchEvent(MotionEvent event) {
        dismiss();
        return true;
    }
}
