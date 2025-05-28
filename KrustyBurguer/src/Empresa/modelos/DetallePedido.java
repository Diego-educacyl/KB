/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.modelos;

/**
 *
 * @author martine.llaviv
 */
public class DetallePedido {
    private int id;
    private int pedidoId;
    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public DetallePedido(int id, int idPedido, Producto producto, int cantidad, double precioUnitario) {
        this.id = id;
        this.pedidoId = idPedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public DetallePedido(Producto producto) {
        this.producto = producto;
        this.cantidad = 1;
        this.precioUnitario = producto.getPrecio();
    }

    public void incrementarCantidad() {
        cantidad++;
    }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }

    @Override
    public String toString() {
        return String.format("%-20s %2dx %.2f€ = %.2f€",
                producto.getNombre(), cantidad, precioUnitario, getSubtotal());
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPedidoId() { return pedidoId; }
    public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) {
        this.producto = producto;
        this.precioUnitario = producto.getPrecio();
    }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
}