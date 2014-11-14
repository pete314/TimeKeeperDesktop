/*
 * This is the Excel export class
 * Exporting all content from the database separated into sheets
 */
package net.creativeidesign.timekeeper_v1.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.creativeidesign.timekeeper_v1.ToDoItemModel;
import net.creativeidesign.timekeeper_v1.db.DerbyDB;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Pi
 */
public class BigDump {
        
    public BigDump(){
        
    }
    public void export(){
        DerbyDB db = new DerbyDB();
        Integer iCnt;
        final String[] strarr5Titles = {"ID", "Title", "Description", "Finish Until", "Category"};
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); 
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("TimeKeeper All Tasks Export");
        
                
        //This data needs to be written (Object[])
        ArrayList<ToDoItemModel> items = db.readItemsInDB();
        db.closeConnection();
        TreeMap<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
        iCnt = 0;
        for(ToDoItemModel item : items)
            data.put(iCnt++, new Object[] {item.getiId(), item.getStrTitle(), item.getStrDescription(), item.getDtDateUntil(), item.getiCategory(), item.getDtUpdated(), item.getDtCreatedDate()});
        
        
        //Iterate over data and write to sheet
        Set<Integer> keyset = data.keySet();
        int rownum = 0;
        //Create first row manually and lock it
        Row headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(12.75f);
        for (int i = 0; i < strarr5Titles.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(strarr5Titles[i]);
        }
        //freeze the first row
        sheet.createFreezePane(0, 1);

        for (int key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            //Write the workbook in file system
            JFrame parentFrame = new JFrame();
            
            FileFilter filter = new FileNameExtensionFilter("MS Excel .xlsx", "xlsx");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save (.xlsx) or give a new name");
            fileChooser.setFileFilter(filter);

            int userSelection = fileChooser.showSaveDialog(parentFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePathName = fileToSave.getAbsolutePath();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                
                if(filePathName.substring(filePathName.length() - 5).equalsIgnoreCase(".xlsx"))
                    ;
                else{
                    filePathName += ".xlsx";
                }
                //filePathName.substring(filePathName.length() - 5).equalsIgnoreCase(".xlsx") ? filePathName : (filePathName.concat(".xlsx"));
                
                FileOutputStream out = new FileOutputStream(new File(filePathName));//fileToSave.getAbsolutePath()+ ".xlsx"));
                workbook.write(out);
                out.close();
                System.out.println(filePathName+" is written to the disk.");
                JOptionPane.showMessageDialog (null, "File Saved: " + filePathName, "File Saved", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
