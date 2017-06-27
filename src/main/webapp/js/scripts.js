$( document ).ready(function() {
    tabs();
    calculateTotalPrice();
});

function tabs() {
	console.log("yyyyy");
	$(".tab-title").click(function() {
		console.log("jjjj");
		console.log($(this));
		$(".tab-title").toggleClass("active");
		$(".tab-content").toggleClass("tab-active");
	});
}

var equipments_ids = [];
function calculateTotalPrice() {
	
	$('.eq_check').change(function(){
		var el = document.getElementById('totalCalc');
		var existedValue = parseFloat(document.getElementById('price_' + this.id).innerHTML);
		if( this.checked ) {
			el.innerHTML = parseFloat(el.innerHTML) + existedValue;
			equipments_ids.push(this.id);
		} else {
			el.innerHTML = parseFloat(el.innerHTML) - existedValue;
			for (var i = 0; i < equipments_ids.length; i++) {
				if ( equipments_ids[i] == this.id ) {
					equipments_ids.splice(equipments_ids[i], 1);
					break;
				}
			}
		}
		console.log(equipments_ids);
	});
}

function makeOrder() {
	if ( document.getElementById('equipments_ids') ) {
		document.getElementById('equipments_ids').value = equipments_ids.join(',');
		return true;
	}
	
	return false;
}


function checkFBForm(els)
{
  var res = 1;
  for (field in els){
  	res &= checkInput(els,field);
	}
  if (!res){
  	return false;
  }else{
  	return true;
	}
	return false;
}

function checkInput(els, field){
	if (!document.getElementById(field)) return true;
	var el = document.getElementById(field);
	var result = 0;
	switch(els[field])
	{
		case "text":
			var element = el.getElementsByTagName("input")[0];
			result = ( element.value!=undefined && element.value!="" ? 1 : 0 ) ;	
			break;
		case "text_group":
			var inputs = el.getElementsByTagName("input");
			for (var i = 0; i < inputs.length; i++)
			{
				result |= ( inputs[i].value != "" && inputs[i].value != undefined ? 1 : 0 );
			}
			break;
		case "file":
			break;
		case "checkbox":
			var checkboxes = el.getElementsByTagName("input");
			for (var i = 0; i < checkboxes.length; i++)
			{
				result |= (checkboxes[i].checked ? 1 : 0);
			}
			break;	
		case "date":
			
			break;	
	}
	if ( result )
	{
		el.style.border = '1px';
		return true;
	}
	else
	{
		el.style.border = '1px solid red';
		return false;
	}
}

/////  addClass(curr_obj, addImgClass);
function addClass(o, c){
    var re = new RegExp("(^|\\s)" + c + "(\\s|$)", "g");
    if (re.test(o.className)) return;
    o.className = (o.className + " " + c).replace(/\s+/g, " ").replace(/(^ | $)/g, "");
}

function removeClass(o, c){
    var re = new RegExp("(^|\\s)" + c + "(\\s|$)", "g");
    o.className = o.className.replace(re, "$1").replace(/\s+/g, " ").replace(/(^ | $)/g, "");
}

function hasClass(o, c){
    var re = new RegExp("(^|\\s)" + c + "(\\s|$)", "g");
    if (re.test(o.className)) return 1;
    return 0;
}

function ajaxActionListener(blockId, url) {
	
		console.log("jjjj");
		console.log($(this));
		$(".category").removeClass("active");
		$(this).toggleClass("active");

		
		getContent(blockId, url);
		//$(".tab-content").toggleClass("tab-active");
		return false;
}

function getContent(res_id, url) {
	if (!document.getElementById(res_id)) return;
	
	var msg = "";

	var xhr = new XMLHttpRequest();
	var end_url = 'xhr=html'+(msg=="" ? "": "&"+msg)+'&r=' + Math.random();
	url += url.indexOf('?')==-1 ? "?"+end_url : "&"+end_url;
	console.log(url);
	xhr.open('GET', url, true);
	 
	xhr.onreadystatechange = function() {
	    if (xhr.readyState != 4) return;
			var responseObj = xhr.responseText;
			document.getElementById(res_id).innerHTML = xhr.responseText;
	}
	xhr.send(null);
}
