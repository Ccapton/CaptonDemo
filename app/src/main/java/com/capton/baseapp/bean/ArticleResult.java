package com.capton.baseapp.bean;

import java.util.List;

/**
 * Created by capton on 2018/3/5.
 */

public class ArticleResult {

    /**
     * code : 0
     * message : 获取文章成功
     * data : [{"id":7,"title":"iopjpokifdf","nick":"","avater":"","content":"ouvojcvzkzxjvxockpoxjjcxdfjdapsdjafodufadsopfdsafdusfsfsfdfddfdsfa","author_id":1}]
     */

    private int code;
    private String message;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 7
         * title : iopjpokifdf
         * nick :
         * avater :
         * content : ouvojcvzkzxjvxockpoxjjcxdfjdapsdjafodufadsopfdsafdusfsfsfdfddfdsfa
         * author_id : 1
         */

        private int id;
        private String title;
        private String nick;
        private String avater;
        private String content;
        private int author_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(int author_id) {
            this.author_id = author_id;
        }
    }
}
