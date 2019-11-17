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
	<form action="write.do" method="post" enctype="multipart/form-data" id="write-form">
		<input type="hidden" name="sContent" id="sContent" />
		<input type="hidden" name="pageNum"  id="pageNum" value="${param.pageNum }" />
        <input type="hidden" name="searchField"  id="searchField" value="${param.searchField }" />
        <input type="hidden" name="searchString"  id="searchString" value="${param.searchString }" />
		<ul class="write-wrap">
            <li>
                <label for="first_title" class="label">제목을 입력하세요!</label>
                <input id="first_title" type="text" class="text-field" name="sTitle">
            </li>
            <li>
                <label for="first_id" class="label">아이디를 입력하세요</label>
                <input id="first_id" type="text" class="text-field" name="id">
            </li>
            <li>
                <label for="first_password" class="label">비밀번호를 입력하세요</label>
                <input id="first_password" type="password" class="text-field" name="pw">
            </li>
            <li>
	            <div class="input_row" id="chek_area">
	                <span class="input_box">
	                    <label for="agree-info">
	                        <input type="checkbox" id="agree-info" name="useFlag" value="1" aria-describedby="err_empty_chek" class="focus-move">
	                        <span class="check-btn"></span>비밀글로 하시겠습니까?
	                    </label>
	                </span>
	            </div>
            </li>
            <li>
                <div id="uploader" class="tui-file-uploader">
                    <input multiple="multiple" type="file" name="userfile[]" class="tui-input-file-real hide">
                    <div class="tui-file-uploader-header">
                        <span class="tui-file-upload-msg">파일을 첨부하세요</span>
                        <div class="tui-btn-area">
                            <button type="button" class="tui-btn tui-btn-cancel tui-is-disabled">Remove</button>
                            <label class="tui-btn tui-btn-upload">
                                <span class="tui-btn-txt">Add files</span>
                                <input multiple="multiple" type="file" name="tempFiles[]" class="tui-input-file">
                            </label>
                        </div>
                    </div>
                    <div class="tui-js-file-uploader-list hide tui-js-file-uploader-dropzone tui-file-uploader-area tui-has-scroll">
                        <div class="tui-dropzone-contents">
                            <span class="tui-dropzone-msg">Drop files here.</span>
                        </div>
                        <table class="tui-file-uploader-tbl">
                            <caption><span>File Uploader List</span></caption>
                            <colgroup><col width="0"><col width="0"><col width="0">
                            </colgroup>
                            <thead class="tui-form-header">
                                <tr>
                                    <th scope="col" width="0" style="border-right: 0px;">
                                        <div class="tui-checkbox">
                                            <span class="tui-ico-check">
                                                <input type="checkbox">
                                            </span>
                                        </div>
                                    </th>
                                    <th scope="col" width="0">File Name</th>
                                    <th scope="col" width="0" style="border-right: 0px;">File Size</th>
                                </tr>
                            </thead>
                            <tbody class="tui-form-body tui-js-file-uploader-list-items"></tbody>
                        </table>
                    </div>
                    <div class="tui-file-uploader-info">
                        <span class="tui-info-txt">Selected <em class="tui-spec"><span id="checkedItemCount">0</span> files</em> (<span id="checkedItemSize">0 KB</span>)</span>
                        <span class="tui-info-txt">Total <span id="itemTotalSize">0 KB</span></span>
                    </div>
                </div>
            </li>
            <!-- 파일첨부 -->
            <li>
                <div><div id="editSection"></div></div>
            </li>
        </ul>
        <input type="button" value="글쓰기" class="btn-from-sbmit" />
	</form>
</div>
<script type="text/javascript">
var titleFlag = false;
var idFlag = false;
var pwFlag = false;

$(document).ready(function() {
	 var $body   = $('body');
	$('.container-sisim-write').on( 'blur', '#first_title', checkTitle );
	$('.container-sisim-write').on( 'blur', '#first_id', checkId );
	$('.container-sisim-write').on( 'blur', '#first_password', checkPassWrod );
});

function checkTitle (e){
	titleFlag = false;
    var $target = e.currentTarget != undefined ? $(e.currentTarget) : e;
    var $wraaper = $target.closest('li').find('label');
    var value = $target.val();
    var mgs = '';
    $wraaper.removeClass('error');
    $wraaper.text('제목을 입력하세요');
    
    if(value == ''){
    	$wraaper.addClass('error');
    	$wraaper.removeClass('active');
        return false;
    } 
    
    /* 공백 확인 */
    if ( value.replace(/\s/g , '').length < 1 ) {
    	$wraaper.addClass('error');
    	$wraaper.removeClass('active');
    	$wraaper.text('제목에 공백만 사용은 불가능 합니다. 제목을 입력하세요');
        return false;
    }
    
    titleFlag = true;
    return true;
}

function checkId (e){
	idFlag = false;
    var $target = e.currentTarget != undefined ? $(e.currentTarget) : e;
    var $wraaper = $target.closest('li').find('label');
    var value = $target.val();
    var mgs = '';
    $wraaper.removeClass('error');
    $wraaper.text('아이디을 입력하세요');
    
    if(value == ''){
    	$wraaper.addClass('error');
    	$wraaper.removeClass('active');
        return false;
    } 
    
    /* 공백 확인 */
    if ( value.replace(/\s/g , '').length < 1 ) {
    	$wraaper.text('아디디에 공백만 사용은 불가능 합니다. 아이디을 입력하세요');
    	$wraaper.addClass('error');
    	$wraaper.removeClass('active');
        return false;
    }
    
    idFlag = true;
    return true;
}

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
