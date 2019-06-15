
var userIntensition = true; // ユーザーの意思(ナビゲーション)
var indexSidebarLeft = 2; // 1:sidebar-left表示 2:sidebar-left非表示

var widthNavigation = 220;
var widthTechBox = 170;
var countTechBox = 0;
var maxCountTechBox = 6;

// ウィンドウサイズ変更時アクション
window.onresize = function () {
	
	var quo = calcNumTechBox();
	if(quo != countTechBox){
		countTechBox = quo;
		setDisplayTechBox(countTechBox);
	}
	
	if(indexSidebarLeft == 2 && userIntensition == true && quo >= (maxCountTechBox+1)){
		clickHeaderMenu();
	}
};

// DOMロード終了時アクション
window.addEventListener('DOMContentLoaded', function() {
	var quo = calcNumTechBox();
	if(quo != countTechBox){
		countTechBox = quo;
		setDisplayTechBox(countTechBox);
	}
	if(indexSidebarLeft == 2 && userIntensition == true && quo >= (maxCountTechBox+1)){
		clickHeaderMenu();
	}
})

// TechniqueBoxを表示する個数を計算
// 返り値:表示する個数
function calcNumTechBox(){
	var bodyWidth = document.body.clientWidth;
	var sidebarwidth=0;
	if(indexSidebarLeft==1){
		// 左サイドバー表示
		sidebarwidth = widthNavigation;
	}
	var quo = Math.floor((bodyWidth - sidebarwidth - 10) / widthTechBox);
	return quo;
}

// TechniqueBoxの表示・非表示処理
// count:TechniqueBoxを表示する個数 
function setDisplayTechBox(count){
	var elementsArea = document.getElementsByClassName("content-box");
	for(var i=0; i<elementsArea.length; i++){
		var elementsTechbox = elementsArea[i].children[1].children;
		for(var j=0; j<elementsTechbox.length; j++){
			if(j < count){
				elementsTechbox[j].style.display = "block";
			}else{
				elementsTechbox[j].style.display = "none";
			}
		}//end for
	}//end for
}

// ヘッダメニュークリックアクション
function clickHeaderMenu(){
	
	// サイドバー表示・非表示処理
	if(indexSidebarLeft==1){
		document.getElementById("navigation").style.display = "none";
		indexSidebarLeft=2;
		userIntensition = false;
	}else{
		document.getElementById("navigation").style.display = "block";
		indexSidebarLeft=1;
		userIntensition = true;
	}
	
	// TechniqueBox表示・非表示処理
	var quo = calcNumTechBox();
	if(quo != countTechBox){
		countTechBox = quo;
		setDisplayTechBox(countTechBox);
	}
}



