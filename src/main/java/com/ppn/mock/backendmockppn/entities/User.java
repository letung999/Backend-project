package com.ppn.mock.backendmockppn.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user-id")
    private Integer id;

    @Column(name = "first-name", length = 100, nullable = true)
    private String firstName;

    @Column(name = "last-name", length = 100, nullable = true)
    private String lastName;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "gender", length = 10, nullable = true)
    private String gender;

    @Column(name = "age", nullable = true)
    private String age;

    @Column(name = "phone", length = 20)
    private String phoneNumber;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Car> cars;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Payment> payments;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user-id", referencedColumnName = "user-id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id",
                    table = "roles")
    )
    private Set<Role> roles;
}
