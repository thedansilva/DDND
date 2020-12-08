Distributed Dungeons and Dragons: Arena - README

This application / server combination runs using Apache NetBeans development environment.
The following is a link to where you can download NetBeans.

http://netbeans.apache.org/download/index.html

After installing NetBeans, download the DDND and MCServer folders from github.
Open both DDND and MCServer as Projects in NetBeans. 
Navigate to the MultiServerThread.java file using the 'Projects' panel.
MCServer -> Source Packages -> com.mycompany.mcserver
Double-Clicking the file in the projects panel will open it in the editor view. 

Navigate to the NewJFrame.java file and open it in the editor view.
DDND -> Source Packages -> com.mycompany.ddnd

Select the MulticastServerThread.java tab in the editor view and press the green 
play/run button to start the server. 

Select the NewJFrame.java tab in the editor view and press the green play/run button
to start a client. Multiple clients can be started this way (max of 5).

Follow the instruction on the clients and enter a name + class into the input box (bottom-right).
Character movement is done by selecting the map display (left) and using the arrows keys. 
Attacks are performed by typing 'attack [direction]' in the command box. 
Spells are performed by typing 'spell [direction]' in the command box.
The user can use a potion to heal 10 hp by typing 'potion' in the command box. 
The user can end their turn by typing 'wait' in the command box. 

The last remaining character alive wins, good luck.