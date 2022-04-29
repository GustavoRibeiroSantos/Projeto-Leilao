package com.fatec.leilao.models;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fatec.leilao.enums.TipoImovel;
import com.fatec.leilao.enums.TipoVeiculo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Imovel implements Cruds {
	private int id;
	private TipoImovel tipoimo;
	private Double lance_minimo;
	private String estado;
	private Lance lance_atual;
	private Leilao leilao;
	private List<Lance> lances;
	private static List<Imovel> imoveis = new ArrayList<>();
	
	public Imovel() {}
	
	public void Registro() {
		Scanner obj = new Scanner(System.in);
		
		System.out.println(" | Tipo de imovel ( (1) Casa - (2) Apartamento - (3) Terreno - (4) Edificio): ");
		int tipoopc = obj.nextInt();
		if( tipoopc == 1) {
			TipoImovel tipo = TipoImovel.Casa;
		}
		else if( tipoopc == 2) {
			TipoImovel tipo = TipoImovel.Apartamento;
		}
		else if( tipoopc == 3) {
			TipoImovel tipo = TipoImovel.Terreno;
		}
		else if( tipoopc == 4) {
			TipoImovel tipo = TipoImovel.Edificio;
		}
		
		System.out.print(" | Lance Minimo: ");
		lance_minimo = obj.nextDouble();
		
		System.out.print(" | Id do Lance: ");
		int idcont = obj.nextInt();
		Lance lanceAtual = lance_atual.FindByID(idcont);
		
		System.out.print(" | Estado: ");
		estado = obj.next();
		
		System.out.print(" | Id do Lance: ");
		int idleilao = obj.nextInt();
		Leilao leilao = new Leilao();
		Leilao LeilaoAtual = leilao.FindByID(idleilao);
		
		id = imoveis.size()+1;
		List<Lance> lances = new ArrayList();
		Imovel newimovel = new Imovel(id, tipoimo, lance_minimo, estado, lanceAtual, LeilaoAtual, lances);
		
		imoveis.add(newimovel);
		leilao.AddImovelAoLeilao(newimovel, LeilaoAtual.getId());
		
		System.out.println("Cadastrado!!!");
	}
	
	public Imovel FindByID(int idconsulta) {
		for (Imovel imovel : imoveis) {
			if(imovel.getId() == idconsulta) {
				return imovel;
			}
		}
		return null;
	}
	
	public void adicionarLance(Imovel imovel, Lance novoLance) {
		if(imovel.getLance_minimo() < novoLance.getValor()) {
			Lance lance = new Lance();
			lance = imovel.getLance_atual();
			if(lance.getValor() < novoLance.getValor()) {
				setLance_atual(novoLance);
				System.out.println("cadastrado !");
			}else {
				System.out.println("Desculpe, existem lances maiores");
			}
		}else {
			System.out.println("Desculpe mas o valor informado é inferior ao lance minimo");
		}
	}
	
	public void Consulta() {
		for (Imovel Imovel : imoveis) {
			System.out.println("===== Dados dos imoveis no sistema =====");
			System.out.println(" | Tipo: " + Imovel.getTipoimo() + " | Lance Minimo: "
					+ Imovel.getLance_minimo()+ " | \n");
		}
	}
	
	public void Atualizacao() {
		Scanner obj = new Scanner(System.in);
		System.out.print("Insira o ID do Imovel: ");
		int idcon = obj.nextInt();
		
		for (Imovel imovel : imoveis) {
			if(idcon == imovel.getId()) {	
				System.out.println("Tipo de imovel ( (1) Casa - (2) Apartamento - (3) Terreno - (4) Edificio): ");
				int tipoopc = obj.nextInt();
				if( tipoopc == 1) {
					imovel.setTipoimo(TipoImovel.Casa);
				}
				else if( tipoopc == 2) {
					imovel.setTipoimo(TipoImovel.Apartamento);
				}
				else if( tipoopc == 3) {
					imovel.setTipoimo(TipoImovel.Terreno);
				}
				else if( tipoopc == 4) {
					imovel.setTipoimo(TipoImovel.Edificio);
				}
				
				System.out.print("Insira o Lance Minimo: ");
				lance_minimo = obj.nextDouble();
				imovel.setLance_minimo(lance_minimo);
				
				System.out.print("Id do Lance: ");
				int idcont = obj.nextInt();
				Lance lanceAtual = lance_atual.FindByID(idcont);
				imovel.setLance_atual(lanceAtual);
				
				System.out.print("Id do Leilao: ");
				int idleilao = obj.nextInt();
				Leilao LeilaoAtual = leilao.FindByID(idleilao);
				imovel.setLeilao(LeilaoAtual);
				
				System.out.println("Cadastro do Imovel foi atualizado!");
			}else {
				System.out.println("Imovel não encontrado!!");
			}
		}
	}
	
public void HistoricoLance() {
		
	Scanner obj = new Scanner(System.in);
	int ProdutoDoHistorico;
	System.out.print("Digite o ID do Imovel desejado: ");
	ProdutoDoHistorico = obj.nextInt();
	
	for (Imovel imovel : imoveis) {
		if(ProdutoDoHistorico == imovel.getId()) {
			for (Lance lance : imovel.getLances()) {
					System.out.println(" | Produto Selecionado foi Imovel" + " | Lance Minimo: "
							+ lance.getValor()+ " | Nome do CLiente: " + lance.getCliente().getNome()+ " | \n");
				}
		}
	}
}

	public void Remocao() {
		Scanner obj = new Scanner(System.in);
		System.out.print(" | Insira o CPF do Cliente: ");
		int idcon = obj.nextInt();
		
		for (int i = 0; i < imoveis.size(); i++) {
		    if (imoveis.get(i).getId() == idcon) {
		    	imoveis.remove(i);
		    	leilao.RemocaoImovelListProduto(imoveis.get(i));
		      System.out.println("Imovel removido!!!");
		    }
		}
		
	}
	
}
