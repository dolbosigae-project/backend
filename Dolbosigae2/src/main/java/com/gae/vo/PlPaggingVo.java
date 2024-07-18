package com.gae.vo;

public class PlPaggingVo {
		//전체 게시글 개수
		private int count;
		//현재 페이지 번호
		private int currentPage;
		//한 페이지당 출력할 게시글 개수
		private int pageOfContentCount;
		//게시판 하단에 나타낼 페이지 번호 개수
		private final int PAGE_GROUP_OF_COUNT = 5;
		
		public PlPaggingVo(int count, int currentPage, int pageOfContentCount) {
			this.count = count;
			this.currentPage = currentPage;
			this.pageOfContentCount = pageOfContentCount;
		}

		public int getCurrentPage() {
			return currentPage;
		}
		
		public int getPageOfContentCount() {
			return pageOfContentCount;
		}

		//전체 페이지 개수 : 전체 게시글 개수 / 한페이지당 출력할 게시글 개수 + (나머지가 0 아니면 1)
		public int getTotalPage() {
			// 32 / 5 + (32 % 5 == 0 ? 0 : 1);
			return count / pageOfContentCount + (count % pageOfContentCount == 0 ? 0 : 1);
		}
		
		//전체 페이지 그룹 개수 : 전체 페이지 개수 / 게시판 하단에 나타낼 페이지 번호 개수 + (나머지가 0 아니면 1)
		public int getTotalPageGroup() {
			// 7 / 5 + (0 : 1)
			return getTotalPage() / PAGE_GROUP_OF_COUNT + 
					(getTotalPage() % PAGE_GROUP_OF_COUNT == 0 ? 0 : 1);
		}
		
		//현재 페이지의 그룹번호
		public int getCurrentPageGroupNo() {
			// 현재 페이지 / 하단에 보일 페이지 갯수 + (나머지 == 0? 0 : 1);
			return currentPage / PAGE_GROUP_OF_COUNT + 
					(currentPage % PAGE_GROUP_OF_COUNT == 0 ? 0 : 1);
		}
		
		//현재 페이지 그룹의 시작 페이지 번호 
		public int getStartPageOfPageGroup() {
			// (현재 페이지 그룹번호 - 1) * 5 + 1;
			return (getCurrentPageGroupNo()-1) * PAGE_GROUP_OF_COUNT + 1;
		}
		//현재 페이지 그룹의 마지막 페이지 번호 
		public int getEndPageOfPageGroup() {
			//endNo = 현제 페이지 그룹 번호 * 5;
			// if (endNo > 전체 페이지 수)
			//		endNo = 전체 페이지 수;
			
			int lastNo = getCurrentPageGroupNo() * PAGE_GROUP_OF_COUNT;
			if(lastNo > getTotalPage())
				lastNo = getTotalPage();
			return lastNo;
		}
		//이전 페이지 그룹이 있냐?
		public boolean isPriviousPageGroup() {
			return getCurrentPageGroupNo() > 1;
		}
		//다음 페이지 그룹이 있냐?
		public boolean isNextPageGroup() {
			return getCurrentPageGroupNo() <  getTotalPageGroup();
		}

		@Override
		public String toString() {
			return "MemberPaggingVo [count=" + count + ", currentPage=" + currentPage + ", pageOfContentCount="
					+ pageOfContentCount + ", PAGE_GROUP_OF_COUNT=" + PAGE_GROUP_OF_COUNT + "]";
		}
	
}