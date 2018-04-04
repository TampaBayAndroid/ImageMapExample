package org.tbadg.imagemapexample;

import android.graphics.Rect;

@SuppressWarnings("WeakerAccess")
public class MapItem {
    private Rect rect;
    private String id;

    public MapItem(Rect rect, String id) {
        this.rect = rect;
        this.id = id;
    }

    public Rect getRect() {
        return rect;
    }

    public String getId() {
        return id;
    }
}
