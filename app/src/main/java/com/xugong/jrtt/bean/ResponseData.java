package com.xugong.jrtt.bean;

import java.util.List;

public class ResponseData {

    /**
     * data : [{"id":10007,"title":"北京","type":1,"url":"10007/list_1.json"},{"id":10006,"title":"中国","type":1,"url":"10006/list_1.json"},{"id":10008,"title":"国际","type":1,"url":"10008/list_1.json"},{"id":10010,"title":"体育","type":1,"url":"10010/list_1.json"},{"id":10091,"title":"生活","type":1,"url":"10091/list_1.json"},{"id":10012,"title":"旅游","type":1,"url":"10012/list_1.json"},{"id":10095,"title":"科技","type":1,"url":"10095/list_1.json"},{"id":10009,"title":"军事","type":1,"url":"10009/list_1.json"},{"id":10093,"title":"时尚","type":1,"url":"10093/list_1.json"},{"id":10011,"title":"财经","type":1,"url":"10011/list_1.json"},{"id":10094,"title":"育儿","type":1,"url":"10094/list_1.json"},{"id":10105,"title":"汽车","type":1,"url":"10105/list_1.json"}]
     * retcode : 200
     */

    public int retcode;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 10007
         * title : 北京
         * type : 1
         * url : 10007/list_1.json
         */

        public int id;
        public String title;
        public int type;
        public String url;
    }
}
