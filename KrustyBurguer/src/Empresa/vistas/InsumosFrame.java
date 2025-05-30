/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Empresa.vistas;

import Empresa.controladores.InsumoDAO; // Asegúrate de que el paquete y nombre de la clase sean correctos
import Empresa.modelos.Insumo;         // Asegúrate de que el paquete y nombre de la clase sean correctos

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 *
 * @author martine.llaviv
 */
public class InsumosFrame extends javax.swing.JFrame {

    private JTable tablaInsumos;
    private DefaultTableModel modeloTabla;

    // Campos de texto para la edición de insumos
    private JTextField txtId; // Campo oculto para el ID del insumo seleccionado
    private JTextField txtNombre;
    private JTextField txtStock; // Asumimos que 'stock' es la 'cantidad' en tu diseño
    private JTextField txtCategoria; // Asumimos que 'categoria' es la 'unidad de medida' en tu diseño
    private JTextField txtCodigo; // Campo para el código del insumo
    private JTextField txtPrecio; // Campo para el precio del insumo

    public InsumosFrame() {
        setTitle("Gestionar Insumos");
        setSize(800, 600); // Ajusta el tamaño de la ventana
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Cierra solo esta ventana, no toda la aplicación
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        initComponentsCustom(); // Método para inicializar nuestros componentes
        cargarInsumos(); // Carga los datos al iniciar la ventana
        setVisible(true);
    }

    private void initComponentsCustom() {
        // Layout principal
        setLayout(new BorderLayout(10, 10)); // Espaciado entre componentes

        // --- Panel Superior: Tabla de Insumos ---
        // [MODIFICACIÓN] Columnas de la tabla basadas en tu clase Insumo
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Código", "Nombre", "Stock", "Categoría", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que solo la columna de ID no sea editable directamente en la tabla
                return column != 0;
            }
        };
        tablaInsumos = new JTable(modeloTabla);
        tablaInsumos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite seleccionar solo una fila

        // Añadir listener para cuando se selecciona una fila, cargar datos en los campos de abajo
        tablaInsumos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaInsumos.getSelectedRow() != -1) {
                mostrarDetallesInsumo();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaInsumos);
        add(scrollPane, BorderLayout.NORTH); // Coloca la tabla en la parte superior

        // --- Panel Central: Formulario de Edición y Botones ---
        JPanel panelEdicion = new JPanel(new GridBagLayout());
        panelEdicion.setBorder(BorderFactory.createTitledBorder("Detalles del Insumo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Inicializar campos de texto
        txtId = new JTextField(5);
        txtId.setEditable(false); // El ID no debe ser editable por el usuario

        txtCodigo = new JTextField(20);
        txtNombre = new JTextField(20);
        txtStock = new JTextField(10); // Campo para cantidad/stock
        txtCategoria = new JTextField(20); // Campo para categoría/unidad de medida
        txtPrecio = new JTextField(10); // Campo para precio

        // Etiquetas y campos
        gbc.gridx = 0; gbc.gridy = 0; panelEdicion.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelEdicion.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelEdicion.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelEdicion.add(txtCodigo, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelEdicion.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panelEdicion.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelEdicion.add(new JLabel("Stock (Cantidad):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panelEdicion.add(txtStock, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelEdicion.add(new JLabel("Categoría (Unidad):"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; panelEdicion.add(txtCategoria, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; panelEdicion.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; panelEdicion.add(txtPrecio, gbc);


        // Panel para botones (Guardar, Descartar)
        JPanel panelBotonesEdicion = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton btnGuardar = new JButton("Guardar Cambios");
        JButton btnDescartar = new JButton("Descartar");

        btnGuardar.addActionListener(e -> guardarModificaciones());
        btnDescartar.addActionListener(e -> limpiarCampos());

        panelBotonesEdicion.add(btnDescartar);
        panelBotonesEdicion.add(btnGuardar);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2; // Ocupa dos columnas
        panelEdicion.add(panelBotonesEdicion, gbc);
        
        add(panelEdicion, BorderLayout.CENTER); // Coloca el panel de edición en el centro

        // Añadir espacio inferior para estética
        add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
    }

    // Carga los insumos desde la base de datos a la tabla
    private void cargarInsumos() {
        modeloTabla.setRowCount(0); // Limpia la tabla
        List<Insumo> insumos = InsumoDAO.obtenerInsumos(); // Usa el método correcto de tu DAO
        for (Insumo insumo : insumos) {
            modeloTabla.addRow(new Object[]{
                insumo.getId(),
                insumo.getCodigo(),
                insumo.getNombre(),
                insumo.getStock(),
                insumo.getCategoria(),
                insumo.getPrecio()
            });
        }
    }

    // Muestra los detalles del insumo seleccionado en los campos de edición
    private void mostrarDetallesInsumo() {
        int filaSeleccionada = tablaInsumos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtCodigo.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtStock.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtCategoria.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            txtPrecio.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
        }
    }

    // Guarda las modificaciones del insumo seleccionado
    private void guardarModificaciones() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un insumo para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            double stock = Double.parseDouble(txtStock.getText().trim());
            String categoria = txtCategoria.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());

            // Validaciones básicas
            if (codigo.isEmpty() || nombre.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Código, Nombre y Categoría no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (stock < 0) {
                 JOptionPane.showMessageDialog(this, "El stock no puede ser negativo.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (precio < 0) {
                 JOptionPane.showMessageDialog(this, "El precio no puede ser negativo.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Insumo insumoModificado = new Insumo(id, codigo, nombre, precio, (int)stock, categoria); // Castear stock a int si en la DB es int
            InsumoDAO.actualizar(insumoModificado);

            JOptionPane.showMessageDialog(this, "¡Modificaciones guardadas exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarInsumos(); // Recargar la tabla para ver los cambios
            limpiarCampos(); // Limpiar los campos después de guardar
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce valores numéricos válidos para Stock y Precio.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar modificaciones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // Limpia los campos de texto del formulario de edición
    private void limpiarCampos() {
        txtId.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        txtStock.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        tablaInsumos.clearSelection(); // Desseleccionar cualquier fila
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
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InsumosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InsumosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InsumosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InsumosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

      
       try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InsumosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InsumosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InsumosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InsumosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InsumosFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
