/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.modelos;

import java.time.LocalDate;


/**
 *
 * @author martine.llaviv
 */
public class Pedido {
    private int id;
    private int clienteId;
    private String fecha;
    private String estado;
    private double total;

    public Pedido() {
    }

    public Pedido(int id, int clienteId, String fecha, String estado, double total) {
        this.id = id;
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
    }

    public Pedido(int id, String fecha, String estado, double total) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
    }
    
    

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

 

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return clienteId;
    }

    public void setIdCliente(int idCliente) {
        this.clienteId = idCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}