
package jww.com.moments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;

import java.util.List;

import adapter.MomentAdapter;
import bean.Item;
import thread.UserHttpThread;

public class Moments extends AppCompatActivity {

    private ImageView img_edit;
    private Button btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);

        //接收Login时传进来username,用username这个参数去请求服务器显示不同的朋友圈数据
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username = bundle.getString("username");

        UserHttpThread userHttpThread = new UserHttpThread(username);
        userHttpThread.start();
        try {
            userHttpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //userHttpThread.getResult()返回一个jason字符串形式的mood,解析就成了list
        List<Item>list = JSON.parseArray(userHttpThread.getResult(), Item.class);
        MomentAdapter momentAdapter = new MomentAdapter(this,R.layout.moment_style,list);
        ListView listView = findViewById(R.id.lv_moments);
        listView.setAdapter(momentAdapter);

        //跳转到发朋友圈的界面
        img_edit = findViewById(R.id.edit_mood);
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToPublish = new Intent(Moments.this,Publish.class);
                Bundle bPublish=new Bundle();
                bPublish.putString("usernameForPublish",username);
                intentToPublish.putExtras(bPublish);
                startActivity(intentToPublish);
            }
        });

        //退出登录就回到登录页面
        btnQuit = findViewById(R.id.btn_quitLogin);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLogin = new Intent(Moments.this,MainActivity.class);
                startActivity(intentToLogin);
            }
        });

    }
}

