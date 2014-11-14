/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.creativeidesign.timekeeper_v1.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.creativeidesign.timekeeper_v1.DbCrud;
import net.creativeidesign.timekeeper_v1.ToDoItemModel;
import net.creativeidesign.timekeeper_v1.UserModel;

/**
 *
 * @author Pi
 */


public class DerbyDB implements DbCrud{
    private int currentUserId;
    private final String DB_URL = "jdbc:derby://localhost:1527/timeKeeper_derby_v1;create=false;user=root;pass=";
    private Connection conn = null;
    
    /**
     *
     */
    public DerbyDB(){
        createConnection();
        setCurrentUserId();
    }
    
    private void setCurrentUserId(){
        try (Scanner s = new Scanner(new File("currentUser.dat"))) {
            currentUserId = s.nextInt();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    * From here it's general db functions
    */
    private void createConnection()
    {
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL); 
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     *
     * @param strStatement
     * @return Execute a statement in derby. Takes a string returns true if statement success
     */
    public boolean executeStatement(String strStatement){
        boolean success = false;
        try(
            Statement stmt = conn.createStatement();
            )
        {
            success = stmt.execute(strStatement);
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
        return success;
    }
    
    /**
     * Run a query, return a result set
     * @param strQuery
     */
    public void executeQuery(String strQuery){
        try(Statement stmt = conn.createStatement();){
            stmt.execute(strQuery);
            try(ResultSet rs = stmt.getResultSet()){
                ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next()) {
                    StringBuilder buffer = new StringBuilder();
                    for(int i = 1; i <= rsmd.getColumnCount(); i++)
                        buffer.append(rs.getString(i) + "\t");
                    
                    System.out.println(buffer.toString());
                }
            }
        }catch (SQLException sqlExcept){
            sqlExcept.printStackTrace();
        }
    }
    
    /**
     *
     * @param strQuery
     */
    public ResultSet executeQuery(String strQuery, int i){
        try(Statement stmt = conn.createStatement();){
            stmt.execute(strQuery);
            try(ResultSet rs = stmt.getResultSet()){
                return rs;
            }
        }catch (SQLException sqlExcept){
            sqlExcept.printStackTrace();
        }
        return null;
    }
    
    public int getRowCount(String tableName){
        int result = 0;
        try(Statement stmt = conn.createStatement();){
            stmt.execute("select count(*) from app." + tableName + " where user_id in (" + currentUserId + ")");
            try(ResultSet rs = stmt.getResultSet()){
                //ResultSetMetaData rsmd = rs.getMetaData();
                //result = rs.getInt("rowCount");
                while (rs.next()){
                    result = rs.getInt(1);
                }
            }
        }catch (SQLException sqlExcept){
            sqlExcept.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * @param username  Search by this in db  
     * @return UserModel If record exist in db
     */
    public UserModel findUser(String username){
        String strQuery = "select id, username, password, email, full_name from APP.USERS where username like '" + username+"'";
        UserModel um = null;
        try(Statement stmt = conn.createStatement();){
            stmt.execute(strQuery);
            try(ResultSet rs = stmt.getResultSet()){
                if(rs.next()){
                    um = new UserModel();
                    um.setUserId(rs.getInt(1));
                    um.setUsername(rs.getString(2));
                    um.setPassword(rs.getString(3));
                    um.setEmail(rs.getString(4));
                    um.setFull_name(rs.getString(5));
                    return um;
                }
            }
        }catch (SQLException sqlExcept){
            sqlExcept.printStackTrace();
        }
        return um;
    }
    
    /**
     *
     * @param strStatement
     * @return The method takes a query, if success than returns a ToDoItemModel object with 
     * declared with the resultSetData.
     */
    public ToDoItemModel executeQueryReturnModel(String strStatement){
        boolean success = false;
        ToDoItemModel resultModel = null;
        try(Statement stmt = conn.createStatement();)
        {
            success = stmt.execute(strStatement);
            try(ResultSet rs = stmt.getResultSet()){
                rs.next();//just move the cursor to the first row(the query is limit 1)
                resultModel = new ToDoItemModel(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getTimestamp(6), rs.getInt(2));
            }
        }catch (SQLException sqlExcept){
            sqlExcept.printStackTrace();
        }
        if(success)
            return resultModel;
        else
            return null;
    }
    /**
     *
     * @param strQuery
     * @return The method returns a resultSet for the query if success
     */
    public ArrayList<ToDoItemModel> executeQueryReturnList(String strQuery){
        boolean success = false;
        ArrayList<ToDoItemModel> resultList = new ArrayList<>();
        try(Statement stmt = conn.createStatement();){
            success = stmt.execute(strQuery);
            try(ResultSet rs = stmt.getResultSet()){
                while(rs.next()){
                    ToDoItemModel resultModel = new ToDoItemModel(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getTimestamp(6), rs.getInt(2), rs.getTimestamp(7));
                    resultList.add(resultModel);
                }
            }
        }catch (SQLException sqlExcept){
            sqlExcept.printStackTrace();
        }
        if(success)
            return resultList;
        else
            return null;
    }
    
    /**
     *
     */
    public void closeConnection()
    {
        try{
            if (conn != null){
                //DriverManager.getConnection(DB_URL + ";shutdown=true");
                conn.close();
            }           
        }catch (SQLException e)
        {
            System.err.println(e);
        }
    }
    
    /*
    * From here it's project related functions
    */

    /**
     *
     * @param newItem
     * @return
     */
    
    @Override
    public boolean createItemInDB(ToDoItemModel newItem) {
        String query = "INSERT INTO APP.ITEMS (CATEGORY, TITLE, DESCRIPTION, FINISH_DATE, CREATED, UPDATED, USER_ID) " +
                       "VALUES ("+newItem.getiCategory()+", '"+newItem.getStrTitle()+"', '"+newItem.getStrDescription()+"', '"+newItem.getDtDateUntil()+"', CURRENT_TIMESTAMP, DEFAULT, "+currentUserId+")";
        return executeStatement(query);
       
    }

    /**
     * @param id
     * @param updatedItem
     * @return
     */
    @Override
    public boolean updateItemInDB(int id, ToDoItemModel updatedItem) {
        String query = "UPDATE APP.ITEMS SET "
                        + "CATEGORY = "+updatedItem.getiCategory()+", "
                        + "TITLE = '"+updatedItem.getStrTitle()+"', "
                        + "DESCRIPTION = '"+updatedItem.getStrDescription()+"', "
                        + "FINISH_DATE = '"+updatedItem.getDtDateUntil()+"', "
                        + "UPDATED = DEFAULT "
                        + "WHERE id = " + id
                        + " AND user_id = " + currentUserId;
        System.out.println(updatedItem);
        return executeStatement(query);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ToDoItemModel findItemInDB(int id) {
        String query = "SELECT * FROM APP.ITEMS "
                        + "WHERE id = "+id
                        + " FETCH FIRST 1 ROWS ONLY";
        if(executeQueryReturnModel(query) != null)
            return executeQueryReturnModel(query);
        else
            return null;
    }

    /**
     * 
     * @return Runs the executeQueryReturnResultSet(query) and if success returns ArrayList
     */
    @Override
    public ArrayList<ToDoItemModel> readItemsInDB() {
        String query = "SELECT * FROM APP.ITEMS where user_id = " + currentUserId;
        return executeQueryReturnList(query);
    }

    /**
     * 
     * @return Runs the executeQueryReturnResultSet(query) and if success returns ArrayList
     */
    @Override
    public ArrayList<ToDoItemModel> readItemsInDB(int categoryId) {
        String query = "SELECT * FROM APP.ITEMS WHERE category in (" + categoryId + ") and user_id = " + currentUserId;
        return executeQueryReturnList(query);
    }
    
    /**
     * 
     * @return Runs the executeQueryReturnResultSet(query) and if success returns ArrayList
     */
    @Override
    public ArrayList<ToDoItemModel> readItemsInDB(int categoryId , String queryWhere) {
        String query = "SELECT * FROM APP.ITEMS WHERE category in (" + categoryId + ") AND user_id = "+currentUserId+" AND " + queryWhere;
        return executeQueryReturnList(query);
    }
    /**
     *
     * @param id
     * @param tableName
     * @return Deletes an item in table and returns true if success.
     */
    @Override
    public boolean deleteItemInDB(int id, String tableName) {
        String query = "DELETE FROM APP." + tableName
                            +" WHERE id IN (" + id + ")";
        if(executeStatement(query))
            System.out.println("All looks ok");
        
        return executeStatement(query);
    }
    
    /**
     *
     * @param deleteItem
     * @param tableName
     * @return Deletes an item in table and returns true if success.
     */
    @Override
    public boolean deleteItemInDB(ToDoItemModel deleteItem, String tableName) {
        return deleteItemInDB(deleteItem.getiId(), tableName);
    }
    
}
