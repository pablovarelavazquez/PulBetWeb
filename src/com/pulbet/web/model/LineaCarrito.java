package com.pulbet.web.model;

import com.pvv.pulbet.model.Evento;
import com.pvv.pulbet.model.Resultado;

public class LineaCarrito {
	
	public Evento evento = null;
	public Resultado resultado = null;
	
	public LineaCarrito(Evento evento, Resultado resultado) {
		setEvento(evento);
		setResultado(resultado);
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

}
