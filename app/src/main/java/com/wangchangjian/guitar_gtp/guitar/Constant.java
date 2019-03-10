package com.wangchangjian.guitar_gtp.guitar;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;

/**
 * Created by Administrator on 2019/2/26 0026.
 */
public interface Constant {
    float ONE_PAD = 1.0f;
    float HALF_PAD = 0.5f;
    float QUARTER_PAD = 0.25f;
    float EIGHTH_PAD = 0.125f;
    float SIXTEEN_PAD = 0.0625f;




    int DOT_RADIUS = SizeUtils.dp2px(1.5f);
    int NOTE_HEIGHT = ScreenUtils.getScreenWidth() / 5;
    int NOTE_VERTICAL_PADDING = ScreenUtils.getScreenWidth() / 20;

    int CONTAINER_PADDING_LEFT = ScreenUtils.getScreenWidth() / 5;
    int CONTAINER_PADDING_RIGHT = ScreenUtils.getScreenWidth() / 15;

}
