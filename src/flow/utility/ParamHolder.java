package flow.utility;

public class ParamHolder {


    private static int pageNum = 3;
    private static int threadNum = 7;
    private static int currentPageNum = 1;
    private static String host = "stackoverflow"      ;

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        ParamHolder.host = host;
    }

    public static int getCurrentPageNum() {
        return currentPageNum;
    }

    public static void setCurrentPageNum(int currentPageNum) {
        ParamHolder.currentPageNum = currentPageNum;
    }

    public static int getPageNum() {
        return pageNum;

    }

    public static void setPageNum(int pageNum) {
        ParamHolder.pageNum = pageNum;
    }

    public static int getThreadNum() {
        return threadNum;
    }

    public static void setThreadNum(int threadNum) {
        ParamHolder.threadNum = threadNum;
    }





}
