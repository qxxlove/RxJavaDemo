package com.example.dell.rxjavademo.designmode.builder;

import android.util.Log;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/9/19.
 * 邮箱：123123@163.com
 */

public class Teacher {


        private  String name ;
        private  int  age;
        private  String job;


        public Teacher(Builder builder) {
            this.name = builder.name;
            this.age = builder.age;
            this.job = builder.job;
        }

       public static class Builder {
            private  String name ;
            private  int  age;
            private  String job;

            public String getName() {
                return name;
            }

           /**
            * 必须返回自己
            * @param name
            * @return
            */
            public Builder setName(String name) {
                this.name = name;
                return  this;
            }

            public int getAge() {
                return age;
            }

            public Builder setAge(int age) {
                this.age = age;
                return  this;
            }

            public String getJob() {
                return job;
            }

            public Builder setJob(String job) {
                this.job = job;
                return  this;
            }

            public  Teacher build (){
                Teacher teacher = new Teacher(this);
                Log.e("DesignModeActivity",teacher.toString());
                return  teacher;
            }
        }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", job='" + job + '\'' +
                '}';
    }


}
