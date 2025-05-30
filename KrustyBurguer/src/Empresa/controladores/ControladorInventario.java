/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.controladores;

import Empresa.Database;
import Empresa.modelos.Insumo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorInventario {

    // Método para obtener todos los insumos desde la base de datos
    public static List<Insumo> obtenerInsumos() {
        List<Insumo> insumos = new ArrayList<>();

        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM insumos");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Insumo insumo = new Insumo();
                insumo.setId(rs.getInt("id"));
                insumo.setCodigo(rs.getString("codigo"));
                insumo.setNombre(rs.getString("nombre"));
                insumo.setPrecio(rs.getDouble("precio"));
                insumo.setStock(rs.getInt("stock"));
                insumo.setCategoria(rs.getString("categoria"));
                insumos.add(insumo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return insumos;
    }

    public static void actualizarStockInsumo(String codigo, int nuevoStock) {
        String sql = "UPDATE insumos SET stock = ? WHERE codigo = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, nuevoStock);
            stmt.setString(2, codigo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
