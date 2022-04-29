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
public class Veiculo implements Cruds {
	private static final String ve = null;
	private int id;
	private TipoVeiculo tipo;
	private String marca;
	private String modelo;
	private String descricao;
	private Double lance_minimo;
	private Lance lance_atual;
	private Leilao leilao;
	private List<Lance> lances;
	
	private static List<Veiculo> veiculos = new ArrayList<>();
	
	public Veiculo() {}
	
	public void Registro() {
		Scanner obj = new Scanner(System.in);
		
		System.out.println(" | Tipo de Veiculo ((1) Carro - (2) Moto): ");
		int tipoopc = obj.nextInt();
		TipoVeiculo tipoveiculo = null;
		if( tipoopc == 1) {
			tipoveiculo = TipoVeiculo.Carro;
		}else {
			tipoveiculo = TipoVeiculo.Moto;
		}

		System.out.print(" | Marca: ");
		marca = obj.next();
		
		System.out.print(" | Modelo: ");
		modelo = obj.next();
		
		System.out.print(" | Descricao: ");
		descricao = obj.next();
		
		System.out.print(" | Lance Minimo: ");
		lance_minimo = obj.nextDouble();
		
		//System.out.print(" | Id do Lance: ");
		//int idcont = obj.nextInt();
		Lance lance_atual = new Lance(0, lance_minimo, null);
		//Lance lanceAtual = lance_atual.FindByID(idcont);
		
		System.out.print(" | Id do Leilao: ");
		int idleilao = obj.nextInt();
		Leilao leilao = new Leilao();
		Leilao LeilaoAtual = leilao.FindByID(idleilao);
		
		id = veiculos.size()+1;
		List<Lance> lances = new ArrayList();
		Veiculo newveiculo = new Veiculo(id, tipoveiculo, marca, modelo, descricao, lance_minimo, lance_atual, LeilaoAtual, lances);
		veiculos.add(newveiculo);
		
		Leilao leilaotest = new Leilao();
		leilaotest.AddVeiculoAoLeilao(newveiculo, LeilaoAtual.getId());
		
		System.out.println("Cadastrado!!!");
		
	}
	
	public Veiculo FindByID(int idconsulta) {
		for (Veiculo veiculo : veiculos) {
			if(veiculo.getId() == idconsulta) {
				return veiculo;
			}
		}
		return null;
	}
	
	public void adicionarLance(Veiculo veiculo, Lance novoLance) {
		if(veiculo.getLance_minimo() < novoLance.getValor()) {
			Lance lance = new Lance();
			lance = veiculo.getLance_atual();
			if(lance.getValor() < novoLance.getValor()) {
				setLance_atual(novoLance);
				veiculo.getLances().add(novoLance);
				System.out.println("cadastrado !");
			}else {
				System.out.println("Desculpe, existem lances maiores");
			}
		}else {
			System.out.println("Desculpe mas o valor informado é inferior ao lance minimo");
		}
	}
	
	public void Consulta() {
		System.out.println(" | ===== Lista de Veiculos no sistema ===== | \n");
		for (Veiculo veiculo : veiculos) {
			System.out.println(" | ID: " + veiculo.getId() +" | "+ "\n | Tipo: " + veiculo.getTipo() +" | "+ "\n | Marca: " + veiculo.getMarca() +" | " 
			+ "\n | Modelo: " + veiculo.getModelo()+" | " + "\n | Lance Minimo: " + veiculo.getLance_minimo()+" | \n");
		}
	}

	public void Atualizacao() {
		Scanner obj = new Scanner(System.in);
		System.out.print(" | Insira o ID do Veiculo: ");
		int idcon = obj.nextInt();
		
		for (Veiculo veiculo : veiculos) {
			if(idcon == veiculo.getId()) {	
				System.out.println(" | Tipo de Veiculo ((1) Carro - (2) Moto): ");
				int tipoopc = obj.nextInt();
				if( tipoopc == 1) {
					veiculo.setTipo(TipoVeiculo.Carro);
				}else {
					veiculo.setTipo(TipoVeiculo.Moto);
				}

				System.out.print(" | Marca: ");
				marca = obj.nextLine();
				veiculo.setMarca(marca);
				
				System.out.print(" | Modelo: ");
				modelo = obj.nextLine();
				veiculo.setModelo(modelo);
				
				System.out.print(" | Descricao: ");
				descricao = obj.nextLine();
				veiculo.setDescricao(descricao);
				
				System.out.print(" | Lance Minimo: ");
				lance_minimo = obj.nextDouble();
				veiculo.setLance_minimo(lance_minimo);
				
				System.out.print(" | Id do Leilao: ");
				int idleilao = obj.nextInt();
				Leilao LeilaoAtual = leilao.FindByID(idleilao);
				veiculo.setLeilao(LeilaoAtual);
				
				System.out.println(" | Cadastro do Veiculo foi atualizado!");
			}else {
				System.out.println("Veiculo não encontrado!!");
			}
		}
		
	}
	
public void HistoricoLanceVeiculo() {

		Scanner obj = new Scanner(System.in);
		int ProdutoDoHistorico;
		System.out.print("Digite o ID do veiculo desejado: ");
		ProdutoDoHistorico = obj.nextInt();
		
		for (Veiculo veiculo : veiculos) {
			if(ProdutoDoHistorico == veiculo.getId()) {
				for (Lance lance : veiculo.getLances()) {
						System.out.println(" | Produto Selecionado foi Veiculo" + " | Lance Minimo: "
								+ lance.getValor()+ " | Nome do CLiente: " + lance.getCliente().getNome()+ " | \n");
					}
			}
		}
	}


	public void Remocao() {
		Scanner obj = new Scanner(System.in);
		System.out.println(" | Insira o ID do Produto: ");
		int idcon = obj.nextInt();
		
		for (int i = 0; i < veiculos.size(); i++) {
		    if (veiculos.get(i).getId() == idcon) {
		    		veiculos.remove(i);
		    		leilao.RemocaoVeiculoListProduto(veiculos.get(i));
		    		System.out.println("Veiculo removido!!!");
			    }
		}
	}
		
}
