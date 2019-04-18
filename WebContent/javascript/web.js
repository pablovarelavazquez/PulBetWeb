//Menu desplegable
$(document).ready(function () { 

	$("#paises").change(function(){
		var selectedCountry = $(this).children("option:selected").val();
		$.ajax({
			type: "GET",
			url: "/PulBetWeb/usuario",
			data: { 'id':selectedCountry,
				'action':"preRegistro"},
				contentType:"application/x-www-form-urlencoded; charset=ISO-8859-1",
				dataType:"json",
				success: function (provinciasArray) {
					
					if(!$("#provincias").html().isEmpty){
						$("#provincias").html("");
					}
					
					for(var i = 0; i < provinciasArray.length; i++){
						$("#provincias").html($("#provincias").html()+"<option value="+provinciasArray[i].id+">"+provinciasArray[i].nome+"</option>")

					}

				}
		});

	})
	var x = ($("#acumulada").val())*($("#importe").val());
	$("#ganancias").val(x);

});


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

