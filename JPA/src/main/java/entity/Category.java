package entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cate_id;

	private String cate_name;
	private String icons;

	@ManyToOne
	@JoinColumn(name = "user_id") // FK tới Users.id
	private User user;

	// ===== Getter & Setter =====
	public Integer getCate_id() {
		return cate_id;
	}

	public void setCate_id(Integer cate_id) {
		this.cate_id = cate_id;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	public String getIcons() {
		return icons;
	}

	public void setIcons(String icons) {
		this.icons = icons;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
