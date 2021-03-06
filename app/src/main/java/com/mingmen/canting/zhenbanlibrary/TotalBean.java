package com.mingmen.canting.zhenbanlibrary;

import java.util.List;

public class TotalBean {


    private List<CategoryOneArrayBean> categoryOneArray;

    public List<CategoryOneArrayBean> getCategoryOneArray() {
        return categoryOneArray;
    }

    public void setCategoryOneArray(List<CategoryOneArrayBean> categoryOneArray) {
        this.categoryOneArray = categoryOneArray;
    }

    public static class CategoryOneArrayBean {
        /**
         * categoryTwoArray : [{"name":"处方药(RX)","imgsrc":"https://121.10.217.171:9002/_ui/desktop/common/cmyy/image/app_tongsufenlei_chufangyao.png","cacode":"chufangyao"},{"name":"非处方(OTC)","imgsrc":"https://121.10.217.171:9002/_ui/desktop/common/cmyy/image/app_tongsufenlei_feichufang.png","cacode":"feichufang"},{"name":"抗生素","imgsrc":"https://121.10.217.171:9002/_ui/desktop/common/cmyy/image/app_tongsufenlei_kangshengsu.png","cacode":"kangshengsu"},{"name":"维生素","imgsrc":"https://121.10.217.171:9002/_ui/desktop/common/cmyy/image/app_tongsufenlei_kangshengsu.png","cacode":"kangshengsu"}]
         * name : 通俗分类
         * imgsrc : https://121.10.217.171:9002/_ui/desktop/common/cmyy/image/app_0.png
         * cacode : tongsufenlei
         */

        private String name;
        private String imgsrc;
        private String cacode;
        private List<CategoryTwoArrayBean> categoryTwoArray;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getCacode() {
            return cacode;
        }

        public void setCacode(String cacode) {
            this.cacode = cacode;
        }

        public List<CategoryTwoArrayBean> getCategoryTwoArray() {
            return categoryTwoArray;
        }

        public void setCategoryTwoArray(List<CategoryTwoArrayBean> categoryTwoArray) {
            this.categoryTwoArray = categoryTwoArray;
        }

        public static class CategoryTwoArrayBean {
            /**
             * name : 处方药(RX)
             * imgsrc : https://121.10.217.171:9002/_ui/desktop/common/cmyy/image/app_tongsufenlei_chufangyao.png
             * cacode : chufangyao
             */

            private String name;
            private String imgsrc;
            private String cacode;
            private int count;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            private boolean isChecked = false;
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public String getCacode() {
                return cacode;
            }

            public void setCacode(String cacode) {
                this.cacode = cacode;
            }
        }
    }
}
