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