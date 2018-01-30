package com.example.dell.rxjavademo.retrofit.model;


import com.example.dell.rxjavademo.utils.BaseUtils;
import com.example.dell.rxjavademo.utils.LogUtils;
import com.example.dell.rxjavademo.utils.SharePreUtils;
import com.google.gson.Gson;

/**
 * description:   登录信息
 * autour: TMM
 * date: 2017/7/31 15:05
 * update: 2017/7/31
 * version:
*/

public class LoginBean {

    private static final String KEY = "login_user";
    /**
     * executeResult : true
     * messageCode : S0000
     * result : {"lastTime":1499232948528,"token":"e67db581-21d1-491c-8c5b-dc85f78cbf67","userInfo":{"age":18,"balance":399.93,"cashPledge":"1","create_date":"2017-06-19 16:15:40","driverNo":"158964975215","expenses":400,"favorables":400,"iCode":"000000","idcard":"1201031531254798564","idcard_photo_a":"","lastConsumption":1,"loginState":"0","realName":"回来了睡","recharges":400,"sex":"1","uid":6,"uname":"18334787764","upwd":"e10adc3949ba59abbe56e057f20f883e","ustate":2}}
     */

    private boolean executeResult;
    private String messageCode;
    private ResultBean result;


    /**
     * @Description (保存用户信息)
     * @param
     */
    public  void setUserInfo() {
        try {
            SharePreUtils.put(KEY, this.toJSON());
           // BroadcastManager.getInstance().sendBroadcast(Actions.ACCOUNT_CHANGED);
        } catch (Exception e) {
            LogUtils.e("save userInfo error!");
            e.printStackTrace();
        }
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // 取出用户信息
    public static LoginBean getLoginUser() {
        try {
            String loginUserJson = SharePreUtils.get(KEY, "");
            if (!BaseUtils.isEmpty(loginUserJson)) {
                Gson gson = new Gson();
                return gson.fromJson(loginUserJson , LoginBean.class);
            }
        } catch (Exception e) {
            LogUtils.e("local account error!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description (判断是否登录)
     * @return
     */
    public static boolean isLogin(){
        boolean isLogin = false;
        if (getLoginUser() != null){
            LoginBean loginUser = getLoginUser();
            if (loginUser != null) {
                if(loginUser.getResult().getUserInfo().getUname() != null && loginUser.getResult().getUserInfo().getUpwd() != null ){
                    isLogin = true;
                }
            }
        }
        return isLogin;
    }


    public static void clearLoginUserInfo(){
        SharePreUtils.remove(KEY);
    }

    public boolean isExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(boolean executeResult) {
        this.executeResult = executeResult;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }


    public static class ResultBean {
        /**
         * lastTime : 1499232948528
         * token : e67db581-21d1-491c-8c5b-dc85f78cbf67
         * userInfo : {"age":18,"balance":399.93,"cashPledge":"1","create_date":"2017-06-19 16:15:40","driverNo":"158964975215","expenses":400,"favorables":400,"iCode":"000000","idcard":"1201031531254798564","idcard_photo_a":"","lastConsumption":1,"loginState":"0","realName":"回来了睡","recharges":400,"sex":"1","uid":6,"uname":"18334787764","upwd":"e10adc3949ba59abbe56e057f20f883e","ustate":2}
         */

        private long lastTime;
        private String token;
        private UserInfoBean userInfo;

        public long getLastTime() {
            return lastTime;
        }

        public void setLastTime(long lastTime) {
            this.lastTime = lastTime;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class UserInfoBean {
            /**
             * age : 18
             * balance : 399.93
             * cashPledge : 1
             * create_date : 2017-06-19 16:15:40
             * driverNo : 158964975215
             * expenses : 400.0
             * favorables : 400.0
             * iCode : 000000
             * idcard : 1201031531254798564
             * idcard_photo_a :
             * lastConsumption : 1
             * loginState : 0
             * realName : 回来了睡
             * recharges : 400.0
             * sex : 1
             * uid : 6
             * uname : 18334787764
             * upwd : e10adc3949ba59abbe56e057f20f883e
             * ustate : 2
             */

            private int age;
            private double balance;
            private String cashPledge;
            private String create_date;
            private String driverNo;
            private double expenses;
            private double favorables;
            private String iCode;
            private String idcard;
            private String idcard_photo_a;
            private String lastConsumption;
            private String loginState;
            private String realName;
            private double recharges;
            private String sex;
            private int uid;
            private String uname;
            private String upwd;
            private int ustate;

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public String getCashPledge() {
                return cashPledge;
            }

            public void setCashPledge(String cashPledge) {
                this.cashPledge = cashPledge;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getDriverNo() {
                return driverNo;
            }

            public void setDriverNo(String driverNo) {
                this.driverNo = driverNo;
            }

            public double getExpenses() {
                return expenses;
            }

            public void setExpenses(double expenses) {
                this.expenses = expenses;
            }

            public double getFavorables() {
                return favorables;
            }

            public void setFavorables(double favorables) {
                this.favorables = favorables;
            }

            public String getICode() {
                return iCode;
            }

            public void setICode(String iCode) {
                this.iCode = iCode;
            }

            public String getIdcard() {
                return idcard;
            }

            public void setIdcard(String idcard) {
                this.idcard = idcard;
            }

            public String getIdcard_photo_a() {
                return idcard_photo_a;
            }

            public void setIdcard_photo_a(String idcard_photo_a) {
                this.idcard_photo_a = idcard_photo_a;
            }

            public String getLastConsumption() {
                return lastConsumption;
            }

            public void setLastConsumption(String lastConsumption) {
                this.lastConsumption = lastConsumption;
            }

            public String getLoginState() {
                return loginState;
            }

            public void setLoginState(String loginState) {
                this.loginState = loginState;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public double getRecharges() {
                return recharges;
            }

            public void setRecharges(double recharges) {
                this.recharges = recharges;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public String getUpwd() {
                return upwd;
            }

            public void setUpwd(String upwd) {
                this.upwd = upwd;
            }

            public int getUstate() {
                return ustate;
            }

            public void setUstate(int ustate) {
                this.ustate = ustate;
            }
        }
    }
}
