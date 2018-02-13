package com.example.wangmutian.parallax;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by wangmutian on 2018/2/12.
 */

public class ParallaxListView extends ListView {
    public ParallaxListView(Context context) {
        super(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int orignalHeight; //最初的高度
    private int maxHeight;
    private ImageView imageView;
    public void setParallaxImageView(final ImageView imageView, Context context){
        this.imageView = imageView;



        //设定最大高度
        //添加一个全局的布局监听
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //用完要记得移除

                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                orignalHeight=imageView.getHeight();
                Log.e("最开始的高度",orignalHeight+"");
                maxHeight = imageView.getDrawable().getIntrinsicHeight(); // 获取图片高度
            }
        });



//        Resources resources = context.getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        float density = dm.density;
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//
//        Log.e("屏幕宽度",width+"");
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        BitmapFactory.decodeResource(getResources(),R.drawable.timg1,options);
//        //获取图片的宽高
//        int heights = options.outHeight;
//        int widths = options.outWidth;
//        Log.e("图片宽度",widths+"");
//        Log.e("屏幕高度",heights+"");
//        //maxHeight = imageView.getDrawable().getIntrinsicHeight(); // 获取图片高度
//        maxHeight = heights / widths * width;
//
//        Log.e("heights / widths * width",maxHeight+"");


    }


    /**
     * [在listView 滑动到头的时候执行,可以获取到继续滑动的距离和方向]
     * @param deltaX 继续滑动 x 方向的距离
     * @param deltaY 继续滑动 y 方向的距离 负值表示顶部到头 正值表示底部到头
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX x方向最大可以滚动的距离
     * @param maxOverScrollY y方向最大可以滚动的距离
     * @param isTouchEvent 是否是手指拖动滑动 true 是手指拖动滑动  false : 表示惯性滑动
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if(deltaY < 0 && isTouchEvent){
            //表示顶部到头，并且是手动拖动到头的情况
            //我们需要不断的增加ImageView 的高度
            if(imageView!=null){
                int newHeight = imageView.getHeight() - deltaY;
                if(newHeight > maxHeight) newHeight = maxHeight;
                imageView.getLayoutParams().height = newHeight;
                imageView.requestLayout();//使ImageView的布局生效
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //手指抬起
        if(ev.getAction() == MotionEvent.ACTION_UP){
            //缓慢恢复最初高度  imageView.getHeight() 现在高度  变成的高度 orignalHeight
            final ValueAnimator animator= ValueAnimator.ofInt(imageView.getHeight(),orignalHeight);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    //获取动画的值，设置给ImageView
                    int animatedValue = (int) animator.getAnimatedValue();

                    imageView.getLayoutParams().height = animatedValue;
                    imageView.requestLayout();//使ImageView的布局生效
                }
            });
            animator.setDuration(350);
            animator.start();

        }
        return super.onTouchEvent(ev);
    }
}
