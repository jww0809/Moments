package jww.com.moments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import thread.ModifyPwdHttpThread;

public class ModifyPwd extends AppCompatActivity {

    private Button btnModifyPwd;
    /**
     * 更新密码
     * 先跳过来输入用户名和旧密码
     * 然后根据用户名和旧密码和新密码去请求web
     * 在数据库里更新用户的密码
     * 返回true，提示修改成功跳转到登录界面
     * 就可以用新密码登录
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);

        btnModifyPwd = findViewById(R.id.btn_modifyPwd);

        //获取用户输入的值
        final EditText editUserName = findViewById(R.id.edit_modifyUser);

        final EditText editOldPwd = findViewById(R.id.edit_modifyPwd);

        final EditText editnewPwd = findViewById(R.id.edit_modifyNewPwd);

        btnModifyPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textUserName = editUserName.getText().toString();
                String textOldPwd = editOldPwd.getText().toString();
                String textNewPwd = editnewPwd.getText().toString();
                if (!textUserName.equals("") && !textOldPwd.equals("")&&!editnewPwd.equals("")) {

                    //问题：注意还需要判断两次输入的pwd不能相同

                    //写一个线程传三个参数
                    ModifyPwdHttpThread modifyPwdHttpThread = new ModifyPwdHttpThread(textUserName,textOldPwd,textNewPwd);
                    modifyPwdHttpThread.start();
                    try {
                        modifyPwdHttpThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String modifyResult = modifyPwdHttpThread.getResult();
                    Log.i("modifyResult的值",modifyResult);

                    //密码更新成功,跳回登录页面
                    if (modifyResult.equals("true")){
                        Toast.makeText(ModifyPwd.this,"密码更新成功，将跳转登录页面",Toast.LENGTH_SHORT).show();
                        Intent toLogin = new Intent(ModifyPwd.this,Login.class);
                        startActivity(toLogin);
                    }else {
                        Toast.makeText(ModifyPwd.this,"密码更新失败,请检查您的网络",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    Toast.makeText(ModifyPwd.this,"用户名、密码与新密码不能为空，请仔细检查",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
