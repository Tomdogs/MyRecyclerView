package com.example.lg.myrecyclerview.model;

import java.util.List;

/**
 * Created by Tomdog on 2018/11/23.
 */

public class Message {


    /**
     * msg : ok
     * code : 0
     * data : {"tasks":[{"id":1,"userId":"2","summary":"好好学习，天天向上","addTime":"2017-04-29 18:49:49","status":0}]}
     */

    private String msg;
    private String code;
    private DataEntity data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private List<TasksEntity> tasks;

        public List<TasksEntity> getTasks() {
            return tasks;
        }

        public void setTasks(List<TasksEntity> tasks) {
            this.tasks = tasks;
        }

        public static class TasksEntity {
            /**
             * id : 1
             * userId : 2
             * summary : 好好学习，天天向上
             * addTime : 2017-04-29 18:49:49
             * status : 0
             */

            private int id;
            private String userId;
            private String summary;
            private String addTime;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
