package com.autentia;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class CarritoDeLaCompra<T extends Long> {

    private AtomicLong counter = new AtomicLong();

    private Collection<T> precios;

    public CarritoDeLaCompra(Collection<T> precios) {

        this.precios = precios;
    }

    public int calcularPrecioTotal() {

        int precioTotal = 0;

        for (Long precio : precios) {

            precioTotal += precio;

        }
        return precioTotal;
    }

    public int contarNumeroProductos() {

        return precios.size();
    }

    public int calcularPrecioTotalLambda() {

        int precioTotal = this.precios.stream().mapToInt(precio -> precio.intValue()).sum();


        return precioTotal;
    }

    public int calcularPrecioTotalRefMethod() {

        int precioTotal = this.precios.stream().mapToInt(Long::intValue).sum();


        return precioTotal;
    }

    public long calcularDescuentoTotal(int cantidadConDescuento) {

        long descuentoTotal = 0;

        for (Long precio : precios) {

            if (precio >= cantidadConDescuento) {

                descuentoTotal += (cantidadConDescuento * 5) / 100;
            }
        }

        return descuentoTotal;
    }

    public long calcularDescuentoTotalLambda(int cantidadConDescuento) {

        long descuentoTotal = 0;

        Long numeroDeDescuentos = this.precios.stream().filter(precio -> precio.intValue() >= cantidadConDescuento).count();

        descuentoTotal = (cantidadConDescuento * 5 / 100) * numeroDeDescuentos;

        return descuentoTotal;
    }

    public boolean detectarError() {

        boolean negativeFind = false;

        for (Long precio : precios) {

            this.counter.incrementAndGet();

            if (precio < 0) {

                negativeFind = true;
            }
        }

        return negativeFind;
    }

    public boolean detectarErrorAnyMatch() {

        return this.precios.stream()
                           .peek(precio -> counter.incrementAndGet())
                           .anyMatch(precio -> precio.intValue() < 0);
    }


    public boolean detectarErrorFindAny() {

        return this.precios.stream().peek(precio -> counter.incrementAndGet())
                                    .filter(precio -> precio.intValue() < 0)
                                    .findAny()
                                    .isPresent();
    }

    public boolean detectarErrorFindFirst() {

        return this.precios.stream().peek(precio -> counter.incrementAndGet())
                                    .filter(precio -> precio.intValue() < 0)
                                    .findFirst()
                                    .isPresent();
    }

    public boolean detectarErrorAnyMatchParallel() {

        return this.precios.parallelStream().peek(precio -> counter.incrementAndGet())
                                            .anyMatch(precio -> precio.intValue() < 0);
    }

    public boolean detectarErrorFindAnyParallel() {
        return this.precios.parallelStream().peek(precio -> counter.incrementAndGet())
                                            .filter(precio -> precio.intValue() < 0)
                                            .findAny()
                                            .isPresent();
    }

    public boolean detectarErrorFindFirstParallel() {

        return this.precios.parallelStream().peek(precio -> counter.incrementAndGet())
                                            .filter(precio -> precio.intValue() < 0)
                                            .findFirst()
                                            .isPresent();
    }

    public long getCounter() {
        return counter.get();
    }

    public void resetCounter() {
        counter.set(0L);
    }
}
