package io.gitlab.rolfan.adatexamne5.modelo;

public class Tributo extends Habitante {
    public int puntuaje;
    public String habilidades;

    public Tributo(int id, String nombre, int edad, int puntuaje, String habilidades) {
        super(id, nombre,  edad);
        this.puntuaje = puntuaje;
        this.habilidades = habilidades;
    }

    @Override
    public String toString() {
        return "Tributo{" +
                "puntuaje=" + puntuaje +
                ", habilidades='" + habilidades + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", id=" + id +
                '}';
    }
}
