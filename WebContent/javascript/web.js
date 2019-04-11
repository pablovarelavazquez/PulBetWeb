//Menu desplegable

function desplegarMenu() {
	document.getElementById("meumenudes").classList.toggle("show");
}

window.onclick = function (event) {
	if (!event.target.matches('.dropbtn')) {

		var menudess = document.getElementsByClassName("menudes-contido");
		var i;
		for (i = 0; i < menudess.length; i++) {
			var openmenudes = menudess[i];
			if (openmenudes.classList.contains('show')) {
				openmenudes.classList.remove('show');
			}
		}
	}
}