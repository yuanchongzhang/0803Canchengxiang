package com.mingmen.canting.model;

import java.util.List;

public class ZhiweiBean {

    /**
     * code : 0
     * message : 操作成功
     * time : 2020-07-31 16:27:27
     * data : [{"position":"1000","positionNamName":"厨师"},{"position":"1001","positionNamName":"厨师长"},{"position":"1002","positionNamName":"主砧 "},{"position":"1003","positionNamName":"大堂经理 "},{"position":"1004","positionNamName":"服务员 "},{"position":"1005","positionNamName":"老板 "},{"position":"1006","positionNamName":"主管 "},{"position":"1007","positionNamName":"收银 "},{"position":"1008","positionNamName":"传菜员 "},{"position":"1009","positionNamName":"库管 "},{"position":"1010","positionNamName":"出纳 "},{"position":"1011","positionNamName":"采买 "},{"position":"1012","positionNamName":"会计 "},{"position":"1013","positionNamName":"店长 "},{"position":"1014","positionNamName":"迎宾 "},{"position":"1015","positionNamName":"点菜员 "},{"position":"1016","positionNamName":"砧板 "},{"position":"1017","positionNamName":"打荷 "},{"position":"1018","positionNamName":"副厨师长 "},{"position":"1019","positionNamName":"楼层经理 "},{"position":"1020","positionNamName":"其他 "}]
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
         * position : 1000
         * positionNamName : 厨师
         */

        private String position;
        private String positionNamName;

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getPositionNamName() {
            return positionNamName;
        }

        public void setPositionNamName(String positionNamName) {
            this.positionNamName = positionNamName;
        }
    }
}
