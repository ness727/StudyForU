package com.megamaker.studyforu.category.domain;

import org.springframework.stereotype.Repository;

import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class CategoryAddKeyMemoryStore implements CategoryAddKeyStore {
    private final CopyOnWriteArrayList<String> dataList = new CopyOnWriteArrayList<>();

    @Override
    public void save(String randomId) {
        dataList.add(randomId);
    }

    @Override
    public boolean checkContainsKey(String key) {
        return dataList.contains(key);
    }

    @Override
    public void delete(String key) {
        dataList.remove(key);
    }
}
