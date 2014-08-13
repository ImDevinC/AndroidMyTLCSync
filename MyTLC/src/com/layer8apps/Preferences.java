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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Set;

/************
 *  PURPOSE: Stores all of our preferences and makes them easily retrievable
 *  AUTHOR: Devin Collins <agent14709@gmail.com>, Bobby Ore <bob1987@gmail.com>
 *************/
public class Preferences {

    // Class-wide variables
    static SharedPreferences settings;
    static SharedPreferences.Editor editor;
    private Context activity;

    /************
     *  PURPOSE: Public preferences reference
     *  ARGUMENTS: Context act
     *  RETURNS: Preferences
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public Preferences(Context act) {
        // Store the context locally
        activity = act;
        // Create a new references to our apps settings
        settings = PreferenceManager.getDefaultSharedPreferences(activity);
        // Create a new editor
        editor = settings.edit();
    }

    /************
     *  PURPOSE: Saves a string to the preferences
     *  ARGUMENTS: String identifier, String value
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    private void saveString(String identifier, String value) {
        if (activity != null) {
            editor.putString(identifier, value);
            editor.commit();
        }
    }

    /************
     *  PURPOSE: Saves an integer to the preferences
     *  ARGUMENTS: String identifier, int value
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    private void saveInt(String identifier, int value) {
        if (activity != null) {
            editor.putInt(identifier, value);
            editor.commit();
        }
    }

    /************
     *  PURPOSE: Saves a long variable to the preferences
     *  ARGUMENTS: String identifier, long value
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    private void saveFloat(String identifier, float value) {
        if (activity != null) {
            editor.putFloat(identifier, value);
            editor.commit();
        }
    }

    /************
     *  PURPOSE: Gets a string from preferences
     *  ARGUMENTS: String identifier
     *  RETURNS: String
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    private String getString(String identifier) {
        if (activity != null) {
            return settings.getString(identifier, null);
        } else {
            return null;
        }
    }

    /************
     *  PURPOSE: Gets an integer from preferences
     *  ARGUMENTS: String identifier
     *  RETURNS: int
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    private int getInt(String identifier) {
        if (activity != null) {
            return settings.getInt(identifier, -1);
        } else {
            return -1;
        }
    }

    /************
     *  PURPOSE: Gets a long value from preferences
     *  ARGUMENTS: String identifier
     *  RETURNS: long
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    private float getFloat(String identifier) {
        if (activity != null) {
            return settings.getFloat(identifier, -1);
        } else {
            return -1;
        }
    }

    /************
     *  PURPOSE: Delete a stored preference
     *  ARGUMENTS: String identifier
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    private void deleteTag(String identifier) {
        if (activity != null) {
            editor = settings.edit();
            editor.remove(identifier);
            editor.commit();
        }
    }

    /************
     *  PURPOSE: Get the stored address
     *  ARGUMENTS: null
     *  RETURNS: String
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public String getAddress() {
        return getString("address");
    }

    /************
     *  PURPOSE: Save the address
     *  ARGUMENTS: String address
     *  RETURNS: boolean
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public boolean setAddress(String address) {
        saveString("address", address);

        String verify = getString("address");

        return (verify != null && verify.equals(address));
    }

    /************
     *  PURPOSE: Delete the stored address
     *  ARGUMENTS: null
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void deleteAddress() {
        deleteTag("address");
    }

    /************
     *  PURPOSE: Get the stored username
     *  ARGUMENTS: null
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public String getUsername() {
        return getString("username");
    }

    /************
     *  PURPOSE: Save the username to preferences
     *  ARGUMENTS: String user
     *  RETURNS: boolean
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public boolean setUsername(String user) {
        // Store the username
        saveString("username", user);
        // Get the stored username
        String verify = getString("username");
        // Make sure the stored username matches the supplied one
        return (verify != null && verify.equals(user));
    }

    /************
     *  PURPOSE: Delete the stored username
     *  ARGUMENTS: null
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void deleteUsername() {
        deleteTag("username");
    }

    /************
     *  PURPOSE: Get the stored password
     *  ARGUMENTS: null
     *  RETURNS: String
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public String getPassword() {
        return getString("pass");
    }

    /************
     *  PURPOSE: Store the provided password
     *  ARGUMENTS: String pass
     *  RETURNS: boolean
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public boolean setPassword(String pass) {
        saveString("pass", pass);
        String verify = getString("pass");
        return (verify.equals(pass));
    }

    /************
     *  PURPOSE: Delete the stored password
     *  ARGUMENTS: null
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void deletePassword() {
        deleteTag("pass");
    }

    /************
     *  PURPOSE: Get the stored calendar ID
     *  ARGUMENTS: null
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public int getCalendarID() {
        return getInt("calID");
    }

    /************
     *  PURPOSE: Store the calendarID
     *  ARGUMENTS: int ID
     *  RETURNS: boolean
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public boolean setCalendarID(int ID) {
        saveInt("calID", ID);
        int verify = getInt("calID");
        return (verify == ID);
    }

    /************
     *  PURPOSE: Delete the stored calendar ID
     *  ARGUMENTS: null
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void deleteCalendarID() {
        deleteTag("calID");
    }

    /************
     *  PURPOSE: Get the stored time frame for syncing
     *      information
     *  ARGUMENTS: null
     *  RETURNS: int
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public int getTimeSpinner() {
        return getInt("spinSync");
    }

    /************
     *  PURPOSE: Store the sync time frame
     *  ARGUMENTS: int spin
     *  RETURNS: boolean
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public boolean setTimeSpinner(int spin) {
        saveInt("spinSync", spin);
        int verify = getInt("spinSync");
        return (verify == spin);
    }

    /************
     *  PURPOSE: Delete the stored sync time frame
     *  ARGUMENTS: null
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void deleteTimeSpinner() {
        deleteTag("spinSync");
    }

    /************
     *  PURPOSE: Get the stored time of day to sync
     *  ARGUMENTS: null
     *  RETURNS: String
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public String getSyncTime() {
        return getString("syncTime");
    }

    /************
     *  PURPOSE: Stores the time of day to sync
     *  ARGUMENTS: String time
     *  RETURNS: boolean
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public boolean setSyncTime(String time) {
        saveString("syncTime", time);
        String verify = getString("syncTime");
        return (verify.equals(time));
    }

    /************
     *  PURPOSE: Delete the stored time of day to sync
     *  ARGUMENTS: null
     *  RETURNS: String
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void deleteSyncTime() {
        deleteTag("syncTime");
    }

    /************
     *  PURPOSE: Get the day of week to sync
     *  ARGUMENTS: null
     *  RETURNS: int
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public int getWeekSpinner() {
        return getInt("weekSync");
    }

    /************
     *  PURPOSE: Set the day of week to sync
     *  ARGUMENTS: int spin
     *  RETURNS: boolean
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public boolean setWeekSpinner(int spin) {
        saveInt("weekSync", spin);
        int verify = getInt("weekSync");
        return (verify == spin);
    }

    /************
     *  PURPOSE: Delete the day of week to sync
     *  ARGUMENTS: null
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void deleteWeekSpinner() {
        deleteTag("weekSync");
    }

    /************
     *  PURPOSE: Set the theme
     *  ARGUMENTS: int theme
     *  RETURNS: boolean
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public boolean setTheme(int theme) {
        saveInt("theme", theme);
        int verify = getInt("theme");
        return (verify == theme);
    }

    /************
     *  PURPOSE: Get the stored theme
     *  ARGUMENTS: null
     *  RETURNS: int
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public int getTheme() {
        return getInt("theme");
    }

    /************
     *  PURPOSE: Set the notification time
     *  ARGUMENTS: int not
     *  RETURNS: boolean
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public boolean setNotification(int not) {
        saveInt("notification", not);
        int verify = getInt("notification");
        return (verify == not);
    }

    /************
     *  PURPOSE: Get the stored notification settings
     *  ARGUMENTS: null
     *  RETURNS: int
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public int getNotification() {
        return getInt("notification");
    }

    /************
     *  PURPOSE: Delete the notification setting
     *  ARGUMENTS: null
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void deleteNotification() {
        deleteTag("notification");
    }

    /************
     *  PURPOSE: Set the version
     *  ARGUMENTS: long version
     *  RETURNS: boolean
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public boolean setVersion(float version) {
        saveFloat("version", version);
        float verify = getFloat("version");
        return (verify == version);
    }

    /************
     *  PURPOSE: Get the stored version settings
     *  ARGUMENTS: null
     *  RETURNS: long
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public float getVersion() {
        return getFloat("version");
    }

    /************
     *  PURPOSE: Delete the version setting
     *  ARGUMENTS: null
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void deleteVersion() {
        deleteTag("version");
    }

    /************
     *  PURPOSE: Set the timezone
     *  ARGUMENTS: String timezone
     *  RETURNS: boolean
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public boolean setTimezone(String timezone) {
        saveString("timezone", timezone);
        String verify = getString("timezone");
        return (verify.equals(timezone));
    }

    /************
     *  PURPOSE: Get the stored timezone settings
     *  ARGUMENTS: null
     *  RETURNS: String
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public String getTimezone() {
        return getString("timezone");
    }

    /************
     *  PURPOSE: Delete the timezone setting
     *  ARGUMENTS: null
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void deleteTimezone() {
        deleteTag("timezone");
    }

    /************
     *  PURPOSE: Get the number of stored shifts
     *  ARGUMENTS: null
     *  RETURNS: int
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public int getNumberOfShifts() {
        return getInt("numShifts");
    }

    /************
     *  PURPOSE: Delete the number of shifts setting
     *  ARGUMENTS: null
     *  RETURNS: void
     *  AUTHOR: Devin Collins <agent14709@gmail.com>
     ************/
    public void deleteNumShifts() {
        deleteTag("numShifts");
    }

    public boolean setSavedShifts(ArrayList<String> savedShifts) {
		saveInt("numShifts", savedShifts.size());

        for (int x = 0; x < savedShifts.size(); x++) {
            saveString("shift" + x, savedShifts.get(x));
        }

        return true;
    }

	public ArrayList<String> getSavedShifts() {
		int numShifts = getNumberOfShifts();

		ArrayList<String> shifts = new ArrayList<String>();

		for (int x = 0; x < numShifts; x++) {
			shifts.add(getString("shift" + x));
		}

		return shifts;
	}

	public void saveShift(String id) {
		ArrayList<String> shifts = getSavedShifts();
		shifts.add(id);
		setSavedShifts(shifts);
	}

	public void deleteShift(String id) {
		ArrayList<String> shifts = getSavedShifts();

		for (String shift : shifts) {
			if (shift.equals(id)) {
				shifts.remove(id);
				break;
			}
		}

		setSavedShifts(shifts);
	}

	public void setEventName(String name) {
		saveString("eventName", name);
	}

	public String getEventName() {
		return getString("eventName");
	}
}
