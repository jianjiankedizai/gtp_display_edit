package com.wangchangjian.guitar_gtp.guitar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wangchangjian.myapplication.bar.PageMode;
import com.wangchangjian.myapplication.bar.SwipeBar;
import com.wangchangjian.myapplication.bar.note.BrokenNote;
import com.wangchangjian.myapplication.bar.note.Note;
import com.wangchangjian.myapplication.bar.note.SwipeNote;

/**
 * Created by Administrator on 2018/7/18.
 */

public class PageDataParser {


    private static final RuntimeTypeAdapterFactory typeFactory = RuntimeTypeAdapterFactory
            .of(Note.class, "name") // Here you specify which is the parent class and what field particularizes the child class.
            .registerSubtype(BrokenNote.class, BrokenNote.class.getSimpleName()) // if the flag equals the class name, you can skip the second parameter. This is only necessary, when the "type" field does not equal the class name.
            .registerSubtype(SwipeNote.class, SwipeNote.class.getSimpleName());

    public static Gson parseGson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();

    public static PageMode parseDrawData(String json) {
        PageMode courseEditCaches = parseGson.fromJson(json, PageMode.class);
        return courseEditCaches;
    }
    public static SwipeBar parseSwipeBar(String json) {
        SwipeBar courseEditCaches = parseGson.fromJson(json, SwipeBar.class);
        return courseEditCaches;
    }

}
