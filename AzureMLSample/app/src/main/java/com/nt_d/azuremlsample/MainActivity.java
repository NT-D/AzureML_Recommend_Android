package com.nt_d.azuremlsample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*API 呼び出し用のメソッド
    演習で、Azure ML を利用する Webアプリケーション展開していますが、そのWeアプリケーションのAPを呼び出しています。
    演習用アプリケーション : https://github.com/NT-D/AzureML_Recommend_Sample

    Web Application の画面からは jQuery を使っていましたが、Androiでは下記のように呼び出します。
    通信部分には Google の　volley を利用しています。
    https://developer.android.com/intl/ja/training/volley/index.html
     */
    public void callAPI(View view) {
        Log.d("Action Log", "button clicked");

        RequestQueue queue = Volley.newRequestQueue(this);
        String baseURL = "http://azureml-recommend-sample-android.azurewebsites.net";//ご自身の展開した Azure Web Apps のURL を記入してください。
        String url = baseURL +  "/api/values/11";//Web application 側の API を呼び出します。この例では ID 11 の人を呼び出すことになります。


        //https://developer.android.com/intl/ja/training/volley/simple.html　を参考に実装
        StringRequest apiRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //成功時の出力
                        Log.d("response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //失敗時のエラー出力。
                        Log.d("response", error.toString());
                    }
                });

        int custome_timeout_ms = 60000;//timeoutを60000sec(1min)に設定する為の変数
        DefaultRetryPolicy policy = new DefaultRetryPolicy(custome_timeout_ms,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        apiRequest.setRetryPolicy(policy);//リトライのポリシーを設定。ここで、タイムアウトの時間を変更。

        //API 呼び出し
        queue.add(apiRequest);
    }
}
