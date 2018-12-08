package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
	
	public List<String> loadStudents(){ 
		List<String> studentNames= new ArrayList<String>(); 
		Connection myConn= this.Connector(); 
		try { 
			Statement myStmt= (Statement)myConn.createStatement(); 
			String sql = "select name from studenttable"; 
			ResultSet myRs= myStmt.executeQuery(sql); 
			while (myRs.next()) { 
				String name= myRs.getString("name"); 
				studentNames.add(name); 
			} 
			this.close(myConn, myStmt, myRs); 
			return studentNames;
		
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} 
		return null;
	}
	
	public Connection Connector(){ 
		try { 
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/student", "root",""); 
			return connection; 
		}catch (Exception e) { 
			e.printStackTrace(); return null; 
		}
	}
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try{
			if(myStmt!=null) 
				myStmt.close(); 
			if(myRs!=null) 
				myRs.close(); 
			if(myConn!=null) 
				myConn.close();
		} catch(Exception e){ 
			System.out.println(e.getMessage()); 
		}
	}
	
	public Student fetchStudentByName(String name) { 
		Student s = null; 
		Connection myConn= Connector(); 
		try { 
			Statement myStmt= myConn.createStatement(); 
			String sql = "select * from studenttable where name=\""+ name+"\""; 
			ResultSet myRs= myStmt.executeQuery(sql); 
			while (myRs.next()) { 
				int id=myRs.getInt("id"); 
				String gender= myRs.getString("gender"); 
				LocalDate birth=null; 
				if (myRs.getDate("dateofbirth")!=null) { 
					birth = myRs.getDate("dateofbirth").toLocalDate(); 
				} 
				String photo = myRs.getString("photo"); 
				String mark = myRs.getString("mark"); 
				String comments= myRs.getString("comments");
				s = new Student(id,name,gender,birth,photo,mark,comments);
			} 
			this.close(myConn, myStmt, myRs); 
			return s;
		} catch (SQLException e) { 
			e.printStackTrace(); 
			return null; 
		}
	}
	
	public void updateStudent(String name, String new_gender, LocalDate new_birth, String photo, String new_mark, String new_comments) {
		try
	    {
	      String myUrl = "jdbc:mysql://localhost:3307/student";
	      Connection conn = DriverManager.getConnection(myUrl, "root", "");
	    
	      String query = "UPDATE studenttable SET gender = ?, dateofbirth = ?, photo = ?,  mark = ?, comments = ? WHERE name = ?";
	      PreparedStatement preparedStmt = conn.prepareStatement(query);
	      preparedStmt.setString(1, new_gender);
	      Date nb = java.sql.Date.valueOf(new_birth);
	      preparedStmt.setDate(2, nb);
	      preparedStmt.setString(3, photo);
	      preparedStmt.setString(4, new_mark);
	      preparedStmt.setString(5, new_comments);
	      preparedStmt.setString(6, name);
	      
	      preparedStmt.executeUpdate();
	      
	      conn.close();
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! Please fill all arguments!");
	      System.err.println(e.getMessage());
	    }
	}
	
	public void addStudent(String name, String gender, LocalDate birth, String photo, String mark, String comments) {
		try
	    {
	      String myUrl = "jdbc:mysql://localhost:3307/student";
	      Connection conn = DriverManager.getConnection(myUrl, "root", "");
	    
	      String query = "INSERT INTO studenttable (name, gender, dateofbirth, photo, mark, comments) VALUES (?, ?, ?, ?, ?, ?);";
	      PreparedStatement preparedStmt = conn.prepareStatement(query);
	      preparedStmt.setString(1, name);
	      preparedStmt.setString(2, gender);
	      Date nb = java.sql.Date.valueOf(birth);
	      preparedStmt.setDate(3, nb);
	      preparedStmt.setString(4, photo);
	      preparedStmt.setString(5, mark);
	      preparedStmt.setString(6, comments);
	      
	      preparedStmt.executeUpdate();
	      
	      conn.close();
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! Please fill all arguments!");
	      System.err.println(e.getMessage());
	    }
	}
	
	public void deleteStudent(String name) {
		try
	    {
	      String myUrl = "jdbc:mysql://localhost:3307/student";
	      Connection conn = DriverManager.getConnection(myUrl, "root", "");
	    
	      String query = "DELETE FROM studenttable WHERE name = ?;";
	      PreparedStatement preparedStmt = conn.prepareStatement(query);
	      preparedStmt.setString(1, name);

	      preparedStmt.executeUpdate();
	      
	      conn.close();
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
	}




}
