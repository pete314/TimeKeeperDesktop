/*
 * This class ment to sync the local and the remote db.
 * Will be called from the mainDesktopPane every 5 minutes.
 * Also possible to force the sync from main manu
 */
package net.creativeidesign.timekeeper_v1.util;

import java.util.ArrayList;
import net.creativeidesign.timekeeper_v1.ToDoItemModel;
import net.creativeidesign.timekeeper_v1.db.DerbyDB;
import net.creativeidesign.timekeeper_v1.db.MySQLDB;

/**
 *
 * @author Pi
 */
public class SyncData {
    private boolean syncSuccess;

    public SyncData() {
        
    }
    /**
    * Check is there anything to sync to remote db and sync
     * @return true if 
    */
    public static boolean upSync(){
        MySQLDB mdb = new MySQLDB();
        DerbyDB db = new DerbyDB();
        
        ArrayList<ToDoItemModel> localItems = db.readItemsInDB();
        ArrayList<ToDoItemModel> remoteItems = mdb.readItemsInDB();
        ArrayList<ToDoItemModel> uploadItems = new ArrayList<>();
        
        db.closeConnection();
        mdb.closeConn();
        
        System.out.println(remoteItems.size());
        //sync up first
        localItems.stream().forEach((itemLoc) -> {
            boolean notFound = true;
            for(ToDoItemModel itemRem : remoteItems){
                if(itemRem.getiId() == itemLoc.getiId()){
                    notFound = false;
                    System.out.println(itemRem.getDtUpdated().toString());
                    System.out.println(itemLoc.getDtUpdated().toString());
                    if(itemRem.getDtUpdated().before(itemLoc.getDtUpdated()))
                        mdb.updateItemInDB(itemLoc.getiId(), itemLoc);//do update straight
                }
            }
            if (notFound) {
                uploadItems.add(itemLoc);
            }
        });
        
        return (uploadItems.isEmpty() ? true : uploadItems(uploadItems));
    }
    /**
    * Sync the items up to remote db
    */
    private static boolean uploadItems(ArrayList<ToDoItemModel> uploadItems){
       MySQLDB mdb = new MySQLDB(); 
       boolean sucess = false;
       for(ToDoItemModel item : uploadItems){
           sucess = mdb.createItemInDB(item);
       }
       return !sucess; //the db function gives false if 
    }
}
