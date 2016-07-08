var f = document.getElementById("mainform");
if (null == f) {
	f = document.forms[0];
}
if (null != f) {
	var errorFound = false;
	for (i = 0; i < f.elements.length; i++) {
		var e = f.elements[i];
		if (/error/gi.test(e.className)) {
			e.focus();
			e.select();
			errorFound = true;
			break;
		}
	}
	if (!errorFound) {
		for (i = 0; i < f.elements.length; i++) {
			var e = f.elements[i];
			if (!e.disabled && e.type != "hidden"
					&& typeof (e.type) != "undefined") {
				e.focus();
				break;
			}
		}
	}
	for (i = 0; i < f.elements.length; i++) {
		var e = f.elements[i];
		e.setAttribute("autocomplete", "off");
	}
}
var mainContent = document.getElementById("mainContent");
if (mainContent != null) {
	var links = mainContent.getElementsByTagName("A");
	if (links.length > 0) {
		links[0].focus();
	}
}
var submitButton = document.getElementById("submitButton");
if (submitButton != null) {
	var submitButtonClone = submitButton.cloneNode(true);
	submitButtonClone.style.position = "absolute";
	submitButtonClone.style.top = "-1000000px";
	submitButtonClone.id = "submitButtonClone";
	f.insertBefore(submitButtonClone, f.firstChild);
}
var selects = document.getElementsByTagName("select");
for (i = 0; i < selects.length; i++) {
	var s = selects[i];
	s.onkeypress = function(a) {
		if (!a) {
			a = event;
		}
		if (a.keyCode != 13) {
			return;
		}
		if (submitButton != null) {
			submitButton.click();
		}
	};
}
function duplicateButton(c) {
	var a = document.getElementById(c);
	if (a) {
		var b = a.cloneNode(true);
		b.disabled = true;
		a.style.display = "none";
		a.parentNode.insertBefore(b, a);
	}
}
function hideElt(b) {
	var a = document.getElementById(b);
	if (a) {
		a.style.display = "none";
	}
}
if (document.getElementById("PaylineForm")) {
	document.getElementById("PaylineForm").onclick = function(a) {
		if (!a) {
			a = event;
		}
		if (a) {
			srcElt = a.target;
			if (!a.target) {
				srcElt = a.srcElement;
			}
			if (srcElt.id == "submitButton" || srcElt.id == "previousButton"
					|| srcElt.id == "returnButton"
					|| srcElt.id == "cancelButton") {
				if (WaitButton.showWaitButton()) {
					hideElt("submitButton");
					hideElt("previousButton");
					hideElt("cancelButton");
					hideElt("returnButton");
					hideElt("InfoCVV");
				}
			}
		}
	};
}
if (document.getElementById("mainform")) {
	document.getElementById("mainform").onsubmit = function(a) {
		if (!a) {
			a = event;
		}
		if (a) {
			srcElt = a.target;
			if (!a.target) {
				srcElt = a.srcElement;
			}
			if (srcElt.id == "submitButton" || srcElt.id == "previousButton"
					|| srcElt.id == "returnButton"
					|| srcElt.id == "cancelButton" || srcElt.id == "mainform") {
				if (WaitButton.showWaitButton()) {
					hideElt("submitButton");
					hideElt("previousButton");
					hideElt("cancelButton");
					hideElt("returnButton");
					hideElt("InfoCVV");
				}
			}
		}
	};
}
function openTimeoutWarningWindow(b) {
	var a = openPopupWindow(b, "timeoutwarn", null, true);
	var c = setWindowDimensions(a, "small");
	centerPopupWindow(a, c);
	return a;
}