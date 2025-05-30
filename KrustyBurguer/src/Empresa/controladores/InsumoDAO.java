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

public class InsumoDAO {

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
    // Método para actualizar un insumo existente (todos los campos excepto ID)
    public static void actualizar(Insumo insumo) {
        // Asegúrate de que los nombres de las columnas coincidan con tu tabla 'insumos'
        // Es importante que la tabla 'insumos' tenga las columnas 'codigo', 'nombre',
        // 'precio', 'stock', y 'categoria' para que esto funcione.
        String sql = "UPDATE insumos SET codigo = ?, nombre = ?, precio = ?, stock = ?, categoria = ? WHERE id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, insumo.getCodigo());
            stmt.setString(2, insumo.getNombre());
            stmt.setDouble(3, insumo.getPrecio());
            stmt.setInt(4, insumo.getStock());
            stmt.setString(5, insumo.getCategoria());
            stmt.setInt(6, insumo.getId()); // El ID para la condición WHERE

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Insumo con ID " + insumo.getId() + " actualizado con éxito.");
            } else {
                System.out.println("No se encontró el insumo con ID " + insumo.getId() + " para actualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar insumo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
