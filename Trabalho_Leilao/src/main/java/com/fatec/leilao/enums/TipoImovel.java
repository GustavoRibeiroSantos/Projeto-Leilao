package com.fatec.leilao.enums;

public enum TipoImovel {
	Apartamento("apartamento"),
	Casa("casa"),
	Terreno("terreno"),
	Edificio("edificio");
	
	private String tipoImovel;
	
	private TipoImovel(String tipoImovel) {
		this.tipoImovel = tipoImovel;
	}

	public String getTipoImovel() {
		return tipoImovel;
	}
}
