function checkFBForm(els)
{
  var res = 1;
  for (field in els){
  	res &= checkInput(els[field]);
	console.log(checkInput(els, field));
	}
	//foreach ()
  /*if (!res){
  	return false;
  }else{
  	return true;
	}*/
	return false;
}

function checkInput(els, field){
	if (!document.getElementById(field)) return true;
	var el = document.getElementById(field);
	var result = 0;
	switch(els[field])
	{
		case "text":
			result = ( el.value=="" ? 0 : 1 ) ;
			console.log("el="+result);
			console.log(el.value);		
			break;
		case "text_group":
			var inputs = el.getElementsByTagName("input");
			for (var i = 0; i < inputs.length; i++)
			{
				result |= inputs[i].value != "";
			}
			console.log(result);
			break;
		case "file":
			break;
		case "checkbox":
			var checkboxes = el.getElementsByTagName("input");
			for (var i = 0; i < checkboxes.length; i++)
			{
				result |= checkboxes[i].checked;
			}
			console.log(result);
			break;	
		case "file":
			
			break;	
	}
	console.log("fieldType=" + els[field]);
	console.log("res=" + result);
	
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

