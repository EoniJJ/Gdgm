package com.zzj.gdgm.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class LoginActivity extends AppCompatActivity {
    private EditText editText_username;
    private EditText editText_password;
    private EditText editText_code;
    private ImageView imageView_code;
    private TextView textView_code;
    private Spinner spinner;
    private Button button_login;
    private Handler handler;
    private static final String TAG = "LoginActivity";

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
        initView();
        //初始化cookie
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"部门", "教师", "学生", "访客"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);
        this.spinner.setSelection(2);
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

    private void initView() {
        this.editText_username = (EditText) findViewById(R.id.editText_user);
        this.editText_password = (EditText) findViewById(R.id.editText_password);
        this.editText_code = (EditText) findViewById(R.id.editText_code);
        this.imageView_code = (ImageView) findViewById(R.id.imageView_code);
        this.textView_code = (TextView) findViewById(R.id.textView_code);
        this.spinner = (Spinner) findViewById(R.id.spinner_identity);
        this.button_login = (Button) findViewById(R.id.button_login);
    }

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
    }

    private void login() {
        if (TextUtils.isEmpty(editText_username.getText())) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(editText_password.getText())) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(editText_code.getText())) {
            Toast.makeText(LoginActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
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
        progressDialog.setMessage("正在登录....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        OkHttpUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.obj = "登录失败,请检查网络";
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
                        if (JsoupService.isLogin(content) != null) {
                            Intent intent = new Intent();
                            intent.putExtra("content", content);
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(intent);
                            LoginActivity.this.finish();
                            Log.v(TAG, "getUrlLogin --> onSuccess --> " + JsoupService.isLogin(content));
                        } else if (JsoupService.getLoginErrorMessage(content) != null) {
                            message.obj = JsoupService.getLoginErrorMessage(content);
                            handler.sendMessage(message);
                            Log.v(TAG, "getUrlLogin --> onSuccess -->" + JsoupService.getLoginErrorMessage(content));
                            getCheckCode();
                        } else {
                            message.obj = "登陆失败";
                            handler.sendMessage(message);
                            getCheckCode();
                        }
                    } else {
                        message.obj = "登陆失败,请检查网络";
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
}
