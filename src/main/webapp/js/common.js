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
    var namespace = win.honbabzone;
    
    namespace.common = (function(){
        var $body = $('body');
        // 모바일 에이전트 구분
        var isMobile = {
                Android: function () {
                         return navigator.userAgent.match(/Android/i) == null ? false : true;
                },
                BlackBerry: function () {
                         return navigator.userAgent.match(/BlackBerry/i) == null ? false : true;
                },
                IOS: function () {
                         return navigator.userAgent.match(/iPhone|iPad|iPod/i) == null ? false : true;
                },
                Opera: function () {
                         return navigator.userAgent.match(/Opera Mini/i) == null ? false : true;
                },
                Windows: function () {
                         return navigator.userAgent.match(/IEMobile/i) == null ? false : true;
                },
                any: function () {
                         return (isMobile.Android() || isMobile.BlackBerry() || isMobile.IOS() || isMobile.Opera() || isMobile.Windows());
                }
        };
        
        var show = function ( $target ) {
            if ($target != null) {
                if( Array.isArray($target) ){
                    for(var index = 0; index < $target.length; index++){
                        $($target[index]).css("display","block");
                    }
                } else {
                    $target.css("display","block");
                }
                
            }
        }
        
        var hide = function ( $target ) {
            if ($target != null) {
                if( Array.isArray($target) ){
                    for(var index = 0; index < $target.length; index++){
                        $($target[index]).css("display","none");
                    }
                } else {
                    $target.css("display","none");
                }
            }
        }
        
        var errShow = function ( $target , msg ){
            if ($target != null) {
                $target.html(msg);
                $target.show();
            }
        }
        
        var isshift = false;
        var is_capslockon=false;
        
        var checkShiftUp = function(e){
            if (e.which && e.which == 16) {
                isshift = false;
            }
        }
        
        var checkShiftDown = function(e){
            var target = e.currentTarget;
            var showHideTarget = $body.find("#err_capslock");
            var down_keyCode=0;
            if (e.which && e.which == 16) {
                isshift = true;
            }
            if (window.event) {
                down_keyCode = e.keyCode;
            }
            else if (e.which) {
                down_keyCode = e.which;
            }
            
            if (down_keyCode && down_keyCode == 20) {
                if (!is_capslockon) {
                    is_capslockon=true;
                    show(showHideTarget);
                    setTimeout(function(){
                        hide(showHideTarget);
                    },3000);
                } else {
                    is_capslockon=false;
                    setTimeout(function(){
                        hide(showHideTarget);
                    },3000);
                }
            }
        }
        var capslockevt = function(e){
            var target = e.currentTarget;
            var showHideTarget = $body.find("#err_capslock");
            var myKeyCode = 0;
            var myShiftKey = false;
            if (window.event) { // IE
                myKeyCode = e.keyCode;
                myShiftKey = e.shiftKey;
            } else if (e.which) { // netscape ff opera
                myKeyCode = e.which; // myShiftKey=( myKeyCode == 16 ) ? true : // false;
                myShiftKey = isshift;
            }
            if ((myKeyCode >= 65 && myKeyCode <= 90) && !myShiftKey) {
                is_capslockon=true;
                show(showHideTarget);
                setTimeout(function(){
                    hide(showHideTarget);
                },3000);
            } else if ((myKeyCode >= 97 && myKeyCode <= 122) && myShiftKey) {
                is_capslockon=true;
                show(showHideTarget);
                setTimeout(function(){
                    hide(showHideTarget);
                },3000);
            } else {
                is_capslockon=false;
                setTimeout(function(){
                    hide(showHideTarget);
                },3000);
            }
        }
        
        var setLayout = function(){
        };
        
        var bindEvents = function(){
           
        };
        
        var initFun = function(){
            if(isMobile.any()){
                $body.addClass('isMobile');
                
                if(isMobile.Android()){
             
                }else if(isMobile.IOS()){
                    
                }else if(isMobile.BlackBerry()){
                    
                }else if(isMobile.Opera()){
                    
                }else if(isMobile.Windows()){
                    
                }
            } else {
                $body.addClass('isPc');
            }
        }

        var init = function(){
            if ( $body.length < 1 ) return;
            initFun();
            setLayout();
            bindEvents();
        }
        
        var layerAlert = function( text ){
            var $target = $('body').find('.dim-layer'); 
            var msg = text;
            $target.find('.ctxt.mb20').html(msg);
            $target.css('display','block');
            var $el = $target.find('#layer');        //레이어의 id를 $el 변수에 저장
            var isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수

            isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

            var $elWidth = $el.outerWidth(),
                $elHeight = $el.outerHeight(),
                docWidth = $(document).width(),
                docHeight = $(document).height();

            // 화면의 중앙에 레이어를 띄운다.
            if ($elHeight < docHeight || $elWidth < docWidth) {
                $el.css({
                    marginTop: -$elHeight /2,
                    marginLeft: -$elWidth/2
                })
            } else {
                $el.css({top: 0, left: 0});
            }

            $el.find('a.btn-layerClose').click(function(e){
                $target = $(e.currentTarget);
                isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
                return false;
            });

            $('.layer .dimBg').click(function(){
                $('.dim-layer').fadeOut();
                return false;
            });
        } ; 
        return {
            mobileCheck     : isMobile,
            checkShiftUp    : checkShiftUp,
            checkShiftDown  : checkShiftDown,
            capslockevt     : capslockevt,
            show            : show,
            hide            : hide,
            init            : init,
            errShow         : errShow,
            layerAlert      : layerAlert
        }
    })();
    
    namespace.common.init();
})(window, window.jQuery);
