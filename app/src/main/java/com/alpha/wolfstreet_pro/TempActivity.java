package com.alpha.wolfstreet_pro;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.io.StringReader;

public class TempActivity extends AppCompatActivity {
    private TextView a;
    private TextView b;
    private TextView c;
    int i = 0;
    HttpResponse httpResponse;
    double[] last = new double[3];
    String baseUrl="http://www.webservicex.net/stockquote.asmx/GetQuote?symbol=GOOG+AAPL+MSFT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        a = (TextView) findViewById(R.id.alpha);
        b = (TextView) findViewById(R.id.beta);
        c = (TextView) findViewById(R.id.gamma);

        new ContactWebservice().execute();
    }
    public void callService()
    {
        HttpClient client=new DefaultHttpClient();
        HttpGet httpget = new HttpGet(baseUrl);
        int responseCode=0;
        String message;
        String response;
        try
        {
            httpResponse = client.execute(httpget);
            responseCode = httpResponse.getStatusLine().getStatusCode();
            Log.v("respoce code",String.valueOf(responseCode));
            if(responseCode == 500){
                callService();
            }
            Toast.makeText(this,String.valueOf(responseCode), Toast.LENGTH_LONG).show();
            message = httpResponse.getStatusLine().getReasonPhrase();
            readresponse();
        }
        catch (Exception e)
        {
        }
    }
    private void readresponse() {
        HttpEntity entity = httpResponse.getEntity();
        try {
            if (entity != null) {
                InputStream instream = entity.getContent();
                // read the stream
                String innerXml = new String();
                XmlPullParser parser = Xml.newPullParser();
                // auto-detect the encoding from the stream
                parser.setInput(instream,null);
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name = null;
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            System.out.println("start document");
                            break;
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            innerXml=parser.nextText();
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                    }
                    eventType = parser.next();
                }
                parser=Xml.newPullParser();
                parser.setInput(new StringReader(innerXml));
                System.out.println(innerXml);
                eventType=parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name = null;

                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            if (name.equalsIgnoreCase("last")) {
                               last[i] = Double.parseDouble(parser.nextText());
                                i++;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                    }
                    eventType = parser.next();
                }
                instream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class ContactWebservice extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            callService();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String x = String.valueOf(last[0]);
            a.setText(x);
            String y = String.valueOf(last[1]);
            b.setText(y);
            String z = String.valueOf(last[2]);
            c.setText(z);
        }
    }
}
