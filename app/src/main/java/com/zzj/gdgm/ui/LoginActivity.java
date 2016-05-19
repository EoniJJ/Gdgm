package com.zzj.gdgm.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zzj.gdgm.R;
import com.zzj.gdgm.support.JsoupService;
import com.zzj.gdgm.support.OkHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登录Activity
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * 用户名
     */
    private EditText editText_username;
    /**
     * 密码
     */
    private EditText editText_password;
    /**
     * 验证码输入框
     */
    private EditText editText_code;
    /**
     * 验证码图片
     */
    private ImageView imageView_code;
    private TextView textView_code;
    private Spinner spinner;
    private Button button_login;
    /**
     * 记住密码复选框
     */
    private CheckBox checkBox_remember;
    private SharedPreferences sharedPreferences;
    /**
     * 身份下拉框
     */
    private Handler handler;
    private Toolbar toolbar;
    private static final String TAG = "LoginActivity";
    /**
     * 是否正在请求验证码
     */
    private boolean isGetCheckCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.obj != null && msg.obj instanceof Bitmap) {
                    Bitmap bitmap = (Bitmap) msg.obj;
                    imageView_code.setImageBitmap(bitmap);
                } else if (msg.obj != null && msg.obj instanceof String) {
                    Toast.makeText(LoginActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                }
            }
        };

        sharedPreferences = getSharedPreferences("RememberPassword", Context.MODE_PRIVATE);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.login_activity_name));
        //初始化cookie
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"部门", "教师", "学生", "访客"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);
        this.spinner.setSelection(2);
        //初始化记住密码
        initCheckRemember();
        //加载验证码
        getCheckCode();
        /**
         * 添加点击刷新验证码事件
         */
        CodeOnClickListener codeOnClickListener = new CodeOnClickListener();
        imageView_code.setOnClickListener(codeOnClickListener);
        textView_code.setOnClickListener(codeOnClickListener);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        this.toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        this.editText_username = (EditText) findViewById(R.id.editText_user);
        this.editText_password = (EditText) findViewById(R.id.editText_password);
        this.editText_code = (EditText) findViewById(R.id.editText_code);
        this.imageView_code = (ImageView) findViewById(R.id.imageView_code);
        this.textView_code = (TextView) findViewById(R.id.textView_code);
        this.spinner = (Spinner) findViewById(R.id.spinner_identity);
        this.button_login = (Button) findViewById(R.id.button_login);
        this.checkBox_remember = (CheckBox) findViewById(R.id.checkBox_rememberPassword);
    }

    /**
     * 按钮监听事件
     */
    private class CodeOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            getCheckCode();
        }
    }

    /**
     * 请求验证码
     */
    private void getCheckCode() {
        //若正在请求中，直接return
        if (isGetCheckCode) {
            return;
        }
        isGetCheckCode = true;
        try {
            Request request = OkHttpUtil.getRequest(OkHttpUtil.getUrlCode());
            OkHttpUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Message message = Message.obtain();
                    message.obj = BitmapFactory.decodeResource(getResources(), R.drawable.code_error);
                    handler.sendMessage(message);
                    Log.d(TAG, "getCheckCode -->>> onFailure -->>>" + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Message message = Message.obtain();
                    if (response.code() == 200) {
                        message.obj = BitmapFactory.decodeStream(response.body().byteStream());
                    } else {
                        message.obj = BitmapFactory.decodeResource(getResources(), R.drawable.code_error);
                    }
                    handler.sendMessage(message);
                    Log.d(TAG, " getCheckCode -->>> onResponse --> response.code -->" + response.code());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //加载验证码结束
            isGetCheckCode = false;
        }
    }

    /**
     * 登录
     */
    private void login() {
        if (TextUtils.isEmpty(editText_username.getText())) {
            editText_username.setError(getString(R.string.username_error));
//            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(editText_password.getText())) {
            editText_password.setError(getString(R.string.password_error));
//            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(editText_code.getText())) {
            editText_code.setError(getString(R.string.checkCode_error));
//            Toast.makeText(LoginActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //添加请求参数
        RequestBody requestBody = new FormBody.Builder()
                .add("__VIEWSTATE", "dDw3OTkxMjIwNTU7Oz5dolJKJHgqmp4fsn9ciB2avGIU+w==")
                .add("__VIEWSTATEGENERATOR", "92719903")
                .add("TextBox1", editText_username.getText().toString().trim())
                .add("TextBox2", editText_password.getText().toString().trim())
                .add("TextBox3", editText_code.getText().toString().toString())
                .add("RadioButtonList1", spinner.getSelectedItem().toString())
                .add("Button1", "")
                .build();
        Log.d(TAG, "requestBody -->\nTextBox1:" + editText_username.getText().toString().trim()
                + "\nTextBox2 :" + editText_password.getText().toString().trim()
                + "\nTextBox3 :" + editText_code.getText().toString().toString()
                + "\nRadioButtonList1 : " + spinner.getSelectedItem().toString());
        Request request = OkHttpUtil.getRequest(OkHttpUtil.getUrlLogin(), requestBody);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.isLogining));
        progressDialog.setCancelable(false);
        progressDialog.show();
        OkHttpUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.obj = getString(R.string.login_error);
                handler.sendMessage(message);
                Log.d(TAG, " login -->>> onFailure --> " + e.getMessage());
                progressDialog.dismiss();
                getCheckCode();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Message message = Message.obtain();
                    if (response.code() == 200) {
                        String content = new String(response.body().bytes(), "gb2312");
                        //登录成功
                        if (JsoupService.isLogin(content) != null) {
                            Intent intent = new Intent();
                            //将响应的html代码放入intent
                            intent.putExtra("content", content);
                            //将名字放入intent
                            intent.putExtra("name", JsoupService.isLogin(content));
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(intent);
                            //是否记住密码
                            rememberUsernamePassword(checkBox_remember.isChecked(), editText_username.getText().toString().trim(), editText_password.getText().toString().trim());
                            LoginActivity.this.finish();
                            Log.v(TAG, "getUrlLogin --> onSuccess --> " + JsoupService.isLogin(content));
                        }//登录失败，显示登陆失败后的提示信息
                        else if (JsoupService.getLoginErrorMessage(content) != null) {
                            message.obj = JsoupService.getLoginErrorMessage(content);
                            handler.sendMessage(message);
                            Log.v(TAG, "getUrlLogin --> onSuccess -->" + JsoupService.getLoginErrorMessage(content));
                            //登录失败重新加载验证码
                            getCheckCode();
                        }//若没有提示信息
                        else {
                            message.obj = getString(R.string.login_error);
                            handler.sendMessage(message);
                            getCheckCode();
                        }
                    } else {
                        message.obj = getString(R.string.login_error);
                        handler.sendMessage(message);
                        getCheckCode();
                    }
                    Log.d(TAG, " login -->>> onResponse --> response.code -->" + response.code());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    progressDialog.dismiss();
                }
            }
        });
    }

    /**
     * 初始化记住密码选框
     */
    private void initCheckRemember() {
        checkBox_remember.setChecked(sharedPreferences.getBoolean("isRememberPassword", false));
        //如果选择为记住密码
        if (sharedPreferences.getBoolean("isRememberPassword", false)) {
            //通过sharedPreferences获取用户名并设置
            editText_username.setText(sharedPreferences.getString("username", ""));
            //通过sharedPreferences获取密码并设置
            editText_password.setText(sharedPreferences.getString("password", ""));
        }
    }

    /**
     * 是否记住密码，将账号密码通过SharedPreferences存储
     *
     * @param isRemember 是否记住密码
     * @param username   用户名
     * @param password   密码
     */
    private void rememberUsernamePassword(boolean isRemember, String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isRememberPassword", isRemember);
        //如果记住密码
        if (isRemember) {
            editor.putString("username", username);
            editor.putString("password", password);
        }
        editor.commit();

    }
}
