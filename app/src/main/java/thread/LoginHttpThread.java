package thread;

import android.util.Log;
import android.widget.EditText;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import utils.HelpHttp;


/**
 * 使用post方式进行提交请求
 */
public class LoginHttpThread extends Thread {

    private String name;
    private String password;

    private String result;

    public LoginHttpThread(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public void run() {
        super.run();
        URL url;
        try {
            url = new URL(HelpHttp.url + "/LoginServlet");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //连接超时时间
            urlConnection.setConnectTimeout(50 * 1000);
            //读取超时
            urlConnection.setReadTimeout(5 * 1000);
            ////是否使用缓存
            urlConnection.setUseCaches(false);
            //设置允许输入输出
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Charset", "utf-8");
            Log.i("LoginHttp开始连接", "...");
            urlConnection.connect();
            //请求的数据
            String data = "name=" + URLEncoder.encode(name, "utf-8") + "&password=" + URLEncoder.encode(password, "utf-8");
            //获取输出流，并将请求内容写入该流
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            Log.i("LoginHttp已连接", "写入成功");
            if (urlConnection.getResponseCode() == 200) {
                //获取服务器响应的输入流对象
                InputStream inputStream = urlConnection.getInputStream();
                //创建服务器的字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    message.write(buffer, 0, len);
                }
                //返回的值
                result = new String(message.toByteArray());
                Log.i("登录结果", result);
                setResult(result);
                inputStream.close();
                message.close();

            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
