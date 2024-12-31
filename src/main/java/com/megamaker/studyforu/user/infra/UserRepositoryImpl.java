package com.megamaker.studyforu.user.infra;

import com.megamaker.studyforu.user.domain.User;
import com.megamaker.studyforu.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByProviderId(String providerId) {
        return userJpaRepository.findByProviderId(providerId).map(UserEntity::toModel);
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(UserEntity.from(user));
    }
}
