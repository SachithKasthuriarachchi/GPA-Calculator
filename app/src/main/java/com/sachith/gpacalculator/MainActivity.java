package com.sachith.gpacalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.gargoylesoftware.htmlunit.WebClient;
import com.android.gargoylesoftware.htmlunit.html.HtmlInput;
import com.android.gargoylesoftware.htmlunit.html.HtmlPage;
import com.android.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final EditText name = (EditText) findViewById(R.id.editName);
        final EditText password = (EditText) findViewById(R.id.editPass);

        final CardView login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String p = password.getText().toString();
                String[] gf = {n,p};

                new HtmlHandler().execute(n,p);
                try {
                    //result = GET();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //System.out.println(result);


                //WebDriver driver = new AndroidWebDriver()


            }
        });
    }



}
