/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.creativeidesign.timekeeper_v1;

import java.util.ArrayList;

/**
 *
 * @author Pi
 */
public interface DbCrud {
    //create item
    boolean createItemInDB(ToDoItemModel newItem);
    //update item
    boolean updateItemInDB(int id, ToDoItemModel updatedItem);
    //read a item
    ToDoItemModel findItemInDB(int id);
    //read a item
    ArrayList<ToDoItemModel> readItemsInDB();
    //read a item by category
    ArrayList<ToDoItemModel> readItemsInDB(int catgeroryId);
    //read items from category with special query
    public ArrayList<ToDoItemModel> readItemsInDB(int categoryId , String queryWhere);
    //delete item by id
    boolean deleteItemInDB(int id, String tableName);
    //delete item by object
    boolean deleteItemInDB(ToDoItemModel deleteItem, String tableName);
}