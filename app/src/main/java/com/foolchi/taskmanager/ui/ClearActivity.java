package com.foolchi.taskmanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.foolchi.taskmanager.R;
import com.foolchi.taskmanager.provider.TaskProvider;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by foolchi on 7/9/14.
 */
public class ClearActivity extends Fragment {
    private final String SERVICEURL = "http://foolchi-test.appspot.com/";
    private Button bt_clear, bt_login;
    private EditText et_account, et_password;
    private LayoutInflater inflater;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View currentTaskView = inflater.inflate(R.layout.clear_task_layout, container, false);
        this.inflater = inflater;

        bt_clear = (Button)currentTaskView.findViewById(R.id.bt_clear);
        bt_clear.setText("Sync");
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskProvider taskProvider = new TaskProvider(inflater.getContext());
                taskProvider.syncTask();
                bt_clear.setText("Finished");
            }
        });
        et_account = (EditText)currentTaskView.findViewById(R.id.et_account);
        et_password = (EditText)currentTaskView.findViewById(R.id.et_password);
        bt_login = (Button)currentTaskView.findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final String account = et_account.getText().toString().trim();
                final String password = et_password.getText().toString().trim();
                if (account == "" || password == ""){
                    return;
                }

                Thread t = new Thread(){
                    public void run(){

                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(SERVICEURL+"login");
                        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
                        urlParameters.add(new BasicNameValuePair("user", account));
                        urlParameters.add(new BasicNameValuePair("passwd", password));
                        try {
                            post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
                            HttpResponse rp = client.execute(post);
                            int code = rp.getStatusLine().getStatusCode();
                            if (code == 200){
                                System.out.println("OK");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.start();
            }
        });

        return currentTaskView;
    }
}
