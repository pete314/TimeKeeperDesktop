/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.creativeidesign.timekeeper_v1.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import net.creativeidesign.timekeeper_v1.ToDoItemModel;
import net.creativeidesign.timekeeper_v1.ToDoItemModelControl;
import net.creativeidesign.timekeeper_v1.db.DerbyDB;
import net.creativeidesign.timekeeper_v1.util.FrameLister;

/**
 *
 * @author Pi
 */
public class CompactView extends javax.swing.JFrame implements TaskOptions_interface {
    private ToDoItemModelControl itemsListControl;
    private JTable titleTable;
    private JPopupMenu tablePopupMenu;
    private int selectedID;
    
    private final MainDesktopPane mainFrame;
    /**
     * Creates new form CompactView
     */
    public CompactView(MainDesktopPane mainFrame) {
        initComponents();
        this.mainFrame = mainFrame;
        initMyComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        normalViewBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemCategorySelector = new javax.swing.JComboBox();
        addTaskBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Time Keeper [desktop - v0.2]");
        setBackground(new java.awt.Color(0, 204, 204));

        normalViewBtn.setText("Normal View");
        normalViewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalViewBtnActionPerformed(evt);
            }
        });

        itemCategorySelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Importan [SOON]", "Importan [NOT SOON]", "Not Importan [SOON]", "Not Importan [NOT SOON]" }));
        itemCategorySelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCategorySelectorActionPerformed(evt);
            }
        });

        addTaskBtn.setText("Add Task");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(itemCategorySelector, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(normalViewBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addTaskBtn))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(itemCategorySelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(normalViewBtn)
                    .addComponent(addTaskBtn)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void normalViewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalViewBtnActionPerformed
        mainFrame.setVisible(true);
        setVisible(false);
        dispose();
    }//GEN-LAST:event_normalViewBtnActionPerformed

    private void itemCategorySelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCategorySelectorActionPerformed
        int categoryID = itemCategorySelector.getSelectedIndex() + 1;
        //System.out.println("Selected category " + categoryID);
        
        DefaultTableModel tableModel = (DefaultTableModel) titleTable.getModel();
        tableModel.setRowCount(0);

        //Find the items in category & add to repaint
        ArrayList<ToDoItemModel> tempItemList = itemsListControl.getLsToDoItems();
        for(ToDoItemModel item : tempItemList){
            switch(categoryID){
                case 1:
                    if(!compareDateScheduleCategory(item.getDtDateUntil()) && item.getiCategory() == 1){
                          Object[] rowData = new Object[2];
                          rowData[0] = item.getiId();
                          rowData[1] = item.getStrTitle();
                          tableModel.addRow(rowData);
                    }    
                    break;
                case 2:
                    if(compareDateScheduleCategory(item.getDtDateUntil()) && item.getiCategory() == 1){
                          Object[] rowData = new Object[2];
                          rowData[0] = item.getiId();
                          rowData[1] = item.getStrTitle();
                          tableModel.addRow(rowData);
                    }    
                    break;
                case 3:
                    if(!compareDateScheduleCategory(item.getDtDateUntil()) && item.getiCategory() == 2){
                          Object[] rowData = new Object[2];
                          rowData[0] = item.getiId();
                          rowData[1] = item.getStrTitle();
                          tableModel.addRow(rowData);
                    }    
                    break;
                case 4:
                    if(compareDateScheduleCategory(item.getDtDateUntil()) && item.getiCategory() == 2){
                          Object[] rowData = new Object[2];
                          rowData[0] = item.getiId();
                          rowData[1] = item.getStrTitle();
                          tableModel.addRow(rowData);
                    }    
                    break;
            }
        }
        titleTable.setModel(tableModel);
        titleTable.repaint();
        tableModel.fireTableDataChanged();
    }//GEN-LAST:event_itemCategorySelectorActionPerformed
    
    /**
    * This function check if a date is out of range now()+8
    *
    */
    private boolean compareDateScheduleCategory(String dateUntil){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 8);  // this should come from the settings page
        
        String datePlus8Days = sdf.format(c.getTime());
        Date compareDate = null;
        Date dtUntilDate = null;
        try {
            compareDate = new SimpleDateFormat("yyyy-MM-dd").parse(datePlus8Days);  
            dtUntilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateUntil);
        } catch (ParseException ex) {
            Logger.getLogger(CompactView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //if out of now +settings.getTimerIntervall
        return compareDate.before(dtUntilDate);
    }
    
    private void initMyComponents(){
        addWindowListener(new FrameLister());
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CompactView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompactView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompactView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompactView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        tablePopupMenu = new JPopupMenu();
        DerbyDB db = new DerbyDB();
        itemsListControl = new ToDoItemModelControl(db.readItemsInDB());
        
        //create table
        String[] colHeaders = {"id", "Task Title"};
        titleTable = new JTable();
        
        DefaultTableModel titleTableModel = (DefaultTableModel) titleTable.getModel();
        titleTableModel.setColumnIdentifiers(colHeaders);
        
        ListSelectionModel cellSelectionModel = titleTable.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedData = 0;

                int[] selectedRow = titleTable.getSelectedRows();
                int[] selectedColumns = titleTable.getSelectedColumns();

                for (int i = 0; i < selectedRow.length; i++) {
                  for (int j = 0; j < selectedColumns.length; j++) {
                    selectedData = (Integer) titleTable.getValueAt(selectedRow[i], 0);
                  }
                }

                selectedID = selectedData;
            }
        });
        //hide the id column
        titleTable.getColumnModel().getColumn(0).setMinWidth(0);
        titleTable.getColumnModel().getColumn(0).setMaxWidth(0);

        jScrollPane1.setViewportView(titleTable);
        
        /**
         * Right Click menu option
         */
        
        JMenuItem finishMenuItem = new JMenuItem("Finish Task");
        finishMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markAsFinished();
            }   
        });
        tablePopupMenu.add(finishMenuItem);
        
        
        JMenuItem editMenuItem = new JMenuItem("Edit");
        editMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openInternalFrame();
            }   
        });
        tablePopupMenu.add(editMenuItem);
        
        
        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }   
        });
        tablePopupMenu.add(deleteMenuItem);
        //set mouse listener
        titleTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
              if (evt.isPopupTrigger()) {
                tablePopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
              }
            }

            public void mouseReleased(MouseEvent evt) {
              if (evt.isPopupTrigger()) {
                tablePopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
              }
            }
          });
        
    }
    
    @Override
    public void markAsFinished(){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure to mark as done?", "Warning", dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            ToDoItemModel currentItem;
            DerbyDB db = new DerbyDB();
            currentItem = db.findItemInDB(selectedID);
            currentItem.setiCategory(99);

            if(db.updateItemInDB(selectedID, currentItem)){
                JOptionPane.showMessageDialog (null, "Whoops something went wrong, please try again!", "Result message", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog (null, "Succesfully deleted!", "Result message", JOptionPane.INFORMATION_MESSAGE);
                initMyComponents();
            }
            db.closeConnection();
        }
    }  
     
    @Override
    public void openInternalFrame(){
         mainFrame.setVisible(true);
        // JInternalFrame internalFrame = new JInternalFrame();
         //internalFrame.setContentPane(mainFrame.desktopPane);
         AddEditItem addItem = new AddEditItem(selectedID);
         mainFrame.desktopPane.add(addItem);
         addItem.setVisible(true);

         this.dispose();
    }

    @Override
    public void deleteItem(){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure to delete?", "Warning", dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            DerbyDB db = new DerbyDB();
            if(db.deleteItemInDB(selectedID, "items")){
                JOptionPane.showMessageDialog (null, "Whoops something went wrong, please try again!", "Result message", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog (null, "Succesfully deleted!", "Result message", JOptionPane.INFORMATION_MESSAGE);
                initMyComponents();
            }
            db.closeConnection();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTaskBtn;
    private javax.swing.JComboBox itemCategorySelector;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton normalViewBtn;
    // End of variables declaration//GEN-END:variables
}
