package com.example.think.materail;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class ConnectionDetector extends Throwable {

    private Context _context;

    public ConnectionDetector() {

    }

    public ConnectionDetector(Context context) {
        this._context = context;
    }


    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)

                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.d("Network", "Networkname" + info[i].getTypeName());
                        return true;

                    }
        }
        return false;
    }

}