package com.example.rssappmvp.model;

import android.os.Environment;

import com.example.rssappmvp.model.data.RssItem;
import com.example.rssappmvp.model.data.RssResponse;
import com.example.rssappmvp.model.database.DataProvider;
import com.example.rssappmvp.model.exeption.DbException;
import com.example.rssappmvp.model.rest.RestFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import retrofit2.Response;

public class DataManager {

    private DataProvider mDataProvider;
    private static final String FILE_NAME = "rss.json";

    public DataManager() {
        this.mDataProvider = new DataProvider();
    }

    public Collection<RssItem> getRss() {
        return mDataProvider.getRss();
    }

    public Collection<RssItem> requestRss() throws DbException, IOException {
        Response<RssResponse> response = null;
        response = RestFactory.getApiInterface().getRSS().execute();
        List<RssItem> rssItems = response.body().getRssData();
        mDataProvider.setRss(rssItems);
        return rssItems;
    }

    public void saveRssToDB(Collection<RssItem> rssItems) throws DbException {
        mDataProvider.setRss(rssItems);
    }

    public int remove(int id) {
        return mDataProvider.removeRss(id);
    }

    //     Checks if external storage is available for read and write
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    //    Checks if external storage is available to at least read
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static void writeToFile(String json) {
        File root = android.os.Environment.getExternalStorageDirectory();
        File file = new File(root, FILE_NAME);
        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(json);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile() throws IOException {
        final File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);
        BufferedReader br = null;
        String response = null;

        StringBuffer output = new StringBuffer();
        br = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = br.readLine()) != null) {
            output.append(line + "n");
        }
        response = output.toString();
        return response;
    }

    public String serializeToJson(Collection<RssItem> rssItem) {
        Gson gson = new Gson();
        String json = gson.toJson(rssItem);
        return json;
    }

    public static Collection<RssItem> getRssListFromJsonFile(String json) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        Gson gson = new Gson();
        Type rssItemListType = new TypeToken<Collection<RssItem>>() {
        }.getType();
        Collection<RssItem> rssItems = gson.fromJson(reader, rssItemListType);
        return rssItems;
    }
}
