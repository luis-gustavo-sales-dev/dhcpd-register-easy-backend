package br.dev.luisgustavosales.dhcpregister.configs;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class CommandExecutor {
	
	// Controla se o comando já está sendo executado
	public static volatile Integer runningCommand = 0;

	private String restartDhcpServer = "systemctl restart isc-dhcp-server.service";
	private String tagDHCPService = "DHCPService";

	public void restartDhcpService() {
		//System.out.println("Trying to restart dhcp service.");
		try {
			
			// Executa o comando se nenhum outro processo estiver executando
			if (CommandExecutor.runningCommand == 0) {
				// Está liberado e pode executar
				CommandExecutor.runningCommand = 1;
				//System.out.println("Executando comando.");
				//System.out.println("CommandExecutor.runningCommand" + CommandExecutor.runningCommand);
				var command = Runtime.getRuntime().exec(restartDhcpServer);
				//System.out.println("Aguardando executar comando.");
				command.waitFor();
				//System.out.println("CommandExecutor.runningCommand" + CommandExecutor.runningCommand);
				CommandExecutor.runningCommand = 0;
				//System.out.println("CommandExecutor.runningCommand" + CommandExecutor.runningCommand);
			} else {
				// Tenta reexecutar o comando a cada x segundos
				while(true) {
					
					// Esperar 2 segundos para tentar executar
					Thread.sleep(2000);
					
					//System.out.print(tagDHCPService+":");
					//System.out.println("Tentando executar após intervalo de 2 segundos.");
					
					try {
						if(CommandExecutor.runningCommand == 0) {
							CommandExecutor.runningCommand = 1;
							Process retryCommand;
							retryCommand = Runtime.getRuntime().exec(restartDhcpServer);
							retryCommand.waitFor();
							CommandExecutor.runningCommand = 0;
							
							//System.out.print(tagDHCPService+":");
							//System.out.println("Executei após liberação.");
						}
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						CommandExecutor.runningCommand = 0;
						//System.out.print(tagDHCPService+":");
						//System.out.println("Falha na tentativa de reiniciar após estar bloqueado.");
					}
					
					// Se commando já foi executado saí do loop
					if(CommandExecutor.runningCommand == 0) {
						//System.out.print(tagDHCPService+":");
						//System.out.println("Saí do loop.");
						break;
					}
				}
			}
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.print(tagDHCPService+":");
			//System.out.println("Não foi possível reiniciar o servidor do DHCP.");
		}
		//System.out.print(tagDHCPService+": RESTARTED");
	}
}
