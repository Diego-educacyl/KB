/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.controladores;

import Empresa.Database;
import Empresa.modelos.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego.cruang
 */
public class ControladorInventario {
    public static List<Producto> obtenerInventario() {
        List<Producto> lista = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM productos");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getString("categoria"),
                    rs.getInt("cantidad")
                );
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void actualizarStockProducto(String codigo, int nuevaCantidad) {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE productos SET cantidad = ? WHERE codigo = ?");
            ps.setInt(1, nuevaCantidad);
            ps.setString(2, codigo);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
