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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/************
 *  PURPOSE: This class handles restarting services on reboot
 *  AUTHOR: Devin Collins <agent14709@gmail.com>
 *************/
public class BootReceiver extends BroadcastReceiver {

    private Context cContext;

    /************
     *  PURPOSE: This is what happens when the reboot signal is sent
     *  ARGUMENTS: Context, Intent
     *  RETURNS: VOID
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     *************/
    @Override
    public void onReceive(Context context, Intent i) {
        try {
            cContext = context;
            Preferences pf = new Preferences(cContext);
            String time = pf.getSyncTime();

            if (time != null) {
                int hour = Integer.parseInt(time.substring(0, time.indexOf(":")));
                int minute = Integer.parseInt(time.substring(time.indexOf(":") + 1));
                int week = pf.getWeekSpinner();
//                setAlarm(pf.getTimeSpinner(), hour, minute, (week > -1) ? week : 0);
            }
        } catch (Exception e) {
            // TODO: Error reporting?
        }
    }

    /************
     *  PURPOSE: This handles setting our alarm to the proper time
     *  ARGUMENTS: int code, int iHour, int iMinute, int iDay
     *  RETURNS: VOID
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     *************/
    private void setAlarm(int code, int iHour, int iMinute, int iDay) {
        // Get the current calendar
        Calendar c = Calendar.getInstance();
        // Create a new intent based on the AlarmReceiver
        Intent intent = new Intent(cContext, AlarmReceiver.class);
        // Create our alarm class with a unique ID
        PendingIntent sender = PendingIntent.getBroadcast(cContext, 8416, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Create a manager for our alarm
        AlarmManager am = (AlarmManager) cContext.getSystemService(Context.ALARM_SERVICE);
        int interval = 0;
        // Get the current time
        //Date now = c.getTime();
        switch (code) {
            case 1:
                // Set the time based on what the user selected
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), iHour, iMinute);
                // If the date the user selected has already passed...
                if (c.getTime().before(Calendar.getInstance().getTime())) {
                    // Add a month to the date of the next alarm
                    c.add(Calendar.DAY_OF_MONTH, 1);
                }
                // Add milliseconds for a whole day
                interval = 86400000;
                break;
            case 2:
                // Get the current day of the week
                int cDay = c.get(Calendar.DAY_OF_WEEK) - 1;
                // Get the difference between the selected day and our day
                cDay -= iDay;
                // Set the time based on our difference
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), iHour, iMinute);
                // Check to see if the date selected has already passed
                if (c.get(Calendar.DAY_OF_WEEK) - 1 >= iDay && iHour > c.get(Calendar.HOUR_OF_DAY) && iMinute > c.get(Calendar.MINUTE)) {
                    c.add(Calendar.DAY_OF_MONTH, 7 - cDay);
                } else {
                    c.add(Calendar.DAY_OF_MONTH, Math.abs(cDay));
                }
                // Add one week of time
                interval = 604800000;
                break;
        }
        // Set the alarm based on the date we set
//        am.setRepeating(AlarmManager.RTC, c.getTimeInMillis(), interval, sender);
    }

}
