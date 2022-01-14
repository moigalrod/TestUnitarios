package org.plexus.junitapp.ejemplo.models;

import org.plexus.junitapp.ejemplo.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;


class Cuenta {
    private String persona;
    private BigDecimal saldo;

    public Cuenta() {
    }

    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void debito( BigDecimal monto){
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0)
            throw new DineroInsuficienteException("Dinero inSuficiente");

        this.saldo = nuevoSaldo;
    }

    public void credito( BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Cuenta c) || this.persona == null || this.saldo == null) {
            return false;
        }

        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }
}
