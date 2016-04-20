package com.zzj.gdgm.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zzj.gdgm.R;
import com.zzj.gdgm.support.HttpUtil;
import com.zzj.gdgm.support.JsoupService;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    private EditText editText_username;
    private EditText editText_password;
    private EditText editText_code;
    private ImageView imageView_code;
    private TextView textView_code;
    private Spinner spinner;
    private Button button_login;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        //初始化cookie
        HttpUtil.initCookie(this);
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
        HttpUtil.get(HttpUtil.getUrlCode(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(responseBody);
                Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
                imageView_code.setImageBitmap(bitmap);
                Log.v(TAG, "getUrlCode --> onSuccess --> statusCode = " + statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                imageView_code.setImageResource(R.drawable.code_error);
                Log.v(TAG, "getUrlCode --> onFailure --> statusCode = " + statusCode);
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
        HttpUtil.setTextBox1(editText_username.getText().toString().trim());
        HttpUtil.setTextBox2(editText_password.getText().toString().trim());
        HttpUtil.setTextBox3(editText_code.getText().toString().trim());
        HttpUtil.setRadioButtonList1(spinner.getSelectedItem().toString());
        Log.v(TAG, "TextBox1 = " + HttpUtil.getTextBox1() + "\t TextBox2 = " + HttpUtil.getTextBox2() + "\t TextBox3 = " + HttpUtil.getTextBox3() + "\t RadioButtonList1 = " + HttpUtil.getRadioButtonList1());
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("正在登录....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        HttpUtil.getAsyncHttpClient().get(HttpUtil.getUrlLogin(), HttpUtil.getRequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String content = new String(responseBody, "gb2312");
                    Log.v(TAG, "getUrlLogin -->  onSuccess --> statusCode" + statusCode);
                    if (JsoupService.isLogin(content) != null) {
                        Intent intent = new Intent();
                        intent.putExtra("content",content);
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                        LoginActivity.this.finish();
                        Log.v(TAG, "getUrlLogin --> onSuccess --> " + JsoupService.isLogin(content));
                    } else if (JsoupService.getLoginErrorMessage(content) != null) {
                        Toast.makeText(LoginActivity.this, JsoupService.getLoginErrorMessage(content), Toast.LENGTH_SHORT).show();
                        Log.v(TAG, "getUrlLogin --> onSuccess -->" + JsoupService.getLoginErrorMessage(content));
                        getCheckCode();
                    } else {
                        Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                        getCheckCode();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } finally {
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.v(TAG, "getUrlLogin --> onFailure --> statusCode " + statusCode);
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "登录失败,请检查网络是否可用", Toast.LENGTH_SHORT).show();
                getCheckCode();
            }
        });
    }
}
