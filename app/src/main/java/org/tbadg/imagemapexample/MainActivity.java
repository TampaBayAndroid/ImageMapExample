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

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements MappedImageView.Listener {
    @SuppressWarnings("unused")
    private static final String TAG = MainActivity.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MappedImageView image = findViewById(R.id.image);
        image.init(createImageMap(), this);
    }

    private ArrayList<MapItem> createImageMap() {
        // Create an array of map-items, each of which consists of an image map rectangle
        // (left, top, right, bottom) and an id string:

        ArrayList<MapItem> mapList = new ArrayList<>();
        MapItem mapItem;
        mapItem = new MapItem(new Rect(0, 0, 200, 200), "red");
        mapList.add(mapItem);
        mapItem = new MapItem(new Rect(200, 0, 400, 200), "blue");
        mapList.add(mapItem);
        mapItem = new MapItem(new Rect(0, 200, 200, 400), "green");
        mapList.add(mapItem);
        mapItem = new MapItem(new Rect(200, 200, 400, 400), "logo");
        mapList.add(mapItem);

        return mapList;
    }

    @Override
    public void mapResult(String selection) {
        Toast.makeText(this, "Quadrant selected: " + selection, Toast.LENGTH_SHORT).show();
    }
}

