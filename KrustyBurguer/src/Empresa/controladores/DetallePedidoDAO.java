/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.controladores;

import Empresa.Database; 
import Empresa.modelos.DetallePedido;
import Empresa.modelos.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author martine.llaviv
 */
public class DetallePedidoDAO {

    public static void insertarDetallePedido(DetallePedido detalle) {
        String sql = "INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (Connection con = Database.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, detalle.getPedidoId());
            ps.setString(2, detalle.getProducto().getCodigo());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getProducto().getPrecio()); 
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<DetallePedido> obtenerPorPedido(int pedidoId) {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = """
            SELECT dp.id, dp.pedido_id, dp.cantidad, dp.precio_unitario,
                   p.codigo, p.nombre, p.precio, p.categoria, p.cantidad AS stock_producto
            FROM detalle_pedido dp
            JOIN productos p ON dp.producto_id = p.codigo
            WHERE dp.pedido_id = ?
        """;

        try (Connection con = Database.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pedidoId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getDouble("precio_unitario"), 
                        rs.getString("categoria")
                    );

                    DetallePedido detalle = new DetallePedido(
                        rs.getInt("id"),
                        rs.getInt("pedido_id"),
                        producto,
                        rs.getInt("cantidad"), // Esta es la cantidad en el detalle de pedido
                        rs.getDouble("precio_unitario")
                    );

                    detalles.add(detalle);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return detalles;
    }
}