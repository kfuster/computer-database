package com.excilys.formation.model;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.util.Collection;

/**
 * Class depicting a User entity.
 * @author kfuster
 */

@Entity
@Table(name = "user")
public class User implements UserDetails {
    private static final long serialVersionUID = 5516692655625378444L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    Collection<Role> authorities;
    boolean status;

    @Override
    public Collection<Role> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Getter for the id field.
     * @return Long representing the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the id field.
     * @param id Long representing the id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the status field.
     * @return boolean representing the status.
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Setter for the status field.
     * @param status boolean representing the status.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Setter for the username field.
     * @param username String representing the name.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for the password field.
     * @param password String representing the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter for the authorities field.
     * @param authorities Collection<Role> representing the authorities.
     */
    public void setAuthorities(Collection<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (isStatus() != user.isStatus()) {
            return false;
        }
        if (getId() != null ? !getId().equals(user.getId()) : user.getId() != null) {
            return false;
        }
        if (!getUsername().equals(user.getUsername())) {
            return false;
        }
        if (!getPassword().equals(user.getPassword())) {
            return false;
        }
        return getAuthorities() != null ? getAuthorities().equals(user.getAuthorities()) : user.getAuthorities() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + (getAuthorities() != null ? getAuthorities().hashCode() : 0);
        result = 31 * result + (isStatus() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", authorities=" + authorities
                + ", status=" + status + "]";
    }

    /**
     * Class allowing user creation.
     */
    public static final class UserBuilder {
        Collection<Role> authorities;
        boolean status;
        private Long id;
        private String username;
        private String password;

        /**
         * Default constructor.
         */
        public UserBuilder() {
        }

        /**
         * Setter for the id field.
         * @param id Long representing the id.
         * @return UserBuilder
         */
        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Setter for the username field.
         * @param username String representing the username.
         * @return UserBuilder
         */
        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Setter for the password field.
         * @param password String representing the password.
         * @return UserBuilder.
         */
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Setter for the authorities field.
         * @param authorities Collection<Role> representing the authorities.
         * @return UserBuilder.
         */
        public UserBuilder authorities(Collection<Role> authorities) {
            this.authorities = authorities;
            return this;
        }

        /**
         * Setter for the status field.
         * @param status boolean representing the status.
         * @return UserBuilder
         */
        public UserBuilder status(boolean status) {
            this.status = status;
            return this;
        }

        /**
         * Build a User from the USerBuilder's attributes.
         * @return a User built with the builder's attributes.
         */
        public User build() {
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setPassword(password);
            user.setAuthorities(authorities);
            user.setStatus(status);
            return user;
        }
    }
}