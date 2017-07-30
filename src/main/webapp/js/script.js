function checkFBForm(els, blockId) {
	blockId = blockId || null;
	var res = 1;
	for (field in els) {
		res &= checkInput(els, field, blockId);
	}
	if (!res) {
		return false;
	} else {
		return true;
	}
	return false;
}

function checkInput(els, field, blockId) {
	if (!document.getElementById(field))
		return true;
	var el = document.getElementById(field);
	var result = 0;
	switch (els[field]) {
	case "text":
		var element = el.getElementsByTagName("input")[0];
		result = (element.value != undefined && element.value != ""
				&& element.value.length <= 250 ? 1 : 0);
		break;
	case "textarea":
		var element = el.getElementsByTagName("textarea")[0];
		result = (element != undefined && element.value != ""
				&& element.value.length <= 2000 ? 1 : 0);
		break;
	case "text_group":
		var inputs = el.getElementsByTagName("input");
		for (var i = 0; i < inputs.length; i++) {
			result |= (inputs[i].value != "" && inputs[i].value != undefined
					&& /^\d{1,2}$/.test(inputs[i].value) ? 1 : 0);
		}
		break;
	case "file":
		var filesExt = [ 'rar', 'zip', 'doc', 'docx', 'odt', 'txt' ];
		var element = el.getElementsByTagName("input")[0];
		var fileName = element.value;
		result = (element.value != undefined && element.value != ""
				&& element.value.length <= 50 ? 1 : 0);
		if (result) {
			var parts = fileName.split('.');
			result = (filesExt.join().search(parts[parts.length - 1]) != -1) ? 1
					: 0;
			result &= validateFileSize(element);
		}
		break;
	case "checkbox":
		var checkboxes = el.getElementsByTagName("input");
		for (var i = 0; i < checkboxes.length; i++) {
			result |= (checkboxes[i].checked ? 1 : 0);
		}
		if (result && blockId != null)
			result = checkWithNedeedTypes(blockId, checkboxes);
		break;
	case "select":
		var select = el.getElementsByTagName("select");
		result = (select[0] != undefined && select[0].value != ""
			&& select[0].value.length >= 1 ? 1 : 0);
		break;
	case "date":
		var element = el.getElementsByTagName("input")[0];
		result = /^\d{4}-\d{2}-\d{2}$/.test(element.value);
		break;
	case "bigdecimal":
		var element = el.getElementsByTagName("input")[0];
		result = /^[0-9]{1,8}(\.[0-9]{1,2})?$/.test(element.value);
		break;
	case "number":
		var element = el.getElementsByTagName("input")[0];
		result = /^\d+$/.test(element.value);
		break;
	case "login":
		var element = el.getElementsByTagName("input")[0];
		result = (element.value != undefined && element.value != ""
			&& element.value.length <= 50 && /^[a-zA-Z0-9_]{5,}$/.test(element.value) ? 1 : 0);
		break;
	case "password":
		var element = el.getElementsByTagName("input")[0];
		result = (element.value != undefined && element.value != ""
			&& element.value.length <= 50 && /^((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,})$/.test(element.value) ? 1 : 0);
		break;
	}
	
	if (result) {
		el.style.border = '1px';
		return true;
	} else {
		el.style.border = '1px solid red';
		return false;
	}
}

function validateFileSize(fileInput) {
	var fileObj, size;
	if (typeof ActiveXObject == "function") { // IE
		fileObj = (new ActiveXObject("Scripting.FileSystemObject"))
				.getFile(fileInput.value);
	} else {
		fileObj = fileInput.files[0];
	}

	size = fileObj.size; // Size returned in bytes.

	return size <= 5 * 1024 * 1024;
}

function checkWithNedeedTypes(blockId, checkboxes) {
	var checkedElements = {};
	for (var i = 0; i < checkboxes.length; i++) {
		if (checkboxes[i].checked) {
			var id = checkboxes[i].getAttribute("data-id");
			if (!(id in checkedElements)) {
				checkedElements[id] = 0;
			}
			checkedElements[id] += 1;
		}
	}

	var needed = {};
	var block = document.getElementById(blockId);
	var blockElements = block.getElementsByTagName("div");
	for (var i = 0; i < blockElements.length; i++) {
		needed[blockElements[i].getAttribute("data-id")] = blockElements[i]
				.getAttribute("data-count") * 1;
	}

	return Object.equals(checkedElements, needed);
}

/**
 * сравнить объекты по значению рекурсивно все свойства.
 * 
 * @param Object
 *            firstObj один из объектов для сравнения
 * @param Object
 *            secondObject другой объект для сравнения
 * @return boolean true одинаковы, false - различаются Пр.:
 *         Object.equals({a:[1,2,3],b:2}, {b:2,a:[1,2,3]}); //true
 *         Object.equals(null, null); //false Object.equals(1, 1); //true
 *         Object.equals(1, '1');//true Object.equals(1, {a:1});//false
 *         Object.equals([1,2], [1,2]);//true
 */
Object.equals = function(firstObj, secondObject) {
	if ((null == firstObj) || (null == secondObject))
		return false; // null сравниваем правильно. Если хоть в одном операнде
						// значит false
	if (('object' != typeof firstObj) && ('object' != typeof secondObject))
		return firstObj == secondObject;// оба из простых - сравнение простых
										// значений.
	else if (('object' != typeof firstObj) || ('object' != typeof secondObject))
		return false;// сравнивается простое значение с объектом - всегда не
						// равны
	// в этой точке только если сравниваем именно объекты (оба)

	// далее сравниваем нормальные объекты
	var keysFirstObj = Object.keys(firstObj);
	var keysSecondObject = Object.keys(secondObject);
	if (keysFirstObj.length != keysSecondObject.length)
		return false;

	return !keysFirstObj.filter(function(key) {
		if (typeof firstObj[key] == "object" || Array.isArray(firstObj[key])) {
			return !Object.equals(firstObj[key], secondObject[key]);
		} else {
			return firstObj[key] !== secondObject[key];
		}
	}).length;
}

function ajaxActionListener(blockId, url) {

	$(".category").removeClass("active");
	$(this).toggleClass("active");

	getContent(blockId, url);
	// $(".tab-content").toggleClass("tab-active");
	return false;
}

function getContent(res_id, url) {
	if (!document.getElementById(res_id))
		return;

	var msg = "";

	var xhr = new XMLHttpRequest();
	var end_url = 'xhr=html' + (msg == "" ? "" : "&" + msg) + '&r='
			+ Math.random();
	url += url.indexOf('?') == -1 ? "?" + end_url : "&" + end_url;
	console.log(url);
	xhr.open('GET', url, true);

	xhr.onreadystatechange = function() {
		if (xhr.readyState != 4)
			return;
		var responseObj = xhr.responseText;
		document.getElementById(res_id).innerHTML = xhr.responseText;
	}
	xhr.send(null);
}

var searchIdField = "projectName";
var kInput = document.getElementById(searchIdField);
if ( kInput != undefined )
	kInput.onkeyup = handleInput;

function handleInput(e) {

	// var text = e.type +
	// ' keyCode=' + e.keyCode +
	// ' which=' + e.which +
	// ' charCode=' + e.charCode +
	// ' char=' + String.fromCharCode(e.keyCode || e.charCode) +
	// (e.shiftKey ? ' +shift' : '') +
	// (e.ctrlKey ? ' +ctrl' : '') +
	// (e.altKey ? ' +alt' : '') +
	// (e.metaKey ? ' +meta' : '') + "\n";
	// console.log(text);
	console.log(kInput.value);
	// не запускать ajax если длина строки равна 0. Запускаем, только если
	// введены буквы

	// !!! не запускать, если нажимают не буквенную клавишу
	// console.log(kinput.value);
	// kinput.value.length < 10 - чтобы не нагружать базу запросами
	if (kInput.value.length > 3
			&& /^[a-zA-ZА-Яа-я \'\":,]+$/.test(kInput.value)
			&& kInput.value.length < 250) {
		ajaxProjectSearchRequest('Main?action=project_find&xhr=html&title='
				+ kInput.value);
	} else if (kInput.value.length == 0) {
		document.getElementById(searchIdField).value = '';
	}
}

function ajaxProjectSearchRequest(url) {
	var xmlHttp = createXmlHttpRequestObject();
	console.log("ajax");
	if (xmlHttp.readyState == 4 || xmlHttp.readyState == 0) {
		console.log(url);
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = function() {
			if (xmlHttp.readyState == 4) {
				if (xmlHttp.status == 200) {
					if (xmlHttp.responseText != null) {
						//console.log(xmlHttp.responseText);
						 var block = document.getElementById("projectList");
						 block.style.display = 'block';
						 block.innerHTML = xmlHttp.responseText;

					} else
						alert("Данные не получены");
				} else {
				}
			}
		};
		xmlHttp.send(null);
	}
}

function createXmlHttpRequestObject() {
	var xmlHttp;
	if (window.ActiveXObject) {
		try {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e) {
			xmlHttp = false;
		}
	} else {
		try {
			xmlHttp = new XMLHttpRequest();
		} catch (e) {
			xmlHttp = false;
		}
	}
	if (!xmlHttp)
		alert("Ошибка при создании XMLHttpRequext.");
	else
		return xmlHttp;
}
