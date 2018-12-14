package thread;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import utils.HelpHttp;

public class UserHttpThread extends Thread {

    /**
     * 向UserServlet发送请求,然后接受返回的数据放到对应的布局中
     */
    private String username;
    public UserHttpThread(String username){
        this.username = username;
    }


    private  String result;
    @Override
    public void run() {
        try {

            URL url = new URL(HelpHttp.url +"/UserServlet?username="+username);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            Log.i("UserHttp开始获取朋友圈界面","--loading....");
            InputStream is = httpUrlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"utf-8");
            String temp;
            StringBuffer sbResult = new StringBuffer();
            BufferedReader br = new BufferedReader(isr);
            while((temp=br.readLine())!=null){
                sbResult.append(temp);
            }
            setResult(Arrays.toString(sbResult.toString().trim().split("--")));
            Log.i("UserHttp成功获取朋友圈界面","--成功....");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
