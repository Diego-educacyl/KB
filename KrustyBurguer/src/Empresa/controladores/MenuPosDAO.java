/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.controladores;

import Empresa.modelos.Producto;
import Empresa.modelos.DetallePedido;
import Empresa.controladores.ProductoDAO;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author diego.cruang
 */
public class MenuPosDAO {

    private List<DetallePedido> pedidoActual;
    private JTextArea areaPedido;
    private JLabel labelTotal;

    public MenuPosDAO(JTextArea areaPedido, JLabel labelTotal) {
        this.areaPedido = areaPedido;
        this.labelTotal = labelTotal;
        this.pedidoActual = new ArrayList<>();
    }

    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        return ProductoDAO.obtenerPorCategoria(categoria);
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

    public double getTotalPedido() {
        return pedidoActual.stream().mapToDouble(DetallePedido::getSubtotal).sum();
    }
    
    public void realizarPago() {
        if (pedidoActual.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el pedido para pagar.", "Pedido Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double total = getTotalPedido();
        String mensaje = String.format("Total a pagar: %.2f €\n\n¿Confirmar pago?", total);
        int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Confirmar Pago", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            
            JOptionPane.showMessageDialog(null, "Pago realizado con éxito. Total: " + String.format("%.2f €", total), "Pago Confirmado", JOptionPane.INFORMATION_MESSAGE);
            limpiarPedido();
        } else {
            JOptionPane.showMessageDialog(null, "Pago cancelado.", "Pago Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
