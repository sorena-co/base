package ir.sp.base.service.util;

import ir.sp.base.domain.enumeration.Day;

public class Utils {
    public static String getDayFA(Day day) {
        switch (day) {
            case SATURDAY: {
                return "شنبه";
            }
            case SUNDAY: {
                return "یک شنبه";
            }
            case MONDAY: {
                return "دو شنبه";
            }
            case TUESDAY: {
                return "سه شنبه";
            }
            case WEDNESDAY: {
                return "چهارشنبه";
            }
            case THURSDAY: {
                return "پنج شنبه";
            }
            case FRIDAY: {
                return "جمعه";
            }
            default: {
                return null;
            }
        }
    }
}
