package com.parrot.freeflight.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.parrot.freeflight.R;
import com.parrot.freeflight.ui.hud.Sprite;
import com.parrot.freeflight.ui.hud.Text;
import com.parrot.freeflight.video.VideoStageRenderer;
import android.widget.TextView;
import com.parrot.freeflight.milTools.*;

import java.io.FileOutputStream;

import static com.parrot.freeflight.milTools.DroneCompass.*;

/**
 * Created by Emily on 4/2/2016.
 */
public class textUpdateThread extends Thread {




    private Text leftText;
    private Text rightText;

    private final int LEFTTEXT_ID=24;
    private final int RIGHTTEXT_ID=25;

    textUpdateThread(VideoStageRenderer renderer, Activity context){

        Resources res = context.getResources();

        leftText = new Text(context, "", Sprite.Align.BOTTOM_LEFT);
        leftText.setMargin((int) res.getDimension(R.dimen.hud_alert_text_margin_top), 0, 0, 0);
        leftText.setTextColor(Color.RED);
        leftText.setTextSize((int) res.getDimension(R.dimen.hud_alert_text_size));
        leftText.setBold(true);

        rightText = new Text(context, "TEST2", Sprite.Align.BOTTOM_RIGHT);
        rightText.setMargin((int) res.getDimension(R.dimen.hud_alert_text_margin_top), 0, 0, 0);
        rightText.setTextColor(Color.RED);
        rightText.setTextSize((int) res.getDimension(R.dimen.hud_alert_text_size));
        rightText.setBold(true);

        renderer.addSprite(LEFTTEXT_ID, leftText);
        renderer.addSprite(RIGHTTEXT_ID, rightText);

        try {
            getText(renderer, context);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void getText(final VideoStageRenderer renderer, final Activity context) throws InterruptedException {

        //DroneGps gps = new DroneGps(context);

//        Thread.sleep(30000);
//        DroneCompass compass = new DroneCompass(context);


        DroneCompass compass = new DroneCompass(context);

        while (true){
            rightText.setText("4801 2605");
            leftText.setText("" + compass.getAzimuthInMils());
            renderer.addSprite(LEFTTEXT_ID, leftText);
            renderer.addSprite(RIGHTTEXT_ID, rightText);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }


    }




}
