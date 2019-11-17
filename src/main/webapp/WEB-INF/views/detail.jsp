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
    
    <script src="js/editor/lib/tui-code-snippet/tui-code-snippet.js"></script>
	<script src="js/editor/lib/markdown-it/markdown-it.js"></script>
	<script src="js/editor/lib/highlightjs/highlight.pack.js"></script>
	
	
	<link rel="stylesheet" href="js/editor/lib/codemirror/codemirror.css">
	<link rel="stylesheet" href="js/editor/lib/highlightjs/github.css">
	<link rel="stylesheet" href="js/editor/dist/tui-editor-contents.css">
	<script src="js/editor/dist/tui-editor-Viewer.js"></script>
</head>
<body>
<div class="container-sisim-detail">
	<div class="board-detail-upper">    
	    <h2><a href="list.do?pageNum=${param.pageNum}&searchField=${param.searchField}&searchString=${param.searchString}" title="Renee">게시판으로 이동</a></h2>
	</div>
	<div class="board-detail-content">
	    <div class="board-title-area">
	        <div class="title-content">
	            <p class="title">${detail.sTitle }</p>
	        </div>
	        <div class="view-content">
	            <span class="writer"><a href="#">[ ${detail.id } ]</a> </span>
	            <span class="view">${detail.sJoinUpdate } | 조회 ${detail.readCount }</span>
	        </div>
	        <div class="files-downLoad">
	             <c:forEach var="filenames" items="${filenames }">
	                <a href="download.do?seq=${detail.seq }&name=${filenames }" class="down-load-files" target="_blank"><img src="img/board/fileDown.png">${filenames }</a>
	            </c:forEach>
	            
	        </div>
	    </div>
	    <div class="board-detail-area">
	        <div id="editSectionView"></div>
	        <div class="share">
                <a href="list.do?pageNum=${param.pageNum}&searchField=${param.searchField}&searchString=${param.searchString}"><img src="img/main/favorite.png"> 게시판으로 이동</a>
                <a href="update.do?seq=${detail.seq }&pageNum=${param.pageNum}&searchField=${param.searchField}&searchString=${param.searchString}"><img src="img/board/update.png"></a>
                <a href="delete.do?seq=${detail.seq }&pageNum=${param.pageNum}&searchField=${param.searchField}&searchString=${param.searchString}" class="board-remove"><img src="img/board/remove.png"></a>
            </div>
	    </div>
	</div>
	<div class="board-detail-area">
    	<div id="editSectionView"></div>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
    var contents = "${detail.sContent }".replace(/<br>/g,'\n');
    $('#editSectionView').tuiEditor({
        height: '300px',
        initialValue: contents
    });
});
</script>
<script src="js/common.js"></script>
<script src="js/board/page.js"></script>
</body>
</html>
