package net.qiujuer.widget.airpanel;

import android.app.Activity;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */

interface Contract {
    interface Panel extends AirPanel {
        void adjustPanelHeight(int heightMeasureSpec);
    }

    interface Helper extends Panel, AirPanel.Listener {
        int calculateHeightMeasureSpec(int heightMeasureSpec);

        void setup(Activity activity);
    }
}
