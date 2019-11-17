<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta name="title" content="board">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="shortcut icon" href="img/favicon.png">
    <link rel="apple-touch-icon" href="img/favicon.png" sizes="144x144">
	<title>Board</title>
	<link rel="stylesheet" href="css/common.css" type="text/css">
	<script type="text/javascript" src="js/jquery-3-3-1.js"></script>
    <script src="js/common.js"></script>
    <link rel="stylesheet" href="css/board/page.css" type="text/css">
</head>
<body>
<div class="container-sisim">
	<div class="board-upper">
        <select id="show-gride">
        	<option value="5" ${showGrid=="5" ? 'selected' : '' }>5개씩보기</option>
            <option value="10" ${showGrid=="10" ? 'selected' : '' }>10개씩보기</option>
            <option value="20" ${showGrid=="20" ? 'selected' : '' }>20개씩보기</option>
            <option value="30" ${showGrid=="30" ? 'selected' : '' }>30개씩보기</option>
        </select>
        <a href="list.do"><span class="normal-text">심심</span></a>
        <a href="write_view.do?pageNum=${currnetPage}&searchField=${searchField}&searchString=${searchString}" class="button-write" >글쓰기</a>
    </div>
	<div class="board-wrap">
	    <ul>
	        <c:forEach var="board" items="${boadList }">
	            <li ${sessionScope.loginIdCk == board.id || board.id=='admin'  ? 'class="is-my-contents"' :'' }>
	                <div class="detail-area">
	                    <a href="detail.do?seq=${board.seq }&pageNum=${currnetPage}&searchField=${searchField}&searchString=${searchString}" title="user-detail" class="board-title"> ${board.sTitle }
	                    	<c:if test="${board.useFlag == 1}">
						        <img class="lock-icon" src="img/board/lock-1-240.png" alt="my">
						    </c:if>
	                    </a>
	                    <span class="board-user">${board.id }</span><span class="board-date">${board.sJoindate }</span>
	                </div>
	                <div class="view-area">
	                    <span class="board-read-count"><img src="img/main/show.png"> ${board.readCount }</span></a>
	                </div>
	            </li>
	        </c:forEach>
	    </ul>
	</div>
	<div class="board-bottom">
	    <c:if test="${paging.useStartArrow}">
	        <a href="list.do?pageNum=${paging.startPage - 1}&searchField=${searchField}&searchString=${searchString}&showGrid=${showGrid}" class="page-before"></a>
	    </c:if>
	    <c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage }">
	        <c:if test="${currnetPage==i }"> 
	            <span class="isActive"> ${i } </span> 
	        </c:if>
	        <c:if test="${currnetPage != i }">
	            <a href="list.do?pageNum=${i }&searchField=${searchField}&searchString=${searchString}&showGrid=${showGrid}">[${i }]</a>
	        </c:if>
	    </c:forEach>
	    <c:if test="${paging.useEndArrow}">
	        <a href="list.do?pageNum=${paging.endPage + 1}&searchField=${searchField}&searchString=${searchString}&showGrid=${showGrid} "  class="page-after"></a>
	    </c:if>
	    <a href="write_view.do?pageNum=${currnetPage}&searchField=${searchField}&searchString=${searchString}" class="button-write" >글쓰기</a>
	</div>
	<div class="board-bottom-search">
	    <select id="search-field" class="common-select">
	        <option value="sTitle" ${searchField=="sTitle" ? 'selected' : '' }>제목</option>
	        <option value="sTitleContents" ${searchField=="sTitleContents" ? 'selected' : '' }>제목+내용</option>
	        <option value="sWriter" ${searchField=="sWriter" ? 'selected' : '' }>작성자</option>
	    </select>
	    <input type="text" id="search-text" onkeypress="return runScript(event);" class="common-input-txt" value="${searchString }">
	    <input type="button" value="검색" id="search-submit" class="common-button-small">
	</div>
</div>
<script type="text/javascript">
var $showGride = $('#show-gride');
var $searchSubmit = $('#search-submit');
$showGride.on('change',function(e){
    var $target = $(e.currentTarget);
    location.href='list.do?pageNum=1&searchField=${searchField}&searchString=${searchString}&showGrid='+$target.val();
})
$searchSubmit.on('click',function(e){
    searchFn();
})
function searchFn(){
    var $searchField = $('#search-field').val();
    var $searchText = $('#search-text').val();
    location.href='list.do?pageNum=1&searchField='+$searchField+'&searchString='+$searchText+'&showGrid=${showGrid}';
}

</script>
<script src="js/common.js"></script>
<script src="js/board/page.js"></script>
</body>
</html>
