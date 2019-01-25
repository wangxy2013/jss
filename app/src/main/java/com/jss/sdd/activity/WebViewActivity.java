package com.jss.sdd.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jss.sdd.R;
import com.jss.sdd.widget.statusbar.StatusBarUtil;
import com.jss.sdd.widget.webview.MiddlewareChromeClient;
import com.jss.sdd.widget.webview.MiddlewareWebViewClient;
import com.jss.sdd.widget.webview.UIController;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.just.agentweb.PermissionInterceptor;
import com.just.agentweb.WebListenerManager;

import java.util.HashMap;


/**
 * DESC: H5界面跳转
 */
public class WebViewActivity extends BaseActivity
{
    public static final String TAG = WebViewActivity.class.getSimpleName();
    public static final String EXTRA_URL = "extra_url";
    private String mUrl;

    private Toolbar mToolbar;
    private ImageView mBackImageView;
    private TextView mTitleTextView;
    protected AgentWeb mAgentWeb;


    private LinearLayout mContainerLayout;
    public static final String URL_KEY = "url_key";
    private ImageView mMoreImageView;
    private MiddlewareWebClientBase mMiddleWareWebClient;
    private MiddlewareWebChromeBase mMiddleWareWebChrome;
    private Gson mGson = new Gson();

    private static final int SHARE_PHOTO_REQUEST_CODE = 0x91;
    private static final int LOGIN_REQUEST_CODE = 0x92;

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        StatusBarUtil.setTransparentStatusBar(this);
        setContentView(R.layout.activity_webview);
        StatusBarUtil.setStatusBarBackground(this, R.drawable.main_bg);
        StatusBarUtil.StatusBarLightMode(WebViewActivity.this, false);
    }

    protected void initView()
    {
        mBackImageView = (ImageView) findViewById(R.id.iv_back);
        mTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        mBackImageView.setOnClickListener(mOnClickListener);
        mMoreImageView = (ImageView) findViewById(R.id.iv_more);
        mMoreImageView.setOnClickListener(mOnClickListener);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mContainerLayout = (LinearLayout) findViewById(R.id.container);
    }

    protected void initViewData()
    {
        mUrl = getIntent().getStringExtra(EXTRA_URL);


        mAgentWeb = AgentWeb.with(this).setAgentWebParent(mContainerLayout, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
                .useDefaultIndicator(-1, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb
                // DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setAgentWebUIController(new UIController(WebViewActivity.this)) //自定义UI  AgentWeb3.0.0 加入。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .useMiddlewareWebChrome(getMiddlewareWebChrome()) //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
                .useMiddlewareWebClient(getMiddlewareWebClient()) //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
                //                .setDownloadListener(mDownloadListener) 4.0.0 删除该API//下载回调
                //                .openParallelDownload()// 4.0.0删除该API 打开并行下载 , 默认串行下载。 请通过AgentWebDownloader#Extra实现并行下载
                //                .setNotifyIcon(R.drawable.ic_file_download_black_24dp) 4.0.0删除该api
                // 下载通知图标。4.0.0后的版本请通过AgentWebDownloader#Extra修改icon
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go(mUrl); //WebView载入该url地址的页面并显示。


        //AgentWebConfig.debug();


        // AgentWeb 4.0 开始，删除该类以及删除相关的API
        //        DefaultMsgConfig.DownloadMsgConfig mDownloadMsgConfig = mAgentWeb.getDefaultMsgConfig().getDownloadMsgConfig();
        //  mDownloadMsgConfig.setCancel("放弃");  // 修改下载提示信息，这里可以语言切换

        // AgentWeb 没有把WebView的功能全面覆盖 ，所以某些设置 AgentWeb 没有提供 ， 请从WebView方面入手设置。
        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        //mAgentWeb.getWebCreator().getWebView()  获取WebView .


        //		mAgentWeb.getWebCreator().getWebView().setOnLongClickListener();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("GP", new JSService());

    }


    protected void initEvent()
    {
        initView();

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {


            switch (v.getId())
            {
                case R.id.iv_back:
                    // true表示AgentWeb处理了该事件
                    if (!mAgentWeb.back())
                    {
                        WebViewActivity.this.finish();
                    }
                    break;

                case R.id.iv_more:
                    break;
                default:
                    break;

            }
        }

    };


    protected MiddlewareWebChromeBase getMiddlewareWebChrome()
    {
        return this.mMiddleWareWebChrome = new MiddlewareChromeClient()
        {
        };
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    /**
     * MiddlewareWebClientBase 是 AgentWeb 3.0.0 提供一个强大的功能，
     * 如果用户需要使用 AgentWeb 提供的功能， 不想重写 WebClientView方
     * 法覆盖AgentWeb提供的功能，那么 MiddlewareWebClientBase 是一个
     * 不错的选择 。
     *
     * @return
     */
    protected MiddlewareWebClientBase getMiddlewareWebClient()
    {
        return this.mMiddleWareWebClient = new MiddlewareWebViewClient()
        {
            /**
             *
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {

                if (url.startsWith("agentweb"))
                { // 拦截 url，不执行 DefaultWebClient#shouldOverrideUrlLoading
                    Log.i(TAG, "agentweb scheme ~");
                    return true;
                }

                if (super.shouldOverrideUrlLoading(view, url))
                { // 执行 DefaultWebClient#shouldOverrideUrlLoading
                    return true;
                }
                // do you work
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                return super.shouldOverrideUrlLoading(view, request);
            }
        };
    }

    public class JSService
    {
        @JavascriptInterface
        public void onPayDone()
        {
        }


    }


    /**
     * 复制字符串
     *
     * @param context
     * @param text
     */
    private void toCopy(Context context, String text)
    {

        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));

    }


    @Override
    public void onResume()
    {
        mAgentWeb.getWebLifeCycle().onResume();//恢复
        super.onResume();
    }

    @Override
    public void onPause()
    {

        mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (mAgentWeb.handleKeyEvent(keyCode, event))
        {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    protected PermissionInterceptor mPermissionInterceptor = new PermissionInterceptor()
    {

        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        @Override
        public boolean intercept(String url, String[] permissions, String action)
        {
            Log.i(TAG, "mUrl:" + url + "  permission:" + mGson.toJson(permissions) + " action:" + action);
            return false;
        }
    };


    /**
     * @return IAgentWebSettings
     */
    public IAgentWebSettings getSettings()
    {
        return new AbsAgentWebSettings()
        {
            private AgentWeb mAgentWeb;

            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb)
            {
                this.mAgentWeb = agentWeb;
            }

            /**
             * AgentWeb 4.0.0 内部删除了 DownloadListener 监听 ，以及相关API ，将 Download 部分完全抽离出来独立一个库，
             * 如果你需要使用 AgentWeb Download 部分 ， 请依赖上 compile 'com.just.agentweb:download:4.0.0 ，
             * 如果你需要监听下载结果，请自定义 AgentWebSetting ， New 出 DefaultDownloadImpl，传入DownloadListenerAdapter
             * 实现进度或者结果监听，例如下面这个例子，如果你不需要监听进度，或者下载结果，下面 setDownloader 的例子可以忽略。
             * @param webView
             * @param downloadListener
             * @return WebListenerManager
             */
            @Override
            public WebListenerManager setDownloader(WebView webView, android.webkit.DownloadListener downloadListener)
            {
                return super.setDownloader(webView, downloadListener);
            }
        };
    }


    protected WebChromeClient mWebChromeClient = new WebChromeClient()
    {
        @Override
        public void onProgressChanged(WebView view, int newProgress)
        {
            //  super.onProgressChanged(view, newProgress);
            Log.i(TAG, "onProgressChanged:" + newProgress + "  view:" + view);
        }

        @Override
        public void onReceivedTitle(WebView view, String title)
        {
            super.onReceivedTitle(view, title);
            if (mTitleTextView != null && !TextUtils.isEmpty(title))
            {
                if (title.length() > 10)
                {
                    title = title.substring(0, 10).concat("...");
                }
            }
            mTitleTextView.setText(title);
        }
    };

    protected WebViewClient mWebViewClient = new WebViewClient()
    {

        private HashMap<String, Long> timer = new HashMap<>();

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
        {
            super.onReceivedError(view, request, error);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
        {
            return shouldOverrideUrlLoading(view, request.getUrl() + "");
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request)
        {
            return super.shouldInterceptRequest(view, request);
        }

        //
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url)
        {

            Log.i(TAG, "view:" + new Gson().toJson(view.getHitTestResult()));
            Log.i(TAG, "mWebViewClient shouldOverrideUrlLoading:" + url);
            //intent:// scheme的处理 如果返回false ， 则交给 DefaultWebClient 处理 ， 默认会打开该Activity  ， 如果Activity不存在则跳到应用市场上去.  true 表示拦截
            //例如优酷视频播放 ，intent://play?...package=com.youku.phone;end;
            //优酷想唤起自己应用播放该视频 ， 下面拦截地址返回 true  则会在应用内 H5 播放 ，禁止优酷唤起播放该视频， 如果返回 false ， DefaultWebClient  会根据intent 协议处理 该地址 ， 首先匹配该应用存不存在 ，如果存在 ，
            // 唤起该应用播放 ， 如果不存在 ， 则跳到应用市场下载该应用 .
            if (url.startsWith("intent://") && url.contains("com.youku.phone"))
            {
                return true;
            }
			/*else if (isAlipay(view, mUrl))   //1.2.5开始不用调用该方法了 ，只要引入支付宝sdk即可 ， DefaultWebClient 默认会处理相应url调起支付宝
			    return true;*/


            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {

            Log.i(TAG, "mUrl:" + url + " onPageStarted  target:" + url);
            timer.put(url, System.currentTimeMillis());

        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);

            if (timer.get(url) != null)
            {
                long overTime = System.currentTimeMillis();
                Long startTime = timer.get(url);
                Log.i(TAG, "  page mUrl:" + url + "  used time:" + (overTime - startTime));
            }

        }
        /*错误页回调该方法 ， 如果重写了该方法， 上面传入了布局将不会显示 ， 交由开发者实现，注意参数对齐。*/
	   /* public void onMainFrameError(AbsAgentWebUIController agentWebUIController, WebView view, int errorCode, String description, String
	   failingUrl) {

            Log.i(TAG, "AgentWebFragment onMainFrameError");
            agentWebUIController.onMainFrameError(view,errorCode,description,failingUrl);

        }*/

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse)
        {
            super.onReceivedHttpError(view, request, errorResponse);

            //			Log.i(TAG, "onReceivedHttpError:" + 3 + "  request:" + mGson.toJson(request) + "  errorResponse:" + mGson.toJson
            // (errorResponse));
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
        {
            super.onReceivedError(view, errorCode, description, failingUrl);

            //			Log.i(TAG, "onReceivedError:" + errorCode + "  description:" + description + "  errorResponse:" + failingUrl);
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_REQUEST_CODE)
        {
            if (null != mAgentWeb)
            {
                mAgentWeb.getWebCreator().getWebView().reload();
            }
        }
        else if (requestCode == SHARE_PHOTO_REQUEST_CODE)
        {

            //            if ((int) (Math.random() * 100) <= 50)
            //            {
            // mAgentWeb.getJsAccessEntrace().quickCallJs("pear.onShareResult(true)");
            mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:pear.onShareResult(true)");
            //           }
        }
        /**
         * 2.0.0开始 废弃该api ，没有api代替 ,使用 ActionActivity 绕过该方法 ,降低使用门槛,4.0.0 删除该API。
         */
        //        mAgentWeb.uploadFileResult(requestCode, resultCode, data);
    }

}
