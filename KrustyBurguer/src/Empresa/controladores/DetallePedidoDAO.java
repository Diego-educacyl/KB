/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.controladores;


import Empresa.Database;
import Empresa.modelos.DetallePedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author martine.llaviv
 */
public class DetallePedidoDAO {
   private static Connection getConnection() throws SQLException {
        // Cambia estos datos por los tuyos
        String url = "jdbc:mysql://localhost:3306/empresa";
        String user = "root";
        String pass = "1234";
        return DriverManager.getConnection(url, user, pass);
    }

    public static List<DetallePedido> obtenerPorPedido(int pedidoId) {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT id, pedido_id, producto_id, cantidad, precio_unitario FROM detalle_pedido WHERE pedido_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pedidoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetallePedido d = new DetallePedido();
                    d.setId(rs.getInt("id"));
                    d.setIdPedido(rs.getInt("pedido_id"));
                    d.setIdProducto(rs.getInt("producto_id"));
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    detalles.add(d);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }
}