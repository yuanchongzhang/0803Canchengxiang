package com.mingmen.canting.model;

import java.util.List;

public class ShopBean {


    /**
     * code : 0
     * message : 操作成功
     * time : 2020-07-31 13:22:39
     * data : [{"categoryId":"200","categoryName":"办公用品","parentId":"0","havaChildren":1},{"categoryId":"201","categoryName":"低值易耗品","parentId":"0","havaChildren":1},{"categoryId":"202","categoryName":"日用品","parentId":"0","havaChildren":1}]
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
         * categoryId : 200
         * categoryName : 办公用品
         * parentId : 0
         * havaChildren : 1
         */

        private String categoryId;
        private String categoryName;
        private String parentId;
        private int havaChildren;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public int getHavaChildren() {
            return havaChildren;
        }

        public void setHavaChildren(int havaChildren) {
            this.havaChildren = havaChildren;
        }
    }
}
