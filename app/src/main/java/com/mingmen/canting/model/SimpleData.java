package com.mingmen.canting.model;

import java.util.List;

/**
 * @author wang
 * @date {2020/4/13}
 * Tip : 严格要求自己，对每一行代码负责
 * description：
 */
public class SimpleData {

    public SimpleData(String typeName, List<ChildData> list) {
        this.typeName = typeName;
        this.list = list;
    }

    public String typeName;

    public List<ChildData> list;

    public static class ChildData {
        public String childName;
        public boolean select;

        public ChildData(String childName) {
            this.childName = childName;
        }
    }
}
