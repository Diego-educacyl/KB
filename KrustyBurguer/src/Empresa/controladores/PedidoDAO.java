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
        String sql = "INSERT INTO pedidos (id_cliente, fecha, total) VALUES (?, ?, ?)";
        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setString(2, pedido.getFecha());
            stmt.setDouble(3, pedido.getTotal());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarPedido(Pedido pedido) {
        String sql = "UPDATE pedidos SET id_cliente = ?, fecha = ?, total = ? WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setString(2, pedido.getFecha());
            stmt.setDouble(3, pedido.getTotal());
            stmt.setInt(4, pedido.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPedido(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pedido> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setIdCliente(rs.getInt("id_cliente"));
                pedido.setFecha(rs.getString("fecha"));
                pedido.setTotal(rs.getDouble("total"));
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }
}