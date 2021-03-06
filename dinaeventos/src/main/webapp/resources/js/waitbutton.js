var WaitButton = WaitButton || (function() {
	var a = true;
	var b;
	return {
		init : function(f, e, c) {
			b = document.createElement("div");
			b.setAttribute("id", "textDiv");
			b.className = "waitButton";
			b.style.display = "none";
			if (c === undefined || c == "") {
				b.innerHTML = e;
			} else {
				b.innerHTML = e + " <img height='20px' src='" + c + "' />";
			}
			var d = document.getElementById(f);
			if (d) {
				d.parentNode.insertBefore(b, d);
			}
		},
		noWaitButton : function() {
			a = false;
		},
		forceUseWaitButton : function() {
			a = true;
		},
		showWaitButton : function() {
			if (a == true) {
				b.style.display = "inline";
				return true;
			} else {
				return false;
			}
		}
	};
}());