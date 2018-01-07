package com.example.asus.bugstore.Stored_access_user_data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Nguyen Quang Huy
 */
public class SharedPrefManager {
    // Tên file
    private static final String PREF_NAME = "demo_pref";
    // Key cho user name
    public static final String KEY_USERNAME = "key_username";
    // Key cho email
    public static final String KEY_EMAIL = "key_email";
    //Key cho user id
    public static final String KEY_USERID = "key_userid";
    //Key cho json Product String
    public static final String KEY_JSON_PRODUCT = "key_json_product";

    private Context mContext;
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;

    // Hàm khởi tạo
    public SharedPrefManager(Context mContext) {
        this.mContext = mContext;
        mSharedPref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPref.edit();
    }

    // Lưu username và id
    public void createSessionName(String userName) {
        mEditor.putString(KEY_USERNAME, userName);
        mEditor.commit();
    }

    public void createSessionId(String userId) {
        mEditor.putString(KEY_USERID, userId);
        mEditor.commit();
    }

    //luu json product
    public void createSessionProduct(String jsonProduct) {
        mEditor.putString(KEY_JSON_PRODUCT, jsonProduct);
        mEditor.commit();
    }

    // Lấy username đã được lưu trong sharedpreferencs;
    public String getUserName() {
        String userName = mSharedPref.getString(KEY_USERNAME, "");
        return userName;
    }

    // Lấy email đã được lưu trong sharedpreferencs;
    public String getUserId() {
        String id = mSharedPref.getString(KEY_USERID, "");
        return id;
    }

    // Get json product
    public String getJsonProduct() {
        String jsonproduct = mSharedPref.getString(KEY_JSON_PRODUCT, "");
        return jsonproduct;
    }

    // xóa thông tin trong sharedpreferences
    public void signout() {
        mEditor.clear();
        mEditor.commit();
    }
}
