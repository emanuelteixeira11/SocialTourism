package com.tourism.social.socialtourism.utils.AsynkTasks;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emanuelteixeira on 07/03/15.
 */
public class AddUser extends AsyncTask<String, Void, Integer> {
    protected final String SERVER_TOKEN = "DbZ0kYuWdstf23Tk8Nqq";
    protected final String SERVER_LINK = "http://arturataide.com/emanuel/addValues.php";

    @Override
    protected Integer doInBackground(String... strings) {
        String username = "dargatter";
        String phone = "+3519539239223";
        String email = "endedn@mail.com";

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(SERVER_LINK);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("token", SERVER_TOKEN));
            nameValuePairs.add(new BasicNameValuePair("name", "drgatterre"));
            nameValuePairs.add(new BasicNameValuePair("email", "emil@ssw.pt"));
            nameValuePairs.add(new BasicNameValuePair("phone", "+3519539239223"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            return response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return -1;
    }
}
