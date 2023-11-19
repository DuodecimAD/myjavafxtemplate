package com.myjavafxtemplate.myapp.java.models;

import java.util.Date;
import java.util.List;

import com.myjavafxtemplate.myapp.java.utility.database.DbRead;

public class Client {

	public int ID_CLIENT;
	public String NOM_CLIENT;
	public String PRENOM_CLIENT;
	public Date DATE_NAIS_CLIENT;
	public String TEL_CLIENT;
	public String EMAIL_CLIENT;
	
	public Client() {
		// TODO Auto-generated constructor stub
	}
	
	public Client(int ID_CLIENT, String NOM_CLIENT, String PRENOM_CLIENT, Date DATE_NAIS_CLIENT, String TEL_CLIENT, String EMAIL_CLIENT) {
		this.ID_CLIENT = ID_CLIENT;
		this.NOM_CLIENT = NOM_CLIENT;
		this.PRENOM_CLIENT = PRENOM_CLIENT;
		this.DATE_NAIS_CLIENT = DATE_NAIS_CLIENT;
		this.TEL_CLIENT = TEL_CLIENT;
		this.EMAIL_CLIENT = EMAIL_CLIENT;
	}
	
	



	public int getID_CLIENT() {
		return ID_CLIENT;
	}

	public void setID_CLIENT(int iD_CLIENT) {
		ID_CLIENT = iD_CLIENT;
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

	public Date getDATE_NAIS_CLIENT() {
		return DATE_NAIS_CLIENT;
	}

	public void setDATE_NAIS_CLIENT(Date dATE_NAIS_CLIENT) {
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
        return DbRead.read("CLIENT", "ID_CLIENT");
    }
}