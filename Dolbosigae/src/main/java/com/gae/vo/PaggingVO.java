package com.gae.vo;

public class PaggingVO {
    private int count;
    private int currentPage;
    private int pageOfContentCount;
    private final int PAGE_GROUP_OF_COUNT = 5;

    public PaggingVO(int count, int currentPage, int pageOfContentCount) {
        this.count = count;
        this.currentPage = currentPage;
        this.pageOfContentCount = pageOfContentCount;
    }

    public int getCount() {
        return count;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageOfContentCount() {
        return pageOfContentCount;
    }

    public int getTotalPages() {
        return count / pageOfContentCount + (count % pageOfContentCount == 0 ? 0 : 1);
    }

    public int getTotalPageGroup() {
        return getTotalPages() / PAGE_GROUP_OF_COUNT +
                (getTotalPages() % PAGE_GROUP_OF_COUNT == 0 ? 0 : 1);
    }

    public int getCurrentPageGroupNo() {
        return currentPage / PAGE_GROUP_OF_COUNT +
                (currentPage % PAGE_GROUP_OF_COUNT == 0 ? 0 : 1);
    }

    public int getStartPageOfPageGroup() {
        return (getCurrentPageGroupNo() - 1) * PAGE_GROUP_OF_COUNT + 1;
    }

    public int getEndPageOfPageGroup() {
        int lastNo = getCurrentPageGroupNo() * PAGE_GROUP_OF_COUNT;
        if (lastNo > getTotalPages())
            lastNo = getTotalPages();
        return lastNo;
    }

    public boolean isPreviousPageGroup() {
        return getCurrentPageGroupNo() > 1;
    }

    public boolean isNextPageGroup() {
        return getCurrentPageGroupNo() < getTotalPageGroup();
    }

    @Override
    public String toString() {
        return "PaggingVO [count=" + count + ", currentPage=" + currentPage + ", pageOfContentCount="
                + pageOfContentCount + ", PAGE_GROUP_OF_COUNT=" + PAGE_GROUP_OF_COUNT + "]";
    }
}






