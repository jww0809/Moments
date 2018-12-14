package jww.com.moments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import thread.RegHttpThread;

public class Register extends AppCompatActivity {

    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /**
         * 将输入的数据发至servlet并插入数据库
         */
        Button btnReg = findViewById(R.id.btn_reg);

        final EditText editUser = findViewById(R.id.reg_user);
        final EditText editPwd = findViewById(R.id.reg_password);
        final EditText editPhone = findViewById(R.id.reg_phone);
        final EditText editEmail = findViewById(R.id.reg_mail);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegHttpThread regHttpThread = new RegHttpThread(editUser.getText().toString(), editPwd.getText().toString(),
                        editPhone.getText().toString(), editEmail.getText().toString());
                regHttpThread.start();

                try {
                    regHttpThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String regResult = regHttpThread.getRegResult();
                if (regResult.equals("true")) {
                    Toast.makeText(Register.this, "注册成功,正在跳转登录界面...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }
                if(regResult.equals("alreadyExist")){
                    Toast.makeText(Register.this, "该用户已注册,请直接登录", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(Register.this, "注册失败,请检查您的网络状态", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
