package com.dnd.accompany.domain.device.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "login_devices")
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE login_devices SET deleted = true WHERE id = ?")
public class LoginDevice {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String deviceToken;

    @Column(nullable = false)
    private LocalDateTime loginAt;

    private boolean deleted = Boolean.FALSE;

    @Builder
    protected LoginDevice(Long id, Long userId, String deviceToken, LocalDateTime loginAt) {
        this.id = id;
        this.userId = userId;
        this.deviceToken = deviceToken;
        this.loginAt = loginAt;
    }

    public void update(String deviceToken, LocalDateTime loginAt) {
        this.deviceToken = deviceToken;
        this.loginAt = loginAt;
    }
}
