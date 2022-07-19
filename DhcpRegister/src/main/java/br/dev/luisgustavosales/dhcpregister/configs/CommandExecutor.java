package br.dev.luisgustavosales.dhcpregister.configs;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class CommandExecutor {
	
	// Controla se o comando já está sendo executado
	public static volatile Integer runningCommand = 0;

	private String restartDhcpServer = "systemctl restart isc-dhcp-server.service";
	private String tagDHCPService = "DHCPService";

	public void restartDhcpService() {
		System.out.println("Trying to restart dhcp service.");
		try {
			
			// Executa o comando se nenhum outro processo estiver executando
			if (CommandExecutor.runningCommand == 0) {
				// Está liberado e pode executar
				CommandExecutor.runningCommand = 1;
				var command = Runtime.getRuntime().exec(restartDhcpServer);
				command.waitFor();
				CommandExecutor.runningCommand = 0;				
			} else {
				// Tenta reexecutar o comando a cada x segundos
				while(true) {
					
					// Através de uma thread
					ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
					exec.scheduleAtFixedRate(new Runnable() {
					  @Override
					  public void run() {
						try {
							if(CommandExecutor.runningCommand == 0) {
								CommandExecutor.runningCommand = 1;
								Process retryCommand;
								retryCommand = Runtime.getRuntime().exec(restartDhcpServer);
								retryCommand.waitFor();
								CommandExecutor.runningCommand = 0;
							}
						} catch (IOException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							CommandExecutor.runningCommand = 0;
							System.out.print(tagDHCPService+":");
							System.out.println("Falha na tentativa de reiniciar após estar bloqueado.");
						}
					  }
					}, 0, 2, TimeUnit.SECONDS);
					
					// Se commando já foi executado saí do loop
					if(CommandExecutor.runningCommand == 0) {
						break;
					}
				}
			}
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print(tagDHCPService+":");
			System.out.println("Não foi possível reiniciar o servidor do DHCP.");
		}
		System.out.print(tagDHCPService+": RESTARTED");
	}
}
