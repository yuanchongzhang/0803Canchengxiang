package com.mingmen.canting.http.callback;


import android.app.Activity;
import android.util.Log;

import com.mingmen.canting.http.convert.StringConvert;

import okhttp3.Response;

/**
 * ================================================
 * 作    者：张宇
 * 版    本：1.0
 * 创建日期：2016/9/11
 * 描    述：返回字符串类型的数据
 * 修订历史：
 * ================================================
 */
public abstract class StringNoDialogCallback extends AbsCallback<String> {
    public StringNoDialogCallback(Activity activity) {
        super();

    }

    @Override
    public String convertSuccess(Response response) throws Exception {
        String s = StringConvert.create().convertSuccess(response);
        response.close();

        Log.d("dkdkkdsdfdfk", response.message());
        return s;

    }


}