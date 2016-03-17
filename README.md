#Java desktop time management app 
###Project General Description 
The project is a simple time management application with desktop and compact view. Can be used to create quick notes and organize them based on importance and date. The credit for the categorization idea goes to Randy Pausch [Randy Pausch Lecture: Time Management](https://youtu.be/oTugjssqOT0?t=1255)

###Features
 - Multi user can login/work on same installation
 - Register/login/password reset
 - Import & export from and to excel
 - Sync to remote database
 - Backup
 - Simultaneous work multiple open tasks
 - Task grid view
 - Compact & desktop and fullscreen view

###Screen shoots
![Registration view](https://raw.githubusercontent.com/pete314/TimeKeeperDesktop/master/screens/register.png)
*** Data is only sent to databases what are connected up***

![Desktop view](https://raw.githubusercontent.com/pete314/TimeKeeperDesktop/master/screens/dektop_view_3.png)

![Compact view](https://raw.githubusercontent.com/pete314/TimeKeeperDesktop/master/screens/compact_view_1.png)
###Repository description
The application requires at least a Derby database for running, for what the schema is located in DerbyDb folder. The application meant to synchronize the local database with a remote one. Table extraction can be found in MySql folder to regenerate a sync database.
###Execution instruction 
Starting at least the Derby database and then the application can be started with:
```
java -jar /path/to/repo/dist/TimeKeeper_v1.jar
```

###Notes 
The email notification feature is onyl working if you give a working Gmail address and password. The code to modify can be found at **RegistrationForm.java - line 340**
```
try {
            SendEmail.Send("Timekeeper", "password", emailTextField.getText(), "someusername@gmail.com", "New Registration to timekeeper", msg);
 } catch (MessagingException ex) {
         ...
 }
```
This project can be a good starting point for junior developers or newcomers to java to see basic GUI application source.

###Disclaimer
THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 
