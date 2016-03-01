package com.xuanwu.art;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xuanwu.art.view.BaikeTextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaikeTextView baikeView = (BaikeTextView) findViewById(R.id.main_baike);
        String text = "测试内容1234567890abcdefghijklmn。。。。.....测试内容1234567890abcdefghijklmnaaaa123....."
                + "测试内容1234567890abcdefghijklmn。。。。123.....测试内容1234567890abcdefghijklmn。。。。123....."
                + "测试内容1234567890abcdefghijklmn。。。。123.....测试内容1234567890abcdefghijklmn。。。。123....."
                + "测试内容1234567890abcdefghijklmn。。。。123....e测试内容1234567890abcdefghijklmn。。。。123....f"
                + "测试内容1234567890abcdefghijklmn。。。。123....g测试内容1234567890abcdefghijklmn。。。。123....h01"
                + "测试内容12345678";
//        String text = "测试内容12353432qgdeew";
        baikeView.setBaikeText(text);
        ImageView imgView = (ImageView) findViewById(R.id.img);
        Glide.with(getApplicationContext())
                .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                .into(imgView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
