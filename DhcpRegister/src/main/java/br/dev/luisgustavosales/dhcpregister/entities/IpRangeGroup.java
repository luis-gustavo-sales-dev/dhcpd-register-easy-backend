package br.dev.luisgustavosales.dhcpregister.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_ip_range_group")
public class IpRangeGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String range;
	
	@ManyToOne
	@JoinColumn(name = "device_user_group_id")
	private DeviceUserGroup deviceUserGroup;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
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
		IpRangeGroup other = (IpRangeGroup) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "IpRangeGroup [id=" + id + ", range=" + range + "]";
	}
	
	
}
