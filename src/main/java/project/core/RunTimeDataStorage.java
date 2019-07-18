package project.core;

public class RunTimeDataStorage {

    public static class Statistic {

        private static int caseOrderNumber;

        public static void resetCaseOrderNumber() {
            caseOrderNumber = 0;
        }

        public static void incrementCaseOrderNumber() {
            caseOrderNumber++;
        }

        public static int getCaseOrderNumber() {
            return caseOrderNumber;
        }
    }

}