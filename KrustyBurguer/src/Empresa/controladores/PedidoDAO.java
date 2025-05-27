/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.controladores;

import Empresa.Database;
import Empresa.modelos.Pedido;
import Empresa.modelos.DetallePedido;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author martine.llaviv
 */
public class PedidoDAO {

    public void insertarPedido(Pedido pedido) {
        String sql = "INSERT INTO pedidos (cliente_id, fecha, estado, total) VALUES (?, ?, ?, ?)"; // Agregado 'estado'
        try (Connection con = Database.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setString(2, pedido.getFecha());
            stmt.setString(3, pedido.getEstado());
            stmt.setDouble(4, pedido.getTotal());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarPedido(Pedido pedido) {
        String sql = "UPDATE pedidos SET cliente_id = ?, fecha = ?, estado = ?, total = ? WHERE id = ?"; // Agregado 'estado'
        try (Connection con = Database.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setString(2, pedido.getFecha());
            stmt.setString(3, pedido.getEstado()); // Si agregas el estado al modelo
            stmt.setDouble(4, pedido.getTotal());
            stmt.setInt(5, pedido.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pedido> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (Connection con = Database.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setIdCliente(rs.getInt("cliente_id")); // Cambiado a 'cliente_id'
                pedido.setFecha(rs.getString("fecha"));
                pedido.setEstado(rs.getString("estado")); // Agregado 'estado'
                pedido.setTotal(rs.getDouble("total"));
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }
}
