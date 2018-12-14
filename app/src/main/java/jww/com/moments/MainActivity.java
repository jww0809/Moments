package jww.com.moments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import thread.LoginHttpThread;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btn_login);

        final EditText et_user = findViewById(R.id.edit_user);
        final EditText et_pwd = findViewById(R.id.edit_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里不能直接传String进来，服务器会读不到数据
                String username = et_user.getText().toString();
                String password = et_pwd.getText().toString();
                if (!username.equals("") && !password.equals("")) {
                    LoginHttpThread loginHttpThread = new LoginHttpThread(username, password);
                    loginHttpThread.start();
                    try {
                        loginHttpThread.join();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String temp = loginHttpThread.getResult();
                    if (temp.equals("true")) {
                        //将username的值传到朋友圈显示界面Moments
                        //不同的username显示不同的朋友圈内容
                        Bundle bMoments = new Bundle();//声明bundle对象
                        Intent intentMoments = new Intent(MainActivity.this, Moments.class);
                        bMoments.putString("username", username);//设置bundle内容
                        intentMoments.putExtras(bMoments);//绑定bundle到intent
                        startActivity(intentMoments);
                    } else {
                        Toast.makeText(MainActivity.this, "用户名或密码错误,请重新输入!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "用户名与密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        btnReg = findViewById(R.id.btn_reg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

    }

}
