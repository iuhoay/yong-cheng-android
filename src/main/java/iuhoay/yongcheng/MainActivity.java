package iuhoay.yongcheng;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import iuhoay.ningbo.ICServer;

public class MainActivity extends Activity {


    ICServer icServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText icNO = (EditText) findViewById(R.id.icNO);
        final Button submit = (Button) findViewById(R.id.submit);
        final TextView show = (TextView) findViewById(R.id.show);
        icServer = new ICServer();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String icNoString = icNO.getText().toString();
                    if (icNoString == null || icNoString.length() == 0) {
                        Toast.makeText(MainActivity.this, "请输入 IC 卡号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    show.setText(new ICTask().execute(icNoString).get());
                } catch (Exception e) {
                    Log.e("", "", e);
                    e.printStackTrace();
                }
            }
        });
    }
}
