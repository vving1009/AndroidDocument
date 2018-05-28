package com.android.app.sqlitetest;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CurrencyBean {

    private List<RateBean> rate;

    public static CurrencyBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), CurrencyBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<RateBean> getRate() {
        return rate;
    }

    public void setRate(List<RateBean> rate) {
        this.rate = rate;
    }

    public static class RateBean {
        /**
         * n1 : AED
         * n2 : ARS
         * r : 4.62097696
         */

        private String n1;
        private String n2;
        private String r;

        public static RateBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), RateBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public String getN1() {
            return n1;
        }

        public void setN1(String n1) {
            this.n1 = n1;
        }

        public String getN2() {
            return n2;
        }

        public void setN2(String n2) {
            this.n2 = n2;
        }

        public String getR() {
            return r;
        }

        public void setR(String r) {
            this.r = r;
        }
    }
}
