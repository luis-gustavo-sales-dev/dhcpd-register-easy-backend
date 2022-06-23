package br.dev.luisgustavosales.dhcpregister.entities;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_device_group")
public class DeviceUserGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@NotBlank
	@Column( unique = true )
	public String name;
	
	@OneToMany(mappedBy = "deviceUserGroup",fetch = FetchType.EAGER)
	public Set<IpRangeGroup> iprangegroup;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<IpRangeGroup> getIprangegroup() {
		return iprangegroup;
	}
	public void setIprangegroup(Set<IpRangeGroup> iprangegroup) {
		this.iprangegroup = iprangegroup;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceUserGroup other = (DeviceUserGroup) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	@Override
	public String toString() {
		return "DeviceUserGroup [id=" + id + ", name=" + name + ", iprange=" + iprangegroup + "]";
	}
	
	
	
	
}
