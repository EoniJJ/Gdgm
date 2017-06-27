package com.example.arron.gdgm.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.arron.gdgm.GdgmApplication;

import java.util.Set;

/**
 * Created by Arron on 2017/4/13.
 */

public class SharedPreferencesManager {

    private SharedPreferences sharedPreferences;

    public static SharedPreferencesManager getInstance() {
        return SharedPreferencesManager.SingletonHolder.INSTANCE;
    }

    private SharedPreferencesManager() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GdgmApplication.getContext());
    }

    public static class SingletonHolder {
        private static final SharedPreferencesManager INSTANCE = new SharedPreferencesManager();
    }

    public boolean put(String key, String value) {
        return sharedPreferences.edit().putString(key, value).commit();
    }

    public boolean put(String key, int value) {
        return sharedPreferences.edit().putInt(key, value).commit();
    }

    public boolean put(String key, boolean value) {
        return sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public boolean put(String key, float value) {
        return sharedPreferences.edit().putFloat(key, value).commit();
    }

    public boolean put(String key, long value) {
        return sharedPreferences.edit().putLong(key, value).commit();
    }

    public boolean put(String key, Set<String> values) {
        return sharedPreferences.edit().putStringSet(key, values).commit();
    }

    public boolean put(String key, Object value) {
        if (value instanceof String) {
            return put(key, (String) value);
        } else if (value instanceof Boolean) {
            return put(key, (boolean) value);
        } else if (value instanceof Integer) {
            return put(key, (int) value);
        } else if (value instanceof Float) {
            return put(key, (float) value);
        } else if (value instanceof Long) {
            return put(key, (long) value);
        } else if (value instanceof Set) {
            return put(key, (Set) value);
        } else {
            throw new UnsupportedOperationException("This value type " + value.getClass().toString() + " can't put SharedPreferences ");
        }
    }

    public String get(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public int get(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public long get(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public float get(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public Set get(String key, Set<String> defValue) {
        return sharedPreferences.getStringSet(key, defValue);
    }

    public Object get(String key, Object defValue) {
        if (defValue instanceof String) {
            return get(key, (String) defValue);
        } else if (defValue instanceof Boolean) {
            return get(key, (boolean) defValue);
        } else if (defValue instanceof Integer) {
            return get(key, (int) defValue);
        } else if (defValue instanceof Float) {
            return get(key, (float) defValue);
        } else if (defValue instanceof Long) {
            return get(key, (long) defValue);
        } else if (defValue instanceof Set) {
            return get(key, (Set) defValue);
        } else {
            throw new UnsupportedOperationException("This value type " + defValue.getClass().toString() + " can't get in SharedPreferences ");
        }
    }


    public boolean remove(String key) {
        return sharedPreferences.edit().remove(key).commit();
    }

    public boolean clearAll() {
        return sharedPreferences.edit().clear().commit();
    }
}
