package com.example.wangmutian.parallax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ParallaxListView listView;
    private String[] indexArr = {"A","B","C","D","B","C","D","B","C","D","B","C","D","B","C","D","B","C","D","B","C","D","B","C","D"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ParallaxListView) findViewById(R.id.listview);

        //添加header
        View headerView = View.inflate(this,R.layout.layout_header,null);
        ImageView imageView = headerView.findViewById(R.id.imageview);

        listView.setParallaxImageView(imageView,this);
        listView.addHeaderView(headerView);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,indexArr));

    }
}
