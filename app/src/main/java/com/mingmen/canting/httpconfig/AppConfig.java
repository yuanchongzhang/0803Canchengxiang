package com.mingmen.canting.httpconfig;

public class AppConfig {
    //
//    public static String Url = "http://192.168.0.12:8803/";
    public static String Url = "http://192.168.0.18:8801/";
    public static String getCode = "login/login/verify";
    public static String REGISTER = "login/login/register";
    public static String LOGIN = "login/login/login";
    public static String FORGET = "login/login/modify";
    public static String RESTRUANT = "restaurant/base/getAllRestaurant";
    public static String ZHIWEI = "restaurant/base/getPosition";

    public static String DAISHENHE = "restaurant/apply/getApplyBillList";
    //获取商品分类
    public static String SHOPFENLEI = "restaurant/base/getCategoryList";
    public static String SHOPFENLEI_LIST = "restaurant/apply/getCategoryCommodityList";

    public static String DELETE_ITEM = "restaurant/apply/deleteApplyBill";

    public static String ADD_ITEM = "restaurant/apply/addApplyCommodity";
//    参照历史申请单生成新申请单
    public static String NEW_PRICE = "restaurant/apply/copyApplyBill";

//    【根据申请单显示商品清单】
    public static String SHOW_SHOPDETAIL = "restaurant/apply/getApplyBillDetail";
//【获取被驳回的申请单数量】
    public static String GETCOUNT = "restaurant/apply/getApplyRejectCount";
}
