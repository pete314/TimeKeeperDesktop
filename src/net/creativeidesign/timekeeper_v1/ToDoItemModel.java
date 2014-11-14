/*
 * This class is the is the item View Model
 */
package net.creativeidesign.timekeeper_v1;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author Pi
 */
public class ToDoItemModel {
    private int iId;
    private String strTitle;
    private String strDescription;
    private String strDateUntil;
    private Date dtCreatedDate;
    private Date dtUpdated;
    private int iCategory;

    //Constructors
    //Default Constructor
    public ToDoItemModel() {
        this.iId = 0;
        this.strTitle = null;
        this.strDescription = null;
        this.strDateUntil = null;
        this.dtCreatedDate = null;
        this.dtUpdated = null;
        this.iCategory = 0;
    }
    
    //Constructor for all values, without updated
    public ToDoItemModel(int iId, String strTitle, String strDescription, String dtDateUntil, Date dtCreatedDate, int iCategory) {
        this.iId = iId;
        this.strTitle = strTitle;
        this.strDescription = strDescription;
        this.strDateUntil = dtDateUntil;
        this.dtCreatedDate = dtCreatedDate;
        this.dtUpdated = null;
        this.iCategory = iCategory;
    }
    
    //Constructor for all values
    public ToDoItemModel(int iId, String strTitle, String strDescription, String dtDateUntil, Date dtCreatedDate, int iCategory, Date dtUpdated) {
        this.iId = iId;
        this.strTitle = strTitle;
        this.strDescription = strDescription;
        this.strDateUntil = dtDateUntil;
        this.dtCreatedDate = dtCreatedDate;
        this.dtUpdated = dtUpdated;
        this.iCategory = iCategory;
    }
    /*
    * Getters and setters from here
    */
    public int getiId() {
        return iId;
    }

    public ToDoItemModel setiId(int iId) {
        this.iId = iId;
        return this;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public ToDoItemModel setStrTitle(String strTitle) {
        this.strTitle = strTitle;
        return this;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public ToDoItemModel setStrDescription(String strDescription) {
        this.strDescription = strDescription;
        return this;
    }

    public String getDtDateUntil() {
        return strDateUntil;
    }

    public ToDoItemModel setDtDateUntil(String strDateUntil) {
        this.strDateUntil = strDateUntil;
        return this;
    }

    public Date getDtCreatedDate() {
        return dtCreatedDate;
    }

    public ToDoItemModel setDtCreatedDate(Date dtCreatedDate) {
        this.dtCreatedDate = dtCreatedDate;
        return this;
    }
    //Overload for creating Current_Timestamp
    public ToDoItemModel setDtCreatedDate() {
        this.dtCreatedDate = new Timestamp(Calendar.getInstance().getTime().getTime());
        return this;
    }
    
    public int getiCategory() {
        return iCategory;
    }

    public ToDoItemModel setiCategory(int iCategory) {
        this.iCategory = iCategory;
        return this;
    }

    public Date getDtUpdated() {
        return dtUpdated;
    }

    public void setDtUpdated(Date dtUpdated) {
        this.dtUpdated = dtUpdated;
    }
    /*
    * The place of no return :D toString
    */

    @Override
    public String toString() {
        return "ToDoItemModel{" + "iId=" + iId + ", strTitle=" + strTitle + ", strDescription=" + strDescription + ", dtDateUntil=" + strDateUntil + ", dtCreatedDate=" + dtCreatedDate + ", iCategory=" + iCategory + '}';
    }
    
    
}//end public class ToDoItemModel 
