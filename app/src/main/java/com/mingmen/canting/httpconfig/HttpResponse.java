package com.mingmen.canting.httpconfig;

import com.mingmen.canting.http.MyOkhttp;
import com.mingmen.canting.http.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

public class HttpResponse {

    public static void getCode(String phone, String t, StringCallback callback) {
        MyOkhttp.post(AppConfig.Url + AppConfig.getCode)
                .params("phone", phone)
                .params("t", t)
                .execute(callback);

    }

    public static void getRegister(String phone, String pwd, String verifyCode, String companyId, String departmentId, String position, String t, String userName, StringCallback callback) {
        MyOkhttp.post(AppConfig.Url + AppConfig.REGISTER)
                .params("phone", phone)
                .params("pwd", pwd)
                .params("verifyCode", verifyCode)
                .params("companyId", companyId)
                .params("departmentId", departmentId)
                .params("position", position)
                .params("t", t)
                .params("userName", userName)
                .execute(callback);

    }

    public static void getLogin(String phone, String pwd, String t, String Apptype, String CompanyId, StringCallback callback) {
//        CompanyId 没有传0
        MyOkhttp.post(AppConfig.Url + AppConfig.LOGIN)
                .params("phone", phone)
                .params("pwd", pwd)
                .params("t", t)
                .params("appType", Apptype)
                .params("comPanyId", CompanyId)
                .execute(callback);

    }

    public static void Forget(String token, String phone, String verifyCode, String originalPassword, String newPassword, String t, StringCallback callback) {
        MyOkhttp.post(AppConfig.Url + AppConfig.FORGET)
                .params("token", token)
                .params("phone", phone)
                .params("verifyCode", verifyCode)
                .params("originalPassword", originalPassword)
                .params("newPassword", newPassword)
                .params("t", t)
                .execute(callback);

    }

    public static void getRestruant(StringCallback callback) {
        MyOkhttp.post(AppConfig.Url + AppConfig.RESTRUANT)

                .execute(callback);

    }

    public static void getZhiwei(StringCallback callback) {
//        MyOkhttp.post(AppConfig.Url + AppConfig.ZHIWEI)
        MyOkhttp.post("http://192.168.0.18:8801/restaurant/base/getPosition")
                .execute(callback);

    }

    public static void daiShenhe(String token, String st, String startTime, String endTime, String flag, StringCallback callback) {
        MyOkhttp.post(AppConfig.Url + AppConfig.DAISHENHE)
                .params("token", token)
                .params("st", st)
                .params("startTime", startTime)
                .params("endTime", endTime)
                .params("flag", flag)

                .execute(callback);
    }

    //     获取商品分类列表
    public static void getShop(String token, String categoryId, String flag, StringCallback callback) {
        MyOkhttp.post(AppConfig.Url + AppConfig.SHOPFENLEI)
                .params("token", token)
                .params("categoryId", categoryId)
                .params("flag", flag)
                .execute(callback);
    }


    //    【获取分类下商品】
    public static void getFenLeiShop(String token, String categoryId, StringCallback callback) {
        MyOkhttp.post(AppConfig.Url + AppConfig.SHOPFENLEI_LIST)
                .params("token", token)
                .params("categoryId", categoryId)

                .execute(callback);
    }

    //删除订单
    public static void deleteItem(String token, String applyId, StringCallback callback) {
        MyOkhttp.post(AppConfig.Url + AppConfig.DELETE_ITEM)
                .params("token", token)
                .params("categoryId", applyId)

                .execute(callback);
    }

    //追加订单
    public static void getItem(String token, String applyId, String commodityId, String amount, String role, StringCallback callback) {
        MyOkhttp.post(AppConfig.Url + AppConfig.DELETE_ITEM)
                .params("token", token)
                .params("applyId", applyId)
                .params("commodityId", commodityId)
                .params("amount", amount)
                .params("role", role)
                .execute(callback);
    }

    //    NEW_PRICE 参照历史申请单生成新申请单
    public static void addNewPrice(String token, String applyId, String arrivalDate, StringCallback callback) {
        MyOkhttp.post(AppConfig.Url + AppConfig.NEW_PRICE)
                .params("token", token)
                .params("applyId", applyId)
                .params("arrivalDate", arrivalDate)

                .execute(callback);
    }

    //    【根据申请单显示商品清单】
    public static void showShopDetail(String token, String applyId, StringCallback callback) {
        MyOkhttp.post(AppConfig.Url + AppConfig.SHOW_SHOPDETAIL)
                .params("token", token)
                .params("applyId", applyId)

                .execute(callback);
    }
//【获取被驳回的申请单数量】
    public static void getBohuiCount(String token, String startTime, String endTime, StringCallback callback) {
        MyOkhttp.post(AppConfig.Url + AppConfig.GETCOUNT)
                .params("token", token)
                .params("startTime", startTime)
                .params("endTime", endTime)

                .execute(callback);
    }
}
