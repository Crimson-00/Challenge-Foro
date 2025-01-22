package com.aluraforo.foro.infra.errors;

public class Validacion extends RuntimeException {
    public Validacion (String s){
        super(s);
    }
}
