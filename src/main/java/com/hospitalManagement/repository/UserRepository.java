package com.hospitalManagement.repository;

import com.hospitalManagement.entity.User;
import com.hospitalManagement.enums.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByProviderTypeAndProviderId(AuthProviderType providerType, String providerId);

}