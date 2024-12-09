package io.gitlab.rolfan.adatexamne5.dao;

import java.util.List;

public interface CRUD <T, U> {
    void crear(T obj);
    void eliminar(T obj);
    T encontrar(U clave);
    List<T> encontrarTodos();
    void actualizar(T obj);
}
