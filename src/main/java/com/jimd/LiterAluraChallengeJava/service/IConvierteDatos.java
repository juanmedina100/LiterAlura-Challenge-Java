package com.jimd.LiterAluraChallengeJava.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json,Class<T> clase);
}
