package thread;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

import utils.HelpHttp;

public class ModifyPwdHttpThread extends Thread{

    private String username;
    private String oldpwd;
    private String newpwd;

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ModifyPwdHttpThread(String username, String oldpwd, String newpwd) {
        this.username = username;
        this.oldpwd = oldpwd;
        this.newpwd = newpwd;


    }
    @Override
    public void run() {
        super.run();

        try {
            URL url = new URL(HelpHttp.url +"/ModifyPwdServlet?username="+URLEncoder.encode(username, "utf-8")
                    +"&oldpwd="+URLEncoder.encode(oldpwd, "utf-8")+"&newpwd="+URLEncoder.encode(newpwd, "utf-8"));
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            Log.i("ModifyHttpThread创建链接","--loading....");
            InputStream is = httpUrlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"utf-8");
            String temp;
            StringBuffer sbResult = new StringBuffer();
            BufferedReader br = new BufferedReader(isr);
            while((temp=br.readLine())!=null){
                sbResult.append(temp);
            }
            setResult(sbResult.toString());
            Log.i("ModifyHttpThread","--成功....");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
