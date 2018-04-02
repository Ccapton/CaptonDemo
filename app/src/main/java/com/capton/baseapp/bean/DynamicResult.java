package com.capton.baseapp.bean;

import com.capton.common.user.User;

import java.util.List;

/**
 * Created by capton on 2018/3/5.
 */

public class DynamicResult {

    /**
     * code : 0
     * message : 获取成功
     * data : [{"id":6,"text":"iopjpokifdf","nick":"CAPTON","avater":"https://avatars3.githubusercontent.com/u/20469019?s=460&v=4","picUrls":"{'picList':['https://avatars3.githubusercontent.com/u/20469019?s=460&v=4','https://avatars3.githubusercontent.com/u/20469019?s=460&v=4','https://avatars3.githubusercontent.com/u/20469019?s=460&v=4']}","author_id":1}]
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
         * id : 6
         * text : iopjpokifdf
         * nick : CAPTON
         * avater : https://avatars3.githubusercontent.com/u/20469019?s=460&v=4
         * picUrls : {'picList':['https://avatars3.githubusercontent.com/u/20469019?s=460&v=4','https://avatars3.githubusercontent.com/u/20469019?s=460&v=4','https://avatars3.githubusercontent.com/u/20469019?s=460&v=4']}
         * author_id : 1
         */

        private int id;
        private String text;
        private String nick;
        private String avater;
        private String picUrls;
        private int author_id;
        private User author;

        public User getAuthor() {
            return author;
        }

        public void setAuthor(User author) {
            this.author = author;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
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

        public String getPicUrls() {
            return picUrls;
        }

        public void setPicUrls(String picUrls) {
            this.picUrls = picUrls;
        }

        public int getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(int author_id) {
            this.author_id = author_id;
        }
    }
}
