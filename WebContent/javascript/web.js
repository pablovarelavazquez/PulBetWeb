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

	});
	
	$("#searchdeporte").change(function(){
		var selectedSport = $(this).children("option:selected").val();
		$.ajax({
			type: "GET",
			url: "/PulBetWeb/evento",
			data: { 'id': selectedSport,
				'action': "findCompetition"},
				contentType:"application/x-www-form-urlencoded; charset=ISO-8859-1",
				dataType:"json",
				success: function (deportesArray) {

					if(!$("#searchcompeticion").html().isEmpty){
						$("#searchcompeticion").html("");
					}
					$("#searchcompeticion").html("<option></option>");
					for(var i = 0; i < deportesArray.length; i++){
						$("#searchcompeticion").html($("#searchcompeticion").html()+"<option value="+deportesArray[i].id+">"+deportesArray[i].nome+"</option>")
					}
					

				}
		});

	});
	
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
				},
			success: function () {
				location.reload(true);
			}
		});
		
	});
	
	$(".cuota").mouseover().css("cursor","pointer");
	$(".dellinea").mouseover().css("cursor","pointer");
	$(".masdetalle").mouseover().css("cursor","pointer");
	
	
	$(".dellinea").click(function(){
		
		$.ajax({
			type: "GET",
			url: "/PulBetWeb/carrito",
			data: {
				'action':"delcarrito",
				'idevento': $(this).attr("data-evento"),
				'idresultado': $(this).attr("data-resultado") 
				},
			success: function () {
				location.reload(true);
			}
		});
		
	});

	//Cando se carga
	var acumulada = parseFloat($("#acumulada").val());
	var importe = parseFloat($("#importe").val());
	$("#ganancias").val((importe*acumulada).toFixed(2));
	$("#acumulada").val(acumulada.toFixed(2));
	
	//Cando hai cambio
	$("#importe").change(function(){
		var acumulada = parseFloat($("#acumulada").val());
		var importe = parseFloat($("#importe").val());
		$("#ganancias").val((importe*acumulada).toFixed(2));
	});
	
	 $('.masdetalle').click(function () {
		 
		 var estediv = $(this);
		 
		 if((estediv.find(".fillo").html() == "")){
		 
		 $.ajax({
				type: "GET",
				url: "/PulBetWeb/apuesta",
				data: { 'id': $(this).attr("data-id"),
					'action':"detail"},
					contentType:"application/x-www-form-urlencoded; charset=ISO-8859-1",
					dataType:"json",
					success: function (lineasArray) {

						for(var i = 0; i < lineasArray.length; i++){
							
								estediv.find(".fillo").html(estediv.find(".fillo").html()+"<div class='lineadetalle'><p class='detres'>"+lineasArray[i].resultado+"</p><p class='detcuot'> Cuota: "+lineasArray[i].cuota+"</p><p class='detev'>"+lineasArray[i].local+" VS "+lineasArray[i].visitante+"  "+lineasArray[i].fecha+"</p></p><p class='dettipres'>"+lineasArray[i].tipoResultado+"</p><p class='detest "+lineasArray[i].clase+"'>"+lineasArray[i].mensaje+"</p></div>");
							
						}
					}
					
			});
		 } else {
			 estediv.find(".fillo").html("");
		 }


	  });
	 
	 $(".historycheck").change(function(){
		 
		 if($(this).val() == 3){
			 $("#findfecha input").prop('disabled', false);
		 } else {
			 $("#findfecha input").prop('disabled', true);
		 }
		 
	 });
	 
});


function desplegarMenu() {
	document.getElementById("meumenudes").classList.toggle("show");
}

//Menu desplegable
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

