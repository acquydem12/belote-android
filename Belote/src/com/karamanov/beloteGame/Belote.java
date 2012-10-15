package com.karamanov.beloteGame;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class Belote extends Application {
	
	public Belote() {
		super();
		//BelotExceptionHamdler handler = new BelotExceptionHamdler();
		//Thread.setDefaultUncaughtExceptionHandler(handler);
		
		/*
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
		    	BelotLogicTest.test();
			}
		});
		t.start();
		*/
	}

	/*
	public static void _saveLog(ArrayList<String> log) {
		try {
		    File root = Environment.getExternalStorageDirectory();
		    if (root.canWrite()){
		        File file = new File(root, "belotLog.txt");
		        file.createNewFile();
		        FileWriter fileWriter = new FileWriter(file);
		        BufferedWriter out = new BufferedWriter(fileWriter);
		        for (String text : log) {
		        	out.write(text + "\n");		        
		        }
		        out.close();
		    }
		} catch (IOException e) {
			//D.N.
		}
	}
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
}

/*
class BelotExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Context context = Belote.getContext();
		if (context != null) {
			AlertDialog alertDialog = new AlertDialog.Builder(context).create();
			alertDialog.setTitle("Error");
			alertDialog.setMessage(ex.toString());
		}
	}
}
*/