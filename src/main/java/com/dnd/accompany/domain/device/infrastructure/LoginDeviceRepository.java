package com.dnd.accompany.domain.device.infrastructure;

import com.dnd.accompany.domain.device.entity.LoginDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginDeviceRepository extends JpaRepository<LoginDevice, Long> {
    Optional<LoginDevice> findLoginDeviceByUserId(Long userId);
}
