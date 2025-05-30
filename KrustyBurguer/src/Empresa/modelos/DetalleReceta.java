/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empresa.modelos;

/**
 *
 * @author diego.cruang
 */
public class DetalleReceta {
    
    private String productoCodigo;
    private String insumoCodigo;
    private double cantidadNecesaria;
    

    public DetalleReceta(String productoCodigo, String insumoCodigo, double cantidadNecesaria) {
        this.productoCodigo = productoCodigo;
        this.insumoCodigo = insumoCodigo;
        this.cantidadNecesaria = cantidadNecesaria;
    }

    public String getProductoCodigo() {
        return productoCodigo;
    }

    public void setProductoCodigo(String productoCodigo) {
        this.productoCodigo = productoCodigo;
    }

    public String getInsumoCodigo() {
        return insumoCodigo;
    }

    public void setInsumoCodigo(String insumoCodigo) {
        this.insumoCodigo = insumoCodigo;
    }

    public double getCantidadNecesaria() {
        return cantidadNecesaria;
    }

    public void setCantidadNecesaria(double cantidadNecesaria) {
        this.cantidadNecesaria = cantidadNecesaria;
    }
    
    
    
    
}
