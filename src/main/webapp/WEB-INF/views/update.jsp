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
	<script src="js/editor/lib/plantuml-encoder/plantuml-encoder.js"></script>
	<script src="js/editor/lib/raphael/raphael.js"></script>
	<script src="js/editor/lib/tui-chart/tui-chart.js"></script>
	<script src="js/editor/lib/tui-color-picker/tui-color-picker.js"></script>
	
	<script src="js/editor/lib/markdown-it/markdown-it.js"></script>
	<script src="js/editor/lib/to-mark/to-mark.js"></script>
	
	<script src="js/editor/lib/codemirror/codemirror.js"></script>
	<script src="js/editor/lib/highlightjs/highlight.pack.js"></script>
	<script src="js/editor/lib/squire/squire-raw.js"></script>
	<link rel="stylesheet" href="js/editor/lib/codemirror/codemirror.css">
	<link rel="stylesheet" href="js/editor/lib/highlightjs/github.css">
	
	<script src="js/editor/dist/tui-editor-Editor-all.js"></script>
	<script src="js/editor/dist/tui-editor-extScrollSync.js"></script>
	<script src="js/editor/dist/tui-editor-extTable.js"></script>
	<script src="js/editor/dist/tui-editor-extColorSyntax.js"></script>
	<!-- <script src="js/editor/dist/tui-file-uploader.js"></script> -->
	
	<link rel="stylesheet" href="js/editor/dist/tui-editor.css">
	<link rel="stylesheet" href="js/editor/dist/tui-editor-contents.css">
	<link rel="stylesheet" href="js/editor/dist/tui-file-uploader.css">
	<link rel="stylesheet" href="js/editor/lib/tui-color-picker/tui-color-picker.css">
	<link rel="stylesheet" href="js/editor/lib/tui-chart/tui-chart.css">
</head>
<body>
<div class="container-sisim-write">
	<div class="board-detail-upper">    
	    <h2><a href="list.do?pageNum=${param.pageNum}&searchField=${param.searchField}&searchString=${param.searchString}" title="Renee">게시판으로 이동</a></h2>
	</div>
	<form action="update_view.do" method="post" id="write-form">
		<ul class="write-wrap">
            <li>
            	<input type="hidden" class="text-field" name="seq" value="${param.seq }">
            	<input type="hidden" class="text-field" name="pageNum" value="${param.pageNum }">
            	<input type="hidden" class="text-field" name="searchField" value="${param.searchField }">
            	<input type="hidden" class="text-field" name="searchString" value="${param.searchString }">
                <label for="first_password" class="label">비밀번호를 입력하세요</label>
                <input id="first_password" type="text" class="text-field" name="pw">
            </li>
        </ul>
        <input type="button" value="확인" class="btn-from-sbmit" />
	</form>
</div>
<script type="text/javascript">
var pwFlag = false;
var titleFlag = true;
var idFlag = true;
$(document).ready(function() {
	 var $body   = $('body');
	 if( !$('#first_password').val() ){
		 $('.label').removeClass('active');
	 }
	$('.container-sisim-write').on( 'blur', '#first_password', checkPassWrod );
});

function checkPassWrod (e){
	pwFlag = false;
    var $target = e.currentTarget != undefined ? $(e.currentTarget) : e;
    var $wraaper = $target.closest('li').find('label');
    var value = $target.val();
    var mgs = '';
    $wraaper.removeClass('error');
    $wraaper.text('비밀번호를 입력하세요');
    
    if(value == ''){
    	$wraaper.addClass('error');
    	$wraaper.removeClass('active');
        return false;
    } 
    
    /* 공백 확인 */
    if ( value.replace(/\s/g , '').length < 1 ) {
    	$wraaper.addClass('error');
    	$wraaper.removeClass('active');
    	$wraaper.text('비밀번호의 공백만 사용은 불가능합니다. 비밀번호를 입력하세요');
    	$target.val('');
        return false;
    }
    
    pwFlag = true;
    return true;
}

</script>
<script src="js/common.js"></script>
<script src="js/board/page.js"></script>
</body>
</html>
