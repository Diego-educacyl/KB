/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Empresa.vistas;

import Empresa.controladores.MenuPosDAO;

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
    private MenuPosDAO controladorPOS;
    private JButton btnGestionarClientes;
    private JButton btnAdmin; 

    public MenuPOS(boolean esAdmin) {
        this.esAdmin = esAdmin;

        setTitle("Sistema POS - Restaurante");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        areaPedido = new JTextArea();
        areaPedido.setEditable(false);
        areaPedido.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaPedido.setMargin(new Insets(10, 10, 10, 10));

        labelTotal = new JLabel("Total: 0.00 €");
        labelTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelTotal.setHorizontalAlignment(SwingConstants.RIGHT);


        this.controladorPOS = new MenuPosDAO(areaPedido, labelTotal);

      
        add(crearPanelIzquierdo(), BorderLayout.WEST);
        add(crearPanelCentro(), BorderLayout.CENTER);
        add(crearPanelDerecho(), BorderLayout.EAST);

       
        setVisible(true);
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; 
        gbc.weightx = 1;
        gbc.ipady = 20; 


        List<JButton> botonesMenu = new ArrayList<>();

        JButton btnHamburguesas = new JButton("Hamburguesas");
        JButton btnAcompanamientos = new JButton("Acompañamientos");
        JButton btnBebidas = new JButton("Bebidas");
        btnGestionarClientes = new JButton("Gestionar Clientes"); 
        JButton btnCambiarUsuario = new JButton("Cambiar Usuario");
        btnAdmin = new JButton("Administración"); 

        botonesMenu.add(btnHamburguesas);
        botonesMenu.add(btnAcompanamientos);
        botonesMenu.add(btnBebidas);
        botonesMenu.add(btnGestionarClientes);
        botonesMenu.add(btnCambiarUsuario);

        if (esAdmin) {
            botonesMenu.add(btnAdmin);
        }

        for (int i = 0; i < botonesMenu.size(); i++) {
            gbc.gridy = i; 
            JButton btn = botonesMenu.get(i);

            panel.add(btn, gbc);
        }

        btnHamburguesas.addActionListener(e -> mostrarCategoria("hamburguesas"));
        btnAcompanamientos.addActionListener(e -> mostrarCategoria("acompanamientos"));
        btnBebidas.addActionListener(e -> mostrarCategoria("bebidas"));

        btnGestionarClientes.addActionListener(e -> {
            ClientesFrame clientesFrame = new ClientesFrame(); 
            clientesFrame.setVisible(true);

        });

        btnCambiarUsuario.addActionListener(e -> {
            dispose(); 
            new PantallaLoginPOS(); 
        });

        if (esAdmin) { 
            btnAdmin.addActionListener(e -> mostrarCategoria("admin"));
        }

        return panel;
    }

    private JPanel crearPanelCentro() {
        panelCentro = new JPanel(new CardLayout());

        panelCentro.add(crearPanelHamburguesas(), "hamburguesas");
        panelCentro.add(crearPanelAcompanamientos(), "acompanamientos");
        panelCentro.add(crearPanelBebidas(), "bebidas");
        panelCentro.add(crearPanelAdministracion(), "admin");

        return panelCentro;
    }

    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(250, 0)); 

        JScrollPane scrollPedido = new JScrollPane(areaPedido); 

        panel.add(scrollPedido, BorderLayout.CENTER);
        panel.add(labelTotal, BorderLayout.NORTH); 

        JButton btnPagar = new JButton("Pagar");
        btnPagar.setFont(new Font("Arial", Font.BOLD, 18));
        btnPagar.addActionListener(e -> realizarPago());

        panel.add(btnPagar, BorderLayout.SOUTH);

        return panel;
    }

    private void mostrarCategoria(String nombre) {
        CardLayout cl = (CardLayout) panelCentro.getLayout();
        cl.show(panelCentro, nombre);
    }

   
    private JPanel crearPanelHamburguesas() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10)); 
        String[] opciones = {"Clásica", "Cheddar", "BBQ", "Doble"};
        for (String item : opciones) {
            JButton btn = new JButton(item);
            btn.addActionListener(e -> controladorPOS.agregarProducto(new Producto("H" + item.charAt(0), item, 5.0, "Hamburguesa")));
            panel.add(btn);
        }
        return panel;
    }

    private JPanel crearPanelAcompanamientos() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        String[] opciones = {"Patatas", "Ensalada", "Aros de Cebolla", "Nachos"};
        for (String item : opciones) {
            JButton btn = new JButton(item);
            btn.addActionListener(e -> controladorPOS.agregarProducto(new Producto("A" + item.charAt(0), item, 2.5, "Acompañamiento")));
            panel.add(btn);
        }
        return panel;
    }

    private JPanel crearPanelBebidas() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        String[] opciones = {"Coca-Cola", "Fanta", "Agua", "Cerveza"};
        for (String item : opciones) {
            JButton btn = new JButton(item);
            btn.addActionListener(e -> controladorPOS.agregarProducto(new Producto("B" + item.charAt(0), item, 1.8, "Bebida")));
            panel.add(btn);
        }
        return panel;
    }

    private JPanel crearPanelAdministracion() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10)); // Una columna para la administración

        JButton btnResumenVentas = new JButton("Resumen de Ventas");
        JButton btnInformeInventario = new JButton("Informe de Inventario");
        JButton btnModificarInventario = new JButton("Modificar Inventario");
        JButton btnEliminarTicket = new JButton("Eliminar Ticket");
        JButton btnGestionarEmpleadosInterno = new JButton("Gestionar Empleados"); // Botón para empleados

        btnResumenVentas.addActionListener(e -> areaPedido.setText(
                "🔍 Resumen de ventas por producto:\n\n- Hamburguesa Clásica: 20 uds\n- Bebida: 35 uds\n\nTotal: 550€"
        ));

        btnInformeInventario.addActionListener(e -> areaPedido.setText(
                "📦 Informe de inventario:\n\n- Hamburguesa Clásica: 80 uds\n- Patatas: 120 uds\n- Coca-Cola: 60 uds"
        ));
        
        // --- INICIO DE LA MODIFICACIÓN: Acción para "Modificar Inventario" ---
        // Se reemplazó el antiguo código que usaba JOptionPane para pedir datos.
        // Ahora, al hacer clic, se abre el nuevo InsumosFrame.
        btnModificarInventario.addActionListener(e -> {
            // Abre la nueva ventana InsumosFrame (Gestión de Insumos)
            InsumosFrame insumosFrame = new InsumosFrame();
            // El constructor de InsumosFrame ya se encarga de hacerlo visible,
            // por lo que no es necesario llamar a insumosFrame.setVisible(true) aquí.
        });
        // --- FIN DE LA MODIFICACIÓN ---

       
        btnEliminarTicket.addActionListener(e -> {
            String ticketId = JOptionPane.showInputDialog(this, "Ingrese el número de ticket a eliminar:");
            if (ticketId != null && !ticketId.trim().isEmpty()) {
                areaPedido.setText("🗑️ Ticket #" + ticketId + " eliminado correctamente.");

            }
        });

        btnGestionarEmpleadosInterno.addActionListener(e -> {
            EmpleadosFrame empleadosFrame = new EmpleadosFrame();
            empleadosFrame.setVisible(true);
        });

        panel.add(btnResumenVentas);
        panel.add(btnInformeInventario);
        panel.add(btnModificarInventario);
        panel.add(btnEliminarTicket);


        if (esAdmin) {
            panel.add(btnGestionarEmpleadosInterno);
        }

        return panel;
    }

    private void realizarPago() {
       
        controladorPOS.realizarPago();
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
    }                        
                  

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPOS(true).setVisible(true);
            }
        });                   
}

}
