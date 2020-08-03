package com.mingmen.canting.model;

import java.util.List;

public class ShopListModel {


    /**
     * code : 0
     * message : 操作成功
     * time : 2020-08-01 09:32:28
     * data : [{"commodityId":"1288724611470999552","photo":"","commodityName":"商品1","specs":"啊","transitAmount":0,"mark":"","applyAmount":0,"inventoryAmount":0},{"commodityId":"1288724702780997632","photo":"","commodityName":"商品2","specs":"啊","transitAmount":0,"mark":"","applyAmount":0,"inventoryAmount":0},{"commodityId":"1288724725941944320","photo":"","commodityName":"商品3","specs":"啊","transitAmount":0,"mark":"","applyAmount":0,"inventoryAmount":0},{"commodityId":"1288724764449849344","photo":"","commodityName":"商品4","specs":"啊","transitAmount":0,"mark":"","applyAmount":0,"inventoryAmount":0},{"commodityId":"1288724784955801600","photo":"","commodityName":"商品5","specs":"啊","transitAmount":0,"mark":"","applyAmount":0,"inventoryAmount":0},{"commodityId":"1288724805184929792","photo":"","commodityName":"商品6","specs":"啊","transitAmount":0,"mark":"","applyAmount":0,"inventoryAmount":0},{"commodityId":"1288724828253601792","photo":"","commodityName":"商品7","specs":"啊","transitAmount":0,"mark":"","applyAmount":0,"inventoryAmount":0}]
     */

    private int code;
    private String message;
    private String time;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * commodityId : 1288724611470999552
         * photo :
         * commodityName : 商品1
         * specs : 啊
         * transitAmount : 0
         * mark :
         * applyAmount : 0
         * inventoryAmount : 0
         */

        private String commodityId;
        private String photo;
        private String commodityName;
        private String specs;
        private int transitAmount;
        private String mark;
        private int applyAmount;
        private int inventoryAmount;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        private boolean isChecked = false;
        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
        }

        public int getTransitAmount() {
            return transitAmount;
        }

        public void setTransitAmount(int transitAmount) {
            this.transitAmount = transitAmount;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public int getApplyAmount() {
            return applyAmount;
        }

        public void setApplyAmount(int applyAmount) {
            this.applyAmount = applyAmount;
        }

        public int getInventoryAmount() {
            return inventoryAmount;
        }

        public void setInventoryAmount(int inventoryAmount) {
            this.inventoryAmount = inventoryAmount;
        }
    }
}
