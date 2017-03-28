package net.qiujuer.widget.airpanel;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */

public interface AirPanel {
    interface Sub {
        void openPanel();

        void closePanel();

        boolean isOpen();
    }

    interface Boss extends Sub {
        void setPanelListener(Listener listener);
    }

    interface Listener {
        void requestHideSoftKeyboard();
    }
}
