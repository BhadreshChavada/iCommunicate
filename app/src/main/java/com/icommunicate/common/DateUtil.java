package com.icommunicate.common;


import android.util.Log;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    private static Date date = new Date();
    private static final DateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat recentFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private static final DateFormat recentFormatDate = new SimpleDateFormat("dd-MM-yyyy");
    private static final DateFormat recentFormatTime = new SimpleDateFormat("HH:mm a");
    private static final DateFormat activityLogDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
    private static final DateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat orderDateFormat = new SimpleDateFormat("yyyy MMM dd, EEEE");
    private static final DateFormat orderDayFormat = new SimpleDateFormat("dd");
    private static final DateFormat historyDateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
    private static final DateFormat searchMaidDateFormat = new SimpleDateFormat("yyyy/MM/dd EEEE");
    private static final DateFormat futureOrderDateFormat = new SimpleDateFormat("dd MMMM yyyy");
    private static final DateFormat maidInfoDateFormat = new SimpleDateFormat("MMM dd, EEEE");
    private static final DateFormat userInfoDateFormat = new SimpleDateFormat("MMM dd, EEEE");
    private static final DateFormat likesDateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    private static final DateFormat chatHeaderFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final DateFormat lastConnectedFormat = new SimpleDateFormat("MM/dd/yyyy");
    private static final DateFormat taskDateFormat = new SimpleDateFormat("E, MMM dd, yyyy");
    private static final DateFormat chatHeaderFormatsasdasdasdasd = new SimpleDateFormat("MM/dd/yyyy");

    private static final DateFormat taskDateFormatnew = new SimpleDateFormat("d MMM yyyy");

    public static void setApiTimeZone() {
//        apiDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        apiDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
    }

    public static String getApiDate(Date date) {
        return apiDateFormat.format(date);
    }

    public static String getFormattedOrderDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = dateFormat.parse(date);
            return orderDateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return orderDateFormat.format(date);
        }
    }

    public static String getFormattedOrderDay(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = dateFormat.parse(date);
            return orderDayFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return orderDayFormat.format(date);
        }
    }

    public static String setUpDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.000000");

        try {
            Date date1 = dateFormat.parse(date);

            return recentFormatDate.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return recentFormatDate.format(date);
        }
    }

    public static long setDateSent(String dateSent) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.000000");
        dateFormat.setTimeZone(TimeZone.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = dateFormat.parse(dateSent);
            calendar = dateToCalendar(date);

         } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis()/1000L;

    }

    //Convert Date to Calendar
    public static Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    public static String setUpTime(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.000000");

        try {
            Date date1 = dateFormat.parse(date);
            return recentFormatTime.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return recentFormatTime.format(date);
        }
    }


    public static String getHistoryOrderDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = dateFormat.parse(date);
            return historyDateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return historyDateFormat.format(date);
        }
    }

    public static String getShortDate(Date date) {
        return shortDateFormat.format(date);
    }

    public static Date getDateFromApi(String apiDate) {
        try {
            return apiDateFormat.parse(apiDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static boolean isDateEquals(String sDate1, String sDate2) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        Format formatter = new SimpleDateFormat("MM/yyyy");

        try {
            Date date1 = dateFormat.parse(sDate1);
            Date date2 = dateFormat.parse(sDate2);

            sDate1 = formatter.format(date1);
            sDate2 = formatter.format(date2);

            if (sDate1.equals(sDate2)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isCalendarOrderDateEquals(String sDate1, String sDate2) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
//        Format formatter = new SimpleDateFormat("MM/yyyy");

        try {
            Date date1 = dateFormat.parse(sDate1);
            Date date2 = dateFormat.parse(sDate2);

            sDate1 = formatter.format(date1);
            sDate2 = formatter.format(date2);

            if (sDate1.equals(sDate2)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isDateIsGreaterThanToday(String sDate1, String sDate2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = dateFormat.parse(sDate1);
            Date date2 = dateFormat.parse(sDate2);

            if (date1.after(date2)) {
                // In between
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isDateIsGreaterThanTomorrow(String sDate1, String sDate2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(sDate2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Incrementing the date by 1 day
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date date2 = c.getTime();
        try {
            Date date1 = dateFormat.parse(sDate1);
            if (date1.after(date2)) {
                // In between
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isDateIsGreaterThanOrEqualToToday(String sDate1, String sDate2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = dateFormat.parse(sDate1);
            Date date2 = dateFormat.parse(sDate2);

            if (date1.equals(date2)
                    || date1.after(date2)) {
                // In between
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String subtractDate(String sDate1, String sDate2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date1 = dateFormat.parse(sDate1);
            Date date2 = dateFormat.parse(sDate2);

            //milliseconds
            long different = date2.getTime() - date1.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            return String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static Date getOrderDateTime(String date) throws ParseException {
        SimpleDateFormat dateFormatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormatOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        dateFormatIn.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
        dateFormatOut.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = dateFormatIn.parse(date);
            endDate = dateFormatOut.parse(dateFormatOut.format(beginDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endDate;
    }

    public static Date getOrderTime(String date) throws ParseException {
        SimpleDateFormat dateFormatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormatOut = new SimpleDateFormat("HH:mm");

        dateFormatIn.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
        dateFormatOut.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = dateFormatIn.parse(date);
            endDate = dateFormatOut.parse(dateFormatOut.format(beginDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endDate;
    }


    public static Date getOrderDate(String date) throws ParseException {
        SimpleDateFormat dateFormatIn = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatOut = new SimpleDateFormat("yyyy-MM-dd");

        dateFormatIn.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
        dateFormatOut.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = dateFormatIn.parse(date);
            endDate = dateFormatOut.parse(dateFormatOut.format(beginDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endDate;
    }

    public static Date getAPIFormattedCurrentDateTime() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date newDate = null;
        try {
            newDate = dateFormat.parse(dateFormat.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    public static Date getAPIFormattedCurrentDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date newDate = null;
        try {
            newDate = dateFormat.parse(dateFormat.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    public static String getShortCurrentDate() {
        try {
            Date date = new Date();
            return shortDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return shortDateFormat.format(date);
        }
    }


    public static String getSearchMaidOrderDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = dateFormat.parse(date);
            return searchMaidDateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return searchMaidDateFormat.format(date);
        }
    }

    public static String getFormattedForNote(long dates) {
        String tempDate = "";
        if (dates > 5) {
// Log.e("getFormattedForNote()", "Server Date " + dates);

            Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getDefault());
            c.setTimeInMillis(dates * 1000L);
            try {
                Date d = c.getTime();
                tempDate = DateUtil.futureOrderDateFormat.format(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tempDate;
    }

    public static String getHeaderDateFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date1 = dateFormat.parse(date);
            return futureOrderDateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return futureOrderDateFormat.format(date);
        }
    }

    public static String getMaidInfoCurrentDate() {
        try {
            Date date = new Date();
            return maidInfoDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return maidInfoDateFormat.format(date);
        }
    }

    public static String getUserInfoCurrentDate() {
        try {
            Date date = new Date();
            return userInfoDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return userInfoDateFormat.format(date);
        }
    }

    public static String getChatDisplayDate(String msgDate) {
        try {
            Date date = apiDateFormat.parse(msgDate);
            return likesDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return likesDateFormat.format(date);
        }
    }

    public static String getTimeFromDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Format formatter = new SimpleDateFormat("HH:mm");

        try {
            Date date1 = dateFormat.parse(date);
            date = formatter.format(date1);

            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static Date getOrderDateWithCurrentDate(String date) throws ParseException {
        SimpleDateFormat dateFormatIn = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = dateFormatIn.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return beginDate;
    }

    public static String getChatHeader(long time) {
        Date date = new Date(time);
        return chatHeaderFormat.format(date);
    }

    public static String getTaskDate(long dates) {
        String tempDate = "";
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getDefault());
        c.setTimeInMillis(dates * 1000L);
        try {
            Date d = c.getTime();
            tempDate = DateUtil.taskDateFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tempDate;
    }

    public static boolean checktimings(String time, String endtime) {

        String pattern = "HH:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if (date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String CompareTime(String string1, String string2) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        int dtHour;
        int dtMin;
        int iAMPM;
        String strAMorPM = null;
        Date dtCurrentDate;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        try {
            Date date1 = sdf.parse(string1);
            Date date2 = sdf.parse(string2);
            dtMin = cal.get(Calendar.MINUTE);
            dtHour = cal.get(Calendar.HOUR);
            iAMPM = cal.get(Calendar.AM_PM);
            if (iAMPM == 1) {
                strAMorPM = "PM";
            }
            if (iAMPM == 0) {
                strAMorPM = "AM";
            }
            dtCurrentDate = sdf.parse(dtHour + ":" + dtMin + " " + strAMorPM);
            if (dtCurrentDate.after(date2)) {
                Log.e("app", "Date1, Date2 karta agad chhe");
                return "1";
            }
            if (dtCurrentDate.before(date2)) {
                Log.e("app", "Date1, Date2 karta pa6ad chhe");
                return "2";
            }
            if (dtCurrentDate.equals(date2)) {
                return "3";
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "4";
    }


    public static String leadDate(long dates) {
        String tempDate = "";
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getDefault());
        c.setTimeInMillis(dates);
        try {
            Date d = c.getTime();
            tempDate = DateUtil.taskDateFormatnew.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempDate;
    }

    public static String leadCreatedDateFormate(long dates) {
        try {
            Calendar cal = Calendar.getInstance();
            int length = String.valueOf(dates).length();
            if (length == 10) {
                cal.setTimeInMillis(dates * 1000L);
            } else {
                cal.setTimeInMillis(dates);
            }
            Date date = cal.getTime();
            int day = cal.get(Calendar.DATE);

            return new SimpleDateFormat("dd MMM yyyy").format(date);

          /*  if (!((day > 10) && (day < 19))){
                switch (day % 10) {
                    case 1:
                        return new SimpleDateFormat("d'st' MMM yyyy").format(date);
                    case 2:
                        return new SimpleDateFormat("d'nd' MMM yyyy").format(date);
                    case 3:
                        return new SimpleDateFormat("d'rd' MMM yyyy").format(date);
                    default:
                        return new SimpleDateFormat("d'th' MMM yyyy").format(date);
                }
            }*/


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("d'th' MMM yyyy").format(date);
    }

    public static String parseDateSelection(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

        try {
            Date date1 = dateFormat.parse(date);
            return chatHeaderFormatsasdasdasdasd.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String lastConnected(long dates) {
        String tempDate = "";
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getDefault());
        c.setTimeInMillis(dates * 1000L);
        try {
            Date d = c.getTime();
            tempDate = DateUtil.lastConnectedFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tempDate;
    }

    public static String dynamicDateView(long dates) {
        String tempDate = "";
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getDefault());
        c.setTimeInMillis(dates * 1000L);
        try {
            Date d = c.getTime();
            tempDate = DateUtil.shortDateFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tempDate;
    }

    public static String activityLogDate(Calendar calendar) {
        String tempDate = "";
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.setTimeZone(TimeZone.getDefault());
        try {
            Date d = calendar.getTime();
            tempDate = DateUtil.activityLogDateFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tempDate;
    }


}