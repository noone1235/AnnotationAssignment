package com.chary.main.repository;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chary.main.annotaions.ChangeName;
import com.chary.main.model.Student;
//import com.mysql.cj.xdevapi.Result;

public class StudentRepo {

	public Connection establishConnection() throws SQLException {
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/testdb?user=root&&password=2802");

		return conn;
	}

	public List<Integer> sendObjectToDb(List<Student> _students)
			throws IllegalArgumentException, IllegalAccessException, SQLException {
		
		
		Connection conn = establishConnection();

		Class<?> test = _students.get(0).getClass();
		Field[] field = test.getDeclaredFields();
		ArrayList<Integer> studentsId = new ArrayList<>();
		for (Student student : _students) {
			StringBuilder query = new StringBuilder();
			StringBuilder query1 = new StringBuilder();
			query.append("INSERT INTO students3(");
			query1.append("select Id from students3 where ");
			List<String> colNames = new ArrayList<>();
			List<String> colValues = new ArrayList<>();
			List<String> queryStmt=new ArrayList<>();
			for (Field f : field) {
				if (f.isAnnotationPresent(ChangeName.class)) {
					colNames.add(f.getAnnotation(ChangeName.class).value());
				} else {
					colNames.add(f.getName());
				}
			}

			query.append(String.join(",", colNames) + ") VALUES(");
			System.out.println(query);
			for (Field f : field) {
				f.setAccessible(true);

				if (f.getType() == int.class) {
					colValues.add(f.getInt(student) + "");
				} else {
					colValues.add("'" + f.get(student).toString() + "'");
				}

			}
			query.append(String.join(",", colValues));
			query.append(")");
			PreparedStatement stmt = conn.prepareStatement(query.toString());
			stmt.execute();
			
			for (Field f : field) {
				String l="";
				f.setAccessible(true);
				if(f.isAnnotationPresent(ChangeName.class)) {
					l=f.getAnnotation(ChangeName.class).value().toString();
				}
				else {
					l=f.getName();
				}
				if(f.getType()==int.class) {
				queryStmt.add(l+" = "+f.getInt(student));
				}
				else {
				queryStmt.add(l+" = "+"'"+f.get(student)+"'");
				}
			}
			query1.append(String.join(" AND ", queryStmt));
			//query1.setLength(0);
			//query1.append("select Id from students3 where name = 'chary' AND rollNo = 2 AND marks = 'fiftyFive' AND classNo = 10");
			
			PreparedStatement stmt1 = conn.prepareStatement(query1.toString());
			ResultSet resultSet = stmt1.executeQuery();
			
			while (resultSet.next()) {
				int i = resultSet.getInt("Id");
				studentsId.add(i);
			}
			resultSet.close();
			stmt.close();
			query.setLength(0);
		}
		conn.close();
		return studentsId;
	}

	public List<Student> getJsonFromDb() throws SQLException, IllegalArgumentException, IllegalAccessException {
		Connection conn = establishConnection();
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students3");
		ResultSet rs = stmt.executeQuery();
		List<Student> dbStudents = new ArrayList<Student>();
		try {
			while (rs.next()) {
				Student student = new Student();
				Class<?> clapp = student.getClass();
				String k = "";
				for (Field field : clapp.getDeclaredFields()) {
					field.setAccessible(true);
					if (field.isAnnotationPresent(ChangeName.class)) {
						k = field.getAnnotation(ChangeName.class).value();
					} else {
						k = field.getName();
					}

					if (field.getType() == int.class) {
						field.setInt(student, rs.getInt(k));
					} else {
						field.set(student, rs.getString(k));
					}
				}
				dbStudents.add(student);
			}
		} finally {
			conn.close();
			stmt.close();
		}
		return dbStudents;
	}

	public List<Student> getJsonFromDbByName(String name)
			throws SQLException, IllegalArgumentException, IllegalAccessException {
		Connection conn = establishConnection();
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students3 where nickName=?");
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		List<Student> dbStudents = new ArrayList<Student>();
		try {
			while (rs.next()) {
				Student student = new Student();
				Class<?> clapp = student.getClass();
				String k = "";
				for (Field field : clapp.getDeclaredFields()) {
					field.setAccessible(true);
					if (field.isAnnotationPresent(ChangeName.class)) {
						k = field.getAnnotation(ChangeName.class).value();
					} else {
						k = field.getName();
					}

					if (field.getType() == int.class) {
						field.setInt(student, rs.getInt(k));
					} else {
						field.set(student, rs.getString(k));
					}
				}
				dbStudents.add(student);
			}
		} finally {
			conn.close();
			stmt.close();
		}
		return dbStudents;
	}

	
}
