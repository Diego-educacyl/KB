/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.controladores;

import Empresa.Database;
import Empresa.modelos.Empleado;
import java.sql.*;
import java.util.*;

/**
 *
 * @author martine.llaviv
 */
public class EmpleadoDAO {
    public static String verificarClave(String clave) {
        String sql = "SELECT puesto FROM empleados WHERE clave = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, clave);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("puesto"); 
            } else {
                return null; 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
  public static List<Empleado> obtenerTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT id, nombre, telefono, direccion, puesto, clave FROM empleados"; 

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Empleado emp = new Empleado(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("puesto"),
                    rs.getString("clave") 
                );
                empleados.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    public static void insertar(Empleado emp) {
        String sql = "INSERT INTO empleados (nombre, telefono, direccion, puesto, clave) VALUES (?, ?, ?, ?, ?)"; 

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getNombre());
            stmt.setString(2, emp.getTelefono());
            stmt.setString(3, emp.getDireccion());
            stmt.setString(4, emp.getPuesto());
            stmt.setString(5, emp.getClave()); 

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void actualizar(Empleado emp) {
        String sql = "UPDATE empleados SET nombre = ?, telefono = ?, direccion = ?, puesto = ?, clave = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getNombre());
            stmt.setString(2, emp.getTelefono());
            stmt.setString(3, emp.getDireccion());
            stmt.setString(4, emp.getPuesto());
            stmt.setString(5, emp.getClave()); 
            stmt.setInt(6, emp.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
