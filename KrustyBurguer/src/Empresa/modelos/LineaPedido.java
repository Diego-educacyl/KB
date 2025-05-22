/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.modelos;

/**
 *
 * @author diego.cruang
 */
public class LineaPedido {
    
    private Producto producto;
    private int cantidad;

    public LineaPedido(Producto producto) {
        this.producto = producto;
        this.cantidad = 1;
    }

    public void incrementarCantidad() {
        cantidad++;
    }

    public double getSubtotal() {
        return cantidad * producto.getPrecio();
    }

    public String toString() {
        return String.format("%-20s %2dx %.2f€ = %.2f€",
            producto.getNombre(), cantidad, producto.getPrecio(), getSubtotal());
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }
}
