package com.capton.baseapp.bean;

/**
 * Created by capton on 2018/3/6.
 */

public class UpdateUserResult {

    /**
     * code : 0
     * message : 更新成功
     * data : {"id":83,"username":"66666666666","password":"chen3723910","nick":"CAPTON","gender":null,"age":null,"avater":"https://avatars3.githubusercontent.com/u/20469019?s=460&v=4","introduction":null,"token":"6fv6n6c6ms_6jcmk"}
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
         * id : 83
         * username : 66666666666
         * password : chen3723910
         * nick : CAPTON
         * gender : null
         * age : null
         * avater : https://avatars3.githubusercontent.com/u/20469019?s=460&v=4
         * introduction : null
         * token : 6fv6n6c6ms_6jcmk
         */

        private int id;
        private String username;
        private String password;
        private String nick;
        private Object gender;
        private Object age;
        private String avater;
        private Object introduction;
        private String token;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }

        public Object getIntroduction() {
            return introduction;
        }

        public void setIntroduction(Object introduction) {
            this.introduction = introduction;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
