package com.melayer.webservice;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);

       // new GetTask().execute();

        new PostTask().execute();
    }

    private class GetTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {

            String dataStr = null;
            try {

                URL url = new URL("http://ip.jsontest.com/");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                InputStream inputStream = connection.getInputStream();

                StringBuilder builder = new StringBuilder();
                while(true){

                    int ch = inputStream.read();
                    if(ch == -1) break;
                    else{
                        builder.append((char)ch);
                    }
                }
                dataStr = builder.toString();
                Log.i("##### JSON #####", dataStr);
                connection.disconnect();
            }
            catch (Exception e){
                e.printStackTrace();
                Log.i("##### EXCEPTION #####", ""+e);
            }

            return dataStr;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            try {

                JSONObject json = new JSONObject(data);
                String ip = json.getString("ip");

                textView.setText(ip);
            }
            catch (Exception e){

            }


            //textView.setText("ffffffffffff" + data);
        }
    }

    private class PostTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {

            String dataStr = null;
            try {
                URL url = new URL("http://date.jsontest.com/?");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.getOutputStream().write("service=ip".getBytes());

                InputStream inputStream = connection.getInputStream();

                StringBuilder builder = new StringBuilder();
                while(true){

                    int ch = inputStream.read();
                    if(ch == -1) break;
                    else{
                        builder.append((char)ch);
                    }
                }
                dataStr = builder.toString();

                connection.disconnect();

            }
            catch (Exception e){

                e.printStackTrace();
            }

            return dataStr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject json = new JSONObject(s);
                String ip = json.getString("ip");

                textView.setText(ip);
            }
            catch (Exception e){

            }


            Log.i("######### DATA ########", s);
        }
    }
}
