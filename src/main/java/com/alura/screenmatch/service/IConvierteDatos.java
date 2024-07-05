package com.alura.screenmatch.service;

public interface IConvierteDatos {
//    <T> T Dato generico, al no saber que retornaremos
    <T> T obtenerDatos(String json, Class<T> clase);
}
