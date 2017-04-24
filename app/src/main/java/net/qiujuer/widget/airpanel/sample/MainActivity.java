package net.qiujuer.widget.airpanel.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

public class MainActivity extends AppCompatActivity {
    private EditText mContent;
    private AirPanel mAirPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContent = (EditText) findViewById(R.id.edit_content);

        mAirPanel = (AirPanel) findViewById(R.id.airPanelLayout);
        mAirPanel.setPanelListener(new AirPanel.Listener() {
            @Override
            public void requestHideSoftKeyboard() {
                Util.hideKeyboard(mContent);
            }
        });

        findViewById(R.id.btn_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFaceClick();
            }
        });

    }

    private void onFaceClick() {
        if (mAirPanel.isOpen()) {
            Util.showKeyboard(mContent);
        } else {
            mAirPanel.openPanel();
        }
    }

    @Override
    public void onBackPressed() {
        if (mAirPanel.isOpen()) {
            mAirPanel.closePanel();
            return;
        }
        super.onBackPressed();
    }
}
