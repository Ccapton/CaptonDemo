package com.capton.baseapp.bean;

/**
 * Created by capton on 2018/3/5.
 */

public class AddDynamicResult {

    /**
     * code : 0
     * message : 添加成功
     * data : {"id":30,"text":"什么反对党","nick":"admin","avater":"https://avatars3.githubusercontent.com/u/20469019?s=460&v=4","picUrls":"{'picList':['https://avatars3.githubusercontent.com/u/20469019?s=460&v=4']}","author_id":83}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 30
         * text : 什么反对党
         * nick : admin
         * avater : https://avatars3.githubusercontent.com/u/20469019?s=460&v=4
         * picUrls : {'picList':['https://avatars3.githubusercontent.com/u/20469019?s=460&v=4']}
         * author_id : 83
         */

        private int id;
        private String text;
        private String nick;
        private String avater;
        private String picUrls;
        private int author_id;

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
