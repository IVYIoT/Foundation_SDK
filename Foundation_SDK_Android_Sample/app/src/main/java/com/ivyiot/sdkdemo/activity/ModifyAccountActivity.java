package com.ivyiot.sdkdemo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ivyio.sdk.IvyIoSdkJni;
import com.ivyio.sdk.Response;
import com.ivyio.sdk.Result;
import com.ivyiot.sdkdemo.R;
import com.ivyiot.sdkdemo.common.Global;
import com.ivyiot.sdkdemo.common.Logger;
import com.ivyiot.sdkdemo.common.ToastHelper;
import com.ivyiot.sdkdemo.entity.Camera;
import com.ivyiot.sdkdemo.sdk.CmdList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "ModifyAccountActivity";
    private EditText mNewUserName;
    private EditText mNewPwd;
    private Button mModity;
    /**
     * IPC的用户名正则（非admin，数字，字母，符号【_-@$*】，1-20位）
     */
    public static final String REG_IPC_USERNAME = "^(?i)((?!admin\\b)[0-9a-zA-Z_\\-@$*]{1,20})$";
    /**
     * IPC的密码正则（非空，数字，字母，符号【~！@#%^*()_+{}:"|<>?`-;'\,./】，6-12位）
     */
    public static final String REG_IPC_PASSWORD = "[0-9a-zA-Z~!@#%^*()_+{}:\"|<>?`\\-;'\\\\,./]{6,12}";
    /**
     * ipc的密码强度：弱 数字、字母、特殊符号3者只有其一
     */
    public static final String reg_pwd_strength_weak1 = "^[0-9]{1,}$";
    public static final String reg_pwd_strength_weak2 = "^[a-zA-Z]{1,}$";
    public static final String reg_pwd_strength_weak3 = "^[~!@#%^*()_+{}:\"|<>?`\\-;'\\\\,./]{1,}$";
    private Camera mCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_account);
        mNewUserName = findViewById(R.id.et_new_userName);
        mNewPwd = findViewById(R.id.et_new_pwd);
        mModity = findViewById(R.id.bt_modify);
        mModity.setOnClickListener(this);
        mCamera = getIntent().getBooleanExtra("foscamIpc", false) ? Global.mFosCamera : Global.mIvyCamera;

    }

    @Override
    public void onClick(View v) {
        if (validInput()) {
            Global.es.submit(new Runnable() {
                @Override
                public void run() {
                    String newUserName = mNewUserName.getText().toString().trim();
                    String newPassWord = mNewPwd.getText().toString().trim();
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("usr", mCamera.getUserName());
                    params.put("newUsr", newUserName);
                    params.put("pwd", mCamera.getPassword());
                    params.put("newPwd", newPassWord);
                    Response raw = new Response();
                    String data = new JSONObject(params).toString();
                    int re = IvyIoSdkJni.sendCommand(mCamera.getHandle(), CmdList.IVY_CTRL_MSG_CHANGE_USER_INFO, data, raw, Global.TIMEOUT);
                    Logger.d(TAG, "IVY_CTRL_MSG_CHANGE_USER_INFO:" + raw.resp);
                    if (re == Result.OK && !TextUtils.isEmpty(raw.resp)) {
                        try {
                            JSONObject jsonObject = new JSONObject(raw.resp);
                            int ret = jsonObject.getInt("ret");
                            if (ret == Result.OK) {
                                ToastHelper.toast(ModifyAccountActivity.this, "修改成功");
                                mCamera.setUserName(newUserName);
                                mCamera.setPassword(newPassWord);
                                //修改完用户名密码需要释放句柄重新登陆
                                mCamera.destroy();
                                mCamera.login();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastHelper.toast(ModifyAccountActivity.this, "修改失败");

                    }
                }
            });
        }
    }

    /**
     * 验证用户输入内容
     */
    private boolean validInput() {
        boolean isSucc = false;
        String userName = mNewUserName.getText().toString().trim();
        String pwd1 = mNewPwd.getText().toString().trim();
        if (userName.equals("")) {
            ToastHelper.toast(this, "请输入新的用户名");
        } else if (!userName.matches(REG_IPC_USERNAME)) {
            ToastHelper.toast(this, "设备名称应少于20个字符，同时支持数字，字母和_- @ $ *，且不能为默认admin");
        } else if (pwd1.equals("")) {
            ToastHelper.toast(this, "请输入新的密码");
        } else if (!pwd1.matches(REG_IPC_PASSWORD)) {
            ToastHelper.toast(this, "密码格式错误！ 密码长度应在6到12之间，只支持数字，字母和符号组合");
        } else if (pwd1.matches(reg_pwd_strength_weak1) || pwd1.matches(reg_pwd_strength_weak2) || pwd1.matches(reg_pwd_strength_weak3)) {
            ToastHelper.toast(this, "您的密码强度太弱，请使用数字，字母和符号组合");
        } else {
            isSucc = true;
        }
        return isSucc;
    }
}
