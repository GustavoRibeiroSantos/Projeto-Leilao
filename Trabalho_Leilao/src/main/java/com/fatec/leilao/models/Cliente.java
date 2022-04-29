package com.fatec.leilao.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Cliente implements Cruds {
	private String cpf;
	private String nome;
	
	private static List<Cliente> clientes = new ArrayList<>();
	public Cliente() {}
	public void Registro() {
		Scanner obj = new Scanner(System.in);
		System.out.println(" | CPF: ");
		cpf = obj.nextLine();
		System.out.println(" | Nome Completo: ");
		nome = obj.nextLine();

		clientes.add(new Cliente(cpf, nome));

		System.out.println("Cadastrado!!!");
		
	}
	public void Consulta() {
		System.out.println(" | ===== Lista de Clientes no sistema ===== | ");
		for (Cliente Cliente : clientes) {
			System.out.println(
					" | Nome: " + Cliente.getNome() + "  \t| CPF do Cliente: " + Cliente.getCpf());
		}
	}
	
	public Cliente FindByCPF(String cpfconsulta) {
		for (Cliente Cliente : clientes) {
			if(Cliente.getCpf().equals(cpfconsulta)) {
				System.out.println("Cliente Encontrado!");
				return Cliente;
			}
		}
		return null;
	}
	
	public List<Cliente> getClienteList() {
		return clientes;
	}
	
	public void Atualizacao() {
		Scanner obj = new Scanner(System.in);
		System.out.println(" | Insira o CPF: ");
		String cpfcon = obj.nextLine();
		
		for (Cliente cliente : clientes) {
			if(cpfcon.equals(cliente.getCpf())) {
				System.out.println(" | Insira o nome: ");
				nome = obj.nextLine();
				cliente.setNome(nome);
				System.out.println(" | Insira o cpf: ");
				cpf = obj.nextLine();
				cliente.setCpf(cpf);
				
				System.out.println("Cadastro do Cliente foi atualizado!");
			}else {
				System.out.println("Cliente não encontrado!!");
			}
		}
		
	}
	public void Remocao() {
		Scanner obj = new Scanner(System.in);
		System.out.println(" | Insira o CPF do Cliente: ");
		String cpfcon = obj.next();
		
		for (int i = 0; i < clientes.size(); i++) {
		    if (clientes.get(i).getCpf().equals(cpfcon)) {
		      clientes.remove(i);
		      System.out.println("Cliente removido!!!");
		    }
		}
	}
	
	public void RealizarLance() {
		Lance lance = new Lance();
		lance.Registro();
	}
}
