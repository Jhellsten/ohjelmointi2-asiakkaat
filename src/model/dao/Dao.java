package model.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Henkilo;

public class Dao {
	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	private String db ="Myynti.sqlite";
	
	private Connection yhdista(){
    	Connection con = null;    	
    	String path = System.getProperty("user.dir");  
    	path += "\\";
    	String url = "jdbc:sqlite:"+path+db;  
    	System.out.println(url);
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	        System.out.println("Yhteys avattu.");
	     }catch (Exception e){	
	    	 System.out.println("Yhteyden avaus epäonnistui.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public ArrayList<Henkilo> listaaKaikki(){
		ArrayList<Henkilo> asiakkaat = new ArrayList<Henkilo>();
		sql = "SELECT * FROM asiakkaat";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui
					//con.close();					
					while(rs.next()){
						Henkilo henkilo = new Henkilo();
						henkilo.setEtunimi(rs.getString(2));
						henkilo.setSukunimi(rs.getString(3));	
						henkilo.setPuhelin(rs.getString(4));
						henkilo.setSposti(rs.getString(5));
						asiakkaat.add(henkilo);
					}					
				}				
			}	
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return asiakkaat;
	}
	
	public ArrayList<Henkilo> listaaKaikki(String hakusana){
		ArrayList<Henkilo> henkilot = new ArrayList<Henkilo>();
		sql = "SELECT * FROM asiakkaat WHERE etunimi LIKE ? or sukunimi LIKE ? or sposti LIKE ? GROUP BY asiakas_id";      
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);
				stmtPrep.setString(1, "%" + hakusana + "%");
				stmtPrep.setString(2, "%" + hakusana + "%");   
				stmtPrep.setString(3, "%" + hakusana + "%");  
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui
					//con.close();					
					while(rs.next()){
						Henkilo henkilo = new Henkilo();
						henkilo.setEtunimi(rs.getString(2));
						henkilo.setSukunimi(rs.getString(3));
						henkilo.setPuhelin(rs.getString(4));
						henkilo.setSposti(rs.getString(5));	
						henkilot.add(henkilo);
					}					
				}				
			}	
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return henkilot;
	}
	
	public boolean lisaaAsiakas(Henkilo henkilo){
		boolean paluuArvo=true;
		sql="INSERT INTO asiakkaat VALUES(?,?,?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(2, henkilo.getEtunimi());
			stmtPrep.setString(3, henkilo.getSukunimi());
			stmtPrep.setString(4, henkilo.getPuhelin());
			stmtPrep.setString(5, henkilo.getSposti());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	public boolean poistaAsiakas(String sposti){
		boolean paluuArvo=true;
		sql="DELETE FROM asiakkaat WHERE sposti=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, sposti);			
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
}
