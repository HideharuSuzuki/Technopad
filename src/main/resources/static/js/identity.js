window.addEventListener('DOMContentLoaded', function() {
})

// 日にちのoptionを生成
function fncGetOptionDay(){
	var selectY = document.getElementsByName("year_birth")[0];
	var selectedValY = selectY.options[selectY.selectedIndex].value;
	var selectM = document.getElementsByName("month_birth")[0];
	var selectedValM = selectM.options[selectM.selectedIndex].value;
	var selectD = document.getElementsByName("day_birth")[0];
	
	// 一旦日にちoptionクリア
	while (selectD.firstChild) 
		selectD.removeChild(selectD.firstChild);

	// 一旦日にちoptionセット(0:「選択」のみ)
	var option = document.createElement("option");
	option.value = 0;
	option.appendChild(document.createTextNode("選択"));
	selectD.appendChild(option);
	
	if(parseInt(selectedValY,10) != 0 && parseInt(selectedValM,10) != 0){
		// 年と月が選択済
		var date = new Date(parseInt(selectedValY,10), parseInt(selectedValM,10), 0);
		var lastday = date.getDate();
		
		for(var i=1; i <= lastday; i++){
			var option = document.createElement("option");
			option.value = i;
			option.text  = i;
			selectD.appendChild(option);
		}
	}
}