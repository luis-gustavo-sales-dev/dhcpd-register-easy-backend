package br.dev.luisgustavosales.dhcpregister.filegenerator;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.net.util.SubnetUtils;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceUserGroup;
import br.dev.luisgustavosales.dhcpregister.entities.IpRangeGroup;
import br.dev.luisgustavosales.dhcpregister.services.DeviceRegisterService;
import br.dev.luisgustavosales.dhcpregister.services.DeviceUserGroupService;

@Service
public class DhcpFileGenerator {
	
	@Autowired
	private DeviceRegisterService deviceRegisterService;
	
	@Autowired
	private DeviceUserGroupService deviceUserGroupService;
	
	HashMap<Long, DhcpGroupIpPool> mapDeviceUsersPools = new HashMap<>();
	
	/*
	 * 1 - Pegar todos os grupos
	 * 2 - Pegar todos os registros de cada grupo
	 * 3 - Gerar a configuração baseado nas informações acima
	 * */
	
	// 1 - Pegar todos os grupos
	private List<DeviceUserGroup> getDeviceGroupFromDatabase() {
		return this.deviceUserGroupService.findAll();
	}
	
	
	// Gera um Pool de endereços para o grupo em questão
	private DhcpGroupIpPool generateIPPool(DeviceUserGroup deviceUserGroup) {
		
		DhcpGroupIpPool ipPool = new DhcpGroupIpPool(deviceUserGroup.getId(), deviceUserGroup.getIprangegroup().size());
		
		// Gerar endereços e inserir os pools em cada ranges
		/*deviceUserGroup.getIprangegroup().stream().forEach( (iprange) -> {
			
			SubnetUtils utils = new SubnetUtils(iprange.getRange());
			
			List<String> allIps = new ArrayList<String>(
					Arrays.asList(utils.getInfo().getAllAddresses()));
			
			// Aqui está errado. Precisa fazer uma separação por range
			allIps.stream().forEach( (ip) -> ipPool);
		});*/
		int[] index = { 0 };
		for(IpRangeGroup range: deviceUserGroup.getIprangegroup()) {
			
			SubnetUtils utils = new SubnetUtils(range.getRange());
			
			List<String> allIps = new ArrayList<String>(
					Arrays.asList(utils.getInfo().getAllAddresses()));
			
			allIps.stream().forEach( (ip) -> {
				ipPool.getRange().get(index[0]).add(ip);
			});
			
			index[0]++;
		}


		mapDeviceUsersPools.put(deviceUserGroup.getId(), ipPool);
		return ipPool;
	}
	
	private void generateAllIPPools(List<DeviceUserGroup> deviceGroups) {
		deviceGroups.stream().forEach( group -> {
			this.generateIPPool(group);
		});
	}

	// Pega um ip ainda não usado a partir de um grupo
	private String getIpFromDhcpGroupIpPool(Long groupId, Integer indexRange) {
		return this.mapDeviceUsersPools.get(groupId).getRange().get(indexRange).pop();
	}
	
	public void generateFile() {
		// Gerar IPs
		
		// Pegar todos os grupos
		List<DeviceUserGroup> allUserGroups = new ArrayList<DeviceUserGroup>();
		
		// Arquivo de configuraçao
		
		String fileName = "/mnt/ramdisk/dhcpd.conf.registers";
		
		String spaces = new String("    ");
		
		allUserGroups = getDeviceGroupFromDatabase();
		
		// Path configFile = Paths.get("/home/luis/dhcpd.conf");
		
		
		if (allUserGroups.size() > 0) {
			this.generateAllIPPools(allUserGroups);

			allUserGroups.stream().forEach( group -> {
				var allDeviceRegisterGroup = this.deviceRegisterService.findAllByDeviceUserGroup(group);

				if (allDeviceRegisterGroup.size() > 0) {
					
					// Coloque um IP aqui
					allDeviceRegisterGroup.stream().forEach( device -> {
						// Aqui precisa pegar dos dois pools, 2 ips para cada device
						int[] index = {0};
						device.getGroup().getIprangegroup().forEach( range -> {
							StringBuilder sb = new StringBuilder();
							String ipFromPool = this.getIpFromDhcpGroupIpPool(group.getId(), index[0]);
							sb.append("\nhost ");
							sb.append(device.getIds().getCpf());
							sb.append("-");
							sb.append(device.getGroup().getName());
							sb.append("-");
							sb.append(device.getDeviceType().getName());
							sb.append("-");
							sb.append(ipFromPool); // Troque isso daqui
							sb.append("{\n");
							sb.append(spaces);
							sb.append("hardware ethernet " + device.getIds().getMac() + ";\n");
							sb.append(spaces);
							sb.append("fixed-address " + ipFromPool + ";\n}");
							
							System.out.println(sb.toString());
							
							
						    try {
						    	FileOutputStream fos = new FileOutputStream(fileName, true);
							    DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
								outStream.writeBytes(sb.toString());
								outStream.close();								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println("HOUVE UM PROBLEMA NA ESCRITA DO ARQUIVO:");
								System.out.println(fileName);
							}
							// Inserir no arquivo
							
							
							index[0]++;
						});
						
						
						
					});					
				}
			});
		}
		
		// System.out.println("ARQUIVO DE CONFIGURAÇÃO GERADOS");
		// System.out.println("-------------------------------");

		// System.out.println(sb.toString());
		
	}
}
