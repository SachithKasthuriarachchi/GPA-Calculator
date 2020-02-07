package com.sachith.gpacalculator;

import android.os.AsyncTask;

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

public class HtmlHandler extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... strings) {
        try {
            String result = GET(strings[0], strings[1]);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static WebClient LogIn(WebClient webClient, String uname, String pwd) throws Exception {

        //webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage currentPage = webClient.getPage("https://lms.mrt.ac.lk/enrolments.php"); //Load page at the STRING address.
        HtmlInput username = currentPage.getElementByName("LearnOrgUsername"); //Find element called loginuser for username
        username.setValueAttribute(uname); //Set value for username
        HtmlInput password = currentPage.getElementByName("LearnOrgPassword"); //Find element called loginpassword for password
        password.setValueAttribute(pwd); //Set value for password
        HtmlSubmitInput submitBtn = currentPage.getElementByName("LearnOrgLogin"); //Find element called Submit to submit form.
        currentPage = submitBtn.click(); //Click on the button.

        return webClient;
    }

    private String GET(String username, String password) throws Exception {

        WebClient webClient = new WebClient();
        webClient = LogIn(webClient, username, password);

        HtmlPage currentPage = webClient.getPage("http://lms.mrt.ac.lk/enrolments.php");
        String pageSource = currentPage.asXml();

        Document document = Jsoup.parse(pageSource);
        System.out.println(document);
        JSONObject object = new JSONObject();
        object.put("Semester_1", new JSONArray());
        object.put("Semester_2", new JSONArray());
        object.put("Semester_3", new JSONArray());
        object.put("Semester_4", new JSONArray());
        object.put("Semester_5", new JSONArray());
        object.put("Semester_6", new JSONArray());
        object.put("Semester_7", new JSONArray());
        object.put("Semester_8", new JSONArray());

        Element table = document.select("tbody").last();
        Elements row = table.getElementsByTag("tr");

        for (int i = 2; i < row.size(); i++) {

            Elements col = row.eq(i).select("td");

            if (col.size() == 4) {
                String semester = col.get(0).text();
                String code = col.get(1).text();
                String module = col.get(2).text();
                String credits = col.get(3).text();
                JSONObject modObject = new JSONObject();
                modObject.put("name", module);
                modObject.put("credits", credits);

                JSONObject cObject = new JSONObject();
                cObject.put(code, modObject);


                if (semester.contains("Semester - 1")) {
                    ((JSONArray) object.get("Semester_1")).put(cObject);
                } else if (semester.contains("Semester - 2")) {
                    ((JSONArray) object.get("Semester_2")).put(cObject);
                } else if (semester.contains("Semester - 3")) {
                    ((JSONArray) object.get("Semester_3")).put(cObject);
                } else if (semester.contains("Semester - 4")) {
                    ((JSONArray) object.get("Semester_4")).put(cObject);
                } else if (semester.contains("Semester - 5")) {
                    ((JSONArray) object.get("Semester_5")).put(cObject);
                } else if (semester.contains("Semester - 6")) {
                    ((JSONArray) object.get("Semester_6")).put(cObject);
                } else if (semester.contains("Semester - 7")) {
                    ((JSONArray) object.get("Semester_7")).put(cObject);
                } else if (semester.contains("Semester - 8")) {
                    ((JSONArray) object.get("Semester_8")).put(cObject);
                }
                //System.out.println(cObject.toString());
            }
        }

        return object.toString();
    }
}
