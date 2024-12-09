package io.gitlab.rolfan.adatexamne5.modelo;

import java.util.ArrayList;

public class Distrito extends IntId {
    public Distrito(int id, String nombre, ArrayList<Habitante> habitantes) {
        super(id);
        this.habitantes = habitantes;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Distrito{" +
                "habitantes=" + habitantes +
                ", nombre='" + nombre + '\'' +
                ", id=" + id +
                '}';
    }

    public ArrayList<Habitante> habitantes;

    public String nombre;
}
