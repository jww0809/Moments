package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bean.Item;
import jww.com.moments.R;
import thread.ImageHttpThread;

/**
 * 写一个adpter适配朋友圈页面
 * 将UserHttp线程请求到的数据进行布局，弄成一个适配器，然后再Activity里面进行加载显示
 */
public class MomentAdapter  extends ArrayAdapter {

    private int resourceId;

    public MomentAdapter(Context context, int resource, List objects) {

        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Item item = (Item) getItem(position);
        MomentsLayout momentsLayout = new MomentsLayout();
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            momentsLayout.titleView = view.findViewById(R.id.tv_title);
            momentsLayout.contentView = view.findViewById(R.id.tv_content);
            momentsLayout.headView = view.findViewById(R.id.image_icon);
            momentsLayout.moodImgView = view.findViewById(R.id.image_mood);
            view.setTag(momentsLayout);
        }else{
            view = convertView;
            momentsLayout =(MomentsLayout)view.getTag();
        }

        momentsLayout.titleView.setText(item.getUsername());
        momentsLayout.contentView.setText(item.getMood());

        //获取头像
        ImageHttpThread imageHttpThread = new ImageHttpThread(item.getHeadImg());
        imageHttpThread.start();

        try {
            imageHttpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //获取发布的图片
        ImageHttpThread imageHttpThread2 = new ImageHttpThread(item.getMoodImg());
        imageHttpThread2.start();
        try {
            imageHttpThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        momentsLayout.headView.setImageBitmap(imageHttpThread.getResultBitmap());
        momentsLayout.moodImgView.setImageBitmap(imageHttpThread2.getResultBitmap());
        
        return view;
    }
    class MomentsLayout{
        ImageView headView;
        TextView titleView;
        TextView contentView;
        ImageView moodImgView;

    }

}

