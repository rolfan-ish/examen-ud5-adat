package io.gitlab.rolfan.adatexamne5.impl;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import io.gitlab.rolfan.adatexamne5.dao.*;
import io.gitlab.rolfan.adatexamne5.modelo.*;

import java.util.List;

public class BBDDImpl implements BBDD {

    public BBDDImpl(String fichero) {
        db = Db4oEmbedded.openFile(fichero);
    }

    private EmbeddedObjectContainer db;

    @Override
    public void close() {
        db.close();
    }

    private record GenericDAO<T extends IntId>(Class<T> clazz, BBDDImpl impl) implements CRUD<T, Integer> {

        @Override
        public void crear(T obj) {
            impl.db.store(obj);
        }

        @Override
        public void eliminar(T obj) {
            impl.db.delete(obj);
        }

        @Override
        public T encontrar(Integer clave) {
            return impl.db.query(clazz)
                    .stream()
                    .filter(d -> d.id == clave)
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public List<T> encontrarTodos() {
            return impl.db.query(clazz);
        }

        @Override
        public void actualizar(T obj) {
            impl.db.store(obj);
        }
    }

    @Override
    public CRUD<Distrito, Integer> distritoDAO() {
        return new GenericDAO<>(Distrito.class, this);
    }

    @Override
    public CRUD<Habitante, Integer> habitanteDAO() {
        return new GenericDAO<>(Habitante.class, this);
    }

    @Override
    public CRUD<Juego, Integer> juegoDAO() {
        return new GenericDAO<>(Juego.class, this);
    }

    @Override
    public CRUD<Tributo, Integer> tributoDAO() {
        return new GenericDAO<>(Tributo.class, this);
    }
}
