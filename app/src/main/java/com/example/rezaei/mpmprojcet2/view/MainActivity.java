package com.example.rezaei.mpmprojcet2.view;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.rezaei.mpmprojcet2.R;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "test";
    public final static String GOOGLE_URL = "https://play.google.com/store/apps/details?id=";
    public static final String ERROR = "error";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      // getListApps();
        new FetchCategoryTask().execute();
    }
    private void getListApps(){
        final PackageManager pm = getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for(ApplicationInfo info:packages){
            if(!info.packageName.matches("(com.android.*)|(com.samsung.*)|(com.google.android.youtube)")){
                Log.i(TAG, "getListApps: "+info.packageName);
            }


        }
    }
    private  class FetchCategoryTask extends AsyncTask<Void, Void, Void> {

        private final String TAG = FetchCategoryTask.class.getSimpleName();
        private PackageManager pm;

        @Override
        protected Void doInBackground(Void... errors) {
            String category;
            pm = getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            Iterator<ApplicationInfo> iterator = packages.iterator();
            while (iterator.hasNext()) {
                ApplicationInfo packageInfo = iterator.next();
                String query_url = GOOGLE_URL + packageInfo.packageName;
                Log.i(TAG, query_url);
                category = getCategory(query_url);
                Log.i(TAG, "doInBackground: "+category);
                // store category or do something else
            }
            return null;
        }


        private String getCategory(String query_url) {
            boolean network = true;
            if (!network) {
                //manage connectivity lost
                return ERROR;
            } else {
                try {
                    Document doc = Jsoup.connect(query_url).get();
                    Element link = doc.select("span[itemprop=genre]").first();
                    return link.text();
                } catch (Exception e) {
                    return ERROR;
                }
            }
        }
    }
}
