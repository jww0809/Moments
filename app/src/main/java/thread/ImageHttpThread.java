package thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import utils.HelpHttp;


public class ImageHttpThread extends Thread {

    private String imageUrl;
    private Bitmap resultBitmap;

    //传进一个参数去请求图片
    public ImageHttpThread(String imageUrl){
        this.imageUrl = imageUrl;
    }

    @Override
    public void run() {
        super.run();
        try {
            //因为将Android写成Andriod找了半个小时的错误
            URL url =new URL(HelpHttp.url+imageUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream is= httpURLConnection.getInputStream();
            setResultBitmap(BitmapFactory.decodeStream(is));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Bitmap getResultBitmap() {
        return resultBitmap;
    }

    public void setResultBitmap(Bitmap resultBitmap) {
        this.resultBitmap = resultBitmap;
    }
}
