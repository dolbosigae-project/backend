package com.gae.vo;

public class PaggingVO {
    private int count; // 총 데이터 수
    private int currentPage; // 현재 페이지
    private int pageOfContentCount; // 페이지 당 콘텐츠 수
    private final int PAGE_GROUP_OF_COUNT = 5; // 페이지 그룹 당 페이지 수

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
        return (int) Math.ceil((double) count / pageOfContentCount); // 총 페이지 수 계산
    }

    public int getTotalPageGroup() {
        return (int) Math.ceil((double) getTotalPages() / PAGE_GROUP_OF_COUNT); // 총 페이지 그룹 수 계산
    }

    public int getCurrentPageGroupNo() {
        return (int) Math.ceil((double) currentPage / PAGE_GROUP_OF_COUNT); // 현재 페이지 그룹 번호 계산
    }

    public int getStartPageOfPageGroup() {
        return (getCurrentPageGroupNo() - 1) * PAGE_GROUP_OF_COUNT + 1; // 현재 페이지 그룹의 시작 페이지
    }

    public int getEndPageOfPageGroup() {
        int lastNo = getCurrentPageGroupNo() * PAGE_GROUP_OF_COUNT;
        if (lastNo > getTotalPages())
            lastNo = getTotalPages();
        return lastNo; // 현재 페이지 그룹의 끝 페이지
    }

    public boolean isPreviousPageGroup() {
        return getCurrentPageGroupNo() > 1; // 이전 페이지 그룹 존재 여부
    }

    public boolean isNextPageGroup() {
        return getCurrentPageGroupNo() < getTotalPageGroup(); // 다음 페이지 그룹 존재 여부
    }

    @Override
    public String toString() {
        return "PaggingVO [count=" + count + ", currentPage=" + currentPage + ", pageOfContentCount="
                + pageOfContentCount + ", PAGE_GROUP_OF_COUNT=" + PAGE_GROUP_OF_COUNT + "]";
    }
}
