package com.desafiotecnico.product_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageException {
    PRODUCT_NOT_FOUND ("Produto não encontrado: ");

    private final String mensagem;
}
