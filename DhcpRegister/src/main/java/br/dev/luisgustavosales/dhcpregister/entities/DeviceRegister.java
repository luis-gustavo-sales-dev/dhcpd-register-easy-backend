package br.dev.luisgustavosales.dhcpregister.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_device_registers")
public class DeviceRegister implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DeviceRegisterPK ids;
	
	@ManyToOne
	private DeviceUserGroup group;
	
	@ManyToOne
	private DeviceType deviceType;

	public DeviceRegisterPK getIds() {
		return ids;
	}

	public void setIds(DeviceRegisterPK ids) {
		this.ids = ids;
	}

	public DeviceUserGroup getGroup() {
		return group;
	}

	public void setGroup(DeviceUserGroup group) {
		this.group = group;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ids);
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
		return Objects.equals(ids, other.ids);
	}

	@Override
	public String toString() {
		return "DeviceRegister [ids=" + ids + ", group=" + group + ", deviceType=" + deviceType + "]";
	}


	

	
	
	

}
