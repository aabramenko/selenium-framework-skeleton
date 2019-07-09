package project.core;

public class RunTimeDataStorage {

    public static class RunningTestStatistic {

        private static int testOrderNumber;

        public static void resetTestOrderNumber() {
            testOrderNumber = 0;
        }

        public static void incrementTestOrderNumber() {
            testOrderNumber ++;
        }

        public static int getTestOrderNumber() {
            return testOrderNumber;
        }
    }


}