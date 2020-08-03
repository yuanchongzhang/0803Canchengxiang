package com.mingmen.canting.model;

import java.util.List;

public class ZhenBanShenHe {


    /**
     * code : 0
     * message : 操作成功
     * time : 2020-08-01 16:05:42
     * data : [{"applyId":"1289440843727507456","applyTime":"2020-08-02","rejectApplyDetailId":"0","rejectCommodityId":"0","rejectCommodityName":"","rejectReason":"","rejectCount":"0","timeSpan":"8点之前"}]
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
         * applyId : 1289440843727507456
         * applyTime : 2020-08-02
         * rejectApplyDetailId : 0
         * rejectCommodityId : 0
         * rejectCommodityName :
         * rejectReason :
         * rejectCount : 0
         * timeSpan : 8点之前
         */

        private String applyId;
        private String applyTime;
        private String rejectApplyDetailId;
        private String rejectCommodityId;
        private String rejectCommodityName;
        private String rejectReason;
        private String rejectCount;
        private String timeSpan;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        private boolean isSelect;

        public String getApplyId() {
            return applyId;
        }

        public void setApplyId(String applyId) {
            this.applyId = applyId;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getRejectApplyDetailId() {
            return rejectApplyDetailId;
        }

        public void setRejectApplyDetailId(String rejectApplyDetailId) {
            this.rejectApplyDetailId = rejectApplyDetailId;
        }

        public String getRejectCommodityId() {
            return rejectCommodityId;
        }

        public void setRejectCommodityId(String rejectCommodityId) {
            this.rejectCommodityId = rejectCommodityId;
        }

        public String getRejectCommodityName() {
            return rejectCommodityName;
        }

        public void setRejectCommodityName(String rejectCommodityName) {
            this.rejectCommodityName = rejectCommodityName;
        }

        public String getRejectReason() {
            return rejectReason;
        }

        public void setRejectReason(String rejectReason) {
            this.rejectReason = rejectReason;
        }

        public String getRejectCount() {
            return rejectCount;
        }

        public void setRejectCount(String rejectCount) {
            this.rejectCount = rejectCount;
        }

        public String getTimeSpan() {
            return timeSpan;
        }

        public void setTimeSpan(String timeSpan) {
            this.timeSpan = timeSpan;
        }
    }
}
