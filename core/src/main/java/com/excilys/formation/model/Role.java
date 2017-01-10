package com.excilys.formation.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Class depicting the Role entity.
 * Typically every User has a Role.
 */
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    private static final long serialVersionUID = 6318230993825081486L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String role;
    @ManyToMany(mappedBy = "authorities")
    private List<User> users;

    /**
     * Default constructor.
     */
    protected Role() { }

    /**
     * Constructor takeing a String for the user's role.
     * @param role String depicting the role.
     */
    public Role(String role) {
        this.role = role;
    }
    @Override
    public String getAuthority() {
        return role;
    }
    @Override
    public String toString() {
        return "Role [id=" + id + ", role=" + role + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Role other = (Role) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (role == null) {
            if (other.role != null) {
                return false;
            }
        } else if (!role.equals(other.role)) {
            return false;
        }
        return true;
    }
}