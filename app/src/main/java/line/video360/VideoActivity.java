/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package line.video360;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.yingfu.line.R;


/**
 * Basic Activity to hold {@link MonoscopicView} and render a 360 video in 2D.
 * <p>
 * Most of this Activity's code is related to Android & VR permission handling. The real work is in
 * MonoscopicView.
 * <p>
 * The default intent for this Activity will load a 360 placeholder panorama. For more options on
 * how to load other media using a custom Intent, see {@link MediaLoader}.
 */
public class VideoActivity extends Activity {
    private static final String TAG = "VideoActivity";
    private MonoscopicView videoView;

    /**
     * Checks that the appropriate permissions have been granted. Otherwise, the sample will wait
     * for the user to grant the permission.
     *
     * @param savedInstanceState unused in this sample but it could be used to track video position
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);
        videoView = (MonoscopicView) findViewById(R.id.video_view);
        videoView.initialize();
        initializeActivity();

    }

    /**
     * Handles the user accepting the permission.
     */


    /**
     * Initializes the Activity only if the permission has been granted.
     */
    private void initializeActivity() {
        videoView.loadMedia(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.onResume();
    }

    @Override
    protected void onPause() {
        // MonoscopicView is a GLSurfaceView so it needs to pause & resume rendering. It's also
        // important to pause MonoscopicView's sensors & the video player.
        videoView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.destroy();
        super.onDestroy();
    }
}
