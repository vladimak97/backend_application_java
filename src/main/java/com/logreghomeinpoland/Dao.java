package com.logreghomeinpoland;

import java.util.Collection;
import java.util.Optional;

//przechowywania danych - tworzenie, odczyt, aktualizacja i usuwanie (CRUD)//

public interface Dao<T, I> {
    Optional<T> get(String email, String password);
    Collection<T> getAll();
    Optional<I> save(T t);
    void update(T t);
    void delete(T t);
}
