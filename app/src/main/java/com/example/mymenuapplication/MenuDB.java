package com.example.mymenuapplication;


import io.realm.RealmObject;
import io.realm.annotations.Required;

public class MenuDB extends RealmObject {

    private long id;
    @Required
    private String strDate;
    private String strTime;
    private String strMenuNumber;
    @Required
    private String strMenuTitle;
    @Required
    private String strItem1;
    private String strItem2;
    private String strItem3;
    private String strItem4;
    private String strItem5;
    private String strItem6;
    private String strItem7;

    private int intPrice1;
    private int intPrice2;
    private int intPrice3;
    private int intPrice4;
    private int intPrice5;
    private int intPrice6;
    private int intPrice7;

    private int intTotalPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getStrMenuNumber() {
        return strMenuNumber;
    }

    public void setStrMenuNumber(String strMenuNumber) {
        this.strMenuNumber = strMenuNumber;
    }

    public String getStrMenuTitle() {
        return strMenuTitle;
    }

    public void setStrMenuTitle(String strMenuTitle) {
        this.strMenuTitle = strMenuTitle;
    }

    public String getStrItem1() {
        return strItem1;
    }

    public void setStrItem1(String strItem1) {
        this.strItem1 = strItem1;
    }

    public String getStrItem2() {
        return strItem2;
    }

    public void setStrItem2(String strItem2) {
        this.strItem2 = strItem2;
    }

    public String getStrItem3() {
        return strItem3;
    }

    public void setStrItem3(String strItem3) {
        this.strItem3 = strItem3;
    }

    public String getStrItem4() {
        return strItem4;
    }

    public void setStrItem4(String strItem4) {
        this.strItem4 = strItem4;
    }

    public String getStrItem5() {
        return strItem5;
    }

    public void setStrItem5(String strItem5) {
        this.strItem5 = strItem5;
    }

    public String getStrItem6() {
        return strItem6;
    }

    public void setStrItem6(String strItem6) {
        this.strItem6 = strItem6;
    }

    public String getStrItem7() {
        return strItem7;
    }

    public void setStrItem7(String strItem7) {
        this.strItem7 = strItem7;
    }

    public int getIntPrice1() {
        return intPrice1;
    }

    public void setIntPrice1(int intPrice1) {
        this.intPrice1 = intPrice1;
    }

    public int getIntPrice2() {
        return intPrice2;
    }

    public void setIntPrice2(int intPrice2) {
        this.intPrice2 = intPrice2;
    }

    public int getIntPrice3() {
        return intPrice3;
    }

    public void setIntPrice3(int intPrice3) {
        this.intPrice3 = intPrice3;
    }

    public int getIntPrice4() {
        return intPrice4;
    }

    public void setIntPrice4(int intPrice4) {
        this.intPrice4 = intPrice4;
    }

    public int getIntPrice5() {
        return intPrice5;
    }

    public void setIntPrice5(int intPrice5) {
        this.intPrice5 = intPrice5;
    }

    public int getIntPrice6() {
        return intPrice6;
    }

    public void setIntPrice6(int intPrice6) {
        this.intPrice6 = intPrice6;
    }

    public int getIntPrice7() {
        return intPrice7;
    }

    public void setIntPrice7(int intPrice7) {
        this.intPrice7 = intPrice7;
    }

    public int getIntTotalPrice() {
        return intTotalPrice;
    }

    public void setIntTotalPrice(int intTotalPrice) {
        this.intTotalPrice = intTotalPrice;
    }
}
