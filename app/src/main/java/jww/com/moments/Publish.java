package jww.com.moments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import thread.ContentHttpThread;

/**10.200.224.65
 * 发布朋友圈的Activity
 */
public class Publish extends AppCompatActivity {

    private Button btnSend;
    private Button btnCancle;
    private EditText tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        tvContent = findViewById(R.id.tv_writeContent);

        //发送content返回的结果  true？false

        //写动态，发送数据到服务器，数据库
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //接收username
                Intent intentFromMoments=getIntent();
                Bundle bundleFromMoments=intentFromMoments.getExtras();
                String username=bundleFromMoments.getString("usernameForPublish");

                //接收content
                String word = tvContent.getText().toString();
                Log.i("username的值",username);
                Log.i("word的值",word);
                //输入的内容word不能为空
                if(!word.equals("")){
                ContentHttpThread contentHttpThread = new ContentHttpThread(username,word);
                contentHttpThread.start();

                try {
                    contentHttpThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String temp = contentHttpThread.getResult();
                //发送成功,跳回朋友圈页面
                    if (temp.equals("true")){
                        Bundle bundleToMoments=new Bundle();
                        bundleToMoments.putString("username",username);
                        Intent toMoments = new Intent(Publish.this,Moments.class);
                        toMoments.putExtras(bundleToMoments);
                        startActivity(toMoments);
                    }else {
                        Toast.makeText(Publish.this,"发布失败,请检查您的网络",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Publish.this,"发布的动态不能为空",Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnCancle = findViewById(R.id.tv_cancle);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getExtras().getString("usernameForPublish");
                Bundle bundleBackToMoments=new Bundle();
                bundleBackToMoments.putString("username",username);
                Intent intentBackTo = new Intent(Publish.this,Moments.class);
                intentBackTo.putExtras(bundleBackToMoments);
                startActivity(intentBackTo);
            }
        });



    }


}
