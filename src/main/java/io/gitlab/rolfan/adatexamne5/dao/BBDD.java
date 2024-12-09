package io.gitlab.rolfan.adatexamne5.dao;

import io.gitlab.rolfan.adatexamne5.modelo.Distrito;
import io.gitlab.rolfan.adatexamne5.modelo.Habitante;
import io.gitlab.rolfan.adatexamne5.modelo.Juego;
import io.gitlab.rolfan.adatexamne5.modelo.Tributo;

import java.io.Closeable;

public interface BBDD extends Closeable {
    CRUD<Distrito, Integer> distritoDAO();

    CRUD<Habitante, Integer> habitanteDAO();

    CRUD<Juego, Integer> juegoDAO();

    CRUD<Tributo, Integer> tributoDAO();
}
