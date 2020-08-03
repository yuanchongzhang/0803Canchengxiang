package com.mingmen.canting.model;

import java.util.List;

public class LoginBean {


    /**
     * code : 0
     * message : 操作成功
     * time : 2020-07-25 13:04:33
     * data : [{"companyId":681,"companyName":"迎宾烧烤","userId":1286889976134504448,"userName":"张宇","userRole":"1000","departmentId":0,"departmentRole":"","randomId":"1","token":"128d55deb02649a7acbc099833d13a15"}]
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
         * companyId : 681
         * companyName : 迎宾烧烤
         * userId : 1286889976134504448
         * userName : 张宇
         * userRole : 1000
         * departmentId : 0
         * departmentRole :
         * randomId : 1
         * token : 128d55deb02649a7acbc099833d13a15
         */

        private int companyId;
        private String companyName;
        private long userId;
        private String userName;
        private String userRole;
        private int departmentId;
        private String departmentRole;
        private String randomId;
        private String token;

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public String getDepartmentRole() {
            return departmentRole;
        }

        public void setDepartmentRole(String departmentRole) {
            this.departmentRole = departmentRole;
        }

        public String getRandomId() {
            return randomId;
        }

        public void setRandomId(String randomId) {
            this.randomId = randomId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
