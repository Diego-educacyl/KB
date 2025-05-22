/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Empresa.vistas;


import Empresa.controladores.DetallePedidoDAO;
import Empresa.controladores.PedidoDAO;
import Empresa.modelos.DetallePedido;
import Empresa.modelos.Pedido;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author martine.llaviv
 */
public class PedidosFrame extends javax.swing.JFrame {

   private JTable tablaPedidos;
    private JTable tablaDetalles;
    private DefaultTableModel modeloPedidos;
    private DefaultTableModel modeloDetalles;
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private DetallePedidoDAO detalleDAO = new DetallePedidoDAO();
    /**
     * Creates new form PedidosFrame
     */
    public PedidosFrame() {
        setTitle("Gestión de Pedidos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modelo tabla pedidos
        modeloPedidos = new DefaultTableModel(new String[]{"ID", "Fecha", "Cliente ID"}, 0);
        tablaPedidos = new JTable(modeloPedidos);
        JScrollPane scrollPedidos = new JScrollPane(tablaPedidos);

        // Modelo tabla detalles
        modeloDetalles = new DefaultTableModel(new String[]{"ID", "Pedido ID", "Producto ID", "Cantidad"}, 0);
        tablaDetalles = new JTable(modeloDetalles);
        JScrollPane scrollDetalles = new JScrollPane(tablaDetalles);

        // Botones
        JButton btnInsertar = new JButton("Insertar Pedido");
        JButton btnModificar = new JButton("Modificar Pedido");
        JButton btnEliminar = new JButton("Eliminar Pedido");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnInsertar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);

        // Diseño principal
        setLayout(new BorderLayout());
        add(scrollPedidos, BorderLayout.NORTH);
        add(scrollDetalles, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnInsertar.addActionListener(e -> insertarPedido());
        btnModificar.addActionListener(e -> modificarPedido());
        btnEliminar.addActionListener(e -> eliminarPedido());

        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDetallesPedido();
            }
        });

        cargarPedidos();
    }

    private void cargarPedidos() {
        modeloPedidos.setRowCount(0);
        List<Pedido> lista = pedidoDAO.obtenerTodosLosPedidos();
        for (Pedido p : lista) {
            modeloPedidos.addRow(new Object[]{p.getId(), p.getFecha(), p.getIdCliente()});
        }
    }

    private void cargarDetallesPedido() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) return;

        int pedidoId = (int) modeloPedidos.getValueAt(fila, 0);
        modeloDetalles.setRowCount(0);
        List<DetallePedido> detalles = detalleDAO.obtenerPorPedido(pedidoId);
        for (DetallePedido d : detalles) {
            modeloDetalles.addRow(new Object[]{d.getId(), d.getIdPedido(), d.getIdProducto(), d.getCantidad()});
        }
    }

    private void insertarPedido() {
        JTextField campoFecha = new JTextField();
        JTextField campoCliente = new JTextField();
        Object[] mensaje = {
                "Fecha (YYYY-MM-DD):", campoFecha,
                "Cliente ID:", campoCliente
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Nuevo Pedido", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String fecha = campoFecha.getText();
            int clienteId = Integer.parseInt(campoCliente.getText());
            Pedido nuevo = new Pedido(0, fecha, clienteId);
            pedidoDAO.insertarPedido(nuevo);
            cargarPedidos();
        }
    }

    private void modificarPedido() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido.");
            return;
        }

        int id = (int) modeloPedidos.getValueAt(fila, 0);
        String fechaActual = modeloPedidos.getValueAt(fila, 1).toString();
        String clienteIdActual = modeloPedidos.getValueAt(fila, 2).toString();

        JTextField campoFecha = new JTextField(fechaActual);
        JTextField campoCliente = new JTextField(clienteIdActual);

        Object[] mensaje = {
                "Fecha (YYYY-MM-DD):", campoFecha,
                "Cliente ID:", campoCliente
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Modificar Pedido", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String nuevaFecha = campoFecha.getText();
            int nuevoClienteId = Integer.parseInt(campoCliente.getText());
            Pedido p = new Pedido(id, nuevaFecha, ClienteId);
            pedidoDAO.actualizarPedido(p);
            cargarPedidos();
        }
    }

    private void eliminarPedido() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido.");
            return;
        }

        int id = (int) modeloPedidos.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar pedido con ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            pedidoDAO.eliminarPedido(id);
            cargarPedidos();
            modeloDetalles.setRowCount(0);
        }
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
