package com.fatec.leilao.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.fatec.leilao.controller.Controller;
import com.fatec.leilao.enums.Status;
import com.fatec.leilao.models.Cliente;
import com.fatec.leilao.models.Instituicao;
import com.fatec.leilao.models.Lance;
import com.fatec.leilao.models.Leilao;
import com.fatec.leilao.models.Veiculo;

public class Main {

	public static void main(String[] args) throws SchedulerException{
		Controller controller = new Controller();
		int option = 0;
        Scanner input = new Scanner(System.in);

		do {
			System.out.println("1 - Cadastrar um cliente");
			System.out.println("2 - Cadastrar uma instituição");
			System.out.println("3 - Cadastrar um leilao");
			System.out.println("4 - Cadastro lance");
			System.out.println("5 - Cadastro veiculo");
			System.out.println("6 - Historico de lance no veiculo");
			System.out.println("7 - Exportar");
			
			
			option = input.nextInt();
			
			switch(option) {
			case 1:
				controller.cadastroCliente();
				break;
			case 2:
				controller.cadastroInstituicao();
				break;
			case 3:
				controller.cadastroLeilao();
				break;
			case 4:
				controller.cadastroLance();
				break;
			case 5:
				controller.cadastroVeiculo();
				break;
			case 6:
				controller.HistoricoVeiculo();
				break;
			case 7:
				controller.Importar();
				break;
			}
			
		}while(option != 100);

		JobDetail j = JobBuilder.newJob(Leilao.class).build();

		Trigger t = TriggerBuilder.newTrigger().withIdentity("Leilao")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(05)
						.repeatForever()).build();
		
		Scheduler s = StdSchedulerFactory.getDefaultScheduler();
		s.start();
		s.scheduleJob(j, t);
   }
}


