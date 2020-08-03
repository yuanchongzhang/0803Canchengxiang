package com.mingmen.canting;

import android.app.Application;
import android.support.multidex.MultiDex;


import com.mingmen.canting.http.MyOkhttp;
import com.mingmen.canting.http.cache.CacheEntity;
import com.mingmen.canting.http.cache.CacheMode;
import com.mingmen.canting.http.cookie.store.PersistentCookieStore;
import com.mingmen.canting.http.model.HttpHeaders;
import com.mingmen.canting.http.model.HttpParams;

import java.util.logging.Level;

/**
 * ================================================
 * 作    者：张宇     QQ:91238141
 * 版    本：1.0
 * 创建日期：2015/9/23
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数");
        //-----------------------------------------------------------------------------------//
        //必须调用初始化
        MyOkhttp.init(this);
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            MyOkhttp.getInstance()

                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("MyOkhttp", Level.INFO, true)

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(MyOkhttp.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(MyOkhttp.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(MyOkhttp.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 QQ:91238141
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    .setRetryCount(3)

                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

                    //这两行同上，不需要就不要加入
                    .addCommonHeaders(headers)  //设置全局公共头
                    .addCommonParams(params);   //设置全局公共参数

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}