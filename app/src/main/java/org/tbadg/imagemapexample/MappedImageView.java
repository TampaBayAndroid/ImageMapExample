/*
 * Copyright (c) 2018 Scott A Thisse and The Tampa Bay Android Developers Group
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required by
 *  applicable law or agreed to in writing, software distributed under the License
 *  is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied. See the License for the specific language
 *  governing permissions and limitations under the License.
 */

package org.tbadg.imagemapexample;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import static android.view.View.OnTouchListener;

public class MappedImageView extends AppCompatImageView implements OnTouchListener {
    private static final String LOG_TAG = MappedImageView.class.getSimpleName();

    private float displayLeft;
    private float displayTop;
    private float displayScaleX;
    private float displayScaleY;

    public interface Listener {
        void mapResult(String selection);
    }

    private ArrayList<MapItem> mapList;
    private Listener listener;

    public MappedImageView(Context context) {
        super(context);
    }

    public MappedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(ArrayList<MapItem> mapList, Listener listener) {
        this.mapList = mapList;
        this.listener = listener;

        setFocusable(true);
        setFocusableInTouchMode(true);
        setOnTouchListener(this);

        post(new Runnable() {
            @Override
            public void run() {
                float[] imageMatrix = new float[9];
                getImageMatrix().getValues(imageMatrix);
                displayScaleX = imageMatrix[Matrix.MSCALE_X];
                displayScaleY = imageMatrix[Matrix.MSCALE_Y];
                displayLeft = imageMatrix[Matrix.MTRANS_X];
                displayTop = imageMatrix[Matrix.MTRANS_Y];
            }
        });
    }

    public boolean onTouch(View view, MotionEvent event) {
        int xPos = Math.round((event.getX() - displayLeft) / displayScaleX);
        int yPos = Math.round((event.getY() - displayTop) / displayScaleY);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;

            case MotionEvent.ACTION_UP:
                String selection = getSelection(xPos, yPos);
                if (selection != null && listener != null) {
                    listener.mapResult(selection);
                }
                return true;

            default:
                return false;
        }
    }

    private String getSelection(int xPos, int yPos) {
        Log.e(LOG_TAG, String.format("getSelection (%d,%d)", xPos, yPos));

        for (MapItem mapItem : mapList) {
            Rect rect = mapItem.getRect();
            if (rect.left <= xPos && rect.top <= yPos
                && rect.right >= xPos && rect.bottom >= yPos) {
                Log.e(LOG_TAG, "Found item " + mapItem.getId());
                return mapItem.getId();
            }
        }

        return null;
    }
}
