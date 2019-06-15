
var indexDispHeaderSearch = 2; // 1:表示 2:非表示
var indexDispNav = 2; // 1:表示 2:非表示
var flgCancel = false;
var flgNav = false;

// DOMロード終了時アクション
window.addEventListener('DOMContentLoaded', function() {
	
})

// 検索ボタンクリックアクション
function clickSearchBtn(){
	
	// ナビゲーションメニューを表示中ならば非表示化
	if(indexDispNav==1){
		clickHeaderMenu();
	}
	
	// ヘッダー検索表示・非表示処理
	if(indexDispHeaderSearch==1){
		// 表示->非表示
		document.getElementById("header-search").style.display = "none";
		document.getElementById("search-cover").style.display = "none";
		document.getElementById("content-wrap").style.paddingTop = "53px";
		indexDispHeaderSearch=2;
	}else if(indexDispHeaderSearch==2){
		// 非表示->表示
		document.getElementById("header-search").style.display = "block";
		document.getElementById("search-cover").style.display = "block";
		document.getElementById("content-wrap").style.paddingTop = "103px";
		indexDispHeaderSearch=1;
		document.getElementById("search-technique").focus();
	}
	
	// 
}

// ヘッダメニュークリックアクション
function clickHeaderMenu(){
	if(flgCancel==true){
		flgCancel = false;
		return;
	}
	
	// 検索エリアを表示中ならば非表示化
	if(indexDispHeaderSearch==1){
		clickSearchBtn();
	}
	
	if(indexDispNav==1){
		document.getElementById("navigation").style.display = "none";
		indexDispNav=2;
	}else if(indexDispNav==2){
		document.getElementById("navigation").style.display = "block";
		indexDispNav=1;
	}
}
// キャンセルボタンクリックアクション
function clickCancel(){
	flgCancel = true;
	if(indexDispNav==1){
		document.getElementById("navigation").style.display = "none";
		indexDispNav=2;
	}else if(indexDispNav==2){
		document.getElementById("navigation").style.display = "block";
		indexDispNav=1;
	}
}


