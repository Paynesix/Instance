package com.spring.security.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {
    private static ThreadLocal<SimpleDateFormat> fullDateTimeSss = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        }
    };
    private static ThreadLocal<SimpleDateFormat> fullDateTime = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static ThreadLocal<SimpleDateFormat> fullDate = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> fullTime = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss");
        }
    };
    private static ThreadLocal<SimpleDateFormat> simpleDateTime = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };
    private static ThreadLocal<SimpleDateFormat> simpleDate = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> simpleTime = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HHmmss");
        }
    };
    private static ThreadLocal<SimpleDateFormat> otherFormatFullDateTime = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }
    };
    private static ThreadLocal<SimpleDateFormat> otherFormatDate = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy/MM/dd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> otherFormatTime = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss");
        }
    };
    private static ThreadLocal<SimpleDateFormat> chineseFullDateTime = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        }
    };
    private static ThreadLocal<SimpleDateFormat> chineseFullDate = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy年MM月dd日");
        }
    };
    private static ThreadLocal<SimpleDateFormat> chineseFullTime = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss");
        }
    };
    private static final String[] WEEK_DAYS = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private static final String[] SIMPLE_WEEK_DAYS = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    public DateTool() {
    }

    public static SimpleDateFormat getFullDateTimeSss() {
        return (SimpleDateFormat)fullDateTimeSss.get();
    }

    public static SimpleDateFormat getFullDateTime() {
        return (SimpleDateFormat)fullDateTime.get();
    }

    public static SimpleDateFormat getFullDate() {
        return (SimpleDateFormat)fullDate.get();
    }

    public static SimpleDateFormat getFullTime() {
        return (SimpleDateFormat)fullTime.get();
    }

    public static SimpleDateFormat getSimpleDateTime() {
        return (SimpleDateFormat)simpleDateTime.get();
    }

    public static SimpleDateFormat getSimpleDate() {
        return (SimpleDateFormat)simpleDate.get();
    }

    public static SimpleDateFormat getSimpleTime() {
        return (SimpleDateFormat)simpleTime.get();
    }

    public static SimpleDateFormat getOtherFormatDateTime() {
        return (SimpleDateFormat)otherFormatFullDateTime.get();
    }

    public static SimpleDateFormat getOtherFormatDate() {
        return (SimpleDateFormat)otherFormatDate.get();
    }

    public static SimpleDateFormat getOtherFormatTime() {
        return (SimpleDateFormat)otherFormatTime.get();
    }

    public static SimpleDateFormat getChinesetDateTime() {
        return (SimpleDateFormat)chineseFullDateTime.get();
    }

    public static SimpleDateFormat getChinesetDate() {
        return (SimpleDateFormat)chineseFullDate.get();
    }

    public static SimpleDateFormat getChineseTime() {
        return (SimpleDateFormat)chineseFullTime.get();
    }

    public static boolean formatTime(String date,String format){
        boolean convertSuccess=true;
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            try {
                dateFormat.setLenient(false);
                dateFormat.parse(date);
            } catch (ParseException e) {
                convertSuccess=false;
            }
            return convertSuccess;
    }
    public static String getWeekName(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int num = c.get(7) - 1;
            if (num < 0) {
                num = 0;
            }

            return WEEK_DAYS[num];
        }
    }

    public static Integer getMonthDay(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(5);
        }
    }

    public static boolean isToday(Date date) {
        return date == null ? false : getSimpleDate().format(new Date()).equals(getSimpleDate().format(date));
    }

    public static Date getNextDate(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(5, n);
        return c.getTime();
    }

    public static Date getNextDateZero(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (n != 0) {
            cal.add(5, n);
        }

        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getNextHour(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(10, n);
        return c.getTime();
    }

    public static Date getNextMini(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(12, n);
        return c.getTime();
    }

    public static Date getNextSecond(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(13, n);
        return c.getTime();
    }

    /**
     * 
      * @Title: getMinutesInterval
      * @Description: 获取分钟间隔
      * @author dingqi
      * @date 2016年8月27日上午1:18:01
      * @param startDate
      * @param endDate
      * @return Long   
      * @throws
     */
    public static Long getMinutesInterval(Date startDate, Date endDate) {
            return ((endDate.getTime() - startDate.getTime()) / 1000/ 60);
    }
}
