
var btnWidth = 40; // 左・右ボタン幅
var invalidBtnWidth = 5; // ボタンの無効の幅
var menuBtnWidth = 90; // ユーザーナビゲーションのメニュー１個の幅
var intervalID;

var supportTouch = 'ontouchend' in document; // タップイベントが可能か TRUE or FALSE

window.addEventListener('DOMContentLoaded', function() {
	
	// ユーザーナビゲーションメニューの選択
	var elNaviuser = document.getElementById("user-navigation-menu");
	var elListLi = elNaviuser.children[0].children;	
	var path = location.pathname; 
	if( path.indexOf("/user/top") != -1){
		// テクニック
		elListLi[0].children[0].style.borderBottom = "3px solid #333";
		elListLi[0].children[0].style.color = "#333";
	}else if( path.indexOf("/user/technique") != -1){
		// テクニック
		elListLi[1].children[0].style.borderBottom = "3px solid #333";
		elListLi[1].children[0].style.color = "#333";
	}else if( path.indexOf("/user/category") != -1){
		// テクニック
		elListLi[2].children[0].style.borderBottom = "3px solid #333";
		elListLi[2].children[0].style.color = "#333";
	}else if( path.indexOf("/user/report") != -1){
		// テクニック
		elListLi[3].children[0].style.borderBottom = "3px solid #333";
		elListLi[3].children[0].style.color = "#333";
	}else if( path.indexOf("/user/relation") != -1){
		// テクニック
		elListLi[4].children[0].style.borderBottom = "3px solid #333";
		elListLi[4].children[0].style.color = "#333";
	}else if( path.indexOf("/user/userinfo") != -1){
		// テクニック
		elListLi[5].children[0].style.borderBottom = "3px solid #333";
		elListLi[5].children[0].style.color = "#333";
	}else if( path.indexOf("/user/data") != -1){
		// テクニック
		elListLi[6].children[0].style.borderBottom = "3px solid #333";
		elListLi[6].children[0].style.color = "#333";
	}
	
	if(supportTouch == true){
		// 左タップダウンイベント
		document.getElementById("left-btn").ontouchstart = function (ev) {
	        intervalID = setInterval(
	            function(){
	            	clickBtnMoveNavi(15, 1);
	            },
	            20
	        );
	    }
		document.getElementById("left-btn").ontouchmove = function (ev) {
			
			// ページ左上からの座標
			var touchObject = ev.changedTouches[0] ;
			var touchX = touchObject.pageX ;
			var touchY = touchObject.pageY ;
			
			// 要素の位置を取得
			var clientRect = this.getBoundingClientRect() ;
			var positionX = clientRect.left + window.pageXOffset ;
			var positionY = clientRect.top + window.pageYOffset ;
			
			// 要素内におけるタッチ位置を計算
			var x = touchX - positionX ;
			var y = touchY - positionY ;
			
			if(x < invalidBtnWidth || y < invalidBtnWidth || (btnWidth - invalidBtnWidth) < x|| (btnWidth - invalidBtnWidth) < y){
				clearInterval(intervalID);
			}
		}
		// 左タップアップイベント
		document.getElementById("left-btn").ontouchend = function (ev) { clearInterval(intervalID); }
		// 左タップキャンセルイベント
		document.getElementById("left-btn").touchcancel = function (ev) { clearInterval(intervalID); }

		// 右タップダウンイベント
		document.getElementById("right-btn").ontouchstart = function (ev) {
	        intervalID = setInterval( function(){ clickBtnMoveNavi(15, 2); }, 20 );
	    }
		document.getElementById("right-btn").ontouchmove = function (ev) {
			// ページ左上からの座標
			var touchObject = ev.changedTouches[0] ;
			var touchX = touchObject.pageX ;
			var touchY = touchObject.pageY ;
			
			// 要素の位置を取得
			var clientRect = this.getBoundingClientRect() ;
			var positionX = clientRect.left + window.pageXOffset ;
			var positionY = clientRect.top + window.pageYOffset ;
			
			// 要素内におけるタッチ位置を計算
			var x = touchX - positionX ;
			var y = touchY - positionY ;
			
			if(x < invalidBtnWidth || y < invalidBtnWidth || (btnWidth - invalidBtnWidth) < x|| (btnWidth - invalidBtnWidth) < y){
				clearInterval(intervalID);
			}
		}
		// 右タップアップイベント
		document.getElementById("right-btn").ontouchend = function (ev) { clearInterval(intervalID); }
		// 右タップキャンセルイベント
		document.getElementById("right-btn").touchcancel = function (ev) { clearInterval(intervalID); }

	}else{
		// 左ボタンダウンイベント
		document.getElementById("left-btn").onmousedown = function (ev) {
	        if( ev.button == 0 ){ intervalID = setInterval( function(){ clickBtnMoveNavi(15, 1);}, 20 ); }
	    }
		
		document.getElementById("left-btn").onmousemove = function (ev) {
			if(ev.offsetX < invalidBtnWidth || ev.offsetY < invalidBtnWidth || (btnWidth - invalidBtnWidth) < ev.offsetX || (btnWidth - invalidBtnWidth) < ev.offsetY){
				clearInterval(intervalID);
			}
		}
		// 左ボタンアップイベント
		document.getElementById("left-btn").onmouseup = function (ev) {
	        if( ev.button == 0 ){ clearInterval(intervalID);}
	    }
		// 右ボタンダウンイベント
		document.getElementById("right-btn").onmousedown = function (ev) {
	        if( ev.button == 0 ){ intervalID = setInterval( function(){ clickBtnMoveNavi(15, 2);}, 20 ); }
	    }
		// 右ボタン移動イベント
		document.getElementById("right-btn").onmousemove = function (ev) {
			if(ev.offsetX < invalidBtnWidth || ev.offsetY < invalidBtnWidth || (btnWidth - invalidBtnWidth) < ev.offsetX || (btnWidth - invalidBtnWidth) < ev.offsetY){
				clearInterval(intervalID);
			}
		}
		// 右ボタンアップイベント
		document.getElementById("right-btn").onmouseup = function (ev) {
	        if( ev.button == 0 ){ clearInterval(intervalID); }
	    }
	}
})

// ユーザーナビゲーション移動
function clickBtnMoveNavi(diff, index){
	var elNaviuser = document.getElementById("user-navigation-menu");
	var elUL = elNaviuser.children[0];
	var left = elUL.getBoundingClientRect().left;
	var newleft = 0;

	if(index == 1){
		// 左ボタン
		newleft = left - btnWidth - diff;
	}else if(index == 2){
		// 右ボタン
		newleft = left - btnWidth + diff;
	}
	if(index == 1 && (newleft * -1) >= menuBtnWidth * elUL.children.length){
		// 左ボタン
		clearInterval(intervalID);
		return;
	}
	
	if(index == 2 && newleft > 0){
		// 右ボタン
		clearInterval(intervalID);
		return;
	}
	
	elUL.style.left = newleft + 'px';
}









