package com.liuchuanzheng.trtcdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.liuchuanzheng.trtclibrary.login.LoginActivity;
import com.liuchuanzheng.trtclibrary.login.ProfileManager;
import com.liuchuanzheng.trtclibrary.login.UserModel;
import com.liuchuanzheng.trtclibrary.ui.SelectContactActivity;
import com.liuchuanzheng.trtclibrary.ui.TRTCVideoCallActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<UserModel> mContactList        = new ArrayList<>();
    EditText et_number;
    TextView tv_my_userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!ProfileManager.getInstance().isLogin()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        initPermission();

        et_number = findViewById(R.id.et_number);
        tv_my_userId = findViewById(R.id.tv_my_userId);
        findViewById(R.id.btn_duoren).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactList.clear();
                String numbers = et_number.getText().toString().trim();
                String[] split = numbers.split(",");
                for (String s : split) {
                    UserModel userModel = new UserModel();
                    userModel.userId = s;
                    mContactList.add(userModel);
                }
                TRTCVideoCallActivity.startCallSomeone(MainActivity.this, mContactList);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String userId  = ProfileManager.getInstance().getUserModel().userId;
        tv_my_userId.setText("我是"+userId);
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.MICROPHONE, PermissionConstants.CAMERA)
                    .request();
        }
    }
}
