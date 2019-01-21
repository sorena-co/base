package ir.sp.base.service.util;

import ir.sp.base.domain.enumeration.Day;

public class Utils {
    public static String getDayFA(int day) {
        switch (day) {
            case 0: {
                return "شنبه";
            }
            case 1: {
                return "یک شنبه";
            }
            case 2: {
                return "دو شنبه";
            }
            case 3: {
                return "سه شنبه";
            }
            case 4: {
                return "چهارشنبه";
            }
            case 5: {
                return "پنج شنبه";
            }
            case 6: {
                return "جمعه";
            }
            default: {
                return null;
            }
        }
    }

    public static Integer getDayNumber(Day day) {
        switch (day) {
            case SATURDAY:
                return 0;
            case SUNDAY:
                return 1;
            case MONDAY:
                return 2;
            case TUESDAY:
                return 3;
            case WEDNESDAY:
                return 4;
            case THURSDAY:
                return 5;
            case FRIDAY:
                return 6;
            default:
                return null;
        }
    }

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
