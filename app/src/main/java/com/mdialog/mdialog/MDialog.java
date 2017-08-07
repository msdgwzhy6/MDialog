package com.mdialog.mdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdialog.R;

/**
 * Created by wujun on 2017/8/7.
 * 自定义dialog
 *
 * @author madreain
 * @desc 提示框宽／高=6；4，  按钮：提示内容=1：3
 */

public class MDialog extends Dialog {

    Context mContext;
    int width;
    int height;

    private View heng_line;
    private View shu_line;
    private TextView txt_title;
    private TextView txt_left;
    private TextView txt_right;
    private CharSequence titlecharacter;
    private CharSequence leftcharacter;
    private CharSequence rightcharacter;
    private RelativeLayout dialog_layout;

    public MDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        mContext = context;
        init(context);
    }

    public MDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
        init(context);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void init(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth() - dip2px(60);
        height = width / 6 * 4;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mdialog);
        txt_title = (TextView) findViewById(R.id.txt_title);
        heng_line = (View) findViewById(R.id.heng_line);
        shu_line = (View) findViewById(R.id.shu_line);
        txt_left = (TextView) findViewById(R.id.txt_left);
        txt_right = (TextView) findViewById(R.id.txt_right);
        dialog_layout = (RelativeLayout) findViewById(R.id.dialog_layout);

        setDialogSize();
        setDialogColorText();
        setContent();
    }

    /**
     * 根据屏幕大小  设置整体屏幕 标题，按钮的所占位置
     */
    private void setDialogSize() {
        //适配所有屏幕 6：4
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        dialog_layout.setLayoutParams(layoutParams);
        //标题
        RelativeLayout.LayoutParams layoutParamsTitle = new RelativeLayout.LayoutParams(width, height / 4 * 3);
        txt_title.setLayoutParams(layoutParamsTitle);
        //按钮
        RelativeLayout.LayoutParams layoutParamsLeft = new RelativeLayout.LayoutParams(width, height / 4);
        layoutParamsLeft.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.shu_line);
        layoutParamsLeft.addRule(RelativeLayout.LEFT_OF, R.id.shu_line);
        txt_left.setLayoutParams(layoutParamsLeft);

        RelativeLayout.LayoutParams layoutParamsRight = new RelativeLayout.LayoutParams(width, height / 4);
        layoutParamsRight.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.shu_line);
        layoutParamsRight.addRule(RelativeLayout.RIGHT_OF, R.id.shu_line);
        txt_right.setLayoutParams(layoutParamsRight);
        //分割线
        RelativeLayout.LayoutParams layoutParamsline = new RelativeLayout.LayoutParams(dip2px(1), height / 4);
        layoutParamsline.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParamsline.addRule(RelativeLayout.CENTER_HORIZONTAL);
        shu_line.setLayoutParams(layoutParamsline);

    }

    /**
     * 设置相关颜色、字体大小
     */
    private void setDialogColorText() {
        txt_title.setTextColor(mContext.getResources().getColor(R.color.textcolor));
        txt_title.setTextSize(20f);
        txt_left.setTextColor(mContext.getResources().getColor(R.color.textcolor));
        txt_left.setTextSize(16f);
        txt_right.setTextColor(mContext.getResources().getColor(R.color.textcolor));
        txt_right.setTextSize(16f);
        heng_line.setBackgroundColor(mContext.getResources().getColor(R.color.linecolor));
        shu_line.setBackgroundColor(mContext.getResources().getColor(R.color.linecolor));
    }

    /**
     * 设置相关内容
     */
    private void setContent() {
        txt_title.setText(titlecharacter);
        txt_left.setText(leftcharacter);
        txt_right.setText(rightcharacter);
    }

    /**
     * 左边点击按钮的回调 记得设置dialog.dismiss
     *
     * @param onClickListener
     */
    public void setonLeftChilk(View.OnClickListener onClickListener) {
        txt_left.setOnClickListener(onClickListener);
    }

    /**
     * 右边点击按钮的回调 记得设置dialog.dismiss
     *
     * @param onClickListener
     */
    public void setonRightChilk(View.OnClickListener onClickListener) {
        txt_right.setOnClickListener(onClickListener);
    }

    /**
     * 设置提示内容
     *
     * @param titlecharacter
     */
    public void setTitlecharacter(CharSequence titlecharacter) {
        this.titlecharacter = titlecharacter;
    }

    /**
     * 设置左边显示内容
     *
     * @param leftcharacter
     */
    public void setLeftcharacter(CharSequence leftcharacter) {
        this.leftcharacter = leftcharacter;
    }

    /**
     * 设置右边显示内容
     *
     * @param rightcharacter
     */
    public void setRightcharacter(CharSequence rightcharacter) {
        this.rightcharacter = rightcharacter;
    }


}
