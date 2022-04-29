package com.fatec.leilao.models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fatec.leilao.enums.Status;
import com.fatec.leilao.enums.TipoImovel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Leilao implements Cruds, Job {
	private int id;
	private String tipo;
	private Date inicio;
	private Date fim;
	private String endereco;
	private String cidade;
	private String estado;
	private String local;
	private List<Cliente> clientes;
	private List<Veiculo> veiculos;
	private List<Imovel> imoveis;
	private Instituicao instituicao;
	private List<Lance> lances;
	Status status;
	
	public Leilao() {}
	
	public Leilao(int id, String tipo, Date inicio, Date fim, String endereco, String cidade, String estado,
			String local, List<Cliente> clientes, List<Veiculo> veiculos, Instituicao instituicao, Status status) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.inicio = inicio;
		this.fim = fim;
		this.endereco = endereco;
		this.cidade = cidade;
		this.estado = estado;
		this.local = local;
		this.clientes = clientes;
		this.veiculos = veiculos;
		this.instituicao = instituicao;
		this.status = status;
	}

	public Leilao(int id, String tipo, Date inicio, Date fim, String endereco, String cidade, String estado,
			String local, List<Cliente> clientes, Instituicao instituicao, List<Imovel> imoveis, Status status) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.inicio = inicio;
		this.fim = fim;
		this.endereco = endereco;
		this.cidade = cidade;
		this.estado = estado;
		this.local = local;
		this.clientes = clientes;
		this.imoveis = imoveis;
		this.instituicao = instituicao;
		this.status = status;
	}
	
	private static List<Leilao> leiloes = new ArrayList<>();
	
	public void Registro() {
		Scanner obj = new Scanner(System.in);
		
		System.out.println(" | Tipo de Leilão (Imóvel ou Veiculo): ");
		String tipo = obj.next();
		
		System.out.print(" | Data de inicio: ");
		String dateI = obj.next();
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(dateI);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		Date dateInicio = date;
		
		System.out.print(" | Data de Fim: ");
		String dateF = obj.next();
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dateF);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date dateFim = date1;
		
		System.out.println(" | Endereço do Leilão: ");
		String endereco = obj.next();
		
		System.out.println(" | Cidade onde acontecerá o Leilão: ");
		String cidade = obj.next();
		
		System.out.println(" | Estado onde acontecerá o Leilão: ");
		String estado = obj.next();
		
		System.out.println(" | Local onde acontecerá o Leilão: ");
		String local = obj.next();
		
		System.out.println(" | Qual será a quantidade de clientes?  ");
		int quantCliente = obj.nextInt();
		List<Cliente> clientesPermitidos = new ArrayList<>();
		int i = 1;
		while(i  <= quantCliente) {
			System.out.println(" | Insira o CPF do Cliente: ");
			String auxcpf = obj.next();
			Cliente cliente = new Cliente("","");
			Cliente clienteConsulta = cliente.FindByCPF(auxcpf);
			if(auxcpf.equals(clienteConsulta.getCpf())){
				clientesPermitidos.add(clienteConsulta);
			}
			i++;
		}
		
		System.out.println(" | CNPJ do Instituição: ");
		String cnpjcont = obj.next();
		Instituicao instituicao = new Instituicao("","");
		Instituicao instituicaoResp = instituicao.FindByCNPJ(cnpjcont);
		
		id = leiloes.size()+1;
		
		if(tipo.equals("Veiculo")) {
			List<Veiculo> veiculos = new ArrayList();
			leiloes.add(new Leilao(id, tipo, dateInicio, dateFim, endereco, cidade, estado, local, 
				clientesPermitidos, veiculos, instituicaoResp, Status.ABERTO));
		} else {
			List<Imovel> imoveis = new ArrayList();
			leiloes.add(new Leilao(id, tipo, dateInicio, dateFim, endereco, cidade, estado, local, 
					clientesPermitidos, instituicaoResp, imoveis, Status.ABERTO));
		}
		
		System.out.println("Cadastrado!!!");
		
	}
	
	public void setLance(Lance lance) {
		lances.add(lance);
	}
	
	public List<Lance> getLance() {
		return lances;
	}
	
	public boolean iterateLance(double valor) {
		for(Lance lance : lances) {
			if(lance.getValor() > valor) {
				return false;
			}
		}
		return true;
	}
	
	public void Consulta() {
		for (Leilao leilao : leiloes) {
			System.out.println("===== Dados sobre o Leilão =====");
			System.out.println("ID: " + leilao.getId() + " | Data de Inicio: " + leilao.getInicio() + 
					" | Data de Fim: " + leilao.getFim() + " | Endereço: " + leilao.getEndereco() + " | Status: " + leilao.getStatus()+ " | \n");
		}
	}
	
	
	/*public void ConsultaProdutosDoLeilao(int idLeilao) {
		for (Leilao leilao : leiloes) {
			if(leilao.getId() == idLeilao) {
				if(leilao.getTipo().equals("Veiculo")) {
					List<Veiculo> listOrdenadaVeiculo = leilao.getVeiculos().stream().sorted(Comparator.comparing(Veiculo::getMarca)).collect(Collectors.toList());
					System.out.println(" | ===== Lista de produtos ===== | \n");
					for(Veiculo veiculo : listOrdenadaVeiculo) {
						System.out.println(" | Marca: " + veiculo.getMarca() + " | ");
					}
				}
				else if(leilao.getTipo().equals("Imovel")){
					List<Imovel> listOrdenadaImovel = leilao.getImoveis().stream().sorted(Comparator.comparing(Imovel::getEstado)).collect(Collectors.toList());
					System.out.println(" | ===== Lista de produtos ===== | \n");
					for(Imovel imovel : listOrdenadaImovel) {
						System.out.println(" | Marca: " + imovel.getEstado() + " | \n");
					}
				}else {
					System.out.println("Leilão não encontrado no sistema!");
				}
			}
			System.out.println(" | ID: " + leilao.getId() + " | "+ "\n | Data de Inicio: " + " | "+ leilao.getInicio() + 
					"\n | Data de Fim: " + leilao.getFim()+ " | " + "\n | Instituição: " + " | "+ leilao.getInstituicao()+ " | " + "\n | Endereço: " + leilao.getEndereco()+ " | " + "\n | Status: " + leilao.getStatus());
		}
	}*/

/*	public void ConsultaProdutoEspecificoDoLeilao(int idLeilao, int idProduto) {
		System.out.println(" | ===== Dados do produto selecionado ===== | \n");
		for (Leilao leilao : leiloes) {
			if(leilao.getId() == idLeilao) {
				if(leilao.getTipo().equals("Veiculo")) {
					List<Veiculo> listOrdenadaVeiculo = leilao.getVeiculos().stream().sorted(Comparator.comparing(Veiculo::getMarca)).collect(Collectors.toList());
					for(Veiculo veiculo : listOrdenadaVeiculo) {
						if(veiculo.getId() == idProduto) {
							System.out.println(" | Tipo: " + veiculo.getTipo() + " | ");
							System.out.println(" | Marca: " + veiculo.getMarca() + " | ");
							System.out.println(" | Modelo: " + veiculo.getModelo() + " | ");
							System.out.println(" | Lance Minimo: " + veiculo.getLance_minimo() + " | ");
						}
					}
				}
				else if(leilao.getTipo().equals("Imovel")){
					List<Imovel> listOrdenadaImovel = leilao.getImoveis().stream().sorted(Comparator.comparing(Imovel::getEstado)).collect(Collectors.toList());
					for(Imovel imovel : listOrdenadaImovel) {
						if(imovel.getId() == idProduto) {
							System.out.println(" | Tipo: " + imovel.getTipoimo() + " | ");
							System.out.println(" | Marca: " + imovel.getEstado() + " | ");
							System.out.println(" | Modelo: " + imovel.getLance_minimo() + " | ");
						}
					}
				}else {
					System.out.println("Leilão não encontrado no sistema!");
				}
			}
			System.out.println(" | ID: " + leilao.getId() + " | "+ "\n | Data de Inicio: " + " | "+ leilao.getInicio() + 
					"\n | Data de Fim: " + leilao.getFim()+ " | " + "\n | Instituição: " + " | "+ leilao.getInstituicao()+ " | " + "\n | Endereço: " + leilao.getEndereco()+ " | " + "\n | Status: " + leilao.getStatus());
		}
	} */

	public void Atualizacao() {
		Scanner obj = new Scanner(System.in);
		System.out.print(" | Insira o ID do Leilão: ");
		int idleilao = obj.nextInt();
		
		for (Leilao leilao : leiloes) {
			if(leilao.getId() == idleilao) {
				System.out.println(" | Tipo de Leilão (Imóvel ou Veiculo): ");
				String tipo = obj.nextLine();
				leilao.setTipo(tipo);
				
				System.out.print(" | Data de inicio: ");
				String dateI = obj.nextLine();
				try {
					Date dateInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dateI);
					leilao.setInicio(dateInicio);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				System.out.print(" | Data de Fim: ");
				String dateF = obj.nextLine();
				try {
					Date dateFim = new SimpleDateFormat("dd/MM/yyyy").parse(dateF);
					leilao.setFim(dateFim);
				} catch (ParseException e) {
					e.printStackTrace();
				}  
				
				System.out.println(" | Endereço do Leilão: ");
				String endereco = obj.nextLine();
				leilao.setEndereco(endereco);
				
				System.out.println(" | Cidade onde acontecerá o Leilão: ");
				String cidade = obj.nextLine();
				leilao.setCidade(cidade);
				
				System.out.println(" | Estado onde acontecerá o Leilão: ");
				String estado = obj.nextLine();
				leilao.setEstado(estado);
				
				System.out.println(" | Local onde acontecerá o Leilão: ");
				String local = obj.nextLine();
				leilao.setLocal(local);
				
				System.out.println(" | Você deseja adicionar ou excluir algum cliente da lista de participantes? (1) Sim / (2)Não  ");
				int primeiraOpcao = obj.nextInt();
				if(primeiraOpcao == 1) {
					System.out.println(" | Você deseja adicionar ou excluir ? (1) Adicionar / (2) Excluir  ");
					int segundaOpcao = obj.nextInt();
					
					if(segundaOpcao == 1) {
						int fimwhile = 1;
						while(fimwhile == 1) {
							List<Cliente> clientesPermitidos = new ArrayList<>();
								System.out.println(" | Insira o CPF do Cliente: ");
								String auxcpf = obj.next();
								Cliente cliente = new Cliente("","");
								Cliente clienteConsulta = cliente.FindByCPF(auxcpf);
								if(auxcpf.equals(clienteConsulta.getCpf())){
									clientesPermitidos.add(clienteConsulta);
								}
							System.out.print(" | Você ainda deseja adicionar algum cliente na lista de participantes? (1) Sim / (2)Não  ");
							fimwhile = obj.nextInt();
						} 
					}
					if(segundaOpcao == 2) {
						int fimwhile = 1;
						while(fimwhile == 1) {
							System.out.println(" | Insira o CPF do Cliente: ");
							String auxcpf = obj.nextLine();
							for(Cliente indiceCleinte : clientes) {
								if(auxcpf.equals(indiceCleinte.getCpf())){
									int index = 0;
									leiloes.get(index).getClientes().remove(indiceCleinte);
								}
							}
							System.out.print(" | Você ainda deseja adicionar algum cliente na lista de participantes? (1) Sim / (2)Não  ");
							fimwhile = obj.nextInt();
						} 
					}
				}
				
				System.out.println(" | CNPJ do Instituição: ");
				String cnpjcont = obj.nextLine();
				Instituicao instituicaoResp = instituicao.FindByCNPJ(cnpjcont);
				leilao.setInstituicao(instituicaoResp);
				
				System.out.println(" | Dados do Lance foi atualizado!");
			}else {
				System.out.println("Leilão não encontrado!!");
			}
		}
	}

	public void Remocao() {
		Scanner obj = new Scanner(System.in);
		System.out.print("Insira o ID do Leilão: ");
		int idleilao = obj.nextInt();
		
		for (int i = 0; i < leiloes.size(); i++) {
		    if (leiloes.get(i).getId() == idleilao) {
		    		leiloes.remove(i);
		    		System.out.println("Leilão removido!!!");
			}
		}
		
	}
	
	public void AddImovelAoLeilao(Imovel imovel, int idLeilao) {
		for(int i = 0; i < leiloes.size(); i++) {
			if(leiloes.get(i).getId() == idLeilao) {
				leiloes.get(i).getImoveis().add(imovel);
				System.out.println("Adicionado ao Leilao!");
			}
		}
	}

	public void AddVeiculoAoLeilao(Veiculo veiculo, int idLeilao) {
		for(int i = 0; i < leiloes.size(); i++) {
			if(leiloes.get(i).getId() == idLeilao) {
				leiloes.get(i).getVeiculos().add(veiculo);
				System.out.println("Adicionado ao Leilao!");
			}
		}
	}
	
	public void RemocaoImovelListProduto(Imovel imovel) {	
		for (int i = 0; i < leiloes.size(); i++) {
			for(int e = 0; e < leiloes.get(i).getImoveis().size(); e++) {
				leiloes.get(i).getImoveis().remove(e);
			}
		}
	}
	
	public void RemocaoVeiculoListProduto(Veiculo veiculo) {	
		for (int i = 0; i < leiloes.size(); i++) {
			if(leiloes.get(i).getId() == veiculo.getLeilao().getId()) {
				for(int e = 0; e < leiloes.get(i).getVeiculos().size(); e++) {
					if(leiloes.get(i).getVeiculos().get(e).getId() == veiculo.getId()) {
						leiloes.get(i).getVeiculos().remove(e);
					}
					
				}
			}
		}
	}
	
	/*public void ImprivirPorData() {
		List<Leilao> listordenada = leiloes.stream().sorted(Comparator.comparing(Leilao::getInicio)).collect(Collectors.toList());
        for(Leilao leilao : listordenada){
        	System.out.println("ID: " + leilao.getId() + " | Data de Inicio: " + leilao.getInicio() + " | Status: " + leilao.getStatus());
        }
	} */
	
	public Leilao FindByID(int idconsulta) {
		for (Leilao leilao : leiloes) {
			if(leilao.getId() == idconsulta) {
				System.out.println("Leilão encontrado!");
				return leilao;
			}
		}
		System.out.println("Leilão não encontrado!");
		return null;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}
	
	public void FiltrarVeiculoA() {
		Scanner obj = new Scanner(System.in);
		
		int idLeilo;
		System.out.print("Digite o id do leilo");
		idLeilo = obj.nextInt();
		
		Double Min, Max;
		System.out.print("Digite seu valor minimo desejado:");
		Min = obj.nextDouble();
		System.out.print("Digite seu valor maximo desejado:");
		Max = obj.nextDouble();
		
		ArrayList<Veiculo> ProdutoSelecionadoMaxMin = new ArrayList<>();
		
		for(Leilao leilao : leiloes) {
			if(idLeilo == leilao.getId()) {
				for (Veiculo veiculo : ProdutoSelecionadoMaxMin) {
					if(Min >= veiculo.getLance_minimo() && Max <= veiculo.getLance_minimo()) {
						System.out.println("Tipo: " + veiculo.getTipo() + "Marca: " + veiculo.getMarca() 
						+ "Modelo: " + veiculo.getModelo() + " | Lance Minimo: " + veiculo.getLance_minimo());
					}
				}
			}
		}
	}
	
	public void FiltrarVeiculoB() {
		Scanner obj = new Scanner(System.in);
		
		int idLeilo;
		System.out.print("Digite o id do leilo");
		idLeilo = obj.nextInt();
		
		String PalavraChave, nomechave, descricaochave;
		System.out.print("Digite se  nome ou descricao o desejado a procurar:");
		PalavraChave = obj.next();
		ArrayList<Veiculo> ProdutoSelecionadoChave = new ArrayList<>();
		
		
		if(PalavraChave == "nome") {
			System.out.print("Digite o modelo a procurar: ");
			nomechave = obj.next();
			for(Leilao leilao : leiloes) {
				if(idLeilo == leilao.getId()) {
					for(Veiculo veiculo : ProdutoSelecionadoChave) {
						if(nomechave == veiculo.getModelo()) {
							System.out.println("Tipo: " + veiculo.getTipo() + "Marca: " + veiculo.getMarca() 
							+ "Modelo: " + veiculo.getModelo() + " | Lance Minimo: " + veiculo.getLance_minimo());
						}
					
					}
				}
			}
			
		}
		
		if(PalavraChave == "descricao") {
			System.out.print("Digite a palavra chave(Modelo ou descricao): ");
			descricaochave = obj.next();
			for(Leilao leilao : leiloes) {
				if(idLeilo == leilao.getId()) {
					for(Veiculo veiculo : ProdutoSelecionadoChave) {
						if(descricaochave == veiculo.getDescricao()) {
							System.out.println("Tipo: " + veiculo.getTipo() + "Marca: " + veiculo.getMarca() 
							+ "Modelo: " + veiculo.getModelo() + " | Lance Minimo: " + veiculo.getLance_minimo());
						}
					
					}
				}
			}
			
		}
	}
	
	public void FiltrarVeiculoC() {
		
		Scanner obj = new Scanner(System.in);
		
		int idLeilo;
		System.out.print("Digite o id do leilo");
		idLeilo = obj.nextInt();
		
		String TipoVeiculoFiltrado;
		
		ArrayList<Veiculo> ProdutoSelecionadoProduto = new ArrayList<>();

		System.out.print("Tipo do Produto: ");
		TipoVeiculoFiltrado = obj.next();
		for(Leilao leilao : leiloes) {
			if(idLeilo == leilao.getId()) {
				for (Veiculo veiculo : ProdutoSelecionadoProduto) {
					if(TipoVeiculoFiltrado.equals(veiculo.getTipo().toString())) {
						 System.out.println("Tipo: " + veiculo.getTipo() + "Marca: " + veiculo.getMarca() 
						+ "Modelo: " + veiculo.getModelo() + " | Lance Minimo: " + veiculo.getLance_minimo());
					}
				}
			}
		}
		
	}
	
public void FiltrarImovelA() {
		
		Scanner obj = new Scanner(System.in);
		Double Min, Max;
		int idLeilo;
		
		System.out.print("Digite o id do leilo");
		idLeilo = obj.nextInt();
		
		System.out.print("Digite seu valor minimo desejado:");
		Min = obj.nextDouble();
		System.out.print("Digite seu valor maximo desejado:");
		Max = obj.nextDouble();
		
		ArrayList<Imovel> ProdutoSelecionadoMaxMin = new ArrayList<>();
		
		for(Leilao leilao : leiloes) {
			if(idLeilo == leilao.getId()) {
				for (Imovel imovel : leilao.getImoveis()) {
					if(Min >= imovel.getLance_minimo() && Max <= imovel.getLance_minimo()) {
						System.out.println("Tipo: " + imovel.getTipoimo() + " | Lance Minimo: "
								+ imovel.getLance_minimo());
					}
				}
			}
		}
	}
	
	
	public void FiltrarImovelB() {
		Scanner obj = new Scanner(System.in);
		
		int idLeilo;
		System.out.print("Digite o id do leilo");
		idLeilo = obj.nextInt();
		
		String PalavraChave, nomechave, descricaochave;
		System.out.print("Digite se  nome ou descricao o desejado a procurar:");
		PalavraChave = obj.next();
		ArrayList<Imovel> ProdutoSelecionadoChave = new ArrayList<>();
		
		if(PalavraChave == "nome") {
			System.out.print("Digite a palavra chave(Modelo ou descricao): ");
			nomechave = obj.next();
			for(Leilao leilao : leiloes) {
				if(idLeilo == leilao.getId()) {
					for(Imovel imovel : ProdutoSelecionadoChave) {
						if(nomechave == imovel.getEstado()) {
							System.out.println("Tipo: " + imovel.getTipoimo() + " | Lance Minimo: "
									+ imovel.getLance_minimo());
						}
					
					}
				}
			}
			
		}
		
		if(PalavraChave == "descricao") {
			System.out.print("Digite a palavra chave(Modelo ou descricao): ");
			descricaochave = obj.next();
			for(Leilao leilao : leiloes) {
				if(idLeilo == leilao.getId()) {
					for(Imovel imovel : ProdutoSelecionadoChave) {
						if(descricaochave == imovel.getEstado()) {
							System.out.println("Tipo: " + imovel.getTipoimo() + " | Lance Minimo: "
									+ imovel.getLance_minimo());
						}
					
					}
				}
			}
			
		}
	}
	
	public void FiltrarImovelC() {
		
		Scanner obj = new Scanner(System.in);
		
		int idLeilo;
		System.out.print("Digite o id do leilo");
		idLeilo = obj.nextInt();
		
		String TipoImovelFiltrado;
		
		ArrayList<Imovel> ProdutoSelecionadoProduto = new ArrayList<>();

		System.out.print("Tipo do Imovel: ");
		TipoImovelFiltrado = obj.next();
		for(Leilao leilao : leiloes) {
			if(idLeilo == leilao.getId()) {
				for (Imovel imovel : ProdutoSelecionadoProduto) {
					if(TipoImovelFiltrado.equals(imovel.getTipoimo().toString())) {
						System.out.println("Tipo: " + imovel.getTipoimo() + " | Lance Minimo: "
								+ imovel.getLance_minimo());
					}
				}
			}
		}
		
	}
	
	public void ImportarLeilaoTXT() throws IOException {
		 
		 Scanner obj = new Scanner(System.in);
		 String  nomeArquivo;
		    
		    FileWriter arquivo = new FileWriter("../Trabalho_Leilao/Leilao.det");
			PrintWriter gravarArq = new PrintWriter(arquivo);
			
			gravarArq.println("===== Dados sobre o Leilão =====\n");
			//leiloes
			for (Leilao leilao : leiloes) {
				gravarArq.println("ID: " + leilao.getId() + " | Data de Inicio: " + leilao.getInicio() + 
						" | Data de Fim: " + leilao.getFim() + " | Endereço: " + leilao.getEndereco() + " | Status: " + leilao.getStatus()+ " | \n");
				//print dos veiculos
				for (Veiculo veiculo : leilao.getVeiculos()) {
					gravarArq.println(" | ID: " + veiculo.getId() +" | "+ "\n | Tipo: " + veiculo.getTipo() +" | "+ "\n | Marca: " + veiculo.getMarca() +" | " 
					+ "\n | Modelo: " + veiculo.getModelo()+" | " + "\n | Lance Minimo: " + veiculo.getLance_minimo()+" | \n");
					
					//historico veiculos
					for (Lance lance : veiculo.getLances()) {
						gravarArq.println(" | Produto Selecionado foi Veiculo" + " | Lance Minimo: "
								+ lance.getValor()+ " | Nome do CLiente: " + lance.getCliente().getNome()+ " | \n");
					}
					
					//print do imovel
					for (Imovel Imovel : leilao.getImoveis()) {
						gravarArq.println(" | Tipo: " + Imovel.getTipoimo() + " | Lance Minimo: "
								+ Imovel.getLance_minimo()+ " | \n");
					}
					
					//historico imovel
					for (Lance lance : leilao.getLances()) {
					gravarArq.println(" | Produto Selecionado foi Imovel" + " | Lance Minimo: "
					+ lance.getValor()+ " | Nome do CLiente: " + lance.getCliente().getNome()+ " | \n");
					}
					
					//cliente
					for (Cliente Cliente : leilao.getClientes()) {
						gravarArq.println(
						" | Nome: " + Cliente.getNome() + "  \t| CPF do Cliente: " + Cliente.getCpf());
					}
			}
		    arquivo.close();
		    System.out.println("Arquivo .det exportado para a pasta raiz do projeto.");
		  }
	
	/*@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		for(int i = 0; i < leiloes.size(); i++) {
			System.out.println("Procurando... ");
			if(leiloes.get(i).getFim().compareTo(new Date()) > 0) {
				System.out.println("Leilão em Andamento...");
				leiloes.get(i).setStatus(status.ANDAMENTO);
			}
			if(leiloes.get(i).getFim().compareTo(new Date()) < 0) {
				leiloes.get(i).setStatus(status.FINALIZADO);
				if(leiloes.get(i).getTipo().equals("Veiculo")) {
					List<Veiculo> listOrdenadaVeiculo = leiloes.get(i).getVeiculos().stream().sorted(Comparator.comparing(Veiculo::getMarca)).collect(Collectors.toList());
					System.out.println(" | ===== Leilão Finalizado!!! ===== | \n");
					System.out.println(" | ===== Lista de produtos ===== | \n");
					for(Veiculo veiculo : listOrdenadaVeiculo) {
						System.out.println("\n | Marca: " + veiculo.getMarca() + " | \n");
						System.out.println(" | Nome do ganhador: " + veiculo.getLance_atual().getCliente().getNome() + " | \n");
						System.out.println(" | Valor do Lance: R$ " + veiculo.getLance_atual().getValor() + " | \n");
					}
				}
				if(leiloes.get(i).getTipo().equals("Imovel")) {
					List<Imovel> listOrdenadaImovel = leiloes.get(i).getImoveis().stream().sorted(Comparator.comparing(Imovel ::getEstado)).collect(Collectors.toList());
					System.out.println(" | ===== Leilão Finalizado!!! ===== | ");
					System.out.println(" | ===== Lista de produtos ===== | \n");
					for(Imovel imovel : listOrdenadaImovel) {
						System.out.println(" | Marca: " + imovel.getEstado() + " | ");
						System.out.println(" | Nome do ganhador: " + imovel.getLance_atual().getCliente().getNome() + " | ");
						System.out.println(" | Valor do Lance: R$ " + imovel.getLance_atual().getValor() + " | \n");
					}
				}
			}
		}
		System.out.println("Analizando leiloes... "+new Date());
	}*/
	}
}

