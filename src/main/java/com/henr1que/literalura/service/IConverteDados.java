package com.henr1que.literalura.service;

public interface IConverteDados {
    <T> T converterDados(String json, Class<T> classe);
}
