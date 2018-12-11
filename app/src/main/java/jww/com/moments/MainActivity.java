package jww.com.moments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import thread.UserHttpThread;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btn_login);

        final EditText et_user = findViewById(R.id.edit_user);
        //String name = et_user.getText().toString();
        final EditText et_pwd = findViewById(R.id.edit_password);
        //String password = et_pwd.getText().toString();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里不能直接传String进来，服务器会读不到数据
                UserHttpThread userHttpThread = new UserHttpThread(et_user.getText().toString(),et_pwd.getText().toString());
                userHttpThread.start();
                try {
                    userHttpThread.join();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String flag = userHttpThread.getResult();
                if(flag.equals("true")){
                    Intent intent =new Intent(MainActivity.this,Moments.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MainActivity.this,Register.class);
                    startActivity(intent);
                }

            }
        });
        btnReg = findViewById(R.id.btn_reg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });

    }

}
