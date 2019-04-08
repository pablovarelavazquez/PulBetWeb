package com.pulbet.web.model;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
	
	public List<LineaCarrito> lineas = null;
	
	public Carrito() {
		lineas = new ArrayList<LineaCarrito>();
	}
	
	public void add(LineaCarrito lc) {
		lineas.add(lc);
	}
	
	public boolean hasEvent(Long idEvento) {
		
		boolean hasEvent =  false;
		
		for(LineaCarrito linea : lineas) {
			if (linea.getEvento().getIdEvento() == idEvento) {
				hasEvent = true;
			}
		}
		
		return hasEvent;
	}
	
	public List<LineaCarrito> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaCarrito> lineas) {
		this.lineas = lineas;
	}
	

}
