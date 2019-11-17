/*!
 * @ Made by Kimseokjin 
 * @ 7271kim@naver.com
 * @ version 1.0.0
 * @ since 2018.11.11 
 */

;(function (win, $) {
    'use strict';

    if('undefined' === typeof win.honbabzone) {
        win.honbabzone = {};
    }

    if('undefined' === typeof win.honbabzone.common) {
        win.honbabzone.common = {};
    }

    var COMMON_UTILE = win.honbabzone.common;
    var namespace = win.honbabzone;

    namespace.board = (function() {
        var editor;
        var $body   = $('body');
        
        var setLayout = function(){
            titleActive();
            converterYoutube();
            totalFilesSize();
            editorLayout();
        };
        var bindEvents = function(){
            $body.on('focus blur','.text-field',titleActiveThis);
            $body.on('change.file','.tui-input-file',changeFiles);
            $body.on('click.msg','.tui-file-upload-msg',showHideFileform);
            $body.on('click.checkBoxs','.tui-form-body .tui-checkbox',checkBoxs);
            $body.on('click.checkBoxsTotal','.tui-form-header .tui-checkbox',checkBoxsTotal);
            $body.on('click.removeFiles','.tui-file-uploader-header .tui-btn-cancel',removeFiles);
            $body.on('dragover.dropOver','.tui-js-file-uploader-dropzone',dropOver);
            $body.on('dragleave.dropLeave','.tui-js-file-uploader-dropzone',dragleave);
            $body.on('dragenter.dropEnter','.tui-js-file-uploader-dropzone',dropEnter);
            $body.on('drop.changeFiles','.tui-js-file-uploader-dropzone',changeFiles);
            $body.on('dragover.dropOver',dropOver);
            $body.on('dragleave.dropLeave',dragleave);
            $body.on('dragenter.dropEnter',dropEnter);
            $body.on('drop.changeFiles',changeFiles);
            $body.on('click.form','.btn-from-sbmit',editorSubmit);
            
        };
        var editorSubmit = function( e ){
        	if(!titleFlag){
        		$('#first_title').closest('li').find('label').focus();
        		return;
        	}
        	if(!idFlag){
        		$('#first_id').closest('li').find('label').focus();
        		return;
        	}
        	if(!pwFlag){
        		$('#first_password').closest('li').find('label').focus();
        		return;
        	}
            var $target = $(e.currentTarget);
            var $writeForm  = $target.closest('#write-form');
            var $inputContents  = $writeForm.find('#sContent');
            if(editor){
            	$inputContents.val(editor.getValue());
            }
            $writeForm.submit();
        };
        
        var converterYoutubeFirst = function(){
            setTimeout(function(){
                var $target = $body.find('.tui-editor-contents a');
                var videoDate =[];
                for(var index = 0; index < $target.length; index++){
                    var link        = $target.eq(index).attr('href');
                    var youtubeURl  = /https:\/\/www\.youtube\.com\/watch\?v=[^&]*/g;
                    var videoID     = link.match(youtubeURl) || [];
                    var isChange    = $target.eq(index).data('ischange') || 'no';
                    
                    if( videoID.length > 0 ){
                        //$target.eq(index).attr('href','#');
                        videoID = videoID[0].replace('https://www.youtube.com/watch?v=','').replace('&','');
                        var container = 'yt' + Math.random().toString(36).substr(2, 10);
                        $target.eq(index).html('<span class="youtube-video" id="'+container+'" data-video-data=\'{"videoID":"'+videoID+'","autoPlay":"false","controls":"true","divID":"'+container+'"}\'></span>');
                    }
                }
                var $youtubeVideo = $body.find('.tui-editor-contents .youtube-video');
                $youtubeVideo.each(function(index){
                    var $this = $(this);
                    var thisVideData = $this.data('video-data');
                    videoDate.push(thisVideData);
                })
                youTubePlayer.playVideo(videoDate);
            },200)
            
        }
        var converterYoutube = function(){
            converterYoutubeFirst();
        } 
        
        var youTubePlayer = {
            playVideo: function(videoDate) {
                if (typeof(YT) == 'undefined' || typeof(YT.Player) == 'undefined') {
                  window.onYouTubeIframeAPIReady = function() {
                      youTubePlayer.loadPlayer(videoDate);
                  };
    
                  $.getScript('//www.youtube.com/iframe_api');
                } else {
                    youTubePlayer.loadPlayer(videoDate);
                }
              },
              loadPlayer: function(videoDate) {
                  for(var i in videoDate){
                      var container = videoDate[i].divID;
                      var videoId   = videoDate[i].videoID;
                      var autoPlay  = videoDate[i].autoPlay =='true' ? 1 : 0;
                      var controls  = videoDate[i].controls =='true' ? 1 : 0;
                      new YT.Player(container, {
                          videoId: videoId,
                          width: 356,
                          height: 200,
                          playerVars: {
                            autoplay: autoPlay,
                            controls: controls,
                            modestbranding: 1,
                            rel: 0,
                            showInfo: 0
                          }
                      });
                  }
              }
            };
       
        var dropEnter = function(e){
            e.stopPropagation();
            e.preventDefault();
            var $currentTartget = $(e.currentTarget);
            $currentTartget.removeClass('tui-dropzone-enabled');
        };
        var dropOver = function(e){
            e.stopPropagation();
            e.preventDefault();
            var $currentTartget = $(e.currentTarget);
            $currentTartget.addClass('tui-dropzone-enabled');
        };
        var dragleave = function(e){
            e.stopPropagation();
            e.preventDefault();
            var $currentTartget = $(e.currentTarget);
            $currentTartget.removeClass('tui-dropzone-enabled');
        };
        
        var init = function(){
            if ( $body.length < 1 ) return;
            setLayout();
            bindEvents();
        };
        
        var removeFiles = function(){
            var $tuiCheckBox     = $body.find('.tui-form-body .tui-checkbox');
            var $filesData       = $body.find('.tui-input-file');
            var totalIndex       = 0;
            for (var indexCheckBox = $tuiCheckBox.length-1; 0 <= indexCheckBox ; indexCheckBox--){
                var $childCheckBox = $($tuiCheckBox[indexCheckBox]);
                if($childCheckBox.hasClass('tui-is-checked')){
                    var $inputFiles  = $body.find('.tui-input-file-real');
                    var dataTransfer = new DataTransfer();
                    var uploaderFiles = Array.from($inputFiles.prop('files'));
                    uploaderFiles.splice(indexCheckBox,1);
                    for( var index = 0; index < uploaderFiles.length; index++ ){
                        dataTransfer.items.add(uploaderFiles[index]);
                    }
                    $inputFiles.prop('files',dataTransfer.files);
                }
            }
            totalFilesSize();
            checkBoxItemSize();
        };
        
        var checkBoxsTotal = function(e){
            var $currentTartget = $(e.currentTarget);
            var $tuiCheckBox = $body.find('.tui-form-body .tui-checkbox');
            for( var index = 0; index < $tuiCheckBox.length; index++){
                var $target = $($tuiCheckBox[index]);
                if($currentTartget.hasClass('tui-is-checked')){
                    $target.removeClass('tui-is-checked');
                } else {
                    $target.addClass('tui-is-checked');
                }
            }
            if($currentTartget.hasClass('tui-is-checked')){
                $currentTartget.removeClass('tui-is-checked');
            } else {
                $currentTartget.addClass('tui-is-checked');
            }
            
            checkBoxItemSize();
        };
        
        var checkBoxs = function(e){
            var $target = $(e.currentTarget);
            if($target.hasClass('tui-is-checked')){
                $target.removeClass('tui-is-checked');
            } else {
                $target.addClass('tui-is-checked');
            }
            
            checkBoxItemSize();
        };
        
        var checkBoxItemSize = function(){
            var $selectedCheckBox  = $body.find('.tui-form-body .tui-checkbox.tui-is-checked');
            var $cancelBotton      = $body.find('.tui-file-uploader-header .tui-btn-cancel');
            var totalCheckedSise   = 0;
            for(var index = 0; index < $selectedCheckBox.length; index++ ){
                totalCheckedSise += parseInt( $($selectedCheckBox[index].closest('.tui-tr-area')).find('.tui-size-area').text().replace('KB','') ) ;
            }
            $body.find('#checkedItemCount').text($selectedCheckBox.length);
            $body.find('#checkedItemSize').text(totalCheckedSise + 'KB');
            if( $selectedCheckBox.length > 0 ){
                $cancelBotton.removeClass('tui-is-disabled');
            } else {
                $cancelBotton.addClass('tui-is-disabled');
            }
        };
        
        var showHideFileform = function(e){
            var $target = $(e.currentTarget);
            var $fileUploader  = $target.closest('.tui-file-uploader');
            var $uploaderList  = $fileUploader.find('.tui-js-file-uploader-list');
            if($uploaderList.hasClass('hide')){
                $uploaderList.removeClass('hide');
            } else {
                $uploaderList.addClass('hide');
            }
        }
        var addFiles = function( files ){
            var dataTransfer    = new DataTransfer();
            var $inputFiles     = $body.find('.tui-input-file-real');
            var targetFiles     = Array.from(files);
            var uploaderFiles   = Array.from($inputFiles.prop('files'));
            var allowExtents    = ['gif','jpg','jpeg','png','zip','hwp','ppt','pptx','xls','xlsx','doc','docx','pdf','svg']
            for( var index = 0; index < uploaderFiles.length; index++ ){
                var uploadData = uploaderFiles[index];
                dataTransfer.items.add(uploadData);
            }
            for( var index = 0; index < targetFiles.length; index++ ){
                var targetFilesData = targetFiles[index];
                var fileExtenstion  = targetFilesData.name.split('.')[1];
                var fileSizeIsOk        = Math.ceil(targetFilesData.size/(1024*1024)) < 4;
                for(var allowIndex = 0; allowIndex < allowExtents.length; allowIndex++){
                    if(allowExtents[allowIndex] == fileExtenstion && fileSizeIsOk  ){
                        dataTransfer.items.add(targetFilesData);
                    }
                }
            }
            $inputFiles.prop('files',dataTransfer.files);
        }
        var changeFiles = function(e){
            e.preventDefault();
            e.stopPropagation();
            var $target = $(e.currentTarget);
            var $tuiFileUploader = $body.find('.tui-file-uploader');
            var files            = e.type == 'drop' ? e.originalEvent.dataTransfer.files : $target.prop('files');

            $target.removeClass('tui-dropzone-enabled');
            addFiles( files );
            totalFilesSize();
        }
        
        var totalFilesSize = function (){
            var $totalSizeHtml   = $body.find('#itemTotalSize');
            var files            = $body.find('.tui-input-file-real').prop('files') || [];
            var $tuiFormBody     = $body.find('.tui-form-body');
            var inputHtml        = ''
            var totalSize        = 0;
            var $upLoaderList    = $body.find('.tui-js-file-uploader-list');
            for( var index = 0; index < files.length; index++  ){
                $upLoaderList.removeClass('hide');
                totalSize += Math.ceil(files[index].size/1024);
                var sizeChild = Math.ceil(files[index].size/1024);
                inputHtml += '<tr class="tui-tr-area">' +
                                '<td>' +
                                    '<div class="tui-checkbox" data-index="'+index+'"><span class="tui-ico-check"><input type="checkbox"></span></div>'+
                                '</td>' +
                                '<td>'+
                                    '<span class="tui-filename-area"><span class="tui-file-name">'+files[index].name+'</span></span>'+
                                '</td>' +
                                '<td class="tui-size-area">'+ sizeChild +'KB</td>'+
                            '</tr>';
            }
            $tuiFormBody.html(inputHtml);
            $totalSizeHtml.html(totalSize+'KB');
        }
        
        var addRemoveDragFile = function( realTransferFiles ){
            var $target = $(realTransferFiles);
            var $fileUploader  = $target.closest('.tui-file-uploader');
            var $itemList = $fileUploader.find('.tui-js-file-uploader-list-items');
        }
        
       
        
        var titleActive = function(e){
            var $target         = $body.find('.text-field');
            var isTogle         = e ? e.type == 'focusin' || e.type=='focusout' : false;
            $target.each(function(index){
            	 var $item = $(this);
            	 var $targetLabel    = $item.closest('li').find('.label');
                 if( $item.val() == '' ){
                     if(isTogle){
                         $targetLabel.toggleClass('active');
                     }
                 } else {
                     $targetLabel.addClass('active');
                 }
            	
            })
        }; 
        
        var titleActiveThis = function(e){
            var $target = e.currentTarget != undefined ? $(e.currentTarget) : e;
            var isTogle         = e ? e.type == 'focusin' || e.type=='focusout' : false;
            var $targetLabel    = $target.closest('li').find('.label');
            if( $target.val() == '' ){
                if(isTogle){
                    $targetLabel.toggleClass('active');
                }
            } else {
                $targetLabel.addClass('active');
            }
       	
        }; 

        //https://nhnent.github.io/tui.editor/api/latest/tutorial-example01-basic.html#
        // 유투브 사용하고 싶으면 아래 코드 작성
        /*
          CodeBlock Editor : youtube 작성
          content에 ID 작성
        
        */
        var editorLayout = function(){
            if($body.find('#editSection').length > 0){
                // 유투브 제작 
                (function(root, factory) {
                    if (typeof define === 'function' && define.amd) {
                      define(['tui-editor'], factory);
                    } else if (typeof exports === 'object') {
                      factory(require('tui-editor'));
                    } else {
                      factory(root['tui']['Editor']);
                    }
                  })(window, function(Editor) {
                    // define youtube extension
                    Editor.defineExtension('youtube', function() {
                      // runs while markdown-it transforms code block to HTML
                      Editor.codeBlockManager.setReplacer('youtube', function(youtubeId) {
                        // Indentify multiple code blocks
                        var wrapperId = 'yt' + Math.random().toString(36).substr(2, 10);
                        // avoid sanitizing iframe tag
                        setTimeout(renderYoutube.bind(null, wrapperId, youtubeId), 0);

                        return '<div id="' + wrapperId + '"></div>';
                      });
                    });

                    function renderYoutube(wrapperId, youtubeId) {
                      var el = document.querySelector('#' + wrapperId);
                      el.innerHTML = '<iframe width="420" height="315" src="https://www.youtube.com/embed/' + youtubeId + '"></iframe>';
                    }
                  });
                var initialContesnt = $body.find('#editSection').data('content') || ''; 
                initialContesnt = (initialContesnt+'').replace(/<br>/g,'\n');
                    
                editor = tui.Editor.factory({
                    el: document.getElementById('editSection'),
                    initialEditType: 'wysiwyg',
                    previewStyle: 'vertical',
                    height: 800,
                    exts: ['table','colorSyntax','chart','youtube'],
                    toolbarItems: ['heading','bold','italic','strike','divider','hr','quote','divider','ul','ol','task','indent','outdent','divider','table','image','link','divider','code','codeblock'],
                    initialValue : initialContesnt
                }); 
            }
        }
        
        return {
            init : init
        }
    })();
    
    namespace.board.init();
    
})(window, window.jQuery);