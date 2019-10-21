package koreanlearning.hangul.serokorean.utility;

public class NumOfPages {
    private static int CHAPTER_INTRO_NUM_OF_PAGE = 6;
    private static int CHAPTER_ONE_NUM_OF_PAGE = 18;
    private static int CHAPTER_TWO_NUM_OF_PAGE = 10;
    private static int CHAPTER_THREE_NUM_OF_PAGE = 8;
    private static int CHAPTER_FOUR_NUM_OF_PAGE = 5;
    private static int CHAPTER_FIVE_NUM_OF_PAGE = 7;
    private static int CHAPTER_SIX_NUM_OF_PAGE = 12;
    private static int CHAPTER_SEVEN_NUM_OF_PAGE = 7;
    private static int CHAPTER_EIGHT_NUM_OF_PAGE = 5;
    private static int CHAPTER_NINE_NUM_OF_PAGE = 8;
    private static int CHAPTER_TEN_NUM_OF_PAGE = 8;

    public NumOfPages() {
    }

    public static int getChapterIntroNumOfPage() {
        return CHAPTER_INTRO_NUM_OF_PAGE;
    }

    public static void setChapterIntroNumOfPage(int chapterIntroNumOfPage) {
        CHAPTER_INTRO_NUM_OF_PAGE = chapterIntroNumOfPage;
    }

    public static int getChapterOneNumOfPage() {
        return CHAPTER_ONE_NUM_OF_PAGE;
    }

    public static void setChapterOneNumOfPage(int chapterOneNumOfPage) {
        CHAPTER_ONE_NUM_OF_PAGE = chapterOneNumOfPage;
    }

    public static int getChapterTwoNumOfPage() {
        return CHAPTER_TWO_NUM_OF_PAGE;
    }

    public static void setChapterTwoNumOfPage(int chapterTwoNumOfPage) {
        CHAPTER_TWO_NUM_OF_PAGE = chapterTwoNumOfPage;
    }

    public static int getChapterThreeNumOfPage() {
        return CHAPTER_THREE_NUM_OF_PAGE;
    }

    public static void setChapterThreeNumOfPage(int chapterThreeNumOfPage) {
        CHAPTER_THREE_NUM_OF_PAGE = chapterThreeNumOfPage;
    }

    public static int getChapterFourNumOfPage() {
        return CHAPTER_FOUR_NUM_OF_PAGE;
    }

    public static void setChapterFourNumOfPage(int chapterFourNumOfPage) {
        CHAPTER_FOUR_NUM_OF_PAGE = chapterFourNumOfPage;
    }

    public static int getChapterFiveNumOfPage() {
        return CHAPTER_FIVE_NUM_OF_PAGE;
    }

    public static void setChapterFiveNumOfPage(int chapterFiveNumOfPage) {
        CHAPTER_FIVE_NUM_OF_PAGE = chapterFiveNumOfPage;
    }

    public static int getChapterSixNumOfPage() {
        return CHAPTER_SIX_NUM_OF_PAGE;
    }

    public static void setChapterSixNumOfPage(int chapterSixNumOfPage) {
        CHAPTER_SIX_NUM_OF_PAGE = chapterSixNumOfPage;
    }

    public static int getChapterSevenNumOfPage() {
        return CHAPTER_SEVEN_NUM_OF_PAGE;
    }

    public static void setChapterSevenNumOfPage(int chapterSevenNumOfPage) {
        CHAPTER_SEVEN_NUM_OF_PAGE = chapterSevenNumOfPage;
    }

    public static int getChapterEightNumOfPage() {
        return CHAPTER_EIGHT_NUM_OF_PAGE;
    }

    public static void setChapterEightNumOfPage(int chapterEightNumOfPage) {
        CHAPTER_EIGHT_NUM_OF_PAGE = chapterEightNumOfPage;
    }

    public static int getChapterNineNumOfPage() {
        return CHAPTER_NINE_NUM_OF_PAGE;
    }

    public static void setChapterNineNumOfPage(int chapterNineNumOfPage) {
        CHAPTER_NINE_NUM_OF_PAGE = chapterNineNumOfPage;
    }

    public static int getChapterTenNumOfPage() {
        return CHAPTER_TEN_NUM_OF_PAGE;
    }

    public static void setChapterTenNumOfPage(int chapterTenNumOfPage) {
        CHAPTER_TEN_NUM_OF_PAGE = chapterTenNumOfPage;
    }

    public static int detectTheNumberOfPages(String chapter){
        switch (chapter){
            case "1": return NumOfPages.getChapterOneNumOfPage();
            case "2": return NumOfPages.getChapterTwoNumOfPage();
            case "3": return NumOfPages.getChapterThreeNumOfPage();
            case "4": return NumOfPages.getChapterFourNumOfPage();
            case "5": return NumOfPages.getChapterFiveNumOfPage();
            case "6": return NumOfPages.getChapterSixNumOfPage();
            case "7": return NumOfPages.getChapterSevenNumOfPage();
            case "8": return NumOfPages.getChapterEightNumOfPage();
            case "9": return NumOfPages.getChapterNineNumOfPage();
            case "10": return NumOfPages.getChapterTenNumOfPage();
            //TODO: add the number of pages here
            case "11": return 20;
            case "12": return 20;
            case "13": return 20;
            case "14": return 20;
            case "15": return 20;
            case "16": return 20;
            case "17": return 20;
            case "18": return 20;
            case "19": return 20;
            case "20": return 20;
            case "21": return 20;
            case "22": return 20;
            case "23": return 20;
            case "24": return 20;
            case "25": return 20;
            case "26": return 20;
            case "27": return 20;
            case "28": return 20;
            case "29": return 20;
            case "30": return 20;
        }
        return 0;
    }
}
