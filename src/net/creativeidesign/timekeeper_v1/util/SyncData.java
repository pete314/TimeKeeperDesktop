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
    public static boolean Sync(){
        System.out.println("upSync Started");
        MySQLDB mdb = new MySQLDB();
        DerbyDB db = new DerbyDB();
        
        ArrayList<ToDoItemModel> localItems = db.readItemsInDB();
        ArrayList<ToDoItemModel> remoteItems = mdb.readItemsInDB();
        ArrayList<ToDoItemModel> uploadItems = new ArrayList<>();
        ArrayList<ToDoItemModel> downloadItems = new ArrayList<>();
        
        System.out.println(remoteItems.size());
        
        //sync up first
        localItems.stream().forEach((itemLoc) -> {
            boolean notFound = true;
            for(ToDoItemModel itemRem : remoteItems){
                if(itemRem.getiId() == itemLoc.getiId()){
                    notFound = false;
                        System.out.println("Remote item: " + itemRem.getDtUpdated().toString());
                        System.out.println("Local item: " + itemLoc.getDtUpdated().toString());
                    if(itemRem.getDtUpdated().before(itemLoc.getDtUpdated()))
                        mdb.updateItemInDB(itemLoc.getiId(), itemLoc);//do update straight
                }
            }
            if (notFound) {
                uploadItems.add(itemLoc);
            }
        });
        
        
        //sycn Down
        remoteItems.stream().forEach((itemRem) -> {
            boolean notFound = true;
            for(ToDoItemModel itemLoc : localItems){
                if(itemLoc.getiId() == itemRem.getiId()){
                    notFound = false;
                        System.out.println("Remote item: " + itemLoc.getDtUpdated().toString());
                        System.out.println("Local item: " + itemRem.getDtUpdated().toString());
                    if(itemLoc.getDtUpdated().before(itemRem.getDtUpdated()))
                        db.updateItemInDB(itemRem.getiId(), itemRem);//do update straight
                }
            }
            if (notFound) {
                downloadItems.add(itemRem);
            }
        });
        
        db.closeConnection();
        mdb.closeConn();
        
        boolean syncSucess = false;
        syncSucess = (uploadItems.isEmpty() ? true : uploadSyncItems(uploadItems)) 
                && (downloadItems.isEmpty() ? true : downloadSyncItems(downloadItems));//if there is an issue with the remote db connection this will be false
        
        return true;
    }
    /**
    * Sync the items up to remote db
    */
    private static boolean uploadSyncItems(ArrayList<ToDoItemModel> uploadItems){
       MySQLDB mdb = new MySQLDB(); 
       boolean sucess = false;
       for(ToDoItemModel item : uploadItems){
           sucess = mdb.createItemInDB(item);
       }
       mdb.closeConn();
       return !sucess; //the db function gives false if 
    }
    
    /**
    * Sync the items up to remote db
    */
    private static boolean downloadSyncItems(ArrayList<ToDoItemModel> uploadItems){
       DerbyDB db = new DerbyDB();
       boolean sucess = false;
       for(ToDoItemModel item : uploadItems){
           sucess = db.createItemInDB(item);
       }
       db.closeConnection();
       return !sucess; //the db function gives false if 
    }
}
