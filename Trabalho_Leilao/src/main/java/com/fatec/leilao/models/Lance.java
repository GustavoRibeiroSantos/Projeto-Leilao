package com.fatec.leilao.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Lance implements Cruds {
	private int id;
	private Double valor;
	private Cliente cliente;
	private static List<Lance> lances = new ArrayList<>();
	
	public Lance() {}
	
	public void Registro() {
		Leilao leilao = new Leilao();
		Scanner obj = new Scanner(System.in);
		leilao.Consulta();
		System.out.println("Informe o 1 para veiculo e 2 para imovel: ");
		String tipo = obj.next();
		System.out.println("Informe o produto que queira dar o lance: ");
		String objId = obj.next();	
		System.out.print("Valor: ");
		valor = obj.nextDouble();
		System.out.println("CPF do Cliente: ");
		String cpfcont = obj.next();
		Cliente clienteToFind = new Cliente("","");
		Cliente clientelance = clienteToFind.FindByCPF(cpfcont);
			
		id = lances.size()+1;
		
		Lance lance = new Lance(id, valor, clientelance);
		
		System.out.println(tipo);
		//if(tipo == "1") {
			lance.lancarVeiculo(lance, Integer.parseInt(objId));
		//}else if(tipo == "2") {
			//lance.lancarVeiculo(lance, Integer.parseInt(objId));
		//}
		lances.add(lance);
		System.out.println("Cadastrada!!!");
		
	}
	
	public void lancarVeiculo(Lance lance, int idVeiculo) {
		Veiculo veiculo = new Veiculo();
		veiculo = veiculo.FindByID(idVeiculo);
		veiculo.adicionarLance(veiculo, lance);
	}
	
	public void lancarImovel1(Lance lance, int idImovel) {
		Imovel imovel =  new Imovel();
		imovel = imovel.FindByID(idImovel);
		imovel.adicionarLance(imovel, lance);
	}
	
	public void Consulta() {
		System.out.println(" | ===== Lista de Lances no sistema ===== | ");
		for (Lance lance : lances) {
			System.out.println(" | ID: " + lance.getId() +
					" | Nome: " + lance.getCliente().getNome() + "  \t| CPF do Cliente: " + lance.getCliente().getCpf() + "  \t| CPF do Cliente: " + lance.getValor()+ " | \n ");
		}
		
	}

	public void Atualizacao() {
		Scanner obj = new Scanner(System.in);
		System.out.print("Insira o ID do Lance: ");
		int idlance = obj.nextInt();
		
		for (Lance lance : lances) {
			if(idlance == lance.getId()) {
				System.out.print("Insira o nome: ");
				valor = obj.nextDouble();
				lance.setValor(valor);
				System.out.println("CPF do Cliente: ");
				String cpfcont = obj.next();
				Cliente clienteToFind = new Cliente("","");
				Cliente clientelance = clienteToFind.FindByCPF(cpfcont);
				lance.setCliente(clientelance);
				
				System.out.println("Dados do Lance foi atualizado!");
			}else {
				System.out.println("Lance não encontrado!!");
			}
		}
		
	}

	public void Remocao() {
		Scanner obj = new Scanner(System.in);
		System.out.print("Insira o CNPJ da instituicao: ");
		int idlance = obj.nextInt();
		
		for (int i = 0; i < lances.size(); i++) {
		    if (lances.get(i).getId() == idlance) {
		    		lances.remove(i);
		    		System.out.println("Cliente removido!!!");
			    }
		}
		
	}
	
	public Lance FindByID(int idconsulta) {
		for (Lance lance : lances) {
			if(lance.getId() == idconsulta) {
				return lance;
			}
		}
		return null;
	}
}
