package com.renee.jin.utile;

public class Paging {
	private int currentPage=1;
	private int pageSize=10;
	private int blockSize=5;
	private int startRow;
	private int endRow;
	private int startPage;
	private int endPage;
	private int total;
	private int pageCnt;
	private Boolean useStartArrow = false;
	private Boolean useEndArrow = false;
	
	public Paging(int total,String pageNum){
        this.total=total;
        if(pageNum!=null){
            currentPage=Integer.parseInt(pageNum);
        }
        // 보여주는 게시물의 시작과 끝을 찾는다 ex) 1page = 1 ~ 10 , 2page = 21 ~ 30 해당은 오라클 
        // ROWNUM >= 1 AND ROWNUM <= 20; 이것때문에 필요 mysql은 limit존재
        //startRow=(currentPage-1)*pageSize+1;
        //endRow=startRow+pageSize-1;
        
        //LIMIT구문 때문에 바꿈 (LIMIT 0 , 10 =>  1 10개 가지고 오란 말임)
        //LIMIT구문 때문에 바꿈 (LIMIT 21 , 10 =>  22번놈부트 10개 가지고 와라)
        startRow=(currentPage-1)*pageSize;
        
        //총 페이지의 카운트를 찾는다 120개의 게시물 , 10개씩 보여줄 때의 카운트 ceil(올림) ex) 123개 = 123/10 = 12.3 => 13개의 총 페이지
        pageCnt = (int)Math.ceil((double)total/pageSize);
        
        // 현재 페이지 기준으로 1 2 3 4 5 의 시작과 끝을  을 찾는다  ex) 1page = 1 ~ 5, 2page = 1~5 , 9page = 6 ~ 10 , 12page = 11 ~ 15
        startPage = currentPage-(currentPage-1)%blockSize;
        endPage=startPage+blockSize-1;
        
        //ex) 시작페이지가  1page : false , 시작페이지가 6page : true(이전 페이지가 있음)
        if( blockSize < startPage ) {
            useStartArrow = true;
        }
        
        //  블럭의 마지막 보다 페이지가 더 있는 경우 화살표 생성
        if(endPage < pageCnt ) {
            useEndArrow = true;
        }
            System.out.println("useEndArrow : "+ useEndArrow);
        // endPage가 15인데 총 페이지 갯수가 13개인경우 => 마지막은 13으로변경
        if( pageCnt < endPage ) {
            endPage=pageCnt;
        }
    }
	
	public Paging(int total,String pageNum, int pageSize, int blockSize ){
		this.total=total;
		this.pageSize = pageSize;
		this.blockSize = blockSize;
		 if(pageNum!=null){
	            currentPage=Integer.parseInt(pageNum);
	        }
	        // 보여주는 게시물의 시작과 끝을 찾는다 ex) 1page = 1 ~ 10 , 2page = 21 ~ 30 해당은 오라클 
	        // ROWNUM >= 1 AND ROWNUM <= 20; 이것때문에 필요 mysql은 limit존재
	        //startRow=(currentPage-1)*pageSize+1;
	        //endRow=startRow+pageSize-1;
	        
	        //LIMIT구문 때문에 바꿈 (LIMIT 0 , 10 =>  1 10개 가지고 오란 말임)
	        //LIMIT구문 때문에 바꿈 (LIMIT 21 , 10 =>  22번놈부트 10개 가지고 와라)
	        startRow=(currentPage-1)*pageSize;
	        
	        //총 페이지의 카운트를 찾는다 120개의 게시물 , 10개씩 보여줄 때의 카운트 ceil(올림) ex) 123개 = 123/10 = 12.3 => 13개의 총 페이지
	        pageCnt = (int)Math.ceil((double)total/pageSize);
	        
	        // 현재 페이지 기준으로 1 2 3 4 5 의 시작과 끝을  을 찾는다  ex) 1page = 1 ~ 5, 2page = 1~5 , 9page = 6 ~ 10 , 12page = 11 ~ 15
	        startPage = currentPage-(currentPage-1)%blockSize;
	        endPage=startPage+blockSize-1;
	        
	        //ex) 시작페이지가  1page : false , 시작페이지가 6page : true(이전 페이지가 있음)
	        if( blockSize < startPage ) {
	            useStartArrow = true;
	        }
	        
	        //  블럭의 마지막 보다 페이지가 더 있는 경우 화살표 생성
	        if(endPage < pageCnt ) {
	            useEndArrow = true;
	        }
	            System.out.println("useEndArrow : "+ useEndArrow);
	        // endPage가 15인데 총 페이지 갯수가 13개인경우 => 마지막은 13으로변경
	        if( pageCnt < endPage ) {
	            endPage=pageCnt;
	        }
	}
	
	public Boolean getUseStartArrow() {
        return useStartArrow;
    }

    public void setUseStartArrow(Boolean useStartArrow) {
        this.useStartArrow = useStartArrow;
    }

    public Boolean getUseEndArrow() {
        return useEndArrow;
    }

    public void setUseEndArrow(Boolean useEndArrow) {
        this.useEndArrow = useEndArrow;
    }
    public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getBlockSize() {
		return blockSize;
	}
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPageCnt() {
		return pageCnt;
	}
	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}
	
}
