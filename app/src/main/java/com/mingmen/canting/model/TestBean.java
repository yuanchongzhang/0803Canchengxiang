package com.mingmen.canting.model;

import java.util.List;

public class TestBean {


    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * pic_s210 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_734232335ef76f5a05179797875817f3.jpg
         * bg_pic : http://business0.qianqian.com/qianqian/file/5bfe4e9aa7496_218.png
         * color : 0xDC5900
         * pic_s444 : http://hiphotos.qianqian.com/ting/pic/item/c83d70cf3bc79f3d98ca8e36b8a1cd11728b2988.jpg
         * count : 4
         * type : 2
         * content : [{"all_rate":"96,128,224,320,flac","song_id":"672865438","rank_change":"0","biaoshi":"lossless,perm-1","author":"舞蹈女神诺涵","album_id":"672865436","pic_small":"http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_90,h_90","title":"桥边姑娘","pic_big":"http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_150,h_150","album_title":"桥边姑娘"},{"all_rate":"96,224,128,320,flac","song_id":"675017674","rank_change":"1","biaoshi":"lossless,perm-1","author":"宋小睿","album_id":"675017666","pic_small":"http://qukufile2.qianqian.com/data2/pic/8b1aab6be81f10639c01c1401a20463c/675021896/675021896.jpg@s_2,w_90,h_90","title":"少年（童声版）","pic_big":"http://qukufile2.qianqian.com/data2/pic/8b1aab6be81f10639c01c1401a20463c/675021896/675021896.jpg@s_2,w_150,h_150","album_title":"少年"},{"all_rate":"96,128,224,320,flac","song_id":"265715650","rank_change":"1","biaoshi":"lossless,perm-1","author":"周深","album_id":"265715651","pic_small":"http://qukufile2.qianqian.com/data2/pic/0d359ec1be6f5365f92d4c83d3eeb022/603758238/603758238.jpg@s_2,w_90,h_90","title":"大鱼","pic_big":"http://qukufile2.qianqian.com/data2/pic/0d359ec1be6f5365f92d4c83d3eeb022/603758238/603758238.jpg@s_2,w_150,h_150","album_title":"大鱼"},{"all_rate":"96,128,224,320,flac","song_id":"242078437","rank_change":"-2","biaoshi":"lossless,vip,perm-1","author":"薛之谦","album_id":"241838068","pic_small":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_90,h_90","title":"演员","pic_big":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_150,h_150","album_title":"初学者"}]
         * bg_color : 0xFBEFE6
         * web_url :
         * name : 热歌榜
         * comment : 该榜单是根据千千音乐平台歌曲每周播放量自动生成的数据榜单，统计范围为千千音乐平台上的全部歌曲，每日更新一次
         * pic_s192 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_1452f36a8dc430ccdb8f6e57be6df2ee.jpg
         * pic_s260 : http://hiphotos.qianqian.com/ting/pic/item/838ba61ea8d3fd1f1326c83c324e251f95ca5f8c.jpg
         */

        private String pic_s210;
        private String bg_pic;
        private String color;
        private String pic_s444;
        private int count;
        private int type;
        private String bg_color;
        private String web_url;
        private String name;
        private String comment;
        private String pic_s192;
        private String pic_s260;
        private List<ContentBean> content;

        public String getPic_s210() {
            return pic_s210;
        }

        public void setPic_s210(String pic_s210) {
            this.pic_s210 = pic_s210;
        }

        public String getBg_pic() {
            return bg_pic;
        }

        public void setBg_pic(String bg_pic) {
            this.bg_pic = bg_pic;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getPic_s444() {
            return pic_s444;
        }

        public void setPic_s444(String pic_s444) {
            this.pic_s444 = pic_s444;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getBg_color() {
            return bg_color;
        }

        public void setBg_color(String bg_color) {
            this.bg_color = bg_color;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getPic_s192() {
            return pic_s192;
        }

        public void setPic_s192(String pic_s192) {
            this.pic_s192 = pic_s192;
        }

        public String getPic_s260() {
            return pic_s260;
        }

        public void setPic_s260(String pic_s260) {
            this.pic_s260 = pic_s260;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * all_rate : 96,128,224,320,flac
             * song_id : 672865438
             * rank_change : 0
             * biaoshi : lossless,perm-1
             * author : 舞蹈女神诺涵
             * album_id : 672865436
             * pic_small : http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_90,h_90
             * title : 桥边姑娘
             * pic_big : http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_150,h_150
             * album_title : 桥边姑娘
             */

            private String all_rate;
            private String song_id;
            private String rank_change;
            private String biaoshi;
            private String author;
            private String album_id;
            private String pic_small;
            private String title;
            private String pic_big;
            private String album_title;

            public String getAll_rate() {
                return all_rate;
            }

            public void setAll_rate(String all_rate) {
                this.all_rate = all_rate;
            }

            public String getSong_id() {
                return song_id;
            }

            public void setSong_id(String song_id) {
                this.song_id = song_id;
            }

            public String getRank_change() {
                return rank_change;
            }

            public void setRank_change(String rank_change) {
                this.rank_change = rank_change;
            }

            public String getBiaoshi() {
                return biaoshi;
            }

            public void setBiaoshi(String biaoshi) {
                this.biaoshi = biaoshi;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getAlbum_id() {
                return album_id;
            }

            public void setAlbum_id(String album_id) {
                this.album_id = album_id;
            }

            public String getPic_small() {
                return pic_small;
            }

            public void setPic_small(String pic_small) {
                this.pic_small = pic_small;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPic_big() {
                return pic_big;
            }

            public void setPic_big(String pic_big) {
                this.pic_big = pic_big;
            }

            public String getAlbum_title() {
                return album_title;
            }

            public void setAlbum_title(String album_title) {
                this.album_title = album_title;
            }
        }
    }
}
