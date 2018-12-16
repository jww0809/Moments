package jww.com.moments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import thread.ContentHttpThread;
import thread.ImageHttpThread;

/**10.200.224.65
 * 发布朋友圈的Activity
 *
 * 如果你点击的是仅自己可见，那就调用contentServlet
 *所有人可见的话就调用allContentServlet
 */
public class Publish extends AppCompatActivity {

    private Button btnSend;
    private Button btnCancle;
    private EditText tvContent;
    private RadioGroup rgVisible;
    private RadioGroup rgMoodImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        tvContent = findViewById(R.id.tv_writeContent);

        rgMoodImg = findViewById(R.id.rg_moodimg);


        //获取单选框组
        rgVisible = findViewById(R.id.rg_visible);

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
                //开始发布内容
                if(!word.equals("")&&!word.trim().equals("")){

                    //获取图片
                    String imgvar ="";
                    for(int i=0;i<rgMoodImg.getChildCount();i++){
                        RadioButton radioButton = (RadioButton) rgMoodImg.getChildAt(i);
                        if(radioButton.isChecked()){
                            //通过单选框选中的值--->图片的路径
                            imgvar = radioButton.getText().toString();
                        }
                    }
                    String imgUrl="";
                    if(imgvar.equals("图1")){
                        imgUrl = "img/erkang.png";
                    }else if(imgvar.equals("图2")){
                        imgUrl = "img/xiaoyanzi.png";
                    }



                    String var="";
                    for(int i=0;i<rgVisible.getChildCount();i++){
                        RadioButton radioButton = (RadioButton) rgVisible.getChildAt(i);
                        if(radioButton.isChecked()){
                            //获取单选框的选中的值
                            var = radioButton.getText().toString();
                        }
                    }
                    String status ="";
                    if(var.equals("仅自己可见")){
                        status = "myself";
                    }else if(var.equals("公开")){
                        status = "allPeople";
                    }
                    Log.i("status的值",status);

                ContentHttpThread contentHttpThread = new ContentHttpThread(username,word,status,imgUrl);

                contentHttpThread.start();

                try {
                    contentHttpThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String temp = contentHttpThread.getResult();
                Log.i("temp的值",temp);
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

        //返回朋友圈界面
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
