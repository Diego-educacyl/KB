/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Empresa.vistas;

import Empresa.controladores.ControladorPOS;
import Empresa.modelos.DetallePedido;
import Empresa.modelos.Producto;
import Empresa.vistas.MenuPOS;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author diego.cruang
 */
public class MenuPOS extends javax.swing.JFrame {

   
      private JPanel panelCentro;
    private JTextArea areaPedido;
    private JLabel labelTotal;
    private boolean esAdmin;
    private ControladorPOS controladorPOS;
    
     // [MODIFICACIÓN INICIO] - Declaración de nuevos botones como variables de instancia
    // Esto permite un acceso más fácil a ellos para configurar propiedades o listeners.
    private JButton btnGestionarClientes;
    // [MODIFICACIÓN FIN]

    public MenuPOS(boolean esAdmin) {
        this.esAdmin = esAdmin;

        setTitle("Sistema POS - Restaurante");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Izquierdo
        JPanel panelIzquierda = new JPanel(new GridBagLayout());
        panelIzquierda.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.ipady = 40;

        List<JButton> botones = new ArrayList<>();

        JButton btnHamburguesas = new JButton("Hamburguesas");
        JButton btnAcompanamientos = new JButton("Acompañamientos");
        JButton btnBebidas = new JButton("Bebidas");
        JButton btnCambiarUsuario = new JButton("Cambiar Usuario");
        JButton btnAdmin = new JButton("Administración");

        // [MODIFICACIÓN INICIO] - Inicialización de los nuevos botones
        btnGestionarClientes = new JButton("Gestionar Clientes");
        // [MODIFICACIÓN FIN]
        
        botones.add(btnHamburguesas);
        botones.add(btnAcompanamientos);
        botones.add(btnBebidas);
        botones.add(btnGestionarClientes); // [MODIFICACIÓN] - Añadido el botón de Clientes (siempre visible)
        botones.add(btnCambiarUsuario);

        // [MODIFICACIÓN INICIO] - Lógica condicional para añadir el botón de Administración
        // El botón "Administración" solo se añade a la lista si el usuario es administrador.
        // Esto controla el acceso a toda la sección de administración, incluyendo la gestión de empleados.
        if (esAdmin) {
            botones.add(btnAdmin);
        }
        // [MODIFICACIÓN FIN]
        
        
//
        // Panel Central con CardLayout
        panelCentro = new JPanel(new CardLayout());

        panelCentro.add(crearPanelHamburguesas(), "hamburguesas");
        panelCentro.add(crearPanelAcompanamientos(), "acompanamientos");
        panelCentro.add(crearPanelBebidas(), "bebidas");
        panelCentro.add(crearPanelAdministracion(), "admin");

        // Panel Derecho
        JPanel panelDerecha = new JPanel(new BorderLayout());
        panelDerecha.setPreferredSize(new Dimension(250, 0));

        areaPedido = new JTextArea();
        areaPedido.setEditable(false);
        areaPedido.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaPedido.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPedido = new JScrollPane(areaPedido);

        labelTotal = new JLabel("Total: 0.00 €");
        labelTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelTotal.setHorizontalAlignment(SwingConstants.RIGHT);

        JButton btnPagar = new JButton("Pagar");
        btnPagar.setFont(new Font("Arial", Font.BOLD, 18));
        btnPagar.addActionListener(e -> realizarPago());

        panelDerecha.add(scrollPedido, BorderLayout.CENTER);
        panelDerecha.add(labelTotal, BorderLayout.NORTH);
        panelDerecha.add(btnPagar, BorderLayout.SOUTH);

        // Controlador
        controladorPOS = new ControladorPOS(areaPedido, labelTotal);

        for (int i = 0; i < botones.size(); i++) {
            gbc.gridy = i;
            JButton btn = botones.get(i);
            btn.setPreferredSize(new Dimension(200, 40));
            panelIzquierda.add(btn, gbc);
        }

         // [MODIFICACIÓN INICIO] - ActionListeners para los botones del panel lateral
        // Eventos de botones
        btnHamburguesas.addActionListener(e -> mostrarCategoria("hamburguesas"));
        btnAcompanamientos.addActionListener(e -> mostrarCategoria("acompanamientos"));
        btnBebidas.addActionListener(e -> mostrarCategoria("bebidas"));
        
        
        // ActionListener para el botón "Gestionar Clientes"
        btnGestionarClientes.addActionListener(e -> {
            ClientesFrame clientesFrame = new ClientesFrame();
            clientesFrame.setVisible(true);
            // Opcional: Si quieres que el MenuPOS se cierre al abrir ClientesFrame, descomenta la siguiente línea:
            // dispose(); 
        });
        
        btnCambiarUsuario.addActionListener(e -> {
            dispose(); // Cierra el MenuPOS
            new PantallaLoginPOS(); // Abre la pantalla de login
        });

        // ActionListener para el botón "Administración"
        btnAdmin.addActionListener(e -> mostrarCategoria("admin"));
        // El ActionListener para "Gestionar Empleados" se encuentra ahora dentro de crearPanelAdministracion()
        // [MODIFICACIÓN FIN]

        
        // Añadir a ventana principal
        add(panelIzquierda, BorderLayout.WEST);
        add(panelCentro, BorderLayout.CENTER);
        add(panelDerecha, BorderLayout.EAST);

        setVisible(true);
    }

    private void mostrarCategoria(String nombre) {
        CardLayout cl = (CardLayout) panelCentro.getLayout();
        cl.show(panelCentro, nombre);
    }

    private JPanel crearPanelHamburguesas() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        String[] opciones = {"Clásica", "Cheddar", "BBQ", "Doble"};
        for (String item : opciones) {
            JButton btn = new JButton(item);
            btn.addActionListener(e -> controladorPOS.agregarProducto(new Producto("H" + item, item, 5.0, "Hamburguesa")));
            panel.add(btn);
        }
        return panel;
    }

    private JPanel crearPanelAcompanamientos() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        String[] opciones = {"Patatas", "Ensalada", "Aros de Cebolla", "Nachos"};
        for (String item : opciones) {
            JButton btn = new JButton(item);
            btn.addActionListener(e -> controladorPOS.agregarProducto(new Producto("A" + item, item, 2.5, "Acompañamiento")));
            panel.add(btn);
        }
        return panel;
    }

    private JPanel crearPanelBebidas() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        String[] opciones = {"Coca-Cola", "Fanta", "Agua", "Cerveza"};
        for (String item : opciones) {
            JButton btn = new JButton(item);
            btn.addActionListener(e -> controladorPOS.agregarProducto(new Producto("B" + item, item, 1.8, "Bebida")));
            panel.add(btn);
        }
        return panel;
    }

    private JPanel crearPanelAdministracion() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton btnResumenVentas = new JButton("Resumen de Ventas");
        JButton btnInformeInventario = new JButton("Informe de Inventario");
        JButton btnModificarInventario = new JButton("Modificar Inventario");
        JButton btnEliminarTicket = new JButton("Eliminar Ticket");
        // [MODIFICACIÓN INICIO] - Se declara e inicializa el botón de Gestionar Empleados aquí.
        // Este botón es LOCAL a este método y solo se añadirá a este panel.
        JButton btnGestionarEmpleadosInterno = new JButton("Gestionar Empleados"); 
        //[MODIFICACION FIN]

        btnResumenVentas.addActionListener(e -> areaPedido.setText(
            "🔍 Resumen de ventas por producto:\n\n- Hamburguesa Clásica: 20 uds\n- Bebida: 35 uds\n\nTotal: 550€"
        ));

        btnInformeInventario.addActionListener(e -> areaPedido.setText(
            "📦 Informe de inventario:\n\n- Hamburguesa Clásica: 80 uds\n- Patatas: 120 uds\n- Coca-Cola: 60 uds"
        ));

        btnModificarInventario.addActionListener(e -> {
            String producto = JOptionPane.showInputDialog(this, "Producto a modificar:");
            String cantidadStr = JOptionPane.showInputDialog(this, "Cantidad a agregar:");
            areaPedido.setText("✅ Se agregó " + cantidadStr + " unidades a " + producto);
        });

        btnEliminarTicket.addActionListener(e -> {
            String ticketId = JOptionPane.showInputDialog(this, "Ingrese el número de ticket a eliminar:");
            areaPedido.setText("🗑️ Ticket #" + ticketId + " eliminado correctamente.");
        });
        
         // [MODIFICACIÓN] - ActionListener para el botón "Gestionar Empleados" (ahora interno al panel Admin)
        btnGestionarEmpleadosInterno.addActionListener(e -> { // <-- ESTO ES LO QUE FALTABA
            EmpleadosFrame empleadosFrame = new EmpleadosFrame();
            empleadosFrame.setVisible(true);
        });
        

        panel.add(btnResumenVentas);
        panel.add(btnInformeInventario);
        panel.add(btnModificarInventario);
        panel.add(btnEliminarTicket);
        
        // [MODIFICACIÓN CLAVE] - Añadir el botón de Gestionar Empleados SOLO SI esAdmin es true.
        if (esAdmin) { // <-- ESTO ES LO QUE FALTABA
            panel.add(btnGestionarEmpleadosInterno); // <-- ESTO ES LO QUE FALTABA
        }

        return panel;
    }

    private void realizarPago() {
        JOptionPane.showMessageDialog(this, "Pedido enviado a cocina.\nGracias!");
        controladorPOS.limpiarPedido();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
    }// </editor-fold>                        

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
            java.util.logging.Logger.getLogger(MenuPOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPOS(true).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}