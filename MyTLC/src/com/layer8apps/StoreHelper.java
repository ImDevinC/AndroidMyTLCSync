package com.layer8apps;

/*
 * Copyright 2012, 2013 Devin Collins <agent1709@gmail.com>,
 * Bobby Ore <bob1987@gmail.com>, Casey Stark <starkca90@gmail.com>
 *
 * This file is part of MyTLC Sync.
 *
 * MyTLC Sync is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyTLC Sync is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyTLC Sync.  If not, see <http://www.gnu.org/licenses/>.
 */

import android.app.Activity;
import android.content.Context;
import android.location.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StoreHelper {

    private Settings activity;

    private Context context;

    private String url = "http://api.remix.bestbuy.com/v1/stores({0})?{1}";

    private String searchResultTerms = "apiKey={0}&format=json&show=address,city,postalCode,region,storeId";

    private HashMap<SearchType, String> searchTerms = new HashMap<SearchType, String>() {{
        put(SearchType.Id, "storeId={0}");
        put(SearchType.Zip, "area({0}, 30)");
    }};

    private static enum SearchType {
        Id,
        Zip
    }


    public StoreHelper(Context context, Settings sender) {
        this.context = context;
        this.activity = sender;
    }

    public String getStoreAddress(String storeId) {

        String fullUrl = url.replace("{0}", searchTerms.get(SearchType.Id).replace("{0}", storeId))
                .replace("{1}", searchResultTerms.replace("{0}", context.getString(R.string.bbyopenApi)));

        DownloadData data = new DownloadData();

        data.execute(fullUrl);

        return null;
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            activity.setLoadingText();
        }

        protected String doInBackground(String... url) {
            ConnectionManager conn = ConnectionManager.newConnection();

            String result = conn.getData(url[0]);

            try {
                JSONObject json = new JSONObject(result);

                JSONArray stores = json.getJSONArray("stores");

                if (stores.length() == 0) {
                    return "Store not found";
                }

                json = stores.getJSONObject(0);

                String temp = json.optString("address");

                String address;

                if (temp.equals("")) {
                    return "Address not found for store";
                }

                address = temp;

                temp = json.optString("city");

                if (temp.equals("")) {
                    return "Address not found for store";
                }

                address += ", " + temp;

                temp = json.optString("region");

                if (temp.equals("")) {
                    return "Address not found for store";
                }

                address += ", " + temp;

                temp = json.optString("postalCode");

                if (temp.equals("")) {
                    return "Address not found for store";
                }

                address += " " + temp;

                return address;
            } catch (Exception e) {
                 return "Error loading store";
            }
        }

        protected void onPostExecute(String result) {
            activity.setAddress(result);
        }

    }

}
