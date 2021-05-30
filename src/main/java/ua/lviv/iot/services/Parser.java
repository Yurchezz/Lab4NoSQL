package ua.lviv.iot.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Parser {

//    private String url;
//    public Parser(String url){
//        this.url = url;
//    }

    static public JsonArray parse(String url){

        try {
            URL oracle = new URL(url); // URL to Parse
            URLConnection yc = oracle.openConnection();

            yc.connect();
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) yc.getContent())); //Convert the input stream to a json element
            JsonArray rootobj = root.getAsJsonArray(); //May be an array, may be an object.

            return rootobj;

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
