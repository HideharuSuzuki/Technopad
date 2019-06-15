
var btnWidth = 40; // 左・右ボタン幅
var invalidBtnWidth = 5; // ボタンの無効の幅
var menuBtnWidth = 105; // ユーザーナビゲーションのメニュー１個の幅
var intervalID;
var nCntTechKeyword = 5; // 登録可能なキーワード数

var supportTouch = 'ontouchend' in document; // タップイベントが可能か TRUE or FALSE

window.addEventListener('DOMContentLoaded', function() {
	
	// ユーザーナビゲーションメニューの選択
	var elNaviuser = document.getElementById("user-navigation-menu");
	var elListLi = elNaviuser.children[0].children;	
	var path = location.pathname; 
	if( path.indexOf("/user/top") != -1){
		// TOP
		elListLi[0].children[0].style.borderBottom = "3px solid #333";
		elListLi[0].children[0].style.color = "#333";
	}else if( path.indexOf("/user/technique") != -1){
		// テクニック
		elListLi[1].children[0].style.borderBottom = "3px solid #333";
		elListLi[1].children[0].style.color = "#333";
	}else if( path.indexOf("/user/category") != -1){
		// カテゴリ
		elListLi[2].children[0].style.borderBottom = "3px solid #333";
		elListLi[2].children[0].style.color = "#333";
		
//		// カテゴリイメージ、チェンジイベントリスナー登録
//		var elCategoryLbl = document.getElementById("category-label");
//		var elCategoryIMG = document.getElementById("category-image");
//		var elCategoryFile = document.getElementById("category-file");
//		var elCategoryFilenname = document.getElementById("category-filename");
//		var elCategoryFileDel = document.getElementById("category-file-del");
//
//		elCategoryFile.addEventListener('change', function(ev){
//			setIMGToEl(ev, elCategoryLbl, elCategoryIMG, elCategoryFile, elCategoryFilenname, elCategoryFileDel);
//		});
		
	}else if( path.indexOf("/user/report") != -1){
		// レポート
		elListLi[3].children[0].style.borderBottom = "3px solid #333";
		elListLi[3].children[0].style.color = "#333";
	}else if( path.indexOf("/user/relation") != -1){
		// リレーション
		elListLi[4].children[0].style.borderBottom = "3px solid #333";
		elListLi[4].children[0].style.color = "#333";
	}else if( path.indexOf("/user/userinfo") != -1){
		// ユーザー情報
		elListLi[5].children[0].style.borderBottom = "3px solid #333";
		elListLi[5].children[0].style.color = "#333";
	}else if( path.indexOf("/user/data") != -1){
		// データ
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
	if(indexSidebarLeft == 1){
		newleft = newleft - widthNavigation;
	}
	
	if(index == 1 && (newleft * -1) >= menuBtnWidth * elUL.children.length){
		// 左ボタン
		return;
	}
	
	if(index == 2 && newleft > 0){
		// 右ボタン
		return;
	}
	
	elUL.style.left = newleft + 'px';
}

// プレビューに選択画像をセットする
// ev : イベント
// elLbl : label エレメント
// elIMG : img エレメント
// elFile : input file エレメント
// elFilename : ファイル名のエレメント
// elDel : 削除ボタンのエレメント
function setIMGToEl(ev, elLbl, elIMG, elFile, elFilename, elDel){
	
	// 引数チェック
	if(!ev || !elLbl || !elIMG || !elFile || !elFilename || !elDel){
		alert("引数エラー");
		return;
	}
	var file = ev.target.files[0];
	if(!file)
		return;
	
	// プレビューに選択画像をセット
	var reader = new FileReader();
	reader.onload = function(){
		
		// 画像のサイズチェック
		if(file.size <= 0){
			alert("選択されたファイルのサイズが0");
			return;
		}
		
		if(file.type != "image/jpeg" && file.type != "image/jpg" && file.type != "image/png" && file.type != "image/gif"){
			alert("選択できるファイルはjpeg(jpg),png,gifのみです");
			return;
		}
		
		var image = new Image();
		image.src = reader.result;
		image.onload = function() {
			
			// 画像の横幅・高さチェック
			if(image.naturalWidth <= 0 || image.naturalHeight <= 0){
				alert("選択された画像の横幅または高さが0");
				return;
			}
			
			// 選択画像のリサイズ
			
			// 元領域の情報
			var motoRatio = elLbl.clientHeight / elLbl.clientWidth; // 縦横比率
			var motoWidth = elLbl.clientWidth;
			var motoHeight = elLbl.clientHeight;
			
			// 選択画像の情報
			var newRatio = image.naturalHeight / image.naturalWidth; // 縦横比率
			
			if(motoRatio >= newRatio){
				// 元領域の方が選択画像より縦長(=選択画像の方が元領域より横長)
				var newWidth = motoWidth;
				var newHeight = image.naturalHeight * (motoWidth / image.naturalWidth);
			}else{
				var newWidth = image.naturalWidth * (motoHeight / image.naturalHeight);
				var newHeight = motoHeight;
			}
			
			newWidth = newWidth * resizeFold;
			newHeight = newHeight * resizeFold;
			
			var resizeIMG = imageResize(image, file.type, newWidth*2, newHeight*2);
			elIMG.setAttribute("src", resizeIMG);
			elIMG.width = newWidth;
			elIMG.height = newHeight;
			elIMG.style.marginLeft = Math.round((motoWidth - newWidth) / 2.0) + "px";
			elIMG.style.marginTop  = Math.round((motoHeight - newHeight) / 2.0) + "px";

			// ファイル名セット
			elFilename.innerHTML = file.name;
			
			// 削除ボタン、クリックイベント追加
			elDel.firstElementChild.addEventListener('click', function(ev){
				clickDel(elIMG, elFile, elFilename, elDel);
			});
			
			// 削除ボタン表示
			elDel.style.display = "block";
		}
	};
	reader.readAsDataURL(file);
}

// 画像をリサイズ
function imageResize(image_src, mime_type, width, height) {
    
	// 新しいCanvas作成
    var canvas = document.createElement('canvas');
    canvas.width = width;
    canvas.height = height;
    
    // Draw (Resize)
    var ctx = canvas.getContext('2d');
    ctx.drawImage(image_src, 0, 0, width, height);
    // Image Base64
    return canvas.toDataURL(mime_type);
}

//プレビュー画像削除
function clickDel(elIMG, elFile, elFilename, elDel){
	
	// 引数チェック
	if(!elIMG || !elFile || !elFilename || !elDel)
		return;

	elIMG.setAttribute("src", "/images/noimage_d@2x.jpg");
	elFile.value="";
	elFilename.innerHTML = "選択されていません";
	elDel.style.display = "none";
}

//キーワード追加時処理
function fncAddKeyword(){
	
	// 入力キーワード取得
	var elKeyword = document.getElementById("keyword-input");
	var strKeyword = elKeyword.value;
	if(strKeyword.length == 0){
		// 入力文字なし
		return;
	}
	
	// 表示領域
	var elKeywordContent = document.getElementById("keyword-content");
	
	// 追加されたキーワード数チェック
	var count = elKeywordContent.childElementCount; // 子要素数
	if(count >= nCntTechKeyword){
		// キーワード5つすでにあり
		alert("キーワードは"+ nCntTechKeyword +"つまで");
		return;
	}

	if(strKeyword.length > 15){
		// 15文字より大きい
		alert("キーワードは15文字まで");
		return;
	}
	if(strKeyword.indexOf(' ') != -1 || strKeyword.indexOf('　') != -1){
		// 半角・全角スペースあり
		alert("スペースは入力不可");
		strKeyword = strKeyword.replace(/ /g, '');
		strKeyword = strKeyword.replace(/　/g, '');
		document.getElementById("keyword-input").value = strKeyword;
		return;
	}
	
	// 重複確認
	for(var i=0; i<nCntTechKeyword; i++){
		var elKeywordHidden = document.getElementById("keyword" + (i+1));
		if (elKeywordHidden.value != null && elKeywordHidden.value == strKeyword){
			alert("キーワードが重複");
			return;
		}
	}//end for
	
	// hidden領域にセット
	var elKeywordHidden = document.getElementById("keyword"+(count+1));
	elKeywordHidden.value = strKeyword;
	
	// 表示領域に設定
	var elDIV = document.createElement('div');
	elDIV.classList.add("keyword");
	var elP = document.createElement('p');
	elP.classList.add("keyword-name");
	var elSPAN = document.createElement('span');
	var tx = document.createTextNode("# ");
	var elSPAN2 = document.createElement('span');
	var elP2 = document.createElement('p');
	elP2.classList.add("keyword-del");
	var tx2 = document.createTextNode("×");
	
	// 階層作成
	elDIV.appendChild(elP);
	elDIV.appendChild(elP2);
	elP.appendChild(elSPAN);
	elSPAN.appendChild(tx);
	elP.appendChild(elSPAN2);
	elSPAN2.textContent = strKeyword;
	elP2.appendChild(tx2);
	elKeywordContent.appendChild(elDIV);
	
	// 引数は動的
	fncAddClickKeywordDel();
	
	// 入力クリア
	elKeyword.value="";
}

function fncAddClickKeywordDel(){
	
	var elKeywordContent = document.getElementById("keyword-content");
	var count = elKeywordContent.childElementCount; // 子要素数
	var index1 = 0;
	var index2 = 0;
	var index3 = 0;
	var index4 = 0;
	var index5 = 0;
	
	for(var i = 0; i < count; i++){
		var children = elKeywordContent.children[i];
		if(i==0){
			index1 = i+1;
			children.children[1].onclick = function(){
				fncDelKeyword(index1);
			}
		}
		if(i==1){
			index2 = i+1;
			children.children[1].onclick = function(){
				fncDelKeyword(index2);
			}
		}
		if(i==2){
			index3 = i+1;
			children.children[1].onclick = function(){
				fncDelKeyword(index3);
			}
		}
		if(i==3){
			index4 = i+1;
			children.children[1].onclick = function(){
				fncDelKeyword(index4);
			}
		}
		if(i==4){
			index5 = i+1;
			children.children[1].onclick = function(){
				fncDelKeyword(index5);
			}
		}
	}// end for
}

//キーワード削除ボタンクリック
function fncDelKeyword(index){
	var keywordnames = document.getElementsByName("listkeywordname");
	
	// hidden要素で削除
	keywordnames[index-1].value = "";
	
	// キーワードエリア削除
	var elKeywordContent = document.getElementById("keyword-content");
	var children = elKeywordContent.children[index-1];
	for (var i =children.childNodes.length-1; i>=0; i--) {
		children.removeChild(children.childNodes[i]);
	}
	elKeywordContent.removeChild(children);
	
	fncAddClickKeywordDel();
	fncKeywordnamesReset();
}

function fncKeywordnamesReset(){
	var keywordnames = document.getElementsByName("listkeywordname");
	var tmp = [];
	for(var i=0; i<keywordnames.length; i++){
		if(keywordnames[i].value != null && keywordnames[i].value.length > 0){
			tmp.push(keywordnames[i].value);
		}
	}
	for(var i=0; i<keywordnames.length; i++){
		if(i < tmp.length){
			keywordnames[i].value = tmp[i];
		}else{
			keywordnames[i].value = '';
		}
	}
}



