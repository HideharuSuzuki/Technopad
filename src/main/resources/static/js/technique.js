
var cropper = null;
var nCntKeyword = 5; // 登録可能なキーワード数

// DOMロード時処理
window.addEventListener('DOMContentLoaded', function() {
	// TOP画像にイベントリスナー登録
	var elTopIMG = document.getElementById("topimg");
	elTopIMG.addEventListener('change', function(ev){
		fncSetIMGModal(ev);
	});
});

function fncSetIMGModal(ev){
	var file = ev.target.files[0];
	if(!file){
		// エラー
	}
	// モーダル背景エレメント作成・追加
	var elDiv = document.createElement("div");
	elDiv.id = "modal-bg";
	document.getElementsByTagName("body")[0].appendChild(elDiv);
	
	var reader = new FileReader();
	reader.onload = function(){
		// プレビュー画像のエレメント作成
		var img = document.createElement("img");
		img.setAttribute("src", reader.result);
		img.setAttribute("id", "previewImageModal");
		document.getElementById("modal-main").appendChild(img);
		
		modalResize(); //画面中央を計算する関数を実行
		
		cropper = new Cropper(img, {
			aspectRatio: 3 / 2,
			responsive:false,
			guides:false,
			background:true,
			crop: function(event) {
			}
		});
	};
	reader.readAsDataURL(file);
	
	// モーダルフェードイン
	$("#modal-bg,#modal-main").fadeIn("fast");
	$(window).resize(modalResize);
	
	$("#modal-bg").click(function(){
		$("#modal-main").fadeOut("fast",function(){
			// モーダルフェードアウト時処理
			$('#modal-bg').remove(); //挿入した<div id="modal-bg"></div>を削除

			// モーダルの画像削除
			var previewImageModal=document.getElementById("previewImageModal");
			var dom_obj_parent = previewImageModal.parentNode;
			dom_obj_parent.removeChild(previewImageModal);

			var imgfile =document.getElementById("topimg");
			imgfile.value="";
			document.getElementById("previewTopIMG").src="http://localhost:8080/static/images/noimage.png";
			// クロッパー破棄
			if(cropper){
				cropper.destroy();
				cropper=null;
			}
		});
	});
}//end function

//画面中央を計算する関数を実行
function modalResize(){
	var w = window.innerWidth
	var h = window.innerHeight
	var cw = document.getElementById("modal-main").clientWidth;
	var ch = document.getElementById("modal-main").clientHeight;
	document.getElementById("modal-main").style.left = ((w - cw)/2) + "px";
	document.getElementById("modal-main").style.top  = ((h - ch)/2) + "px";
}//end function

//画像のトリミングを行う
function fncDoTrimming(){
	
	if(cropper==null)
		return;
	var canvas = cropper.getCroppedCanvas();
	var cropData=cropper.getData();
	
	// トリミング情報セット
	document.getElementById("crop_data_topimg").value = "x:"+cropData.x+"/y:"+cropData.y+"/width:"+cropData.width+"/height:"+cropData.height;
	
	// プレビュー画像にセット
	var img_jpeg_src = canvas.toDataURL("image/jpeg");
	var elPreviewIMG = document.getElementById("previewTopIMG");
	elPreviewIMG.children[0].src = img_jpeg_src;
//	elPreviewImage.src = img_jpeg_src;
	
	// プレビュー画像右上に「×」ボタン追加
	var elDiv = document.createElement('div');
	elDiv.classList.add('delbtn');
	elDiv.appendChild(document.createTextNode("×"));
	elDiv.onclick = clickDelBtn;
	elPreviewIMG.appendChild(elDiv);
	
	// モーダルの画像削除
	var previewImageModal=document.getElementById("previewImageModal");
	var dom_obj_parent = previewImageModal.parentNode;
	dom_obj_parent.removeChild(previewImageModal);
	
	// フェードアウト
	$("#modal-main,#modal-bg").fadeOut("fast",function(){
		//挿入した<div id="modal-bg"></div>を削除
		$('#modal-bg').remove() ;
	});
	
	// クロッパー破棄
	cropper.destroy();
	cropper=null;
}

//画像右上「×」ボタンクリック
function clickDelBtn(obj){
	var previewImage = obj.parentNode
	console.log(obj);
	if(obj instanceof MouseEvent){
		// event object -> 登録
		previewImage = obj.target.parentNode
	}else{
		// div object -> 修正
		previewImage = obj.parentNode
	}
	console.log(previewImage);
	
//	previewImage.src="http://mikke.com/cp-bin/wordpress/wp-content/uploads/2018/06/sample_img.gif";
	
	var index = previewImage.id.substr(previewImage.id.length-1);
	
	for (var i = 0; i < previewImage.children.length; i++){
		if(previewImage.children[i].tagName == "IMG"){
			var elIMG =previewImage.children[i];
			elIMG.src="http://mikke.com/cp-bin/wordpress/wp-content/uploads/2018/06/sample_img.gif";
		}
		if(previewImage.children[i].tagName == "DIV"){
			var elDIV =previewImage.children[i];
			previewImage.removeChild(elDIV);
		}
	}//end for
	
	// ファイル削除
	var topimg = document.getElementById("topimg");
	topimg.value="";
}

// キーワード追加時処理
function fncAddKeyword(){
	
	// 表示領域
	var elKeywordArea = document.getElementById("keywordarea");
	// 追加されたキーワード数チェック
	var count = elKeywordArea.childElementCount; // 子要素数
	if(count >= nCntKeyword){
		// キーワード5つすでにあり
		alert("キーワードは5つまで");
		return;
	}
	// 入力キーワード取得
	var elKeyword = document.getElementById("keyword");
	var strKeyword = elKeyword.value;
	if(strKeyword.length == 0){
		// 入力文字なし
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
		document.getElementById("keyword").value = strKeyword;
		return;
	}
	
	// 重複確認
	for(var i=0; i<nCntKeyword; i++){
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
	elKeywordArea.appendChild(elDIV);
	
	// 引数は動的
	fncAddClickKeywordDel();
	
	// 入力クリア
	elKeyword.value="";
}

// キーワード削除ボタンクリック
function fncDelKeyword(index){
	var keywordnames = document.getElementsByName("keywordnames");
	
	// hidden要素で削除
	keywordnames[index-1].value = "";
	
	// キーワードエリア削除
	var elKeywordArea = document.getElementById("keywordarea");
	var children = elKeywordArea.children[index-1];
	for (var i =children.childNodes.length-1; i>=0; i--) {
		children.removeChild(children.childNodes[i]);
	}
	elKeywordArea.removeChild(children);
	
	fncAddClickKeywordDel();
	fncKeywordnamesReset();
}

function fncAddClickKeywordDel(){
	
	var elKeywordArea = document.getElementById("keywordarea");
	var count = elKeywordArea.childElementCount; // 子要素数
	var index1 = 0;
	var index2 = 0;
	var index3 = 0;
	var index4 = 0;
	var index5 = 0;
	
	for(var i = 0; i < count; i++){
		var children = elKeywordArea.children[i];
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

function fncKeywordnamesReset(){
	var keywordnames = document.getElementsByName("keywordnames");
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

