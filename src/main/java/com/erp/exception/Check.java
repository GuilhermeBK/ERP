package com.erp.exception;

import org.apache.commons.lang3.Validate;

import java.util.Collection;

public class Check {

    public static <T> T nonNull(T object, String message, Object... values) {
        try {
            return Validate.notNull(object, message, values);
        } catch (Exception e) {
            throw newBusinessException(e);
        }
    }

    public static <T extends Collection<?>> T notEmpty(T collection, String message, Object... values) {
        try {
            return Validate.notEmpty(collection, message, values);
        } catch (Exception e) {
            throw newBusinessException(e);
        }
    }

    private static BusinessException newBusinessException (Exception e) {
        return new BusinessException("Erro na regra de negocio: " + e.getMessage(), e);
    }
}
