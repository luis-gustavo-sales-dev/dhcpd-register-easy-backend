package br.dev.luisgustavosales.dhcpregister.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_device_registers")
public class DeviceRegister {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@NotBlank
	@Size( min = 11, max = 11, message = "O campo CPF deve ter 11 caracteres.")
	public String cpf;
	
	@Size( min = 17, max = 17, message = "O campo MAC deve ter 17 caracteres (contando com os dois pontos)")
	public String mac;
	
	@ManyToOne
	public DeviceUserGroup group;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public DeviceUserGroup getGroup() {
		return group;
	}

	public void setGroup(DeviceUserGroup group) {
		this.group = group;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceRegister other = (DeviceRegister) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "DeviceRegister [id=" + id + ", cpf=" + cpf + ", mac=" + mac + ", group=" + group + "]";
	}
	
	

}
