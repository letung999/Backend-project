package com.ppn.mock.backendmockppn.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ppn.mock.backendmockppn.constant.Privileges;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true, nullable = false)
    private long roleId;

    @Column(name = "name", unique = true)
    private String name;

    @ElementCollection(targetClass = Privileges.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "privileges")
    private Set<Privileges> privilegesEnums;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
