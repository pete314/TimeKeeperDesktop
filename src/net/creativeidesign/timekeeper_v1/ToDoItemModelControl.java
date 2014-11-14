/*
 * This class is the view model for the ToDoItemModel
 */
package net.creativeidesign.timekeeper_v1;

import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author Pi
 */
public class ToDoItemModelControl{
    private ArrayList<ToDoItemModel> lsToDoItems;
    private String strCategoryName;
    private int iCategoryType;

    public ToDoItemModelControl() {
        lsToDoItems = new ArrayList<>();
    }
    
    public ToDoItemModelControl(ArrayList<ToDoItemModel> itemsList) {
        lsToDoItems = itemsList;
    }
    /**
     *
     * @param strCategoryName
     * @param iCategoryType
     */
    public ToDoItemModelControl(String strCategoryName, int iCategoryType) {
        this.lsToDoItems = new ArrayList<>();
        setStrCategoryName(strCategoryName);
        setiCategoryType(iCategoryType);
    }
    /*
    * CRUD for LIST elements
    */

    /**
     *
     * @param iId
     * @param strTitle
     * @param strDescription
     * @param dtDateUntil
     * @param dtCreatedDate
     */
    
    public void createToDoItemModel(int iId, String strTitle, String strDescription, String dtDateUntil, Date dtCreatedDate){
        ToDoItemModel toDoItemModel = new ToDoItemModel(iId, strTitle, strDescription, dtDateUntil, dtCreatedDate, iCategoryType);
        lsToDoItems.add(toDoItemModel);
    }

    /**
     *
     * @param newToDoItemModel
     */
    public void createToDoItemModel(ToDoItemModel newToDoItemModel){
        ToDoItemModel toDoItemModel = newToDoItemModel;
        lsToDoItems.add(toDoItemModel);
    }

    /**
     *
     * @param iSearchId
     * @return
     */
    public ToDoItemModel getToDoItemById(int iSearchId){
        ToDoItemModel foundToDoItem = null;

        for(ToDoItemModel td : lsToDoItems){
            if(td.getiId() == iSearchId)
                foundToDoItem = td;
        }
        if(foundToDoItem != null)
            return foundToDoItem;
        else
            return null;
    }
    
    
    public boolean isInList(ToDoItemModel tmpItem){
        int iEqualsCnt = 0;//if this is 3 title, description, until equals also
        ToDoItemModel itemInList = getToDoItemById(tmpItem.getiId());
        
        if(itemInList == null){
            for(ToDoItemModel item : lsToDoItems){
                if(item.getStrTitle().equalsIgnoreCase(tmpItem.getStrTitle()))
                    ++iEqualsCnt;
                if(item.getDtDateUntil().equalsIgnoreCase(tmpItem.getDtDateUntil()))
                    ++iEqualsCnt;
                if(item.getStrDescription().equalsIgnoreCase(tmpItem.getStrDescription()))
                    ++iEqualsCnt;
            }
        }
        
        if(iEqualsCnt == 3 || itemInList != null)
            return true;
        else
            return false;
    }
    
    /*
    * CRUD for db
    */
    
    
    /*
    * Getters and setters from here
    */

    /**
     *
     * @return
     */
    
    public ArrayList<ToDoItemModel> getLsToDoItems() {
        return lsToDoItems;
    }

    /**
     *
     * @param lsToDoItems
     */
    public void setLsToDoItems(ArrayList<ToDoItemModel> lsToDoItems) {
        this.lsToDoItems = lsToDoItems;
    }

    /**
     *
     * @return
     */
    public String getStrCategoryName() {
        return strCategoryName;
    }
    //NOTE: this is private to avoid category name/type change 
    private void setStrCategoryName(String strCategoryName) {
        this.strCategoryName = strCategoryName;
    }

    /**
     *
     * @return
     */
    public int getiCategoryType() {
        return iCategoryType;
    }
    //NOTE: this is private to avoid category name/type change 
    private void setiCategoryType(int iCategoryType) {
        this.iCategoryType = iCategoryType;
    }
    
    /*
    * toString
    */
    @Override
    public String toString() {
        return "ToDoItemViewModel{" + "strCategoryName=" + strCategoryName + ", iCategoryType=" + iCategoryType + '}';
    }
}
