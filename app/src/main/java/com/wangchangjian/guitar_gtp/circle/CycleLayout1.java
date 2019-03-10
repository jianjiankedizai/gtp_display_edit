package com.wangchangjian.guitar_gtp.circle;//package com.wangchangjian.myapplication;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.database.DataSetObservable;
//import android.database.DataSetObserver;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.RectF;
//import android.graphics.Region;
//import android.os.Build;
//import android.util.AttributeSet;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//
//import com.topmedlab.roficlassic.R;
//import com.topmedlab.roficlassic.logic.DeviceStateLogic;
//
///**
// * Created by zouyijie on 2017/8/17.
// * 转盘控件
// */
//
//public class CycleLayout1 extends ViewGroup {
//    private static final String TAG = "CircleMenuLayout";
//
//    private boolean IS_VIEWSHOW_FLAG = false;
//    private boolean IS_KEYBOARD_OPAN = false;
//
//    /**
//     * 子控件的大小
//     */
//    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 3.5f;
//
//    /**
//     * size of center item relative to parent
//     */
//    private static final float RADIO_DEFAULT_CENTER_DIMENSION = 1 / 3f;
//    /**
//     * 子控件距圆心的长度
//     */
//    private static final float RADIO_PADDING_LAYOUT = 1 / 36f;
//
//
//    private static final int INVALID_POSITION = -1;
//
//
//    private static final float DEFAULT_SELECTED_ANGLE = 0;
//
//    /**
//     * Angle a touch can wander before we think the user is flinging
//     */
//    private static final int FLINGABLE_VALUE = 300;
//
//    /**
//     *
//     */
//    private static final int NOCLICK_VALUE = 3;
//
//    //DP
//    public static final int DEFAULT_TRIANGLE_HEIGHT = 13;
//
//    /**
//     * 子控件旋转的flag
//     */
//    public static final int ITEM_ROTATE_MODE_NONE = 0;
//
//    public static final int ITEM_ROTATE_MODE_INWARD = 1;
//
//    public static final int ITEM_ROTATE_MODE_OUTWARD = 2;
//
//
//    /**
//     * CircleMenuLayout 's size
//     */
//    private int mRootDiameter;
//
//
//    private float mPadding;
//
//
//    private double mStartAngle = 0;
//    /**
//     * menu 's source data
//     */
//    private CycleWheelAdapter mWheelAdapter;
//
//    /**
//     *
//     */
//    private int mMenuItemCount;
//
//    /**
//     *
//     */
//    private float mTmpAngle;
//
//
//    /**
//     * weather is fling now
//     */
//    private boolean mIsFling;
//    /**
//     *
//     */
//    private boolean mIsDraging;
//
//    /**
//     */
//    private float mLastX;
//    private float mLastY;
//
//    /**
//     * [0,360)
//     */
//    private double mSelectedAngle = DEFAULT_SELECTED_ANGLE;
//    /**
//     * The currently selected item's child.
//     */
//    private View mSelectedView;
//    /**
//     * The position of the selected View
//     */
//    private int mSelectedPosition = INVALID_POSITION;
//
//    /**
//     * The temp selected item's child.
//     */
//    private View mTempSelectedView;
//
//    /**
//     * The position of the temp selected item's child.
//     */
//    private int mTempSelectedPosition = INVALID_POSITION;
//
//
//    /**
//     * 判断是否需要滚动到最中间，某些时候由于选中角度和布局中心的角度一样，所以不需要在进行一次移动
//     */
//    private boolean mNeedSlotIntoCenter;
//
//    private FlingRunnable mFlingRunnable = new FlingRunnable();
//
//    /**
//     * draw cursor
//     */
//    private Paint mCursorPaint;
//    private int mCursorPaint_color = Color.BLACK;
//
//    /**
//     * 三角形的形状
//     */
//    private Path mTrianglePath;
//
//    /*
//    * 三角形的边长
//    * */
//    private int mTriangleHeight;
//
//
//    /**
//     * callback on menu item being selected
//     */
//    private OnMenuSelectedListener mOnMenuSelectedListener;
//    private OnMenuItemClickListener mOnMenuItemClickListener;
//
//
//    private boolean mIsFirstLayout = true;
//
//    private float mMenuRadioDimension;
//    private float mCenterRadioDimension;
//    private float mPaddingRadio;
//
//
//    private Matrix mBgMatrix = new Matrix();
//
//    private int mItemRotateMode = ITEM_ROTATE_MODE_NONE;
//    private Path cycle_big_BgPath = new Path();
//    private Path cycle_small_BgPath = new Path();
//
//    public CycleLayout1(Context context) {
//        this(context, null);
//    }
//
//    public CycleLayout1(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public CycleLayout1(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initWheel(context, attrs);
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public CycleLayout1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        initWheel(context, attrs);
//    }
//
//    private void initWheel(Context context, AttributeSet attrs) {
//        setPadding(0, 0, 0, 0);
//        final float density = context.getResources().getDisplayMetrics().density;
//        mTriangleHeight = (int) (DEFAULT_TRIANGLE_HEIGHT * density + 0.5);
//        if (attrs != null) {
//            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CycleLayout);
//            mSelectedAngle = ta.getFloat(R.styleable.CycleLayout_wheelSelectedAngle, DEFAULT_SELECTED_ANGLE);
//            if (mSelectedAngle > 360) {
//                mSelectedAngle %= 360;
//            }
//            mStartAngle = mSelectedAngle;
//            mTriangleHeight = ta.getDimensionPixelOffset(R.styleable.CycleLayout_wheelCursorHeight, mTriangleHeight);
//            mMenuRadioDimension = ta.getFloat(R.styleable.CycleLayout_wheelItemRadio, RADIO_DEFAULT_CHILD_DIMENSION);
//            mCenterRadioDimension = ta.getFloat(R.styleable.CycleLayout_wheelCenterRadio, RADIO_DEFAULT_CENTER_DIMENSION);
//            mPaddingRadio = ta.getFloat(R.styleable.CycleLayout_wheelPaddingRadio, RADIO_PADDING_LAYOUT);
//            mItemRotateMode = ta.getInt(R.styleable.CycleLayout_wheelItemRotateMode, ITEM_ROTATE_MODE_NONE);
//            ta.recycle();
//        }
//        init(context);
//    }
//
//    private void init(Context context) {
//        setWillNotDraw(false);
//        mCursorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mCursorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        mCursorPaint.setColor(mCursorPaint_color);
//        mCursorPaint.setDither(true);
//
//        mTrianglePath = new Path();
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int desiredWidth = getDefaultWidth();
//        int desiredHeight = desiredWidth;
//        int widthSpec = resolveSizeAndState(desiredWidth, widthMeasureSpec);
//        int heightSpec = resolveSizeAndState(desiredHeight, heightMeasureSpec);
//        setMeasuredDimension(widthSpec, heightSpec);
//
//        mRootDiameter = Math.max(getMeasuredWidth(), getMeasuredHeight());
//
//        final int count = getChildCount();
//        // menu item 's size
//        int childSize = (int) (mRootDiameter * mMenuRadioDimension);
//        // menu item 's MeasureSpec
//        int childMode = MeasureSpec.EXACTLY;
//
//        for (int i = 0; i < count; i++) {
//            final View child = getChildAt(i);
//
//            if (child.getVisibility() == GONE) {
//                continue;
//            }
//
//            int makeMeasureSpec = -1;
//
//            if (child.getId() == R.id.id_wheel_menu_center_item) {
//                makeMeasureSpec = MeasureSpec.makeMeasureSpec(
//                        (int) (mRootDiameter * mCenterRadioDimension),
//                        childMode);
//            } else {
//                makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
//                        childMode);
//            }
//            child.measure(makeMeasureSpec, makeMeasureSpec);
//        }
//        mPadding = mPaddingRadio * mRootDiameter;
//    }
//
//    private int resolveSizeAndState(int desireSize, int measureSpec) {
//        int result = desireSize;
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//        switch (specMode) {
//            case MeasureSpec.UNSPECIFIED:
//                result = desireSize;
//                break;
//            case MeasureSpec.AT_MOST:
//                if (specSize < desireSize) {
//                    result = specSize;
//                } else {
//                    result = desireSize;
//                }
//                break;
//            case MeasureSpec.EXACTLY:
//                result = specSize;
//                break;
//        }
//        return result;
//    }
//
//    public View getCenterItem() {
//        return findViewById(R.id.id_wheel_menu_center_item);
//    }
//
//
//    /**
//     * 黑色三角新的大小和位置
//     */
//    private void initTriangle() {
//        int layoutRadial = (int) (mRootDiameter / 2.0) - 82;
//        mTrianglePath.moveTo(layoutRadial - mTriangleHeight + 80, 0);
//        mTrianglePath.lineTo(layoutRadial, 0 - mTriangleHeight / 2.0f);
//        mTrianglePath.lineTo(layoutRadial, 0 + mTriangleHeight / 2.0f);
//        mTrianglePath.close();
//    }
//
//
//    public void setOnMenuSelectedListener(OnMenuSelectedListener onMenuSelectedListener) {
//        mOnMenuSelectedListener = onMenuSelectedListener;
//    }
//
//    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
//        mOnMenuItemClickListener = onMenuItemClickListener;
//    }
//
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        int layoutDiameter = mRootDiameter;
//        int layoutRadial = (int) (layoutDiameter / 2.0);
//
//        final int childCount = getChildCount();
//
//        int left, top;
//        // size of menu item
//        int cWidth = (int) (layoutDiameter * mMenuRadioDimension);
//
//        float angleDelay;
//        if (getCenterItem() != null) {
//            angleDelay = 360 / (getChildCount() - 1);
//        } else {
//            angleDelay = 360 / (getChildCount());
//        }
//        //angle diff [0,360)
//        double minimumAngleDiff = -1;
//        double angleDiff;
//        double includedAngle = 0;
//        for (int i = 0; i < childCount; i++) {
//            final View child = getChildAt(i);
//            if (child.getId() == R.id.id_wheel_menu_center_item) {
//                continue;
//            }
//            if (child.getVisibility() == GONE) {
//                continue;
//            }
//
//            mStartAngle %= 360;
//            includedAngle = mStartAngle;
//            //menu 's angle relative to 0°
//            child.setTag(R.id.id_wheel_view_angle, mStartAngle);
//            angleDiff = Math.abs(mSelectedAngle - includedAngle);
//            angleDiff = angleDiff >= 180 ? 360 - angleDiff : angleDiff;
//            //find the intentional selected item
//            if (minimumAngleDiff == -1 || minimumAngleDiff > angleDiff) {
//                minimumAngleDiff = angleDiff;
//                mTempSelectedView = child;
//                if (getCenterItem() != null) {
//                    mTempSelectedPosition = i - 1;
//                } else {
//                    mTempSelectedPosition = i;
//                }
//                //allowable error
//                mNeedSlotIntoCenter = ((int) minimumAngleDiff) != 0;
//            }
//            // 计算，中心点到menu item中心的距离
//            float tmp = layoutRadial - cWidth / 2 - mPadding;
//
//            double tempAngle = 360 - mStartAngle - 180;
//
//
//            // {tmp*cos(a)-1/2*width}即menu item相对中心点的横坐标
//            left = layoutRadial
//                    + (int) Math.round(tmp
//                    * Math.cos(Math.toRadians(tempAngle)) - 1 / 2f
//                    * cWidth);
//
//            //{tmp*sin(a)-1/2*height}即menu item相对中心点的纵坐标
//            top = layoutRadial
//                    + (int) Math.round(tmp
//                    * Math.sin(Math.toRadians(tempAngle)) - 1 / 2f
//                    * cWidth);
//
//            child.layout(left, top, left + cWidth, top + cWidth);
//            float angel;
//            switch (mItemRotateMode) {
//                case ITEM_ROTATE_MODE_NONE:
//                    angel = 0;
//                    break;
//                case ITEM_ROTATE_MODE_INWARD:
//                    angel = (float) (-90 + tempAngle);
//                    break;
//                case ITEM_ROTATE_MODE_OUTWARD:
//                    angel = (float) (90 + tempAngle);
//                    break;
//                default:
//                    angel = 0;
//                    break;
//            }
//            child.setPivotX(cWidth / 2.0f);
//            child.setPivotY(cWidth / 2.0f);
//            child.setRotation(angel);
//            mStartAngle += angleDelay;
//        }
//        //layout center menu
//        View cView = findViewById(R.id.id_wheel_menu_center_item);
//        if (cView != null) {
//            // 设置center item位置
//            int cl = layoutRadial - cView.getMeasuredWidth() / 2;
//            int cr = cl + cView.getMeasuredWidth();
//            cView.layout(cl, cl, cr, cr);
//        }
//        if (mIsFirstLayout) {
//            mIsFirstLayout = false;
//            scrollIntoSlots();
//        }
//
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        mBgMatrix.reset();
//        initTriangle();
//        //设置可滑动的圆,触点在圆中，圆可滑动
//        int big_radial = (int) (mRootDiameter / 2.5f);
//        cycle_big_BgPath.addCircle(0, 0, big_radial, Path.Direction.CW);
//        int small_radial = (int) (mRootDiameter / 4.5f);
//        cycle_small_BgPath.addCircle(0, 0, small_radial, Path.Direction.CW);
//
//
//    }
//
//    @Override
//    public void requestLayout() {
//        mIsFirstLayout = true;
//        super.requestLayout();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        //Draw Wheel's Background
//        canvas.save();
//        int radial = (int) (mRootDiameter / 2.0f);
//        canvas.translate(radial, radial);
//        if (mBgMatrix.isIdentity()) {
//            canvas.getMatrix().invert(mBgMatrix);
//        }
//        canvas.restore();
//    }
//
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//        canvas.save();
//        canvas.translate(mRootDiameter / 2f, mRootDiameter / 2f);
//        canvas.rotate((float) (mSelectedAngle), 0, 0);
//        canvas.drawPath(mTrianglePath, mCursorPaint);
//        canvas.restore();
//
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//        if (!IS_VIEWSHOW_FLAG && !IS_KEYBOARD_OPAN) {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    if (!isEventInWheel(x, y)) {
//                        return false;
//                    }
//                    if (DeviceStateLogic.getState()) {
//                        return false;
//                    }
//                    mLastX = x;
//                    mLastY = y;
//                    mTmpAngle = 0;
//                    mIsDraging = false;
//                    if (mIsFling) {
//                        removeCallbacks(mFlingRunnable);
//                        mIsFling = false;
//                        return true;
//                    }
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                /*
//                 * 获得开始的角度
//                 */
//                    final float start = getAngle(mLastX, mLastY);
//                /*
//                 * 获得当前的角度
//                 */
//                    final float end = getAngle(x, y);
//
//                    // 如果是一、四象限，则直接(end-start)代表角度改变值（正是上移动负是下拉）
//                    if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
//                        mStartAngle -= end - start;
//                        mTmpAngle -= end - start;
//                    } else
//                    // 二、三象限，(start - end)代表角度改变值
//                    {
//                        mStartAngle -= start - end;
//                        mTmpAngle -= start - end;
//                    }
//                    mIsDraging = true;
//                    // 重新布局
//                    requestLayout();
//
//                    mLastX = x;
//                    mLastY = y;
//
//                    break;
//                case MotionEvent.ACTION_UP:
//
//                    mIsFling = false;
//                    mIsDraging = false;
//                    mFlingRunnable.stop(false);
//                    scrollIntoSlots();
//                    // 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击
//                    if (Math.abs(mTmpAngle) > NOCLICK_VALUE) {
//                        return true;
//                    }
//
//                    break;
//            }
//        }
//        return super.dispatchTouchEvent(event);
//    }
//
//
//    private Region mBigRegion = new Region();
//    private Region mSmallRegion = new Region();
//
//    /**
//     * @param x the X coordinate of this event for the touching pointer
//     * @param y the Y coordinate of this event for the touching pointer
//     * @return Is touching the wheel
//     */
//    private boolean isEventInWheel(float x, float y) {
//        float[] pts = new float[2];
//        pts[0] = x;
//        pts[1] = y;
//        mBgMatrix.mapPoints(pts);
//        RectF bounds = new RectF();
//        //
//        cycle_big_BgPath.computeBounds(bounds, true);
//        mBigRegion.setPath(cycle_big_BgPath, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
//        //
//        cycle_small_BgPath.computeBounds(bounds, true);
//        mSmallRegion.setPath(cycle_small_BgPath, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
//
//
//        return mBigRegion.contains((int) pts[0], (int) pts[1]) && !mSmallRegion.contains((int) pts[0], (int) pts[1]);
//    }
//
//    /**
//     * 如果触摸事件交由自己处理，都接受好了
//     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return true;
//    }
//
//    @Override
//    public boolean performClick() {
//        return super.performClick();
//    }
//
//    /**
//     * 根据触摸的位置，计算角度返
//     *
//     * @param xTouch
//     * @param yTouch
//     * @return 返回的是普通数字所代表的最小夹角的角度值，如45度就是45而不是1/4×PI;
//     * 如果是钝角，如315度，返回结果是-45;
//     * 而需要注意的是在同水平方向返回值都为0，垂直方向为90/-90
//     */
//    private float getAngle(float xTouch, float yTouch) {
//        double x = xTouch - (mRootDiameter / 2d);
//        double y = yTouch - (mRootDiameter / 2d);
//        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
//    }
//
//    /**
//     * 根据当前位置计算象限
//     *
//     * @param x
//     * @param y
//     * @return
//     */
//    private int getQuadrant(float x, float y) {
//        int tmpX = (int) (x - mRootDiameter / 2);
//        int tmpY = (int) (y - mRootDiameter / 2);
//        if (tmpX >= 0) {
//            return tmpY >= 0 ? 4 : 1;
//        } else {
//            return tmpY >= 0 ? 3 : 2;
//        }
//
//    }
//
//    public void setAdapter(CycleWheelAdapter adapter) {
//        if (adapter == null) {
//            throw new IllegalArgumentException("Can not set a null adbapter to CycleLayout!!!");
//        }
//
//        if (mWheelAdapter != null) {
//            if (mWheelDataSetObserver != null) {
//                mWheelAdapter.unregisterDataSetObserver(mWheelDataSetObserver);
//            }
//            removeAllViews();
//            mWheelDataSetObserver = null;
//        }
//        mWheelAdapter = adapter;
//        mWheelDataSetObserver = new WheelDataSetObserver();
//        mWheelAdapter.registerDataSetObserver(mWheelDataSetObserver);
//        addMenuItems();
//    }
//
//    private void onDateSetChanged() {
//        mFlingRunnable.stop(false);
//        removeAllViews();
//        addMenuItems();
//        mStartAngle = mSelectedAngle;
//        mSelectedPosition = INVALID_POSITION;
//        mTempSelectedPosition = INVALID_POSITION;
//        mSelectedView = null;
//        mTempSelectedView = null;
//        mIsDraging = false;
//        mIsFling = false;
//        requestLayout();
//    }
//
//
//    /**
//     * add menu item to this layout
//     */
//    private void addMenuItems() {
//        if (mWheelAdapter == null || mWheelAdapter.getCount() == 0) {
//            throw new IllegalArgumentException("Empty menu source!");
//        }
//        mMenuItemCount = mWheelAdapter.getCount();
//        View view;
//        for (int i = 0; i < mMenuItemCount; i++) {
//            final int j = i;
//            view = mWheelAdapter.getView(this, i);
//            view.setOnClickListener(new InnerClickListener(i));
//            addView(view);
//        }
//    }
//
//    /**
//     * @return
//     */
//    private int getDefaultWidth() {
//        WindowManager wm = (WindowManager) getContext().getSystemService(
//                Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
//    }
//
//
//    private void endFling(boolean scrollIntoSlots) {
//        mIsDraging = false;
//        mIsFling = false;
//        if (scrollIntoSlots) {
//            scrollIntoSlots();
//        }
//    }
//
//    /**
//     * Scrolls the items so that the selected item is in its 'slot' (its center
//     * is the Wheel's center).
//     */
//    private void scrollIntoSlots() {
//        scrollIntoSlots(true);
//    }
//
//    /**
//     * Scrolls the items so that the selected item is in its 'slot' (its center
//     * is the Wheel's center).
//     *
//     * @param showAnimation Weather show fling animation or not
//     */
//    private void scrollIntoSlots(boolean showAnimation) {
//        if (mIsDraging || mIsFling) {
//            return;
//        }
//
//        if (getChildCount() == 0 || mTempSelectedView == null) {
//            return;
//        }
//
//        if (!mNeedSlotIntoCenter) {
//            if (mSelectedView != mTempSelectedView || mSelectedPosition != mTempSelectedPosition) {
//                onInnerItemUnselected(mSelectedView);
//                mSelectedView = mTempSelectedView;
//                onInnerItemSelected(mSelectedView);
//                mSelectedPosition = mTempSelectedPosition;
//                mTempSelectedView = null;
//                mTempSelectedPosition = INVALID_POSITION;
//                selectionChangeCallback();
//            }
//        } else {
//            double angle;
//            try {
//                angle = (double) mTempSelectedView.getTag(R.id.id_wheel_view_angle);
//            } catch (NullPointerException e) {
//                return;
//            }
//            double diff = Math.abs(mSelectedAngle - angle);
//            diff = diff >= 180 ? 360 - diff : diff;
//            double diagonal = (angle + 180) % 360;
//            boolean clockWise;
//            if (diagonal < angle) {
//                clockWise = (mSelectedAngle <= diagonal || mSelectedAngle >= angle);
//            } else {
//                clockWise = (mSelectedAngle >= angle && mSelectedAngle <= diagonal);
//            }
//            double sweepAngle = diff * (clockWise ? 1 : -1);
//            if (showAnimation) {
//                mFlingRunnable.stop(false);
//                mFlingRunnable.startUsingAngle(sweepAngle);
//            } else {
//                mStartAngle += sweepAngle;
//                requestLayout();
//            }
//        }
//    }
//
//
//    /**
//     * select变化，回调
//     */
//    private final void selectionChangeCallback() {
//        if (mOnMenuSelectedListener != null) {
//            mOnMenuSelectedListener.onItemSelected(this, mSelectedView, mSelectedPosition);
//        }
//    }
//
//    /**
//     * @author chensuilun
//     */
//    private class InnerClickListener implements OnClickListener {
//        private final int mPosition;
//
//        public InnerClickListener(int position) {
//            mPosition = position;
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (mOnMenuItemClickListener != null) {
//                mOnMenuItemClickListener.onItemClick(v, mPosition);
//            }
//        }
//    }
//
//    /**
//     * Responsible for fling behavior.
//     *
//     * @author chensuilun
//     */
//    private class FlingRunnable implements Runnable {
//
//        private static final int DEFAULT_REFRESH_TIME = 16;
//
//        /**
//         * 滑动速度
//         */
//        private float mAngelPerSecond;
//        /**
//         * 是否以旋转某个角度为目的
//         */
//        private boolean mStartUsingAngle;
//        /**
//         * 最终的角度
//         */
//        private double mEndAngle;
//        /**
//         * 需要旋转的角度
//         */
//        private double mSweepAngle;
//        /**
//         *
//         */
//        private boolean mBiggerBefore;
//        /**
//         * 记录下开始转动的时候的startAngle，因为{@link CycleLayout#mStartAngle}属于[0,360),如果直接用来和{@link FlingRunnable#mEndAngle}比较大小就会比较麻烦了
//         */
//        private double mInitStarAngle;
//
//        private void startCommon() {
//            // Remove any pending flings
//            removeCallbacks(this);
//        }
//
//        public FlingRunnable() {
//
//        }
//
//        public void stop(boolean scrollIntoSlots) {
//            removeCallbacks(this);
//            endFling(scrollIntoSlots);
//        }
//
//        /**
//         * @param velocity
//         */
//        public void startUsingVelocity(float velocity) {
//            mStartUsingAngle = false;
//            startCommon();
//            this.mAngelPerSecond = velocity;
//            post(this);
//
//        }
//
//        /**
//         * @param angle
//         */
//        public void startUsingAngle(double angle) {
//            mStartUsingAngle = true;
//            mSweepAngle = angle;
//            mInitStarAngle = mStartAngle;
//            mEndAngle = mSweepAngle + mInitStarAngle;
//            mBiggerBefore = mInitStarAngle >= mEndAngle;
//            post(this);
//        }
//
//        public void run() {
//            if (mMenuItemCount == 0) {
//                stop(true);
//                return;
//            }
//            if (!mStartUsingAngle) {
//                if ((int) Math.abs(mAngelPerSecond) < 20) {
//                    stop(true);
//                    return;
//                }
//                mIsFling = true;
//                mStartAngle = mStartAngle + (mAngelPerSecond / 30);
//                mAngelPerSecond /= 1.0666F;
//            } else {
//                mStartAngle %= 360;
//                if (Math.abs((int) (mEndAngle - mInitStarAngle)) == 0 || (mBiggerBefore && (mInitStarAngle < mEndAngle)) || (!mBiggerBefore && (mInitStarAngle > mEndAngle))) {
//                    mNeedSlotIntoCenter = false;
//                    stop(true);
//                    return;
//                }
//                mIsFling = true;
//                double change = mSweepAngle / 5;
//                mInitStarAngle += change;
//                mStartAngle += change;
//            }
//            postDelayed(this, DEFAULT_REFRESH_TIME);
//            requestLayout();
//        }
//    }
//
//    /*
//    * 获取现在显示的是第几项
//    * */
//
//    /**
//     * @author chensuilun
//     */
//
//    public int getSelectedPosition() {
//        return mSelectedPosition;
//    }
//
//    /**
//     * 设置显示第几项
//     *
//     * @param position
//     */
//    public void setSideSelection(int position, boolean flag) {
//        Log.i("message", "---------------->>>setSideSelection==" + position);
//
//        if (flag) {
//            setSelectionReal(--position < 0 ? 9 : position, true);
//        } else {
//            setSelectionReal(++position >= 10 ? 0 : position, true);
//
//        }
//    }
//
//    /**
//     * 设置显示第几项
//     */
//    public void setSelection(int position) {
//        Log.i("message", "---------------->>>setSelection==" + position);
//
//        setSelectionReal(position, true);
//    }
//
//
//    /**
//     * @param position
//     */
//    public void setSelectionReal(final int position, final boolean animation) {
//        if (position > mMenuItemCount) {
//            throw new IllegalArgumentException("Position:" + position + " is out of localIndex!");
//        }
//        post(new Runnable() {
//            @Override
//            public void run() {
//                int itemPosition = getCenterItem() == null ? position : position + 1;
//                mFlingRunnable.stop(false);
//                mIsDraging = false;
//                mIsFling = false;
//                mTempSelectedPosition = itemPosition;
//                mTempSelectedView = getChildAt(itemPosition);
//                mNeedSlotIntoCenter = true;
//                scrollIntoSlots(animation);
//            }
//        });
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        removeAllViews();
//        super.onDetachedFromWindow();
//        mFlingRunnable.stop(false);
//        mIsFirstLayout = false;
//    }
//
//    /**
//     * to do whatever you want to perform the selected view
//     *
//     * @param v
//     */
//    protected void onInnerItemSelected(View v) {
//
//    }
//
//    /**
//     * to do whatever you want to perform the unselected view
//     *
//     * @param v
//     */
//    protected void onInnerItemUnselected(View v) {
//
//    }
//
//
//    /**
//     * callback when item selected
//     *
//     * @author chensuilun
//     */
//    public interface OnMenuSelectedListener {
//
//        void onItemSelected(CycleLayout parent, View view, int pos);
//    }
//
//
//    /**
//     * Interface definition for a callback to be invoked when a view is clicked.
//     *
//     * @author chensuilun
//     */
//    public interface OnMenuItemClickListener {
//
//        void onItemClick(View view, int pos);
//
//    }
//
//    private WheelDataSetObserver mWheelDataSetObserver;
//
//    /**
//     * @author chensuilun
//     */
//    public class WheelDataSetObserver extends DataSetObserver {
//        @Override
//        public void onChanged() {
//            super.onChanged();
//            onDateSetChanged();
//        }
//    }
//
//    /**
//     * An Adapter object acts as a bridge between an {@link CycleLayout} and the
//     * underlying data for that view. The Adapter provides access to the data items.
//     * The Adapter is also responsible for making a {@link View} for
//     * each item in the data set.
//     *
//     * @author chensuilun
//     */
//    public static abstract class CycleWheelAdapter {
//
//        private final DataSetObservable mDataSetObservable = new DataSetObservable();
//
//        public void registerDataSetObserver(DataSetObserver observer) {
//            mDataSetObservable.registerObserver(observer);
//        }
//
//        public void unregisterDataSetObserver(DataSetObserver observer) {
//            mDataSetObservable.unregisterObserver(observer);
//        }
//
//        /**
//         * Notifies the attached observers that the underlying data has been changed
//         * and any View reflecting the data set should refresh itself.
//         */
//        public void notifyDataSetChanged() {
//            mDataSetObservable.notifyChanged();
//        }
//
//        /**
//         * How many menu items are in the data set represented by this Adapter.
//         *
//         * @return Count of items.
//         */
//        public abstract int getCount();
//
//        /**
//         * Get a View that displays the data at the specified position in the data set.
//         */
//        public abstract View getView(View parent, int position);
//
//        /**
//         * Get the data item associated with the specified position in the data set.
//         *
//         * @param position Position of the item whose data we want within the adapter's
//         *                 data set.
//         * @return The data at the specified position.
//         */
//        public abstract Object getItem(int position);
//
//    }
//
//    /*
//   * 圆盘下拉展开
//   * */
//    public void setViewOpen() {
//        this.IS_VIEWSHOW_FLAG = true;
//    }
//
//    /*
//    * 圆盘上推收回
//    * */
//    public void setViewClose() {
//        this.IS_VIEWSHOW_FLAG = false;
//    }
//
//    /*
//    * 键盘弹起
//    * */
//    public void setKeyBoardOpen() {
//        this.IS_KEYBOARD_OPAN = true;
//    }
//
//    /*
//    * 键盘收回
//    * */
//    public void setKeyBoardClose() {
//        this.IS_KEYBOARD_OPAN = false;
//    }
//}