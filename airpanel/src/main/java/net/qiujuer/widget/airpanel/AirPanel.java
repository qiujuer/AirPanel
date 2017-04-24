package net.qiujuer.widget.airpanel;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */

public interface AirPanel {
    interface Listener {
        void requestHideSoftKeyboard();
    }

    interface OnStateChangedListener {
        void onSoftKeyboardStateChanged(boolean isOpen);

        void onAirPanelStateChanged(boolean isOpen);
    }

    void openPanel();

    void closePanel();

    boolean isOpen();

    void setPanelListener(Listener listener);

    void setOnStateChangedListener(OnStateChangedListener listener);
}
