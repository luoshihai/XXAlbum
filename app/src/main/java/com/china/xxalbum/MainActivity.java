package com.china.xxalbum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lsh.XXRecyclerview.CommonRecyclerAdapter;
import com.lsh.XXRecyclerview.CommonViewHolder;
import com.lsh.XXRecyclerview.XXRecycleView;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.task.ImageLocalLoader;
import com.yanzhenjie.album.util.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ACTIVITY_REQUEST_SELECT_PHOTO = 11;
    private XXRecycleView xxrl;
    private FloatingActionButton fab;
    private List<String> datas = new ArrayList<>();
    private CommonRecyclerAdapter mAdapter;
    private int mScreenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayUtils.initScreen(this);
        mScreenWidth = DisplayUtils.screenWidth / 3;
        xxrl = (XXRecycleView) findViewById(R.id.xrl);
        xxrl.setLayoutManager(new GridLayoutManager(this,3));
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Album.startAlbum(MainActivity.this, ACTIVITY_REQUEST_SELECT_PHOTO
                        , 9                                                         // 指定选择数量。
                        , ContextCompat.getColor(MainActivity.this, R.color.colorPrimary)        // 指定Toolbar的颜色。
                        , ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));  // 指定状态栏的颜色。
            }
        });

        mAdapter = new CommonRecyclerAdapter<String>(this, datas, R.layout.img) {
            @Override
            public void convert(CommonViewHolder commonViewHolder, String s, int i, boolean b) {
                ImageView itemView = commonViewHolder.getView(R.id.iv);
                itemView.getLayoutParams().width = mScreenWidth;
                itemView.getLayoutParams().height = mScreenWidth;
                itemView.requestLayout();
                itemView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLocalLoader.getInstance().loadImage(itemView, s, mScreenWidth, mScreenWidth);
            }
        };
        xxrl.setAdapter(mAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_REQUEST_SELECT_PHOTO) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                Toast.makeText(this, "pathList.size():" + pathList.size(), Toast.LENGTH_SHORT).show();
                mAdapter.notifydataChanged(pathList);
            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
    }
}
