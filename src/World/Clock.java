package World;

import java.util.Timer;

import java.text.DecimalFormat;
import testing.displayConfig;

public class Clock {

    private int hour;
    private int minute;

    public Clock() {
        hour = 0;
        minute = -10;
    }

    //TODO: Add customFormat to hint
    static public String customFormat(String pattern, double value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return output;
    }

    public String getTime() {
        String time = customFormat("000", hour) + ":" + customFormat("00", minute);
        displayConfig.setTime(time);
        return time;
    }

    public int getMinute() {
        return minute;
    }

    public int getTimeRaw() {
        return hour * 60 + minute;
    }

    public void increase() {

        minute += 10;
        hour += minute / 60;
        minute %= 60;

    }
}
