/*
 * THIS CLASS NEEDS A SERIOUS RE-JIG
 * The upsync has two options if run as startup it wipes the table(!!!RE-JIG!!!)
 * and creates new items based what is in the local db. If not startup it compares what is new.
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
    public static boolean upSync(boolean startUpSync){
        System.out.println("upSync Started");
        MySQLDB mdb = new MySQLDB();
        DerbyDB db = new DerbyDB();
        int uploadCnt, sucessCnt;
        boolean sucess = false;
        
        
        ArrayList<ToDoItemModel> localItems = db.readItemsInDB();
        ArrayList<ToDoItemModel> remoteItems = mdb.readItemsInDB();
        ArrayList<ToDoItemModel> uploadItems = new ArrayList<>();
        ArrayList<ToDoItemModel> downloadItems = new ArrayList<>();
        
        System.out.println(remoteItems.size());
        
        mdb.executeStatement("BEGIN");
        
        uploadCnt = sucessCnt = 0;
        
        //
        if(!startUpSync){
            //sync up first
            for(ToDoItemModel itemLoc : localItems){
                boolean notFound = true;
                for(ToDoItemModel itemRem : remoteItems){
                    if(itemRem.getiId() == itemLoc.getiId() || itemRem.getDtCreatedDate().equals(itemLoc.getDtCreatedDate())){
                        notFound = false;
                        if(itemRem.getDtUpdated().before(itemLoc.getDtUpdated()) || itemRem.getiId() != itemLoc.getiId() )
                            mdb.updateItemInDB(itemLoc.getiId(), itemLoc);//do update straight
                    }
                }
                if (notFound) {
                    uploadItems.add(itemLoc);
                    ++uploadCnt;
                }
            }
            
            for(ToDoItemModel item : uploadItems){
               if(!mdb.createItemInDB(item))
                   ++sucessCnt;
            }
            if(uploadCnt == sucessCnt){
                mdb.executeStatement("COMMIT");
                sucess = true;
            }
            else
                mdb.executeStatement("ROLL BACK");
        }else{
            mdb.deleteAllItems();
            System.out.println("Table wiped!");
            //At this stage the remote db is empty so simply populate with local data
            for(ToDoItemModel itemLoc : localItems){
                if(!mdb.createItemInDB(itemLoc))
                   ++sucessCnt;
            }
            
            if(sucessCnt == localItems.size()){
                mdb.executeStatement("COMMIT");
                sucess = true;
            }
            else
                mdb.executeStatement("ROLL BACK");
                
        }
        
        
        db.closeConnection();
        mdb.closeConn();
        
        return sucess;
    }
    
    /**
    *downSync from here
    */
    public static boolean downSync(){
        System.out.println("upSync Started");
        MySQLDB mdb = new MySQLDB();
        DerbyDB db = new DerbyDB();
        
        ArrayList<ToDoItemModel> localItems = db.readItemsInDB();
        ArrayList<ToDoItemModel> remoteItems = mdb.readItemsInDB();
        ArrayList<ToDoItemModel> uploadItems = new ArrayList<>();
        ArrayList<ToDoItemModel> downloadItems = new ArrayList<>();
        
        remoteItems.stream().forEach((itemRem) -> {
                boolean notFound = true;
                for(ToDoItemModel itemLoc : localItems){
                    if(itemLoc.getiId() == itemRem.getiId()){
                        notFound = false;
                            System.out.println("Remote item: " + itemLoc.getiId());
                            System.out.println("Local item: " + itemRem.getiId());
                        if(itemLoc.getDtUpdated().before(itemRem.getDtUpdated()))
                            db.updateItemInDB(itemRem.getiId(), itemRem, true);//do with the overloaded sync update method
                    }
                }
                if (notFound) {
                    downloadItems.add(itemRem);
                }
            });
        
        db.closeConnection();
        mdb.closeConn();
        
        //at this stage the remote db is uodated with local 
        return downloadItems.isEmpty() ? true : downloadSyncItems(downloadItems);//if there is an issue with the remote db connection this will be false
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
