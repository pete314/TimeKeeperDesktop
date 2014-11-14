/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.creativeidesign.timekeeper_v1.GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import net.creativeidesign.timekeeper_v1.UserModel;
import net.creativeidesign.timekeeper_v1.db.DerbyDB;
import net.creativeidesign.timekeeper_v1.db.MySQLDB;
import net.creativeidesign.timekeeper_v1.util.PasswordHash;
import net.creativeidesign.timekeeper_v1.util.SendEmail;

/**
 *
 * @author Pi
 */
public class SplashScreen extends javax.swing.JFrame {
    private UserModel userModel;
    /**
     * Creates new form SplashScreen
     */
    public SplashScreen() {
        initComponents();
        initMyComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        passwordTextField = new javax.swing.JTextField();
        loginBtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        resetBtn = new javax.swing.JButton();
        registerBtn = new javax.swing.JButton();
        resetPane = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        reminderEmailTextField = new javax.swing.JTextField();
        sendBtn = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Time Keeper - [Desktop v0.2]");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("Password");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Username");

        usernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextFieldActionPerformed(evt);
            }
        });

        loginBtn.setText("Login");
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        resetBtn.setText("Reset Password");
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        registerBtn.setText("Register");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        jLabel3.setText("Email:");

        sendBtn.setText("Send");
        sendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout resetPaneLayout = new javax.swing.GroupLayout(resetPane);
        resetPane.setLayout(resetPaneLayout);
        resetPaneLayout.setHorizontalGroup(
            resetPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resetPaneLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reminderEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        resetPaneLayout.setVerticalGroup(
            resetPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resetPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resetPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(reminderEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        errorLabel.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        errorLabel.setText("Error");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loginBtn)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordTextField)))
                        .addGap(11, 11, 11))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(resetPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(resetBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(registerBtn)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginBtn)
                    .addComponent(errorLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetBtn)
                    .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTextFieldActionPerformed

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        if(validateLoginValues()){
            if(tryLogin())
                createMainFrame();
        }else{
            errorLabel.setText("Please enter Username/Password");
        }
            
    }//GEN-LAST:event_loginBtnActionPerformed

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        resetPane.setVisible(true);
    }//GEN-LAST:event_resetBtnActionPerformed

    private void sendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendBtnActionPerformed
        DerbyDB db = new DerbyDB();
        MySQLDB mdb = new MySQLDB();
        String query[] = {"select id, username, password, email, full_name from users where email like '" + reminderEmailTextField.getText().trim() + "' limit 1", 
                            "select id, username, password, email, full_name from app.users where email like '" + reminderEmailTextField.getText().trim() + "'" };
        if(!validateResetValues())
            errorLabel.setText("Please enter valid email address!");
        else{
            ResultSet rs = mdb.runQuery(query[0]);
            if(rs==null)
                rs = db.executeQuery(query[1], 1);
            
            if(rs == null)
                errorLabel.setText("Email address not found!");
            else{
                try {
                    rs.next();
                    changePassword(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(5));
                } catch (SQLException ex) {
                    Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_sendBtnActionPerformed
    
    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegistrationForm dialog = new RegistrationForm(new javax.swing.JFrame(), true);
                
                dialog.setVisible(true);
            }
        });
    }//GEN-LAST:event_registerBtnActionPerformed
    
    private void createMainFrame(){
        System.out.println(userModel.getUserId());
        createCurrentUserIdFile(userModel.getUserId());
        System.out.println("MainFrame starts");

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainDesktopPane mainDesktop = new MainDesktopPane(userModel);
                mainDesktop.setVisible(true);
                dispose();
            }
        });
    }
    
    private boolean tryLogin(){
        try {
            DerbyDB db = new DerbyDB();
            MySQLDB mdb = new MySQLDB();
            
            String userInputPass = passwordTextField.getText().trim();
            String username = usernameTextField.getText().trim();
            
            userModel = db.findUser(username);
            db.closeConnection();

            //if not in local db
            if(userModel == null){
                userModel = mdb.findUser(username);
                mdb.closeConn();
                if(userModel == null){
                    errorLabel.setText("Username not found");
                    return false;
                }
            }
            
            //username exists, lets check the password
            String dbPassHash = userModel.getPassword();
            if(PasswordHash.validatePassword(userInputPass, userModel.getPassword())){
                System.out.println("All metch");
                errorLabel.setText("All Correct!");
                return true;
            }else{
                System.out.println("The password does not match");
                errorLabel.setText("Password/Username incorect!");
                return false;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //The place of no return, at this stage the only option is that there was an error
        return false;
    }
    
    
    private void changePassword(int id, String username, String email, String fullName){
        MySQLDB mdb = new MySQLDB();
        
        String newPass = null;
        try {
            newPass = PasswordHash.createHash(username);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "update users set password = '" + newPass + "' where id = " + id;
        if(!mdb.executeStatement(query)){
            System.out.println("Reset Success!");
            sendMail(email, fullName, username);
            JOptionPane.showMessageDialog (null, "Password reset, please check your email!", "Password reset success", JOptionPane.INFORMATION_MESSAGE);
        }else
            JOptionPane.showMessageDialog (null, "Wohoo, there was an error, please try again later!", "Password reset failed", JOptionPane.WARNING_MESSAGE);
        
    }
    
    private void sendMail(String email, String fullName, String username){
        String msg = "Hello "+fullName+"!\n\n" +
                    "Please keep this email, your login credentials are:\n\n" + 
                    "Username: " + username+ "\n" +
                    "Password: " + username + "\n" +
                    "\n\n*****************\nThis email was generated automatically please do not reply!\n";
        
        try {
            SendEmail.Send("mszaki.mailer", "(r6id^A~193[T4B", email, "webpmaster@gmail.com", "Password reset", msg);
        } catch (MessagingException ex) {
            Logger.getLogger(RegistrationForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initMyComponents(){
        //refactor {security flaw, if user does change the id at runtime other data is loaded
        createCurrentUserIdFile(0);//DONT REMOVE {reuired to db class to work
        
        //center jFrame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        errorLabel.setText(null);
        resetPane.setVisible(false);
    }
    
    private boolean validateLoginValues(){
        return !(usernameTextField.getText().equals("") || passwordTextField.getText().equals(""));
            
    }
    
    private boolean validateResetValues(){
        return !(reminderEmailTextField.getText().equals("") || !reminderEmailTextField.getText().contains("@"));
    }
   
    private void createCurrentUserIdFile(int id){
        try {
            File file = new File("currentUser.dat");
            // creates the file
            file.createNewFile();
            // Writes the content to the file
            try ( // creates a FileWriter Object
                FileWriter writer = new FileWriter(file)) {
                // Writes the content to the file
                writer.write(Integer.toString(id));
                writer.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SplashScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton loginBtn;
    private javax.swing.JTextField passwordTextField;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField reminderEmailTextField;
    private javax.swing.JButton resetBtn;
    private javax.swing.JPanel resetPane;
    private javax.swing.JButton sendBtn;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
