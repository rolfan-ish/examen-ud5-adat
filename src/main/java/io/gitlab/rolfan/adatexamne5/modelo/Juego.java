package io.gitlab.rolfan.adatexamne5.modelo;

import java.util.ArrayList;

public class Juego extends IntId {
    public int anio;
    public ArrayList<Tributo> tributos;

    public Juego(int id, int anio, ArrayList<Tributo> tributos, Tributo ganador) {
        super(id);
        this.anio = anio;
        this.tributos = tributos;
        this.ganador = ganador;
    }

    @Override
    public String toString() {
        return "Juego{" +
                "anio=" + anio +
                ", tributos=" + tributos +
                ", ganador=" + ganador +
                ", id=" + id +
                '}';
    }

    public Tributo ganador;
}
