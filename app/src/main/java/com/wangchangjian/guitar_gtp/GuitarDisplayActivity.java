package com.wangchangjian.guitar_gtp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wangchangjian.guitar_gtp.bar.Bar;
import com.wangchangjian.guitar_gtp.bar.SwipeBar;
import com.wangchangjian.guitar_gtp.guitar.NoteContainer;
import com.wangchangjian.guitar_gtp.guitar.PageDataParser;

import java.util.ArrayList;
import java.util.List;

import static com.wangchangjian.guitar_gtp.GsonString.swepBar;

public class GuitarDisplayActivity extends Activity {

    private Button button_next;
    private Button button_prv;
    private NoteContainer noteContainer;
    private TextView tv_config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_display);
        button_next = (Button) findViewById(R.id.button_next);
        button_prv = (Button) findViewById(R.id.button_prv);
        noteContainer = (NoteContainer) findViewById(R.id.note_container);

        tv_config = (TextView) findViewById(R.id.tv_config);
        noteContainer.contrastTextView(tv_config);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<List<Bar>> bars = new ArrayList<>();
                for (int i = 0; i < 6; i++) {
                    List<Bar> barLines = new ArrayList<>();
                    for (int j = 0; j < 4; j++) {
                        Bar swipeBar = getNote(i, j);
                        barLines.add(swipeBar);
                    }
                    bars.add(barLines);
                }
                noteContainer.setBars(bars);

            }
        });
        button_prv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<List<Bar>> bars = new ArrayList<>();
                for (int i = 0; i < 1; i++) {
                    List<Bar> barLines = new ArrayList<>();
                    for (int j = 0; j < 1; j++) {
                        Bar swipeBar = getNote(i, j);
                        barLines.add(swipeBar);
                    }
                    bars.add(barLines);
                }
                noteContainer.setBars(bars);

            }
        });
    }


    private Bar getNote(int i, int j) {
        SwipeBar swipeBar = PageDataParser.parseSwipeBar(swepBar);
        return swipeBar;
//        } else return new BrokenBar();
    }
}
