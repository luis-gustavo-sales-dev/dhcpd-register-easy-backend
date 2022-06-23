package br.dev.luisgustavosales.dhcpregister.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class DeviceRegisterPK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Size( min = 11, max = 11, message = "O campo CPF deve ter 11 caracteres.")
	public String cpf;
	
	@Size( min = 17, max = 17, message = "O campo MAC deve ter 17 caracteres (contando com os dois pontos)")
	public String mac;

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

	@Override
	public int hashCode() {
		return Objects.hash(cpf, mac);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceRegisterPK other = (DeviceRegisterPK) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(mac, other.mac);
	}

	@Override
	public String toString() {
		return "DeviceRegisterPK [cpf=" + cpf + ", mac=" + mac + "]";
	}
	
	

}