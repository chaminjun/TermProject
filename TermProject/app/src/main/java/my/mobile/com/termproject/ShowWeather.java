package my.mobile.com.termproject;

/**
 * Created by chaminjun on 2016. 12. 2..
 */

public class ShowWeather {
    private String hour;  // 시간
    private String day;   // 오늘, 내일, 모레
    private String temp;  // 온도
    private String wfKor; // 상태
    private String pop; // 강수확률


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setWfKor(String wfKor) {
        this.wfKor = wfKor;
    }

    public String getHour() {
        return hour;
    }

    public String getTemp() {
        return temp;
    }

    public String getWfKor() {
        return wfKor;
    }
}

