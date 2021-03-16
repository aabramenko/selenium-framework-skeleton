package selenium.core;

import java.util.ArrayList;

public class RunTimeDataStorage {

    public static class CasesStatistics {

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

    public static class PagesLoadStatistics {

        static ArrayList<String[]> pageUploadingTimeArray;

        public static void initiatePageUploadingTimeArrayList() {
            pageUploadingTimeArray = new ArrayList();
        }

        public static ArrayList getPageUploadingTimeArray() {
            return pageUploadingTimeArray;
        }

        public static void setResult(String page, String thread, String timeSec) {
            String[] result = {page, thread, timeSec};
            pageUploadingTimeArray.add(result);
        }
    }
}