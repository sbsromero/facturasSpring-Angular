package com.example.demo.util.paginator;

public class PageItem {

	private int numero;
	private boolean isActual;
	
	public PageItem(int numero, boolean isActual) {
		super();
		this.numero = numero;
		this.isActual = isActual;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isActual() {
		return isActual;
	}

	public void setActual(boolean isActual) {
		this.isActual = isActual;
	}
	
}
