/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.diploma.tool;

import com.mxgraph.model.mxICell;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.view.mxGraph;
import edu.diploma.metamodel.Metamodel;
import edu.diploma.tool.util.UmlClass;
import edu.diploma.tool.visitors.AstDrawVisitor;
import edu.diploma.tool.visitors.CfgDrawVisitor;
import edu.diploma.tool.visitors.ClassDiagramDrawVisitor;
import edu.diploma.tool.visitors.DrawVisitor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author alexander
 */
public class Metalyser extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Creates new form Metalyser
     */
    public Metalyser() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JMenuBar jMenuBar1 = new javax.swing.JMenuBar();
        final javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem openMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem closeMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu viewMenu = new javax.swing.JMenu();
        javax.swing.JMenu DisplayMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Metalyser");

        jScrollPane1.setPreferredSize(new java.awt.Dimension(800, 500));

        drawPanel.setName(""); // NOI18N
        drawPanel.setPreferredSize(new java.awt.Dimension(800, 500));
        drawPanel.setLayout(new java.awt.BorderLayout());
        jScrollPane1.setViewportView(drawPanel);

        fileMenu.setText("File");

        openMenuItem.setText("Open");
        openMenuItem.setToolTipText("");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        closeMenuItem.setText("Close");
        closeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(closeMenuItem);

        jMenuBar1.add(fileMenu);

        viewMenu.setText("View");
        viewMenu.setToolTipText("");

        fitViewMenuItem.setText("Fit view");
        fitViewMenuItem.setEnabled(false);
        fitViewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fitViewMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(fitViewMenuItem);

        foldGraphMenuItem.setText("Fold graph");
        foldGraphMenuItem.setEnabled(false);
        foldGraphMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foldGraphMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(foldGraphMenuItem);

        jMenuBar1.add(viewMenu);

        DisplayMenu.setText("Display");

        cfgMenuItem.setText("Control Flow Graph");
        cfgMenuItem.setToolTipText("");
        cfgMenuItem.setEnabled(false);
        cfgMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cfgMenuItemActionPerformed(evt);
            }
        });
        DisplayMenu.add(cfgMenuItem);

        astViewMenuItem.setText("Abstract Syntax Tree");
        astViewMenuItem.setEnabled(false);
        astViewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                astViewMenuItemActionPerformed(evt);
            }
        });
        DisplayMenu.add(astViewMenuItem);

        classDiagramMenuItem.setText("Class Diagram");
        classDiagramMenuItem.setEnabled(false);
        classDiagramMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classDiagramMenuItemActionPerformed(evt);
            }
        });
        DisplayMenu.add(classDiagramMenuItem);

        jMenuBar1.add(DisplayMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuItemActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_closeMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        final JFileChooser chooser = new JFileChooser();
        int res = chooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            final Serializer serializer = new Persister();
            final File file = chooser.getSelectedFile();
            try {
                metamodel = serializer.read(Metamodel.class, file);
                JOptionPane.showMessageDialog(
                        this,
                        "Metamodel successfully imported",
                        "Success!",
                        JOptionPane.INFORMATION_MESSAGE);
                drawPanel.removeAll();
                drawPanel.revalidate();
                drawPanel.repaint();

                cfgMenuItem.setEnabled(true);
                astViewMenuItem.setEnabled(true);
                classDiagramMenuItem.setEnabled(true);
                foldGraphMenuItem.setEnabled(false);
                fitViewMenuItem.setEnabled(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error while loading the metamodel (see the log for more details)",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(Metalyser.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void cfgMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cfgMenuItemActionPerformed
        runVisitor(new CfgDrawVisitor());
    }//GEN-LAST:event_cfgMenuItemActionPerformed

    private void foldGraphMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foldGraphMenuItemActionPerformed
        final mxGraph graph = graphComponent.getGraph();
        graph.foldCells(true, true, graph.getChildVertices(graph.getDefaultParent()));
    }//GEN-LAST:event_foldGraphMenuItemActionPerformed

    private void fitViewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fitViewMenuItemActionPerformed
        double newScale = 1;

        final Dimension graphSize = graphComponent.getGraphControl().getSize();
        final Dimension viewPortSize = graphComponent.getViewport().getSize();

        int gw = (int) graphSize.getWidth();
        int gh = (int) graphSize.getHeight();

        if (gw > 0 && gh > 0) {
            int w = (int) viewPortSize.getWidth();
            int h = (int) viewPortSize.getHeight();

            newScale = Math.min((double) w / gw, (double) h / gh);
        }

        graphComponent.zoom(newScale);
    }//GEN-LAST:event_fitViewMenuItemActionPerformed

    private void astViewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_astViewMenuItemActionPerformed
        runVisitor(new AstDrawVisitor());
    }//GEN-LAST:event_astViewMenuItemActionPerformed

    private void classDiagramMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classDiagramMenuItemActionPerformed
        runVisitor(new ClassDiagramDrawVisitor());
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseReleased(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    final Point point = e.getPoint();
                    final mxICell cell = (mxICell) graphComponent.getCellAt(point.x, point.y);
                    if (cell != null && cell.isVertex()) {
                        showGraphPopupMenu(e, cell);
                    }
                }
            }

        });
    }//GEN-LAST:event_classDiagramMenuItemActionPerformed

    private void showMetricsMenuItemActionPerformed(final mxICell cell) {
        final UmlClass uml = (UmlClass) cell.getValue();
        final String label = "Showing metrics for class \"" + uml.getName() + "\"";
        final JDialog frame = new MetricsFrame(this, label, uml.getMetrics());
        frame.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Metalyser().setVisible(true);
            }
        });
    }

    private void runVisitor(final DrawVisitor visitor) {
        drawPanel.removeAll();

        visitor.dispatch(metamodel);
        final mxGraph graph = visitor.getGraph();
        graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(false);

        createRubberbandZoom();

        final mxGraphOutline graphOutline = new mxGraphOutline(graphComponent);
        graphOutline.setPreferredSize(new Dimension(100, 100));

        final MouseWheelListener wheelTracker = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getSource() instanceof mxGraphOutline || e.isControlDown()) {
                    if (e.getWheelRotation() < 0) {
                        graphComponent.zoomIn();
                    } else {
                        graphComponent.zoomOut();
                    }
                }
            }

        };
        graphOutline.addMouseWheelListener(wheelTracker);
        graphComponent.addMouseWheelListener(wheelTracker);

        drawPanel.add(graphOutline, BorderLayout.WEST);
        drawPanel.add(graphComponent, BorderLayout.CENTER);
        drawPanel.revalidate();

        foldGraphMenuItem.setEnabled(true);
        fitViewMenuItem.setEnabled(true);
    }

    private void createRubberbandZoom() {
        new mxRubberband(graphComponent) {
            @Override
            public void mouseReleased(MouseEvent e) {
                final Rectangle rect = bounds;
                super.mouseReleased(e);

                if (rect != null) {
                    double newScale = 1;

                    final Dimension graphSize = new Dimension(rect.width, rect.height);
                    final Dimension viewPortSize = graphComponent.getViewport().getSize();

                    int gw = (int) graphSize.getWidth();
                    int gh = (int) graphSize.getHeight();

                    if (gw > 0 && gh > 0) {
                        int w = (int) viewPortSize.getWidth();
                        int h = (int) viewPortSize.getHeight();

                        newScale = Math.min((double) w / gw, (double) h / gh);
                    }

                    graphComponent.zoom(newScale);
                    graphComponent.getGraphControl().scrollRectToVisible(
                            new Rectangle((int) (rect.x * newScale),
                                          (int) (rect.y * newScale),
                                          (int) (rect.width * newScale),
                                          (int) (rect.height * newScale)));
                }

            }
        };
    }

    private void showGraphPopupMenu(final MouseEvent e, final mxICell cell) {
        final Point pt = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), graphComponent);
        final JPopupMenu menu = new JPopupMenu();
        final JMenuItem menuItem = new JMenuItem("View metrics");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                showMetricsMenuItemActionPerformed(cell);
            }
        });
        menu.add(menuItem);
        menu.show(graphComponent, pt.x, pt.y);
        e.consume();
    }

    private Metamodel metamodel;
    private mxGraphComponent graphComponent;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private final javax.swing.JMenuItem astViewMenuItem = new javax.swing.JMenuItem();
    private final javax.swing.JMenuItem cfgMenuItem = new javax.swing.JMenuItem();
    private final javax.swing.JMenuItem classDiagramMenuItem = new javax.swing.JMenuItem();
    private final javax.swing.JPanel drawPanel = new javax.swing.JPanel();
    private final javax.swing.JMenuItem fitViewMenuItem = new javax.swing.JMenuItem();
    private final javax.swing.JMenuItem foldGraphMenuItem = new javax.swing.JMenuItem();
    // End of variables declaration//GEN-END:variables
}
