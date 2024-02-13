package model;

public class TimeStamp {
    private Integer hour;
    private Integer minute;
    private Integer second;
    private Integer millisecond;


    public TimeStamp(Integer hour, Integer minute, Integer second, Integer millisecond) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millisecond = millisecond;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(Integer millisecond) {
        this.millisecond = millisecond;
    }

    @Override
    public String toString() {
        return "TimeStamp{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                ", millisecond=" + millisecond +
                '}';
    }
}
