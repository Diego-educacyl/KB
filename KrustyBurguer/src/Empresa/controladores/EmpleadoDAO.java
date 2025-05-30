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
  /**
     * Autentica a un empleado usando su ID y clave (contraseña).
     * @param idEmpleado El ID del empleado.
     * @param clave La clave (contraseña) del empleado.
     * @return Un objeto Empleado si las credenciales son correctas, null en caso contrario.
     */
    public static Empleado autenticarEmpleado(int idEmpleado, String clave) {
        String sql = "SELECT id, nombre, telefono, direccion, puesto, clave FROM empleados WHERE id = ? AND clave = ?"; // [MODIFICACIÓN] Incluir 'clave'

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Empleado(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("puesto"),
                    rs.getString("clave") // [MODIFICACIÓN] Leer la clave
                );
            } else {
                return null; // Credenciales incorrectas
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al autenticar empleado: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Verifica la clave (contraseña) y devuelve el puesto. Utilizado para login solo con clave.
     * @param clave La clave (contraseña) a verificar.
     * @return El puesto del empleado si la clave es correcta, null en caso contrario.
     */
    public static String verificarClave(String clave) {
        String sql = "SELECT puesto FROM empleados WHERE clave = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, clave);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("puesto"); // "administrador" o "camarero"
            } else {
                return null; // clave incorrecta
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene todos los empleados de la base de datos.
     * @return Una lista de objetos Empleado.
     */
    public static List<Empleado> obtenerTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT id, nombre, telefono, direccion, puesto, clave FROM empleados"; // [MODIFICACIÓN] Incluir 'clave'

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
                    rs.getString("clave") // [MODIFICACIÓN] Leer la clave
                );
                empleados.add(emp);
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al obtener todos los empleados: " + e.getMessage());
            e.printStackTrace();
        }

        return empleados;
    }

    /**
     * Inserta un nuevo empleado en la base de datos.
     * @param emp El objeto Empleado a insertar.
     */
    public static void insertar(Empleado emp) {
        // [MODIFICACIÓN] Añadida la columna 'clave' al INSERT
        String sql = "INSERT INTO empleados (nombre, telefono, direccion, puesto, clave) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getNombre());
            stmt.setString(2, emp.getTelefono());
            stmt.setString(3, emp.getDireccion());
            stmt.setString(4, emp.getPuesto());
            stmt.setString(5, emp.getClave()); // [MODIFICACIÓN] Insertar la clave

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error SQL al insertar empleado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los datos de un empleado existente en la base de datos.
     * @param emp El objeto Empleado con los datos actualizados.
     */
    public static void actualizar(Empleado emp) {
        // [MODIFICACIÓN] Añadida la columna 'clave' al UPDATE
        String sql = "UPDATE empleados SET nombre = ?, telefono = ?, direccion = ?, puesto = ?, clave = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getNombre());
            stmt.setString(2, emp.getTelefono());
            stmt.setString(3, emp.getDireccion());
            stmt.setString(4, emp.getPuesto());
            stmt.setString(5, emp.getClave()); // [MODIFICACIÓN] Actualizar la clave
            stmt.setInt(6, emp.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar empleado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Elimina un empleado de la base de datos por su ID.
     * @param id El ID del empleado a eliminar.
     */
    public static void eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar empleado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
