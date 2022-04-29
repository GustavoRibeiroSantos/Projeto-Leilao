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
public class Instituicao implements Cruds {
	private String CNPJ;
	private String nome;

	private static List<Instituicao> instituicoes = new ArrayList<>();
	public Instituicao() {}
	public void Registro() {
		Scanner obj = new Scanner(System.in);
		System.out.println(" | CNPJ: ");
		CNPJ = obj.next();
		System.out.println(" | Nome da Instituicao: ");
		nome = obj.next();

		instituicoes.add(new Instituicao(CNPJ, nome));

		System.out.println("Cadastrada!!!");
		
	}

	public void Consulta() {
		for (Instituicao instituicao : instituicoes) {
			System.out.println("===== Dados da Instituição =====");
			System.out.println(" | CNPJ: " + instituicao.getCNPJ() + " | Nome: "
					+ instituicao.getNome()+ " | \n");
		}
	}
	
	public Instituicao FindByCNPJ(String cnpjconsulta) {
		for (Instituicao instituicao : instituicoes) {
			if(instituicao.getCNPJ().equals(cnpjconsulta)) {
				return instituicao;
			}
		}
		return null;
	}

	public void Atualizacao() {
		Scanner obj = new Scanner(System.in);
		System.out.println("Insira o CNPJ: ");
		String cnpjcon = obj.nextLine();
		
		for (Instituicao instituicao : instituicoes) {
			if(cnpjcon.equals(instituicao.getCNPJ())) {
				System.out.println("Insira o nome: ");
				nome = obj.nextLine();
				instituicao.setNome(nome);
				System.out.println("Insira o CNPJ: ");
				CNPJ = obj.nextLine();
				instituicao.setCNPJ(CNPJ);
				
				System.out.println("Cadastro da Instituicao foi atualizado!");
			}else {
				System.out.println("Instituicao não encontrado!!");
			}
		}
		
	}

	public void Remocao() {
		Scanner obj = new Scanner(System.in);
		System.out.println("Insira o CNPJ da instituicao: ");
		String cnpjcon = obj.next();
		
		for (int i = 0; i < instituicoes.size(); i++) {
		    if (instituicoes.get(i).getCNPJ().equals(cnpjcon)) {
		    		instituicoes.remove(i);
		    		System.out.println("Instituicao removido!!!");
			    }
		}
	}
}
