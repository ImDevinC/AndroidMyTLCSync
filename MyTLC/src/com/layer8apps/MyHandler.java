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

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.widget.TextView;

/************
 *  PURPOSE: This is what our background services sends all of it's
 *      messages to.  This handles displaying the messages properly.
 *  AUTHOR: Devin Collins <agent14709@gmail.com>
 *************/
public class MyHandler extends Handler {

    // Create references to class variables for storage
    private MyTlc activity;
    private String sMessage = "";
    MyTlc.TLCProgressDialog progress;

    /************
     *  PURPOSE: Stores the activity from the main thread for rotation
     *      handling
     *  ARGUMENTS: MyTlc activity
     *  RETURNS: VOID
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void setActivity(MyTlc activity) {
        // Store the reference to our activity
        this.activity = activity;
        // If there's a message in queue...
        if (!sMessage.equals("")) {
            // Sending the message again
            setDefaultMessage();
        }
    }

    /************
     *  PURPOSE: Replaces the message after rotation
     *  ARGUMENTS: null
     *  RETURNS: VOID
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    private void setDefaultMessage() {
        // Create a new messenger to send our data, using
        // ourselves as the handler
        Messenger messenger = new Messenger(this);
        // Create a new blank message
        Message msg = Message.obtain();
        // Create a new bundle to hold data
        Bundle data = new Bundle();
        // Add the most recent status message to the bundle
        data.putString("status", sMessage);
        // Add the bundle to the messages
        msg.setData(data);
        try {
            // Send the message
            messenger.send(msg);
        } catch (Exception e) {
            // TODO: Error reporting?
        }
    }

    /************
     *  PURPOSE: This is where the messages actually get received
     *      and we figure out what to do with them
     *  ARGUMENTS: Message message
     *  RETURNS: VOID
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void handleMessage(Message message) {
        try {
            // Make sure the activity exists
            if (activity != null) {
                // Create a reference to the progressDialog
                progress = (MyTlc.TLCProgressDialog) activity.getSupportFragmentManager().findFragmentByTag("progress");
                // We read the information from the message and do something with it
                // based on what the result code is
                String result = message.getData().getString("status");
                if (result.equals("ERROR")) {
                    // Get rid of the dialog box
                    progress.dismiss();
                    // Store the message that is in the bundle
                    sMessage = message.getData().getString("error");
                    TextView txtResult = (TextView) activity.findViewById(R.id.results);
                    // Display the message to the user
                    txtResult.setText(sMessage);
                } else if (result.equals("DONE")) {
                    // Get the number of shifts from the message
                    int count = message.getData().getInt("count", 0);
                    sMessage = "Added " + count + " shifts to the calendar";
                    // Get rid of the dialog box
                    progress.dismiss();
                    TextView txtResult = (TextView) activity.findViewById(R.id.results);
                    // Display the message to the user
                    txtResult.setText(sMessage);
                } else {
                    // Store the message that is in the bundle
                    sMessage = result;
                    // Update the dialog box with the message
                    progress.setMessage(sMessage);
                }
            }
        } catch (Exception e) {
            // TODO: Error reporting?
        } finally {
            // This disperses the message and let's the program know it can let go of it
            super.handleMessage(message);
        }
    }

}
