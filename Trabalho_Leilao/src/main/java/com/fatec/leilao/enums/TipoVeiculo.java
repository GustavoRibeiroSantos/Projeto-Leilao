package com.fatec.leilao.enums;

public enum TipoVeiculo {
	Carro("carro"),
	Moto("moto");
	
	private String tipoVeiculo;
	
	private TipoVeiculo(String tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}
	
	public String getTipoVeiculo() {
		return tipoVeiculo;
	}
}
