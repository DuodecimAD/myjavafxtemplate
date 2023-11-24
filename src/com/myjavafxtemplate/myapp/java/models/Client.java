package com.myjavafxtemplate.myapp.java.models;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.myjavafxtemplate.myapp.java.utility.database.DbCreate;
import com.myjavafxtemplate.myapp.java.utility.database.DbRead;

public class Client {

	public String NOM_CLIENT;
	public String PRENOM_CLIENT;
	public LocalDate DATE_NAIS_CLIENT;
	public String TEL_CLIENT;
	public String EMAIL_CLIENT;
	
	public Client() {
		// TODO Auto-generated constructor stub
	}
	
	public Client(String NOM_CLIENT, String PRENOM_CLIENT, LocalDate DATE_NAIS_CLIENT, String TEL_CLIENT, String EMAIL_CLIENT) {

		this.NOM_CLIENT = NOM_CLIENT;
		this.PRENOM_CLIENT = PRENOM_CLIENT;
		this.DATE_NAIS_CLIENT = DATE_NAIS_CLIENT;
		this.TEL_CLIENT = TEL_CLIENT;
		this.EMAIL_CLIENT = EMAIL_CLIENT;
	}
	
	

	public String getNOM_CLIENT() {
		return NOM_CLIENT;
	}

	public void setNOM_CLIENT(String nOM_CLIENT) {
		NOM_CLIENT = nOM_CLIENT;
	}

	public String getPRENOM_CLIENT() {
		return PRENOM_CLIENT;
	}

	public void setPRENOM_CLIENT(String pRENOM_CLIENT) {
		PRENOM_CLIENT = pRENOM_CLIENT;
	}

	public LocalDate getDATE_NAIS_CLIENT() {
		return DATE_NAIS_CLIENT;
	}

	public void setDATE_NAIS_CLIENT(LocalDate dATE_NAIS_CLIENT) {
		DATE_NAIS_CLIENT = dATE_NAIS_CLIENT;
	}

	public String getTEL_CLIENT() {
		return TEL_CLIENT;
	}

	public void setTEL_CLIENT(String tEL_CLIENT) {
		TEL_CLIENT = tEL_CLIENT;
	}

	public String getEMAIL_CLIENT() {
		return EMAIL_CLIENT;
	}

	public void setEMAIL_CLIENT(String eMAIL_CLIENT) {
		EMAIL_CLIENT = eMAIL_CLIENT;
	}

	public static List<List<Object>> getAllClientsData() {
        // Fetch data from the database (using DbRead or any other method)
        // Return raw data as a List<List<?>>
        return DbRead.read("CLIENT", "NOM_CLIENT");
    }
	
	public static void insertIntoDatabase(Client client) throws SQLException {

		List<String> columnsList = new ArrayList<>(List.of("NOM_CLIENT", "PRENOM_CLIENT", "DATE_NAIS_CLIENT", "TEL_CLIENT", "EMAIL_CLIENT"));
		List<Object> valuesList =  new ArrayList<>(List.of(client.getNOM_CLIENT(), client.getPRENOM_CLIENT(), client.getDATE_NAIS_CLIENT(), client.getTEL_CLIENT(), client.getEMAIL_CLIENT()));
		List<Integer> check = List.of(3,4);

        try {
			DbCreate.insert("CLIENT", columnsList, valuesList, check);
		} catch (SQLException e) {
			throw e;
		} 
    }
	
}
