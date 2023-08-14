package com.example.bthbuoi10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myadapter.MyAdapter;
import com.example.myadapter.Tutorial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ProgressBar progressBar;
    private static final String JSON_URL = "https://thud.fcsevn.com/get-data1";
    List<Tutorial> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        lst = new ArrayList<>();
        loadTutorial();
    }

    private void loadTutorial() {
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        StringRequest sReq = new StringRequest(Request.Method.GET, JSON_URL, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray arr = obj.getJSONArray("tutorials");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject tutObj = arr.getJSONObject(i);
                    Tutorial tut = new Tutorial(
                            tutObj.getString("name"),
                            tutObj.getString("imageUrl"),
                            tutObj.getString("description")
                    );
                    lst.add(tut);
                }
                MyAdapter adapter = new MyAdapter(lst, getApplicationContext());
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        },
                error -> {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(sReq);
    }
}
