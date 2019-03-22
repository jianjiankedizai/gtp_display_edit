package com.wangchangjian.guitar_gtp;

import android.app.Activity;
import android.os.Bundle;

import com.wangchangjian.guitar_gtp.bar.Bar;
import com.wangchangjian.guitar_gtp.bar.SwipeBar;
import com.wangchangjian.guitar_gtp.guitar.PageDataParser;

import static com.wangchangjian.guitar_gtp.GsonString.swepBar;

public class TestActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
    }


    private Bar getNote(int i, int j) {
        SwipeBar swipeBar = PageDataParser.parseSwipeBar(swepBar);
        return swipeBar;
//        } else return new BrokenBar();
    }
}
