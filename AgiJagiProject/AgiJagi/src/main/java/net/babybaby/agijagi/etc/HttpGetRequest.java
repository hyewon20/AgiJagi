package net.babybaby.agijagi.etc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by FlaShilver on 13. 8. 26..
 */
public class HttpGetRequest {
    public String getHTML(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = rd.readLine())!=null){
                result += line;
            }
            rd.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }
}
