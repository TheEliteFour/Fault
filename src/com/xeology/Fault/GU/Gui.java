/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xeology.Fault.GU;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Xeology
 */
public class Gui extends javax.swing.JFrame {

    /**
     * Creates new form Gui
     */
    public Gui() {
	initComponents();
	this.setIconImage(new ImageIcon(getClass().getResource("Gears.png")).getImage());
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	int w = this.getSize().width;
	int h = this.getSize().height;
	int x = (dim.width - w) / 2;
	int y = (dim.height - h) / 2;

	this.setLocation(x, y);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        Memory = new javax.swing.JLabel();
        CPU = new javax.swing.JLabel();
        Output = new javax.swing.JLabel();
        settings = new javax.swing.JLabel();
        Current = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Screen = new javax.swing.JTextArea();
        border = new javax.swing.JLabel();
        Pause = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fault: Graphic Interface");
        setIconImages(null);
        setResizable(false);

        jLayeredPane1.setFocusable(false);

        Memory.setBackground(new java.awt.Color(0, 0, 0));
        Memory.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Memory.setFocusable(false);
        Memory.setOpaque(true);
        Memory.setBounds(14, 514, 320, 70);
        jLayeredPane1.add(Memory, javax.swing.JLayeredPane.DEFAULT_LAYER);

        CPU.setBackground(new java.awt.Color(0, 0, 0));
        CPU.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        CPU.setFocusable(false);
        CPU.setOpaque(true);
        CPU.setBounds(14, 314, 320, 170);
        jLayeredPane1.add(CPU, javax.swing.JLayeredPane.DEFAULT_LAYER);

        Output.setBackground(new java.awt.Color(0, 0, 0));
        Output.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Output.setFocusable(false);
        Output.setOpaque(true);
        Output.setBounds(14, 104, 320, 180);
        jLayeredPane1.add(Output, javax.swing.JLayeredPane.DEFAULT_LAYER);

        settings.setBackground(new java.awt.Color(0, 0, 0));
        settings.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        settings.setFocusable(false);
        settings.setOpaque(true);
        settings.setBounds(234, 10, 100, 80);
        jLayeredPane1.add(settings, javax.swing.JLayeredPane.DEFAULT_LAYER);

        Current.setBackground(new java.awt.Color(0, 0, 0));
        Current.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Current.setFocusable(false);
        Current.setOpaque(true);
        Current.setBounds(14, 10, 210, 70);
        jLayeredPane1.add(Current, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 0));
        jLabel1.setText("Memory");
        jLabel1.setBounds(20, 490, 60, 20);
        jLayeredPane1.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 255, 0));
        jLabel2.setText("CPU");
        jLabel2.setBounds(20, 290, 60, 20);
        jLayeredPane1.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jScrollPane1.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane1.setAutoscrolls(true);

        Screen.setBackground(new java.awt.Color(0, 0, 0));
        Screen.setColumns(20);
        Screen.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        Screen.setForeground(new java.awt.Color(255, 255, 255));
        Screen.setRows(5);
        Screen.setBorder(null);
        jScrollPane1.setViewportView(Screen);

        jScrollPane1.setBounds(350, 15, 540, 565);
        jLayeredPane1.add(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        border.setText("jLabel3");
        border.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        border.setBounds(348, 14, 544, 568);
        jLayeredPane1.add(border, javax.swing.JLayeredPane.DEFAULT_LAYER);

        Pause.setText("Pause");
        Pause.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                PauseStateChanged(evt);
            }
        });
        Pause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PauseActionPerformed(evt);
            }
        });
        Pause.setBounds(20, 600, 90, 23);
        jLayeredPane1.add(Pause, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/xeology/Fault/GU/background.png"))); // NOI18N
        jLabel3.setText("jLabel3");
        jLabel3.setBounds(0, -7, 910, 650);
        jLayeredPane1.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        Background.setBackground(new java.awt.Color(0, 0, 0));
        Background.setForeground(new java.awt.Color(0, 255, 0));
        Background.setFocusable(false);
        Background.setOpaque(true);
        Background.setBounds(0, 0, 920, 650);
        jLayeredPane1.add(Background, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PauseActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_PauseActionPerformed

    private void PauseStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_PauseStateChanged
	if (GUIHandler.paused) {
	    GUIHandler.paused = false;
	} else {
	    GUIHandler.paused = true;
	}
    }//GEN-LAST:event_PauseStateChanged

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
	    java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>

	/* Create and display the form */
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		new Gui().setVisible(true);
	    }
	});
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    public javax.swing.JLabel CPU;
    public javax.swing.JLabel Current;
    public javax.swing.JLabel Memory;
    public javax.swing.JLabel Output;
    public javax.swing.JToggleButton Pause;
    public javax.swing.JTextArea Screen;
    private javax.swing.JLabel border;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane1;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel settings;
    // End of variables declaration//GEN-END:variables
}
