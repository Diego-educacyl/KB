/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.modelos;

/**
 *
 * @author martine.llaviv
 */
public class Empleado {
  
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String puesto;
    private String clave; // [MODIFICACIÓN] Nueva propiedad para la clave/contraseña

    public Empleado(int id, String nombre, String telefono, String direccion, String puesto, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.puesto = puesto;
        this.clave = clave; // [MODIFICACIÓN] Inicializar la nueva propiedad
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
    public String getPuesto() { return puesto; }
    public String getClave() { return clave; } // [MODIFICACIÓN] Getter para la clave

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
    public void setClave(String clave) { this.clave = clave; } // [MODIFICACIÓN] Setter para la clave
}