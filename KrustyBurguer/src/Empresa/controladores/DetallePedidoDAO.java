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
   private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/empresa";
        String user = "root";
        String pass = "1234";
        return DriverManager.getConnection(url, user, pass);
    }

    public static List<DetallePedido> obtenerPorPedido(int pedidoId) {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = """
            SELECT dp.id, dp.pedido_id, dp.cantidad, dp.precio_unitario,
                   p.codigo, p.nombre, p.precio, p.categoria
            FROM detalle_pedido dp
            JOIN productos p ON dp.producto_id = p.codigo
            WHERE dp.pedido_id = ?
        """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pedidoId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getString("categoria")
                    );

                    DetallePedido detalle = new DetallePedido(
                        rs.getInt("id"),
                        rs.getInt("pedido_id"),
                        producto,
                        rs.getInt("cantidad"),
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