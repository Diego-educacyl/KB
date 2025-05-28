/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.controladores;

import Empresa.Database;
import Empresa.modelos.Pedido;
import Empresa.modelos.DetallePedido; // Aunque no se usa directamente en este DAO, está importado.
import java.sql.*;
import java.time.LocalDateTime; // Si lo usas para la fecha
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author martine.llaviv
 */
public class PedidoDAO {

    public int insertarPedido(Pedido pedido) { 
        String sql = "INSERT INTO pedidos (cliente_id, fecha, estado, total) VALUES (?, ?, ?, ?)";
        int idGenerado = -1; 

        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 

            stmt.setInt(1, pedido.getIdCliente());
            
            stmt.setString(2, pedido.getFecha()); 
            stmt.setString(3, pedido.getEstado());
            stmt.setDouble(4, pedido.getTotal());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGenerado;
    }

    public void actualizarPedido(Pedido pedido) {
        String sql = "UPDATE pedidos SET cliente_id = ?, fecha = ?, estado = ?, total = ? WHERE id = ?";
        try (Connection con = Database.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setString(2, pedido.getFecha());
            stmt.setString(3, pedido.getEstado());
            stmt.setDouble(4, pedido.getTotal());
            stmt.setInt(5, pedido.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPedido(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (Connection con = Database.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pedido> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id, cliente_id, fecha, estado, total FROM pedidos";

        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setIdCliente(rs.getInt("cliente_id"));
                pedido.setFecha(rs.getString("fecha"));
                pedido.setEstado(rs.getString("estado"));
                pedido.setTotal(rs.getDouble("total"));
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }
    
    public Pedido obtenerPedidoPorId(int id) {
        String sql = "SELECT id, cliente_id, fecha, estado, total FROM pedidos WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setIdCliente(rs.getInt("cliente_id"));
                pedido.setFecha(rs.getString("fecha"));
                pedido.setEstado(rs.getString("estado"));
                pedido.setTotal(rs.getDouble("total"));
                return pedido;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}