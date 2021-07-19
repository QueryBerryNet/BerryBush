package ru.mar4elkin.models.interfaces;

import java.util.List;

public interface ModelQuery<T> {
    public List<T> select(String queryText);
}
