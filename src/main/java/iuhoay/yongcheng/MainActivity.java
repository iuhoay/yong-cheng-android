package iuhoay.yongcheng;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import iuhoay.ningbo.ICServer;

public class MainActivity extends Activity {


    ICServer icServer;
    SharedPreferences temporary;

    final String TEMPORARY = "temporary";
    final String TEMPORARY_LATEST = "latest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edit_icNO = (EditText) findViewById(R.id.edit_icNO);
        final Button button_submit = (Button) findViewById(R.id.button_submit);
        final TextView text_result = (TextView) findViewById(R.id.text_result);
        icServer = new ICServer();
        temporary = getSharedPreferences(TEMPORARY, Context.MODE_PRIVATE);

        edit_icNO.setText(temporary.getString(TEMPORARY_LATEST, ""));

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String icNoString = edit_icNO.getText().toString();
                if (icNoString == null || icNoString.length() == 0) {
                    Toast.makeText(MainActivity.this, R.string.message_input_ic_number, Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    String result = new ICTask().execute(icNoString).get();
                    if (result != null) {
                        if (ICServer.MESSAGE_NO_RESULT.equals(result)) {
                            Toast.makeText(MainActivity.this, R.string.message_no_result, Toast.LENGTH_SHORT).show();
                        } else if (ICServer.MESSAGE_ERROR.equals(result)) {
                            Toast.makeText(MainActivity.this, R.string.message_error, Toast.LENGTH_SHORT).show();
                        } else {
                            text_result.setText(new ICTask().execute(icNoString).get());
                            temporary.edit().putString(TEMPORARY_LATEST, icNoString).commit();
                        }
                    }
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(edit_icNO.getWindowToken(), 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
