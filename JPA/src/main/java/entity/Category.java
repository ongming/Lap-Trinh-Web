package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Category")   // đặt đúng tên bảng trong DB (ví dụ: Category hoặc Categories)
public class Category {

    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer cate_id;
	
	    private String cate_name;
	    private String icon;
	
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
