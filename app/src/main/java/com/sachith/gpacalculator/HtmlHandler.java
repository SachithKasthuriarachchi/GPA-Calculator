package com.sachith.gpacalculator;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

public class HtmlHandler extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... urls) {

        try {
            String login = URLEncoder.encode("Login", "UTF-8");
            String name = URLEncoder.encode("160290R", "UTF-8");
            String pass = URLEncoder.encode("SN1Math$lifE23", "UTF-8");
            Connection.Response res = Jsoup.connect("https://lms.mrt.ac.lk/enrolments.php")
                    .data(URLEncoder.encode("LearnOrgUsername", "UTF-8"), name,
                            URLEncoder.encode("LearnOrgPassword", "UTF-8"), pass,
                            URLEncoder.encode("LearnOrgLogin", "UTF-8"), login).header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Connection", "keep-alive")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Host", "lms.mrt.ac.lk")
                    .header("Origin", "https://lms.mrt.ac.lk")
                    .header("Referer", "https://lms.mrt.ac.lk/login_index.php")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0").method(Connection.Method.POST)
                    .execute();

            Map<String, String > loginCookies = res.cookies();

            Document document = Jsoup.connect("https://lms.mrt.ac.lk/enrolments.php")
                    .cookies(loginCookies).get();

            return  document.outerHtml();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
