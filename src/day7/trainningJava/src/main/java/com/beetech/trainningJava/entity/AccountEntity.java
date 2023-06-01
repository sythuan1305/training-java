package com.beetech.trainningJava.entity;

import com.beetech.trainningJava.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity này dùng để lưu thông tin tài khoản
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "AccountEntity.cart",
                attributeNodes = {
                        @NamedAttributeNode("cart")
                }
        )
)
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "username", nullable = false)
    private RefreshTokenEntity refreshTokenEntity;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "verify", nullable = false)
    private boolean verify;

    @Column(name = "blocked", nullable = false)
    private boolean blocked;

    @Column(name = "role", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cart_id")
    @JsonManagedReference
    private CartEntity cart;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "token", length = 150)
    private String token;

}