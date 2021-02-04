package com.spring.security.tools;

/**
 * @author wxy
 * @Title: 时间转化工具
 * @Description: 秒转化为时分秒的格式
 * @date 2020.12.23 10:42
 **/

public class TimeConvert {
    public static VideoDuration secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return new VideoDuration(0, 0, 0);
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return new VideoDuration(59, 59, 99);
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return new VideoDuration(Integer.parseInt(unitFormat(second)), Integer.parseInt(unitFormat(minute)), Integer.parseInt(unitFormat(hour)));
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + i;
        else
            retStr = "" + i;
        return retStr;
    }

    public static class VideoDuration {

        /**
         * 视频时长秒
         */
        private Integer second;

        /**
         * 视频时长分
         */
        private Integer minute;

        /**
         * 视频时长时
         */
        private Integer hour;

        public VideoDuration(int second, int minute, int hour) {
            this.second = second;
            this.minute = minute;
            this.hour = hour;
        }

        public Integer getSecond() {
            return second;
        }

        public void setSecond(Integer second) {
            this.second = second;
        }

        public Integer getMinute() {
            return minute;
        }

        public void setMinute(Integer minute) {
            this.minute = minute;
        }

        public Integer getHour() {
            return hour;
        }

        public void setHour(Integer hour) {
            this.hour = hour;
        }

        @Override
        public String toString() {
            return hour + "小时" + minute + "分钟" + second + "秒";
        }
    }
}
