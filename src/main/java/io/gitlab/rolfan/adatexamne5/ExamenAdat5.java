package io.gitlab.rolfan.adatexamne5;

import io.gitlab.rolfan.adatexamne5.dao.BBDD;
import io.gitlab.rolfan.adatexamne5.impl.BBDDImpl;
import io.gitlab.rolfan.adatexamne5.modelo.Distrito;
import io.gitlab.rolfan.adatexamne5.modelo.Habitante;
import io.gitlab.rolfan.adatexamne5.modelo.Juego;
import io.gitlab.rolfan.adatexamne5.modelo.Tributo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ExamenAdat5 {
    enum DISTRITO {
        ID, NOMBRE
    }

    enum HABITANTE {
        ID, NOMBRE, EDAD, ES_TRIBUTO, DISTRITO, HABILIDAD, PUNTUAJE
    }

    enum JUEGO {
        ID, ANIO, TRIBUTOS, GANADOR
    }

    static final String BBDD_DB4O = "db.db4o";
    static Scanner sc = new Scanner(System.in);

    BBDD bbdd;

    void generarFichero() {
        bbdd = new BBDDImpl(BBDD_DB4O);
        System.out.println("Creado fichero " + Path.of(BBDD_DB4O));


        var daoDis = bbdd.distritoDAO();
        var daoHab = bbdd.habitanteDAO();
        var daoTri = bbdd.tributoDAO();
        var daoJue = bbdd.juegoDAO();

        try {
            System.out.println("Introduce el csv de distritos:");
            Files.readAllLines(Path.of(sc.next()))
                    .stream().skip(1)
                    .map(s -> s.split(","))
                    .map(d -> new Distrito(
                            Integer.parseInt(d[DISTRITO.ID.ordinal()]),
                            d[DISTRITO.NOMBRE.ordinal()],
                            new ArrayList<>()))
                    .forEach(daoDis::crear);

            System.out.println("Introduce el csv de habitantes:");
            Files.readAllLines(Path.of(sc.next()))
                    .stream().skip(1)
                    .map(s -> s.split(","))
                    .forEach(d -> {
                        int id = Integer.parseInt(d[HABITANTE.ID.ordinal()]);
                        var nom = d[HABITANTE.NOMBRE.ordinal()];
                        int edad = Integer.parseInt(d[HABITANTE.EDAD.ordinal()]);
                        Habitante hab;
                        if (d[HABITANTE.ES_TRIBUTO.ordinal()].equals("True")) {
                            hab = new Habitante(id, nom, edad);
                            daoHab.crear(hab);
                        } else {
                            var tib = new Tributo(id, nom, edad,
                                    Integer.parseInt(d[HABITANTE.PUNTUAJE.ordinal()]),
                                    d[HABITANTE.HABILIDAD.ordinal()]);
                            daoTri.crear(tib);
                            hab = tib;
                        }
                        var dis = daoDis.encontrar(Integer.parseInt(d[HABITANTE.DISTRITO.ordinal()]));
                        dis.habitantes.add(hab);
                        daoDis.actualizar(dis);
                    });

            System.out.println("Introduce el csv de juegos:");
            Files.readAllLines(Path.of(sc.next()))
                    .stream().skip(1)
                    .map(s -> s.split(","))
                    .map(d -> new Juego(
                            Integer.parseInt(d[JUEGO.ID.ordinal()]),
                            Integer.parseInt(d[JUEGO.ANIO.ordinal()]),
                            Arrays.stream(d[JUEGO.TRIBUTOS.ordinal()].split(";"))
                                    .map(Integer::valueOf)
                                    .map(daoTri::encontrar)
                                    .collect(Collectors.toCollection(ArrayList::new)),
                            daoTri.encontrar(Integer.parseInt(d[JUEGO.GANADOR.ordinal()]))))
                    .forEach(daoJue::crear);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void mostrarDistritos() {
        bbdd.distritoDAO().encontrarTodos().forEach(System.out::println);
    }

    void mostrarTributosMayores() {
        bbdd.tributoDAO().encontrarTodos()
                .stream().filter(t -> t.edad >= 16)
                .forEach(System.out::println);
    }

    void juegoEnElQueParticipo() {
        bbdd.tributoDAO().encontrarTodos().forEach(System.out::println);
        System.out.println("Selecciona un tributos en base al id:");
        var trib = bbdd.tributoDAO().encontrar(sc.nextInt());
        if (trib == null) {
            System.out.println("No seleccionaste un tributo valido");
            return;
        }
        bbdd.juegoDAO().encontrarTodos().stream()
                .filter(j -> j.tributos.contains(trib))
                .forEach(System.out::println);
    }

    void modificaDatosHabitante() {
        bbdd.habitanteDAO().encontrarTodos().forEach(System.out::println);
        System.out.println("Selecciona un habitante por id:");
        var hab = bbdd.habitanteDAO().encontrar(sc.nextInt());
        if (hab == null) {
            System.out.println("No seleccionaste un habitante");
            return;
        }

        System.out.println("Seleccionaste " + hab);

        System.out.println("Introudce el nuevo id:");
        int id = sc.nextInt();
        System.out.println("Introduce la nueva edad:");
        int edad = sc.nextInt();
        System.out.println("Introudce el nuevo nombre:");
        var nom = sc.next();

        hab.edad = edad;
        hab.id = id;
        hab.nombre = nom;

        bbdd.habitanteDAO().actualizar(hab);
    }

    void cambiarGanadorJuego() {
        bbdd.juegoDAO().encontrarTodos().forEach(System.out::println);
        System.out.println("Selecciona un habitante por id:");
        var jue = bbdd.juegoDAO().encontrar(sc.nextInt());
        if (jue == null) {
            System.out.println("No seleccionaste un juego");
            return;
        }
        System.out.println("Selecciona un ganador por id:");
        jue.tributos.forEach(System.out::println);
        int id = sc.nextInt();
        jue.ganador = bbdd.tributoDAO().encontrar(id);
        bbdd.juegoDAO().actualizar(jue);
    }

    void agregarDistrito() {
        System.out.println("Id:");
        int id = sc.nextInt();
        System.out.println("Nombre:");
        var nom = sc.next();
        var dis = new Distrito(id, nom, new ArrayList<>());
        bbdd.distritoDAO().crear(dis);
    }

    void runmenu() {
        System.out.println("1. Generar fichero db4o y carga datos");
        System.out.println("2. Mostrar distritos");
        System.out.println("3. Mostrar tributos mayores de 16");
        System.out.println("4. Mostrar juegos en los que participo un tributo especifico");
        System.out.println("5. Modificar datos de un habitante");
        System.out.println("6. Cambiar ganador de un juego");
        System.out.println("7. Agregar distrito");
        System.out.println();
        System.out.println("0. Salir");
        int opt = sc.nextInt();
        switch (opt) {
            case 0 -> {
                try {
                    bbdd.close();
                } catch (IOException _) {
                }
                System.exit(0);
            }
            case 1 -> generarFichero();
            case 2 -> mostrarDistritos();
            case 3 -> mostrarTributosMayores();
            case 4 -> juegoEnElQueParticipo();
            case 5 -> modificaDatosHabitante();
            case 6 -> cambiarGanadorJuego();
            case 7 -> agregarDistrito();
        }
    }

    public static void main(String[] args) {
        var m = new ExamenAdat5();
        while (true) m.runmenu();
    }
}