/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.controladores;

import Empresa.modelos.Producto;
import Empresa.modelos.DetallePedido;
import javax.swing.*;
import java.util.*;
/**
 *
 * @author diego.cruang
 */
public class ControladorPOS {
    private List<DetallePedido> pedidoActual;
    
    private JTextArea areaPedido;
    private JLabel labelTotal;

    public ControladorPOS(JTextArea areaPedido, JLabel labelTotal) {
        this.areaPedido = areaPedido;
        this.labelTotal = labelTotal;
        this.pedidoActual = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        for (DetallePedido linea : pedidoActual) {
            if (linea.getProducto().getCodigo().equals(producto.getCodigo())) {
                linea.incrementarCantidad();
                actualizarVista();
                return;
            }
        }
        pedidoActual.add(new DetallePedido(producto));
        actualizarVista();
    }

    public void limpiarPedido() {
        pedidoActual.clear();
        actualizarVista();
    }

    private void actualizarVista() {
        StringBuilder sb = new StringBuilder();
        double total = 0.0;
        for (DetallePedido linea : pedidoActual) {
            sb.append(linea.toString()).append("\n");
            total += linea.getSubtotal();
        }
        areaPedido.setText(sb.toString());
        labelTotal.setText(String.format("Total: %.2f €", total));
    }
}
