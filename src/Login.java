import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.ZoneId;

/*
 * SYSC 4810 Assignment 3
 * Emma Maddock, 100996472
 */

public class Login {
	
	File pswd;
	File commonPswds;
	ArrayList<String[]> permissions;
	String[] roles;
	String[] objects;
	
	public Login() {
		//creates the password file and populates it with the test cases
		pswd = new File("pswd.txt");
		commonPswds = new File("commonPswds.txt");
		
		if (!pswd.exists()) {
			try {
				pswd.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//populating an array of role titles according to ID for use by the system
		roles = new String[]{"Client","Premium Client","Teller","Tech Support","Financial Advisor","Financial Planner","Investment Analyst","Compliance Officer"};
		
		//populating an array of object titles according to ID
		objects = new String[]{"Client Information","Client Account","Client Account Balance","Client Investment Portfolio","Financial Advisor Details","Financial Planner Details","Investment Analyst Details","Private Consumer Instruments","Money Market Instruments","Derivatives Trading","Interest Instruments"};

		
		//creating the role-object matrix as depicted in the report
		permissions = new ArrayList<String[]>();
		String[] clientPermissions;
		String[] premiumClientPermissions;
		String[] tellerPermissions;
		String[] techSupportPermissions;
		String[] fAdvisorPermissions;
		String[] fPlannerPermissions;
		String[] iAnalystPermissions;
		String[] cOfficerPermissions;
		
		clientPermissions = new String[]{"","","VIEW","VIEW","VIEW","","","","","",""};
		premiumClientPermissions = new String[]{"","","VIEW,MODIFY","VIEW","VIEW","VIEW","VIEW","","","",""};
		tellerPermissions = new String[]{"","","VIEW","VIEW","","","","","","",""};
		techSupportPermissions = new String[]{"VIEW","REQUEST ACCESS","","","","","","","","",""};
		fAdvisorPermissions = new String[]{"","","VIEW","VIEW,MODIFY","","","","VIEW","","",""};
		fPlannerPermissions = new String[]{"","","VIEW","VIEW,MODIFY","","","","VIEW","VIEW","",""};
		iAnalystPermissions = new String[]{"","","VIEW","VIEW,MODIFY","","","","VIEW","VIEW","VIEW","VIEW"};
		cOfficerPermissions = new String[]{"","","VIEW","VIEW,VALIDATE CHANGES","","","","","","",""};

		permissions.add(clientPermissions);
		permissions.add(premiumClientPermissions);
		permissions.add(tellerPermissions);
		permissions.add(techSupportPermissions);
		permissions.add(fAdvisorPermissions);
		permissions.add(fPlannerPermissions);
		permissions.add(iAnalystPermissions);
		permissions.add(cOfficerPermissions);



		//TODO: populate with test data
				
		
	}

	public static void main(String[] args) {
		
		Login login = new Login();
		Scanner sc = new Scanner(System.in);
		Boolean newUserPrompt = true;
		
		System.out.println("SecVault Investments, Inc.\n");
		
		while (newUserPrompt) {
			//determining whether to bring the user to the "Create User" screen
			System.out.println("Are you a new user? (Y/N): ");
			String newUser = sc.next();
		
		
			//if new user, bring to new user screen
			if ((newUser.length() == 1) && ((newUser.charAt(0) == 'Y') || (newUser.charAt(0) == 'y'))) {
				newUserPrompt = false;
				
				login.newUserScreen();
				sc.close();
			
			} 
			//if not new user, prompt to login
			else if ((newUser.length() == 1) && ((newUser.charAt(0) == 'N') || (newUser.charAt(0) == 'n'))) {
				newUserPrompt = false;
				
				login.loginScreen();
				sc.close();
			
			}
			//else, invalid input and continue to prompt user until they enter a correct input
			else {
				newUserPrompt = true;
			}
		}

	}
	
	public void newUserScreen() {
		String firstName = "";
		String lastName = "";
		String userID = "";
		String password = "";
		String passwordTwo = "";
		int roleID = 0;
		
		Boolean passwordSelection = true;
		Boolean passwordMatch = false;
		Boolean roleSelection = true;
		
		//if the user tries to match the password 3 times and fails, gets to choose a new password
		int passwordMatchTryCounter = 0;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n\nSecVault Investments, Inc.");
		System.out.println("NEW USER ENROLLMENT\n");
		
		System.out.println("What is your first name? ");
		firstName = sc.next();
		
		System.out.println("What is your last name? ");
		lastName = sc.next();
		
		System.out.println("What username would you like to use? ");
		userID = sc.next();
		
		while (passwordSelection) {
			
			System.out.println("Select a password that meets the following criteria:");
			System.out.println("\t-between 8-12 characters in length");
			System.out.println("\t-include one upper-case character");
			System.out.println("\t-include one lower-case character");
			System.out.println("\t-include one digit");
			System.out.println("\t-include one of the following symbols: !, @, #, $, %, ?, *");
			password = sc.next();
		
			//verify password meets requirements
			Boolean passwordSecure = checkPasswordCompliance(password);
			
			if (passwordSecure) {
				while (!passwordMatch) {
					System.out.println("Password meets criteria. Please re-enter password: ");
					passwordTwo = sc.next();
					
					if (password.equals(passwordTwo)) {
						passwordMatch = true;
						passwordSelection = false;
						
					} else {
						passwordMatch = false;
						++passwordMatchTryCounter;
						if (passwordMatchTryCounter == 3) {
							System.out.println("You have tried 3 times to match passwords. Please select a new password.");
							passwordSelection = true;
							break;
						}
					}
				}
			}
			else {
				System.out.println("Password does not match criteria. Please try again.");
				passwordSelection = true;
			}
			
		}
		
		while (roleSelection) {
			
			System.out.println("Select your role with respect to SecVault Investments, Inc. by entering the appropriate number:");
			System.out.println("\t1 - client");
			System.out.println("\t2 - premium client");
			System.out.println("\t3 - teller");
			System.out.println("\t4 - tech support");
			System.out.println("\t5 - financial advisor");
			System.out.println("\t6 - financial planner");
			System.out.println("\t7 - investment analyst");
			System.out.println("\t8 - compliance officer");
			roleID = sc.nextInt();
			
			if ((roleID >= 1) && (roleID <= 8)) {
				roleSelection = false;
				System.out.println("Processing...");
			} else {
				roleSelection = true;
				System.out.println("Please enter one of the existing role codes.");
			}
		}
		
		//salt and hash password
		byte[] salt = generateSalt();
		String salted = salt + password;
		
		String hashed = hashPassword(salted);
		
		//concatenate into single record
		String record = userID + ":" + salt + ":" + hashed + ":" + firstName + " " + lastName + ":" + roleID + "\n";
		
		//store into password file
		try {
			FileWriter writer = new FileWriter("pswd.txt",true);
			BufferedWriter bWriter = new BufferedWriter(writer);
			bWriter.write(record);
			bWriter.close();
		} catch (IOException e) {
			System.out.println("There was an error writing to the password file.");
		}
		
		System.out.println("User record successfully created.");
		
		loginScreen();
	}
	
	public void loginScreen() {
		Scanner scan = new Scanner(System.in);
		BufferedReader reader;
		
		int attempts = 0;
		
		String usernameInput;
		String passwordInput;
		
		String record = null;
		
		Boolean loginMenu = true;
		Boolean username = true;
		Boolean searchingUsername = true;
		Boolean password = true;

		while (loginMenu) {
			System.out.println("\n\nSecVault Investments, Inc.");
			System.out.println("USER LOGIN\n");
			
			while(username) {
				System.out.println("Username: ");
				usernameInput = scan.next();
				
				//reading the password file and retrieving the salt and the hash based off the username
				try {
					reader = new BufferedReader(new FileReader("pswd.txt"));
					record = reader.readLine();
					
					while ((searchingUsername) && (record != null)) {
						//dissect first part of record to see if user ID is a match
						//copy into a string until first colon reached
						String userID = "";
						for (int i=0;i<record.length();i++) {
							Character curr = record.charAt(i);
							userID += curr;
							if (curr == ':') {
								break;
							}
						}
						userID = userID.substring(0,  userID.length() - 1);
						
						if(userID.equals(usernameInput)) {
							searchingUsername = false;
						} else {
							searchingUsername = true;
							record = reader.readLine();
						}
					
					}
					
					reader.close();
					
				} catch (IOException e) {
					System.out.println("There was an error reading the password file.");
				}
				
				//if the loop terminated without a hit
				if (searchingUsername) {
					username = true;
					System.out.println("That username doesn't exist in the database. Try entering a different one.\n");
				}
				else {
					username = false;
				}
				
			}
			
			while (password) {
				System.out.println("Password: ");
				passwordInput = scan.next();
				
				//get salt and hash from record
				//RETRIEVE SALT
				int colonCount = 0;
				String salt = "";
				for (int i=0;i<record.length();i++) {
					Character curr = record.charAt(i);
					if (colonCount == 1) {
						salt += curr;
					}
					if (curr == ':') {
						++colonCount;
					}
				}
				salt = salt.substring(0,  salt.length() - 1);
				
				//RETRIEVE HASH
				int colonCountTwo = 0;
				String recordHash = "";
				for (int i=0;i<record.length();i++) {
					Character curr = record.charAt(i);
					if (colonCountTwo == 2) {
						recordHash += curr;
					}
					if (curr == ':') {
						++colonCountTwo;
					}
				}
				recordHash = recordHash.substring(0,  recordHash.length() - 1);
				
				//RETRIEVE ROLEID
				char roleIDchar = record.charAt(record.length() - 1);
				int roleID = Character.getNumericValue(roleIDchar);
				
				//RETRIEVE NAME
				int colonCountThree = 0;
				String name = "";
				for (int i=0;i<record.length();i++) {
					Character curr = record.charAt(i);
					if (colonCountThree == 3) {
						name += curr;
					}
					if (curr == ':') {
						++colonCountThree;
					}
				}
				name = name.substring(0,  name.length() - 1);
				
				//generate timestamp
				Date dt = new Date();
				int hour = dt.toInstant().atZone(ZoneId.systemDefault()).getHour();
				
				//salting the user's password input
				String inputSalted = salt + passwordInput;
				String inputHashed = hashPassword(inputSalted);
				
				if (inputHashed.equals(recordHash)) {
					if ((roleID != '3') || ((roleID == '3') && ((hour >= 9) && (hour <= 17)))) {
						loginMenu = false;
						username = false;
						password = false;
						System.out.println("Login success. Welcome!");
						
						populateHomeScreen(roleID,name);
					}
					else {
						System.out.println("You only have access to the system 9am-5pm.");
					}
				} else {
					++attempts;
					if (attempts == 3) {
						System.out.println("Too many attempts. You have been booted from the system.");
						System.exit(0);
					}
					loginMenu = true;
					username = true;
					password = true;
					System.out.println("Wrong password. Try again.");
				}
			}
		}
		
	}
	
	public void populateHomeScreen(int roleID, String name) {
		
		int roleIndex = roleID - 1;
		
		System.out.println("\n\nSecVault Investments, Inc.");
		System.out.println("Hello " + name + ", " + roles[roleIndex] + ".");
		System.out.println("WHAT WOULD YOU LIKE TO DO?\n");
		
		String[] rolePermissions = permissions.get(roleIndex);
		
		int menuIndex = 1;
		for (int i=0;i<objects.length;i++) {
			if (rolePermissions[i] != "") {
				System.out.println(menuIndex + ": " + rolePermissions[i] + " " + objects[i]);
				++menuIndex;
			}
		}
		
	}
	
	public Boolean checkPasswordCompliance(String password) {
		Boolean passwordSecure = false;
		Boolean checkOne = false; //checking proper length
		Boolean checkTwo = false; //checking for an upper-case
		Boolean checkThree = false; //checking for a lower-case
		Boolean checkFour = false; //checking for a digit
		Boolean checkFive = false; //checking for a symbol from the set
		Boolean checkSix = true; //checking against a file of common passwords
		
		//array of the special characters to choose from to check against
		char[] specialCharacters = {'!','@','#','$','%','?','*'};
		
		BufferedReader reader;
		
		//CHECK ONE
		if ((password.length() >= 8) && (password.length() <= 12)) {
			checkOne = true;
		} else {
			checkOne =false;
		}
		
		//CHECK TWO
		for (int i=0;i<password.length();i++) {
			Character curr = (Character) password.charAt(i);
			if (Character.isUpperCase(curr)) {
				checkTwo = true;
				break;
			}
		}
		
		//CHECK THREE
		for (int i=0;i<password.length();i++) {
			Character curr = (Character) password.charAt(i);
			if (Character.isLowerCase(curr)) {
				checkThree = true;
				break;
			}
		}
		
		//CHECK FOUR
		for (int i=0;i<password.length();i++) {
			Character curr = (Character) password.charAt(i);
			if (Character.isDigit(curr)) {
				checkFour = true;
				break;
			}
		}
		
		//CHECK FIVE
		for (int i=0;i<password.length();i++) {
			Character curr = (Character) password.charAt(i);
			
			for (int j=0;j<specialCharacters.length;j++) {
				if (curr == specialCharacters[j]) {
					checkFive = true;
					break;
				}
			}
		}
		
		//CHECK SIX
		try {
			reader = new BufferedReader(new FileReader("commonPswds.txt"));
			String commonPassword = reader.readLine();
			while (commonPassword != null) {
				if (password == commonPassword) {
					checkSix = false;
					break;
				}
				commonPassword = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("There was an error reading the common passwords file.");
		}
		
		
		if (checkOne && checkTwo && checkThree && checkFour && checkFive && checkSix) {
			passwordSecure = true;
		} else {
			passwordSecure = false;
		}
		
		return passwordSecure;
	}
	
	//generates the 32-byte salt for the password
	public byte[] generateSalt() {
		byte salt[];
		SecureRandom random = new SecureRandom();
		
		salt = new byte[32];
		random.nextBytes(salt);
		
		return salt;
	}
	
	public String hashPassword(String password) {
		String hashed;
		
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA3-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
		
		hashed = bytesToHex(encodedHash);
		
		return hashed;
	}

	//helper code from open source baeldung.com
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}

}


