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

package com.layer8apps;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.List;

/************
 *  PURPOSE: This class handles our HTTP connections for
 *      simplification of code
 *  AUTHOR: Devin Collins <agent14709@gmail.com>
 *************/
public class ConnectionManager {

    // Public variables used throughout the entire class
    private DefaultHttpClient client;

    /************
     *   PURPOSE: Creates a new instance of client
     *   ARGUMENTS: null
     *   RETURNS: ConnectionManager
     *   AUTHOR: Devin Collins <agent14709@gmail.com>
     *************/
    private ConnectionManager() {
        try {
            SSLSocketFactory factory = new SimpleSSLSocketFactory(null);
            factory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", factory, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            // Create a new conncetion for our client
            client = new DefaultHttpClient(ccm, params);
        } catch (Exception ex) {
            client = new DefaultHttpClient();
        }
    }

    /************
     *   PURPOSE: Public method that creates a new instance
     *      of ConnectionManager
     *   ARGUMENTS: null
     *   RETURNS: ConnectionManager
     *   AUTHOR: Devin Collins <agent14709@gmail.com>
     *************/
    public static ConnectionManager newConnection() {
        ConnectionManager instance = null;
        // Create a new connection
        instance = new ConnectionManager();
        return instance;
    }

    /************
     *   PURPOSE: Gets data from the specified URL and
     *      returns it as a string
     *   ARGUMENTS: String url
     *   RETURNS: String
     *   AUTHOR: Devin Collins <agent14709@gmail.com>
     *************/
    public String getData(String url) {
        String tempData;
        try {
            // Create a new GET request from the specified page
            HttpGet get = new HttpGet(url);
            // Get the data from the request
            HttpResponse response = client.execute(get);
            // Convert the response into a UTF-8 string
            tempData = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
            return tempData;
        } catch (Exception e) {
            return null;
        }
    }

    /************
     *   PURPOSE: Sends an HTTP POST request to the specified
     *      url and returns the information as a string
     *   ARGUMENTS: String url, List<NameValuePair> params
     *   RETURNS: String
     *   AUTHOR: Devin Collins <agent14709@gmail.com>
     *************/
    public String postData(String url, List<NameValuePair> params) {
        String tempData;
        try {
            // Create a new POST request
            HttpPost post = new HttpPost(url);
            // Add the parameters that the user submitted
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            // Set the parameters into the POST request
            post.setEntity(ent);
            // Send the POST request
            HttpResponse response = client.execute(post);
            // Read the information
            HttpEntity resEnt = response.getEntity();
            // Make sure we got data back
            if (resEnt != null) {
                // Convert the data to a readable string
                tempData = EntityUtils.toString(resEnt);
                return tempData;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
