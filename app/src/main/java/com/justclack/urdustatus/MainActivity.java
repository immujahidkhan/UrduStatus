package com.justclack.urdustatus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.JsonWriter;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ProgressBar mProgressDialog;
    RecyclerView recyclerview;
    Quotes_Main_Adapter quotesMain_adapter;
    ArrayList<QuotesModelClass> list = new ArrayList<>();
    JsonWriter writer;
    Button json;
    File myDir,file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getResources().getString(R.string.app_name));
        myDir.mkdirs();
        file = new File(myDir, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".html");
        mProgressDialog = findViewById(R.id.progress);
        recyclerview = findViewById(R.id.recyclerview);
        json = findViewById(R.id.json);
        recyclerview.hasFixedSize();
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        quotesMain_adapter = new Quotes_Main_Adapter(list, MainActivity.this);
        new Title().execute();

        json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private class Title extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect("http://rms.com/showcat.php?cat=%D8%A7%D8%B1%D8%AF%D9%88&m=41").get();
                //Elements els = document.getElementById("b-qt").children();
                Elements els = document.select(".post_day_content p");
                //title = els.text();
                for (Element element : els) {
                    System.out.println(element.ownText());
                    list.add(new QuotesModelClass(element.ownText()));
                    if (!file.exists()) {
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            //writeJson(out);
                            writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
                            writer.beginObject();
                            writer.name("status").value(element.ownText().trim().toString());
                            writer.endObject();
                            writer.close();

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                quotesMain_adapter.notifyDataSetChanged();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            mProgressDialog.setVisibility(View.GONE);
            recyclerview.setAdapter(quotesMain_adapter);
        }
    }
}
