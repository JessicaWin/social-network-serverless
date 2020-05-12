package com.jessica.social.network.serverless.dao;

import com.jessica.social.network.serverless.item.BasicItem;

import java.util.List;

public interface BasicOperationDao<T extends BasicItem> {
    void save(T item);
    void batchSave(List<T> items);
    void delete(T keyItem);
    void batchDelete(List<T> keyItems);
    void update(T item);
    void batchUpdate(List<T> items);
    T load(T keyItem);
    List<T> batchLoad(List<T> keyItems);
}
