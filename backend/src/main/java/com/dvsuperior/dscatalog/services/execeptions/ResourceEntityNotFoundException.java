package com.dvsuperior.dscatalog.services.execeptions;

public class ResourceEntityNotFoundException extends RuntimeException {

    public ResourceEntityNotFoundException(String msg) {
        super(msg);
    }

    public ResourceEntityNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
