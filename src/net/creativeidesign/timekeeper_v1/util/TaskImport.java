/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.creativeidesign.timekeeper_v1.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.creativeidesign.timekeeper_v1.GUI.CategoryTableView;
import net.creativeidesign.timekeeper_v1.ToDoItemModel;
import net.creativeidesign.timekeeper_v1.ToDoItemModelControl;
import net.creativeidesign.timekeeper_v1.db.DerbyDB;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Pi
 */
public class TaskImport extends JInternalFrame {
    private List<ToDoItemModel> importedItems;
    
    public TaskImport(){
        
    }

    public boolean doImport() {
        try
        {
            JFrame parentFrame = new JFrame();
            
            FileFilter filter = new FileNameExtensionFilter("MS Excel .xlsx", "xlsx");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Please select an excel file to import from");   
            fileChooser.setFileFilter(filter);
            
            int userSelection = fileChooser.showSaveDialog(parentFrame);
            File fileToSave = fileChooser.getSelectedFile();
            if(fileToSave == null)
                return false;
            String filePathName = fileToSave.getAbsolutePath();
            
            FileInputStream file = new FileInputStream(new File(filePathName));
            
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            importedItems = new ArrayList<>();
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();//skip first row [it should be the header]
            while (rowIterator.hasNext()) 
            {
                ToDoItemModel tmpItem = new ToDoItemModel();
                int iCellCnt = 0;
                
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                
                while (cellIterator.hasNext()) 
                {
                    String cellStr = null;
                    double cellNum = 0;
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) 
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            cellNum = cell.getNumericCellValue();
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            cellStr = cell.getStringCellValue();
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                    }//end switch (cell.getCellType())
                    
                    switch(iCellCnt){
                        case 0:
                            tmpItem.setiId((int)cellNum);
                            break;
                        case 1:
                            tmpItem.setStrTitle(cellStr);
                            break;
                        case 2:
                            tmpItem.setStrDescription(cellStr);
                            break;
                        case 3:
                            tmpItem.setDtDateUntil(cellStr);
                            break;
                        case 4:
                            tmpItem.setiCategory((int)cellNum);
                            break;
                    }//end switch(iCellCnt)
                    
                    ++iCellCnt;
                }
                System.out.println("");
                importedItems.add(tmpItem);
            }
            file.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog (null, "Wohoo, something went wrong, please try again later", "File Saved", JOptionPane.INFORMATION_MESSAGE);
        }
        
        return compareItemsInDB();
    }//end private void doImport() 
    
    private boolean compareItemsInDB(){
        int iNewTaskCnt = 0, iFoundTaskCnt = 0;
        ToDoItemModelControl dbItems = new ToDoItemModelControl();
        
        DerbyDB db = new DerbyDB();
        dbItems.setLsToDoItems(db.readItemsInDB());
        
        //build a loop around this
        for(ToDoItemModel importedItem : importedItems){
            if(!dbItems.isInList(importedItem)){
               db.createItemInDB(importedItem);
               ++iNewTaskCnt;
               //System.out.println(dbItems.isInList(importedItem));
            }else
                ++iFoundTaskCnt;
        }
        
        //show import nums and ask user to open category view
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null,
                                "Imported successfuly " + iNewTaskCnt + " tasks! \n Found " + iFoundTaskCnt + " identical! \n Would you like to see the new list?", 
                                                            "Warning", dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            return true;
        }else{
            return false;
        }
        
    }//end private void compareItemsInDB()
}
