package com.capton.baseapp.bean;

/**
 * Created by capton on 2018/3/6.
 */

public class SavedPicBean {

    /**
     * nameValuePairs : {"filename":"mmexport1511335681255.jpg","filesize":"122803","url":"http://p54ihw7yb.bkt.clouddn.com/mmexport1511335681255.jpg"}
     */

    private NameValuePairsBean nameValuePairs;

    public NameValuePairsBean getNameValuePairs() {
        return nameValuePairs;
    }

    public void setNameValuePairs(NameValuePairsBean nameValuePairs) {
        this.nameValuePairs = nameValuePairs;
    }

    public static class NameValuePairsBean {
        /**
         * filename : mmexport1511335681255.jpg
         * filesize : 122803
         * url : http://p54ihw7yb.bkt.clouddn.com/mmexport1511335681255.jpg
         */

        private String filename;
        private String filesize;
        private String url;

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFilesize() {
            return filesize;
        }

        public void setFilesize(String filesize) {
            this.filesize = filesize;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
