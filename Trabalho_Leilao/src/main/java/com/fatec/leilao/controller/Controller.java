package com.fatec.leilao.controller;

import java.io.IOException;

import com.fatec.leilao.models.Cliente;
import com.fatec.leilao.models.Instituicao;
import com.fatec.leilao.models.Leilao;
import com.fatec.leilao.models.Veiculo;

public class Controller {
	Cliente cliente = new Cliente();
	Instituicao instituicao = new Instituicao();
	Leilao leilao = new Leilao();
	Veiculo veiculo = new Veiculo();
	
	
	public void cadastroCliente() {
		cliente.Registro();
	}
	
	public void cadastroInstituicao() {
		instituicao.Registro();
	}
	
	public void cadastroLeilao() {
		leilao.Registro();
	}
	
	public void cadastroLance() {
		cliente.RealizarLance();
	}
	
	public void cadastroVeiculo() {
		veiculo.Registro();
	}
	public void HistoricoVeiculo() {
		veiculo.HistoricoLanceVeiculo();
	}
	public void Importar() {
		try {
			leilao.ImportarLeilaoTXT();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
