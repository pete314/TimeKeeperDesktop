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
public class MySQLDB implements DbCrud{
    private int currentUserId;
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String CONN_STRING = 
                    "jdbc:mysql://localhost/tk_remote";
    // Connection objects
    Connection conn = null;
    Statement stmt = null;
    private ResultSet rs = null;

    //Data lists/vars
    private ArrayList<ArrayList<String>> queryData = new ArrayList<ArrayList<String>>();

    public MySQLDB(){
        setConnection();
        setCurrentUserId();
    }

    private void setCurrentUserId(){
        try (Scanner s = new Scanner(new File("currentUser.dat"))) {
            currentUserId = s.nextInt();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setConnection(){
        try {
                conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
                System.err.println(e);
        }
    }

    public ResultSet runQuery(String query){
        try{
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println(e);
        }

        if(rs != null)
            return rs;
        else
            return null;
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

    public int resultNumOfRows(){
        int iRowCnt = 0;
        try {
            while(this.rs.next())
                ++iRowCnt;
            rs.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return iRowCnt;
    }

    /*
    * It's helper functions from here
    */

    /**
    *
    * @param username  Search by this in db  
    * @return UserModel If record exist in db
    */
    public UserModel findUser(String username){
        String strQuery = "select id, username, password, email, full_name from users where username like '" + username +"'";
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
            stmt.execute("select count(*) from " + tableName + " where user_id = " + currentUserId);
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
                    ToDoItemModel resultModel = new ToDoItemModel(rs.getInt(2), rs.getString(4), rs.getString(5), rs.getString(6), rs.getTimestamp(7), rs.getInt(3), rs.getTimestamp(8));
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
     * @param newItem
     * @return
     */

    @Override
    public boolean createItemInDB(ToDoItemModel newItem) {
        String query = "INSERT INTO ITEMS (LOCAL_ID, CATEGORY, TITLE, DESCRIPTION, FINISH_DATE, CREATED, UPDATED, USER_ID) " +
                       "VALUES ("+newItem.getiId()+","+newItem.getiCategory()+", '"+newItem.getStrTitle()+"', '"+newItem.getStrDescription()+"', '"+newItem.getDtDateUntil()+"', '"+newItem.getDtCreatedDate()+"', '"+newItem.getDtUpdated()+"', "+currentUserId+")";
        return executeStatement(query);

    }

    /**
     * @param id
     * @param updatedItem
     * @return
     */
    @Override
    public boolean updateItemInDB(int id, ToDoItemModel updatedItem) {
        String query = "UPDATE ITEMS SET "
                        + "CATEGORY = "+updatedItem.getiCategory()+", "
                        + "TITLE = '"+updatedItem.getStrTitle()+"', "
                        + "DESCRIPTION = '"+updatedItem.getStrDescription()+"', "
                        + "FINISH_DATE = '"+updatedItem.getDtDateUntil()+"', "
                        + "UPDATED = '" + updatedItem.getDtUpdated() + "'"
                        + " WHERE local_id = " + id
                        + " and user_id = " + currentUserId;
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
        String query = "SELECT * FROM ITEMS "
                        + "WHERE local_id = "+id
                        + " AND user_id = " + currentUserId
                        + " LIMIT 1";
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
        String query = "SELECT * FROM ITEMS where user_id = " + currentUserId;
        return executeQueryReturnList(query);
    }

    /**
     * 
     * @return Runs the executeQueryReturnResultSet(query) and if success returns ArrayList
     */
    @Override
    public ArrayList<ToDoItemModel> readItemsInDB(int categoryId) {
        String query = "SELECT * FROM ITEMS WHERE category in (" + categoryId + ") AND user_id = " + currentUserId;
        return executeQueryReturnList(query);
    }

    /**
     * 
     * @return Runs the executeQueryReturnResultSet(query) and if success returns ArrayList
     */
    @Override
    public ArrayList<ToDoItemModel> readItemsInDB(int categoryId , String queryWhere) {
        String query = "SELECT * FROM ITEMS WHERE category in (" + categoryId + ")AND user_id = "+currentUserId+" AND " + queryWhere;
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
        String query = "DELETE FROM " + tableName
                            +" WHERE local_id IN (" + id + ") "
                            +"AND user_id = " + currentUserId;
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
    
    
    public boolean deleteAllItems(){
        return !executeStatement("delete from items where user_id = " + currentUserId);
    }

    /*
    /*
     * Set queryData with strings
     * **********REFACTOR**********
     * This method should set be set to actual table data
     * Maybe do this when this.extend to table class??? 
     *
    private void setQueryData(){
            try {
                    this.queryData.clear();//just flush before 
                    ArrayList<String> rowData = new ArrayList<String>();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    this.rs.first();
                    while(this.rs.next()){
                            for(int i = 1; i < rsmd.getColumnCount(); i++)
                                    rowData.add(rs.getString(i));
                            this.queryData.add(rowData);
                            rowData.clear();
                    }
            } catch (SQLException e) {
                    System.err.println(e);
            }
    }

    public List resultSetToArrayList() throws SQLException{
      ResultSetMetaData md = this.rs.getMetaData();
      int columns = md.getColumnCount();
      ArrayList list = new ArrayList(50);
      this.rs.first();
      while (this.rs.next()){
         HashMap row = new HashMap(columns);
         for(int i=1; i<=columns; ++i){           
          row.put(md.getColumnName(i),rs.getObject(i));
         }
          list.add(row);
      }

     return list;
    }

    public ArrayList<ArrayList<String>> getQueryData() {
            return queryData;
    }

    public void displayResult(){
            try {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    printHeaderColumnNames(rsmd);
                    this.rs.first();
                    while(this.rs.next()){
                            StringBuffer b1 = new StringBuffer();
                            for(int i = 1; i < rsmd.getColumnCount(); i++)
                                    b1.append(this.rs.getString(i) + "\t");
                            System.out.println(b1.toString());
                    }
            } catch (SQLException e) {
                    System.err.println(e);
            }
    }

    private void printHeaderColumnNames(ResultSetMetaData rsmd){
            try{
                    StringBuffer buffer = new StringBuffer();
                    int iCnt = 0;
                    do{
                            buffer.append(rsmd.getColumnName(iCnt+1) + "\t");
                            iCnt++;
                    }while(iCnt < rsmd.getColumnCount());
                    System.out.println(buffer);

            } catch (SQLException e) {
                    System.err.println(e);
            }
    }
    */
    /*
     * The method is closing the connection
     */
    public void closeConn(){
            try {
                    if(this.conn != null)
                            conn.close();
                    if(this.stmt != null)
                            stmt.close();
                    if(this.rs != null)
                            rs.close();
            } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
    }//end closeConn	
}//end MySQLDB