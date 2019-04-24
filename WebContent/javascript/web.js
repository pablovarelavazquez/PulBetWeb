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
	
	$("#editpaises").change(function(){
		var selectedCountry = $(this).children("option:selected").val();
		$.ajax({
			type: "GET",
			url: "/PulBetWeb/usuario",
			data: { 'id':selectedCountry,
				'action':"preEdit"},
				contentType:"application/x-www-form-urlencoded; charset=ISO-8859-1",
				dataType:"json",
				success: function (provinciasArray) {

					if(!$("#editprovincias").html().isEmpty){
						$("#editprovincias").html("");
					}

					for(var i = 0; i < provinciasArray.length; i++){
						$("#editprovincias").html($("#editprovincias").html()+"<option value="+provinciasArray[i].id+">"+provinciasArray[i].nome+"</option>")

					}

				}
		});

	})

	$("#loginbutton").click(function(){
		var email = $("#loginemail").val();
		$.ajax({
			type: "GET",
			url: "/PulBetWeb/usuario",
			data: {'action':"rememberme",
				'email': email,
				'checked': $("#rememberinput").is(':checked')}
		});



	})

	$("#rememberinput").change(function(){
		var email = $("#loginemail").val();
		$.ajax({
			type: "GET",
			url: "/PulBetWeb/usuario",
			data: {'action':"rememberme",
				'email': email,
				'checked': $("#rememberinput").is(':checked')}
		});
	})
	
	$(".cuota").click(function(){
		
		$.ajax({
			type: "GET",
			url: "/PulBetWeb/carrito",
			data: {
				'action':"addcarrito",
				'idevento': $(this).attr("data-evento"),
				'idresultado': $(this).attr("data-resultado") 
				}
		});
		
	});

	//Cando se carga
	var acumulada = parseFloat($("#acumulada").val());
	var importe = parseFloat($("#importe").val());
	$("#ganancias").val(importe*acumulada);
	
	//Cando hai cambio
	$("#importe").change(function(){
		var acumulada = parseFloat($("#acumulada").val());
		var importe = parseFloat($("#importe").val());
		$("#ganancias").val(importe*acumulada);
	});
	

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

