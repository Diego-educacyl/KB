/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Empresa.vistas;

import Empresa.controladores.EmpleadoDAO;
import Empresa.modelos.Empleado;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author martine.llaviv
 */
public class EmpleadosFrame extends javax.swing.JFrame {
    
   private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;

    /**
     * Creates new form EmpleadosFrame
     */
    public EmpleadosFrame() {
        setTitle("Gestión de Empleados");
        setSize(850, 450); // [MODIFICACIÓN] Aumentado el tamaño para la nueva columna
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // [MODIFICACIÓN] Añadida "Clave" a las cabeceras de la tabla
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Teléfono", "Dirección", "Puesto", "Clave"}, 0);
        tablaEmpleados = new JTable(modeloTabla);
        // Opcional: Deshabilitar la edición de la columna ID
        tablaEmpleados.setDefaultEditor(Object.class, null); // Esto deshabilita la edición en todas las celdas
        // Para permitir edición en algunas celdas, pero no ID, necesitaríamos un TableModel personalizado.
        // Por ahora, lo dejaremos editable para simplificar la demostración de la edición de clave.

        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);

        JButton btnInsertar = new JButton("Insertar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");

        btnInsertar.addActionListener(e -> insertarEmpleado());
        btnModificar.addActionListener(e -> modificarEmpleado());
        btnEliminar.addActionListener(e -> eliminarEmpleado());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnInsertar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarEmpleados();
        setVisible(true);
    }

    private void cargarEmpleados() {
        modeloTabla.setRowCount(0); // Limpiar todas las filas existentes
        List<Empleado> empleados = EmpleadoDAO.obtenerTodos();
        for (Empleado emp : empleados) {
            // [MODIFICACIÓN] Añadido emp.getClave() al modelo de la tabla
            modeloTabla.addRow(new Object[]{
                emp.getId(),
                emp.getNombre(),
                emp.getTelefono(),
                emp.getDireccion(),
                emp.getPuesto(),
                emp.getClave() // Añadir la clave
            });
        }
    }

    private void insertarEmpleado() {
        // [MODIFICACIÓN] Uso de JPasswordField para la clave y agrupamiento de inputs
        JTextField txtNombre = new JTextField(20);
        JTextField txtTelefono = new JTextField(20);
        JTextField txtDireccion = new JTextField(20);
        JTextField txtPuesto = new JTextField(20);
        JPasswordField txtClave = new JPasswordField(20); // Campo de contraseña

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
        labels.add(new JLabel("Nombre:"));
        labels.add(new JLabel("Teléfono:"));
        labels.add(new JLabel("Dirección:"));
        labels.add(new JLabel("Puesto:"));
        labels.add(new JLabel("Clave:"));
        panel.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        controls.add(txtNombre);
        controls.add(txtTelefono);
        controls.add(txtDireccion);
        controls.add(txtPuesto);
        controls.add(txtClave);
        panel.add(controls, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, panel, "Insertar Empleado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText();
            String telefono = txtTelefono.getText();
            String direccion = txtDireccion.getText();
            String puesto = txtPuesto.getText();
            String clave = new String(txtClave.getPassword()); // Obtener la clave del JPasswordField

            // Validación básica
            if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || puesto.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ID es 0 ya que la base de datos lo asignará automáticamente (autoincrement).
            Empleado emp = new Empleado(0, nombre, telefono, direccion, puesto, clave); // [MODIFICACIÓN] Pasar la clave
            EmpleadoDAO.insertar(emp);
            JOptionPane.showMessageDialog(this, "Empleado insertado con éxito.");
            cargarEmpleados();
        }
    }

    private void modificarEmpleado() {
        int fila = tablaEmpleados.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un empleado para modificar.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombreActual = (String) modeloTabla.getValueAt(fila, 1);
        String telefonoActual = (String) modeloTabla.getValueAt(fila, 2);
        String direccionActual = (String) modeloTabla.getValueAt(fila, 3);
        String puestoActual = (String) modeloTabla.getValueAt(fila, 4);
        String claveActual = (String) modeloTabla.getValueAt(fila, 5); // [MODIFICACIÓN] Obtener la clave actual

        // [MODIFICACIÓN] Uso de JPasswordField para la clave y agrupamiento de inputs
        JTextField txtNombre = new JTextField(nombreActual, 20);
        JTextField txtTelefono = new JTextField(telefonoActual, 20);
        JTextField txtDireccion = new JTextField(direccionActual, 20);
        JTextField txtPuesto = new JTextField(puestoActual, 20);
        JPasswordField txtClave = new JPasswordField(claveActual, 20); // Campo de contraseña pre-llenado

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
        labels.add(new JLabel("Nombre:"));
        labels.add(new JLabel("Teléfono:"));
        labels.add(new JLabel("Dirección:"));
        labels.add(new JLabel("Puesto:"));
        labels.add(new JLabel("Clave:"));
        panel.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        controls.add(txtNombre);
        controls.add(txtTelefono);
        controls.add(txtDireccion);
        controls.add(txtPuesto);
        controls.add(txtClave);
        panel.add(controls, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modificar Empleado (ID: " + id + ")", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNombre.getText();
            String nuevoTelefono = txtTelefono.getText();
            String nuevaDireccion = txtDireccion.getText();
            String nuevoPuesto = txtPuesto.getText();
            String nuevaClave = new String(txtClave.getPassword()); // Obtener la nueva clave

            // Validación básica
            if (nuevoNombre.isEmpty() || nuevoTelefono.isEmpty() || nuevaDireccion.isEmpty() || nuevoPuesto.isEmpty() || nuevaClave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Empleado emp = new Empleado(id, nuevoNombre, nuevoTelefono, nuevaDireccion, nuevoPuesto, nuevaClave); // [MODIFICACIÓN] Pasar la nueva clave
            EmpleadoDAO.actualizar(emp);
            JOptionPane.showMessageDialog(this, "Empleado modificado con éxito.");
            cargarEmpleados();
        }
    }

    private void eliminarEmpleado() {
        int fila = tablaEmpleados.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un empleado para eliminar.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombreEmpleado = (String) modeloTabla.getValueAt(fila, 1); // Para el mensaje de confirmación

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres eliminar a " + nombreEmpleado + " (ID: " + id + ")?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            EmpleadoDAO.eliminar(id);
            JOptionPane.showMessageDialog(this, "Empleado eliminado con éxito.");
            cargarEmpleados();
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
            java.util.logging.Logger.getLogger(EmpleadosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmpleadosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmpleadosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmpleadosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
       try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmpleadosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmpleadosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmpleadosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmpleadosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmpleadosFrame().setVisible(true);
            }
        });
    }

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
