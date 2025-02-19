package com.github.evseevda;

public class DemoClasses {

    static class Human {
        public static boolean isGoodMood() {
            return true;
        }
    }

    static class Bank {

        public static boolean userIsNotLocked() {
            return true;
        }

        public static int balance() {
            return 100;
        }

        public static int amountNeeded() {
            return 99;
        }

    }

    static class Calendar {

        public static boolean isHoliday() {
            return false;
        }

        public static boolean isFebruary() {
            return true;
        }

        public static boolean isFavouriteMonth() {
            return true;
        }

    }

    static class Weather {

        public static boolean isWinter() {
            return true;
        }

        public static boolean isSunny() {
            return false;
        }

        public static boolean isCold() {
            return true;
        }

    }

}
