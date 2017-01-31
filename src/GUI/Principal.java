/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import ComandoChainOfResponsability.ComandoInvalidoException;
import ComandoChainOfResponsability.CrearRegistroParser;
import ComandoChainOfResponsability.CrearTablaParser;
import ComandoChainOfResponsability.EliminarRegistroParser;
import ComandoChainOfResponsability.EliminarTablaParser;
import ComandoChainOfResponsability.JoinTablaParser;
import ComandoChainOfResponsability.ModificarRegistroParser;
import ComandoChainOfResponsability.ModificarTablaParser;
import ComandoChainOfResponsability.Parser;
import ComandoChainOfResponsability.SeleccionarTablaParser;
import ComandoChainOfResponsability.SinCampoException;
import ComandoChainOfResponsability.SinCampoNuevoException;
import ComandoChainOfResponsability.SinCamposException;
import ComandoChainOfResponsability.SinClaveException;
import ComandoChainOfResponsability.SinLongitudException;
import ComandoChainOfResponsability.SinRegistroException;
import ComandoChainOfResponsability.SinTablaException;
import ComandoChainOfResponsability.SinValorDeCampoException;
import DatosCommandRegistro.CampoClaveVacioException;
import DatosCommandRegistro.NumeroCamposErroneoException;
import DatosCommandRegistro.RegistroExistenteException;
import DatosCommandRegistro.RegistroNoExisteException;
import DatosCommandTabla.CampoClaveInvalidoException;
import DatosCommandTabla.CampoNoExisteException;
import DatosCommandTabla.CamposRepetidosException;
import DatosCommandTabla.GestorTablas;
import DatosCommandTabla.LongitudInvalidaException;
import DatosCommandTabla.NumeroTablasException;
import DatosCommandTabla.TablaExisteException;
import DatosCommandTabla.TablaNoExisteException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.mxrck.autocompleter.TextAutoCompleter;

/**
 *
 * @author Estudiantes
 */
public class Principal extends javax.swing.JFrame {

    private final File ARCHIVO;
    private String nombreTabla;
    private String campos;
    private ListSelectionListener lslBoton;
    private final Parser eliminarTabla;
    private final TextAutoCompleter textAutoAcompleter;
    
    /**
     * Creates new form Principal
     */
    public Principal(final File ARCHIVO) throws IOException {      
        initComponents();
        this.ARCHIVO = ARCHIVO;
        textAutoAcompleter = new TextAutoCompleter(txtEntrada);
        generarTabla();
        txtEntradaConfig();
        bMostrar.setEnabled(false);
        // Chain of Responsability
        eliminarTabla = new SeleccionarTablaParser(new JoinTablaParser(
                        new EliminarTablaParser(new EliminarRegistroParser(
                        new ModificarTablaParser(new ModificarRegistroParser(
                        new CrearRegistroParser(new CrearTablaParser())))))));
    }
    
    private void generarTabla() throws FileNotFoundException, IOException {
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        String[] tabla = new String[5];
        tabla[0] = "Nombre";
        tabla[1] = "N° Registros";
        tabla[2] = "Campo Clave";
        tabla[3] = "Longitud";
        tabla[4] = "Campos";
        
        modelo.setColumnIdentifiers(tabla);
        
        List<String[]> listaTablas = GestorTablas.getInstancia().crearListTablas(ARCHIVO);
        
        if(listaTablas==null || listaTablas.isEmpty()){
            this.tSalida.setModel(modelo);
            return;
        }
        
        for (int i=0; i<listaTablas.size(); i++) {
            modelo.addRow(listaTablas.get(i));
        }
        
        this.tSalida.getSelectionModel().removeListSelectionListener(lslBoton);
        this.tSalida.setModel(modelo);
        
        this.lslBoton = new ListSelectionListener(){
                @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    nombreTabla = (String) tSalida.getModel().getValueAt(
                            tSalida.getSelectedRow(), 0);
                    campos = (String) tSalida.getModel().getValueAt(
                            tSalida.getSelectedRow(), 4);
                    bMostrar.setEnabled(true);
                }
            }
            };
        
        this.tSalida.getSelectionModel().addListSelectionListener(lslBoton);
        
        /*this.tSalida.getSelectionModel().addListSelectionListener(
            new ListSelectionListener(){
                @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    nombreTabla = (String) tSalida.getModel().getValueAt(
                            tSalida.getSelectedRow(), 0);
                    campos = (String) tSalida.getModel().getValueAt(
                            tSalida.getSelectedRow(), 2);
                    bMostrar.setEnabled(true);
                }
            }
            }
        );*/
    }
    
    private void txtEntradaConfig() {
        txtEntrada.addKeyListener(new KeyAdapter() {
            boolean ctrlPressed = false;
            boolean cPressed = false;
            boolean vPressed = false;
            boolean xPreseed = false;

            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_C:
                        cPressed = true;
                        break;
                    case KeyEvent.VK_V:
                        vPressed = true;
                        break;
                    case KeyEvent.VK_X:
                        xPreseed = true;
                        break;
                    case KeyEvent.VK_CONTROL:
                        ctrlPressed = true;
                        break;
                }

                if (ctrlPressed) { 
                    if (cPressed) {
                        txtEntrada.copy();
                        e.consume();// Stop the event from propagating.
                    } else if (vPressed) {
                        txtEntrada.paste();
                        e.consume();// Stop the event from propagating.
                    } else if (xPreseed) {
                        txtEntrada.cut();
                        e.consume();// Stop the event from propagating.
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_C:
                        cPressed = false;
                        break;
                    case KeyEvent.VK_V:
                        vPressed = false;
                        break;
                    case KeyEvent.VK_X:
                        xPreseed = false;
                        break;
                    case KeyEvent.VK_CONTROL:
                        ctrlPressed = false;
                        break;
                }

                if (ctrlPressed) { 
                    if (cPressed) {
                        txtEntrada.copy();
                        e.consume();// Stop the event from propagating.
                    } else if (vPressed) {
                        txtEntrada.paste();
                        e.consume();// Stop the event from propagating.
                    } else if (xPreseed) {
                        txtEntrada.cut();
                        e.consume();// Stop the event from propagating.
                    }
                }
            }
        });
        
        //textAutoAcompleter.setCaseSensitive(true);
        textAutoAcompleter.addItem("CREAR TABLA");
        textAutoAcompleter.addItem("CREAR REGISTRO");
        textAutoAcompleter.addItem("MODIFICAR TABLA");
        textAutoAcompleter.addItem("MODIFICAR REGISTRO");
        textAutoAcompleter.addItem("ELIMINAR TABLA");
        textAutoAcompleter.addItem("ELIMINAR REGISTRO");
        textAutoAcompleter.addItem("SELECCIONAR DE");
        textAutoAcompleter.addItem("UNIR");

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtEntrada = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btEjecutar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tSalida = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtHistorial = new javax.swing.JTextArea();
        bMostrar = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);

        txtEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEntradaActionPerformed(evt);
            }
        });

        jLabel1.setText("Historial:");

        jLabel2.setText("Salida:");

        btEjecutar.setText("Ejecutar");
        btEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEjecutarActionPerformed(evt);
            }
        });

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        tSalida.setAutoCreateRowSorter(true);
        tSalida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "N° Registros", "Campo Clave", "Longitud", "Campos"
            }
        ));
        jScrollPane3.setViewportView(tSalida);

        jScrollPane4.setViewportView(jScrollPane3);

        txtHistorial.setEditable(false);
        txtHistorial.setColumns(20);
        txtHistorial.setRows(5);
        jScrollPane1.setViewportView(txtHistorial);

        jScrollPane5.setViewportView(jScrollPane1);

        bMostrar.setText("Mostrar Campos");
        bMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMostrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bMostrar)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btEjecutar)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btEjecutar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bMostrar)
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEntradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEntradaActionPerformed

    private void btEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEjecutarActionPerformed
        String comando = txtEntrada.getText();
        
        try {
            txtHistorial.append(eliminarTabla.analizar(comando) + '\n');
            generarTabla();
            txtEntrada.setText("");
        } catch (TablaExisteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (TablaNoExisteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (ComandoInvalidoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (SinCamposException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (SinClaveException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (SinLongitudException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (CampoClaveInvalidoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (CamposRepetidosException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "¡Error en la conversión de la longitud a entero!");
        } catch (LongitudInvalidaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (SinCampoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (SinCampoNuevoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (CampoNoExisteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (NumeroCamposErroneoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (RegistroExistenteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (RegistroNoExisteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (SinRegistroException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (SinTablaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (NumeroTablasException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (CampoClaveVacioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (SinValorDeCampoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No existe el archivo META_BD");
        } 
    }//GEN-LAST:event_btEjecutarActionPerformed

    private void bMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMostrarActionPerformed
        // TODO add your handling code here:
        MostrarTabla mostrarTabla = new MostrarTabla(nombreTabla);
        mostrarTabla.setVisible(true);

        try {
            mostrarTabla.generarTabla(GestorTablas.getInstancia().crearListTabla(nombreTabla));
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bMostrarActionPerformed

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
                if ("GTK+".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Principal(new File("archivos/META_BD.csv")).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bMostrar;
    private javax.swing.JButton btEjecutar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tSalida;
    private javax.swing.JTextField txtEntrada;
    private javax.swing.JTextArea txtHistorial;
    // End of variables declaration//GEN-END:variables
}
