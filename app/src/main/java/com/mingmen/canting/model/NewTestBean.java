package com.mingmen.canting.model;

import java.util.List;

public class NewTestBean {

    /**
     * code : 200
     * message : 成功!
     * result : [{"sid":"31454214","text":"自制多米诺开瓶器，一次开一百瓶乌苏。让开盖更加简单且硬气","type":"video","thumbnail":"http://wimg.spriteapp.cn/picture/2020/0628/5ef86ce78d45b_wpd.jpg","video":"http://uvideo.spriteapp.cn/video/2020/0628/5ef86ce78d45b_wpd.mp4","images":null,"up":"421","down":"10","forward":"7","comment":"81","uid":"23126824","name":"好人","header":"http://wimg.spriteapp.cn/profile/large/2019/07/04/5d1d6d1c15ae5_mini.jpg","top_comments_content":"哪里有卖的","top_comments_voiceuri":"","top_comments_uid":"23074700","top_comments_name":"犄角的尽头","top_comments_header":"http://wimg.spriteapp.cn/profile/large/2019/04/09/5cac7f801b351_mini.jpg","passtime":"2020-07-31 21:00:03"},{"sid":"31442440","text":"道理我都懂！可是实力不允许啊","type":"video","thumbnail":"http://wimg.spriteapp.cn/picture/2020/0618/5eeadf4a05620_wpd.jpg","video":"http://uvideo.spriteapp.cn/video/2020/0618/5eeadf4a05620_wpd.mp4","images":null,"up":"206","down":"12","forward":"1","comment":"75","uid":"21041544","name":"爱在两腿之间","header":"http://wimg.spriteapp.cn/profile/large/2020/01/14/5e1d1f69509fe_mini.jpg","top_comments_content":"玩大灯得劲儿不","top_comments_voiceuri":"","top_comments_uid":"20749148","top_comments_name":"天吻nXG","top_comments_header":"http://wx.qlogo.cn/mmopen/Q3auHgzwzM6xveHoIjbbuMUSRjnWjsd5Iuyn2CTtcBFlBgicVR6qribUyzVoMuKKyEewg5fL4pjsx8SkgTyL21fA/0","passtime":"2020-07-31 20:30:08"}]
     */

    private int code;
    private String message;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * sid : 31454214
         * text : 自制多米诺开瓶器，一次开一百瓶乌苏。让开盖更加简单且硬气
         * type : video
         * thumbnail : http://wimg.spriteapp.cn/picture/2020/0628/5ef86ce78d45b_wpd.jpg
         * video : http://uvideo.spriteapp.cn/video/2020/0628/5ef86ce78d45b_wpd.mp4
         * images : null
         * up : 421
         * down : 10
         * forward : 7
         * comment : 81
         * uid : 23126824
         * name : 好人
         * header : http://wimg.spriteapp.cn/profile/large/2019/07/04/5d1d6d1c15ae5_mini.jpg
         * top_comments_content : 哪里有卖的
         * top_comments_voiceuri :
         * top_comments_uid : 23074700
         * top_comments_name : 犄角的尽头
         * top_comments_header : http://wimg.spriteapp.cn/profile/large/2019/04/09/5cac7f801b351_mini.jpg
         * passtime : 2020-07-31 21:00:03
         */

        private String sid;
        private String text;
        private String type;
        private String thumbnail;
        private String video;
        private Object images;
        private String up;
        private String down;
        private String forward;
        private String comment;
        private String uid;
        private String name;
        private String header;
        private String top_comments_content;
        private String top_comments_voiceuri;
        private String top_comments_uid;
        private String top_comments_name;
        private String top_comments_header;
        private String passtime;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
        }

        public String getUp() {
            return up;
        }

        public void setUp(String up) {
            this.up = up;
        }

        public String getDown() {
            return down;
        }

        public void setDown(String down) {
            this.down = down;
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getTop_comments_content() {
            return top_comments_content;
        }

        public void setTop_comments_content(String top_comments_content) {
            this.top_comments_content = top_comments_content;
        }

        public String getTop_comments_voiceuri() {
            return top_comments_voiceuri;
        }

        public void setTop_comments_voiceuri(String top_comments_voiceuri) {
            this.top_comments_voiceuri = top_comments_voiceuri;
        }

        public String getTop_comments_uid() {
            return top_comments_uid;
        }

        public void setTop_comments_uid(String top_comments_uid) {
            this.top_comments_uid = top_comments_uid;
        }

        public String getTop_comments_name() {
            return top_comments_name;
        }

        public void setTop_comments_name(String top_comments_name) {
            this.top_comments_name = top_comments_name;
        }

        public String getTop_comments_header() {
            return top_comments_header;
        }

        public void setTop_comments_header(String top_comments_header) {
            this.top_comments_header = top_comments_header;
        }

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }
    }
}
