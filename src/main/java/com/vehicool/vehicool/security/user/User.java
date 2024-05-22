package com.vehicool.vehicool.security.user;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vehicool.vehicool.persistence.entity.Administrator;
import com.vehicool.vehicool.persistence.entity.DataPool;
import com.vehicool.vehicool.persistence.entity.Lender;
import com.vehicool.vehicool.persistence.entity.Renter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_profile")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status")
    private DataPool userStatus;

    @OneToOne
    @JoinColumn(name = "lender_profile_id")
    @JsonBackReference
    private Lender lenderProfile;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "renter_profile_id")
    private Renter renterProfile;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "administrator_profile_id")
    private Administrator administratorProfile;

    @ElementCollection(targetClass = com.vehicool.vehicool.security.user.Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
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
}
