package io.gitlab.rolfan.adatexamne5.modelo;

public class Habitante extends IntId{
    public String nombre;

    public Habitante(int id, String nombre, int edad) {
        super(id);
        this.nombre = nombre;
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Habitante{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", id=" + id +
                '}';
    }

    public int edad;
}
