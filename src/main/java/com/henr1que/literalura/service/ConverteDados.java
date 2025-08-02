package com.henr1que.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T converterDados(String json, Class<T> classe) {
        try {
            return objectMapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter dados: " + e.getMessage(), e);
        }
    }
}
