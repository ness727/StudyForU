package com.megamaker.studyforu.category.domain;

public interface CategoryAddKeyStore {
    void save(String randomId);

    boolean checkContainsKey(String key);

    void delete(String key);
}
