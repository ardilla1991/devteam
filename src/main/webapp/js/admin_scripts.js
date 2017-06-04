$( document ).ready(function() {
    categories();
});

function categories() {
	console.log("yyyyy");
	$(".category").click(function() {
		console.log("jjjj");
		console.log($(this));
		$(".category").removeClass("active");
		$(this).toggleClass("active");
		var categoryString = $(this).attr('id');
		var categoryId = categoryString.split("_")[1];
		
		getContent("categoryContent", "MainServlet?action=equipment_list_by_category&category_id=" + categoryId);
		//$(".tab-content").toggleClass("tab-active");
	});
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

function elementAdd(form_id, url, res_id){

	if (!document.getElementById(res_id)) return;
	
	    var msg   = $('#'+form_id).serialize();

	    var xhr = new XMLHttpRequest();
	    var end_url = '',
	        postRequestString = 'xhr=html&' + (msg=="" ? "": "&"+msg)+'&r=' + Math.random();

	    url += url.indexOf('?')==-1 ? "?"+end_url : "&"+end_url;

	    xhr.open("POST",url,true);
	    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState != 4) return;

	        document.getElementById(res_id).innerHTML = xhr.responseText;
	    }

	    xhr.send(postRequestString);

	}