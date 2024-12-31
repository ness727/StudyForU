package com.megamaker.studyforu.user.domain;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByProviderId(String providerId);

    Optional<User> findById(Long userId);

    void save(User user);
}
