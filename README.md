# Encrypted Login System
*By Emma Maddock, Carleton University, 2020*

## Description
A Java-based login system for a fictional organization called SecVault, Inc. that utilizes Role-Based Access Control and SHA3-256 encryption to enforce security.


## How to Launch in Eclipse (v4.17 or earlier)

1. Download the Eclipse IDE if you don't already have it
2. Download the source files including the .txt files and unzip
3. Open Eclipse and go to New > Java Project
4. Name the Java Project (doesn’t matter), click Finish
5. If not already opened, open the Package Explorer under Window > Show View > Package Explorer
6. Click on the project name in the Package Explorer
7. Go to Import > expand General > Projects from Folder or Archive > Next
8. Click the Directory button and choose the path where the you unzipped the source files and select OK
9. Ensure that the folder is checked in the box and select Finish
10. Expand the imported folder in the Package Explorer on the left, then src, then the login package
11. Double click on Login.java
12. If not already opened, open the Console under Window > Show View > Console
13. Click on the Run button (green circle with the white play button) at the top in the bar of icons to start the program (or alternatively navigate to Run > Run in the command bar at the top)
14. If a ClassNotFoundException arises, move to the **Troubleshooting section below**, and then continue these steps
15. If you've successfully launched the program, you will see a prompt in the console asking if you are a new user

## Troubleshooting
If you hit a ClassNotFoundException, then do the following:
1. Select the project in the Package Explorer and go to File > Properties
2. Select Run/Debug Settings on the left
3. Click on "Digest" in the sub-window on the right
4. Click the "Edit" button
5. Under "Project:" click the Browse button
6. Select the name of the project you created and click OK
7. Click OK again
8. Click Apply and Close
9. Voilà, move back to step 12 in How to Launch

## System Description

The system is a Role-Based Access Controlled login system that uses SHA3-256 encryption for passwords.
It uses the file *pswd.txt* to store user data and the file *commonPswds.txt* to check against common and insecure passwords.

Each user has a username and password, and is identified by a Role ID within the fictional organization SecVault, Inc.. Each Role ID grants the user a specific set of privileges/actions appropriate to said role. This results in a different menu once each user logs in based on what they're allowed to do within the system.

Role IDs are:
* 01 – client
* 02 – premium client
* 03 – teller
* 04 – tech support
* 05 – financial advisor
* 06 – financial planner
* 07 – investment analyst
* 08 – compliance officer


## Interacting with the System

1. The user is asked if they are a new user. If yes, continue to step 2, if no, continue to step 4.
2. The user is in the enrollment phase. They enter all of the information required from the system. The
system ensures all inputs are valid and that security policies are followed.
3. The system salts and hashes the user’s password using the SHA3-256 algorithm, and places a record
with the hash and other information into a password file named pswd.txt
4. The user is in the login phase. They are asked to first enter a username. The system checks to see if the username exists in the password file pswd.txt. Once a match is found, the user is then asked to enter the password associated with that username. The user’s input password, along with their respective password file record’s salt, is hashed again with the SHA3-256 algorithm. The user’s hashed input and the record’s hash value are then compared for a match. If there is a match, the user is then granted access to their action menu.
5. The user can see their action menu. Their action menu contains the permissions they have within the system based off their roleID from their record in the password file.

*To read the complete details on the system, its design, and how it works, you can read the "Report.pdf" file located in the repo.*
