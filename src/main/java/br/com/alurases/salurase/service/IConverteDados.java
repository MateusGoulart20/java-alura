package br.com.alurases.salurase.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
