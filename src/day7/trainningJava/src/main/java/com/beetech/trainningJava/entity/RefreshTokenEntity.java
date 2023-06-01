package com.beetech.trainningJava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshTokenEntity {
    @Id
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "refresh_token", length = 150)
    @ColumnDefault("''")
    private String refreshToken;

    @Column(name = "access_token", length = 150)
    @ColumnDefault("''")
    private String accessToken;

}