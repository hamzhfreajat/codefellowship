package com.example.codefellowship.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Data
@Setter
@Getter
@Entity
public class ApplicationUser implements UserDetails {
    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName ;
    private String dateOfBirth;
    private String bio ;
    @OneToMany(mappedBy = "user")
    private Set<Posts> posts;

    @ManyToMany
    @JoinTable(
            name = "user_user",
            joinColumns = {@JoinColumn(name = "from_id")},
            inverseJoinColumns = {@JoinColumn(name = "to_id")}
    )
    public List<ApplicationUser> following;

    @ManyToMany(mappedBy = "following", fetch = FetchType.EAGER)
    public List<ApplicationUser> followers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    public List<ApplicationUser> getFollowing() {
        return following;
    }

    public List<ApplicationUser> getFollowers() {
        return followers;
    }

}
