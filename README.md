# MDialog
 自定义dialog

发现android原生的dialog在开发中不怎么受待见，今天讲解一下自定义dialog，有闲情逸致的可以好好的写写，最好有一个美工好好的帮你设计，那样做出来的效果肯定是杠杠的，话不多说上图为正

 ![效果图](https://user-gold-cdn.xitu.io/2017/8/7/787176161794bf645a6cfdd30b16ff83)
 
 简单分析一下，背景需要引入style，为了更好的适配，所有的控件大小位置全是根据当前手机的屏幕大小来决定的(下面来啰嗦这里)，其他的好像也没有什么了
 
### 设置好看的背景
 
 这个的背景利用style、shape来达到这种效果
 
```
     <style name="dialog" parent="@android:style/Theme.Dialog">
         <item name="android:windowFrame">@null</item>
         <!--边框-->
         <item name="android:windowIsFloating">true</item>
         <!--是否浮现在activity之上-->
         <item name="android:windowIsTranslucent">false</item>
         <!--半透明-->
         <item name="android:windowNoTitle">true</item>
         <!--无标题-->
         <item name="android:windowBackground">@android:color/transparent</item>
         <!--背景透明-->
         <item name="android:backgroundDimEnabled">true</item>
         <!--模糊-->
     </style>
     
```

圆角

```
    <?xml version="1.0" encoding="utf-8"?>
    <shape xmlns:android="http://schemas.android.com/apk/res/android">
        <solid android:color="@android:color/white" />
        <!--<corners android:bottomRightRadius="20dp"-->
            <!--android:bottomLeftRadius="20dp"/>-->
        <corners android:radius="@dimen/radius"/>
        <stroke android:width="1dp" android:color="@color/linecolor"/>
    </shape>

```

代码中结合使用
```

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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mdialog);
    }    

```
背景的效果就出来了

### 整体布局、控件适配

这里根据屏幕的宽度-左右预留的空袭来设置dialog显示的宽度，高度采取的是宽：高=6：4，里面的子控件标题：按钮=3：1（3+1=整体的高度），左右两边的按钮都是以中间的竖线为基准进行大小位置的设置。（根据自己实践代码，根据设计图给出的宽高比进行设置，避免使用具体宽高多少，那样机型适配展示很丑）

整体的高度获取（android适配真的很重要，影响UE）
```
/**
     * 获取屏幕的宽度，设置宽高比，从而得到高度
     * @param context
     */
    private void init(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth() - dip2px(60);
        height = width / 6 * 4;
    }

```

整体、控件设置
```
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

```


### 文字显示的设置、左右两边按钮的回调

实践项目，文字的显示，已经左右两边按钮点击后的后续操作等

```
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

```

### 代码中使用

```
txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = new MDialog(MainActivity.this);
                mDialog.setTitlecharacter("测试");
                mDialog.setLeftcharacter("取消哦");
                mDialog.setRightcharacter("确定哦");
                mDialog.show();

                mDialog.setonLeftChilk(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                        // TODO: 2017/8/7
                        Toast.makeText(MainActivity.this, "左边按钮的其他操作", Toast.LENGTH_SHORT).show();
                    }
                });

                mDialog.setonRightChilk(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                        // TODO: 2017/8/7
                        Toast.makeText(MainActivity.this, "右边按钮的其他操作", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
```

如果你想给dialog弄给花出来，一定要找个美工配合，要不然你造出来的花效果不佳


[个人博客](https://madreain.github.io/)
