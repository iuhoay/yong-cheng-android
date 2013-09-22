package iuhoay.yongcheng;

import android.os.AsyncTask;

import iuhoay.ningbo.ICServer;

/**
 * Created by wu on 13-9-22.
 */
public class ICTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strs) {
        ICServer icServer = new ICServer();
        return new ICServer().getCurrentMoney(strs[0]);
    }
}
