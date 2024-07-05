package com.alura.screenmatch.principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EjemplosStreams {
    public void muestraEjemplo(){
        // List.of() crea una lista de elementos que no se pueden modificar
        List<String> nombres1 = List.of("Lalo", "Pedro", "Maria", "Ana", "Luis", "Carlos", "Mario", "Jorge", "Laura");
        // Arrays.asList() crea una lista de elementos que se pueden modificar pero no su tamaño
        List<String> nombres = Arrays.asList("Lalo", "Pedro", "Maria", "Ana", "Luis", "Carlos", "Mario", "Jorge",
                "Laura");
        // new ArrayList<>(Arrays.asList(...)): Convierte la lista fija creada por Arrays.asList en una instancia
        // de ArrayList, que es completamente mutable (puedes agregar, eliminar y cambiar elementos).
//        List<String> nombres2 = new ArrayList<>(Arrays.asList("Lalo", "Pedro", "Maria", "Ana", "Luis", "Carlos", "Mario",
//                "Jorge",
//                "Laura"));
//        nombres2.add("Eduardo");
//        nombres2.forEach(System.out::println);

        nombres.stream()
                .sorted()
//                .limit(3)
                .filter(nombre -> nombre.startsWith("L"))
                .map(String::toUpperCase)
                .forEach(System.out::println);

    }
}
