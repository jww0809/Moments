package thread;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import utils.HelpHttp;

/**
 * 将publishActiity传过来的content发送到Servlet进行处理，写入到数据库
 */

public class ContentHttpThread extends Thread{

    private String username;
    private String content;
    private String status;

    private String result;

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    public ContentHttpThread(String username,String content,String status) {
        this.username = username;
        this.content = content;
        this.status = status;
    }

    @Override
    public void run() {
        super.run();
        URL url = null;
        try {
            url = new URL(HelpHttp.url +"/ContentServlet"+"?username="+URLEncoder.encode(username, "utf-8")+"&content="+URLEncoder.encode(content, "utf-8")+"&status="+URLEncoder.encode(status, "utf-8"));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Charset", "utf-8");
            Log.i("ContentHttp开始连接","开始");
            urlConnection.connect();
            Log.i("ContentHttp已连接","写入成功");
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
                //设置一个返回值，判断是否发送成功
                //如果发送成功就跳转到看动态的内容，并且刚更新的动态要能显示在自己的朋友圈
                result = new String(message.toByteArray());
                Log.i("发布动态的结果", result);
                setResult(result);
                inputStream.close();
                message.close();
            }
        }catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}
