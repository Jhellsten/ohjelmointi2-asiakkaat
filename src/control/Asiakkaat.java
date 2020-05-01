package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import model.Henkilo;
import model.dao.Dao;


@WebServlet("/asiakkaat/*")
public class Asiakkaat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public Asiakkaat() {
        super();
        System.out.println("Asiakkaat.Henkilot()");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doGet()");
		String pathInfo = request.getPathInfo();	//haetaan kutsun polkutiedot, esim. /audi			
		System.out.println("polku: "+pathInfo);	
		String strJSON = "";
		Dao dao = new Dao();
		ArrayList<Henkilo> asiakkaat = null;
		if(pathInfo==null) { 
			asiakkaat = dao.listaaKaikki();
			strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();	
		}else if(pathInfo.indexOf("haeyksi") != -1) {		
			String hakusana = pathInfo.replace("/haeyksi/", "");
			Henkilo asiakas = dao.etsiAsiakas(hakusana);
			System.out.println(asiakas);
			JSONObject JSON = new JSONObject();
			JSON.put("etunimi", asiakas.getEtunimi());
			JSON.put("sukunimi", asiakas.getSukunimi());
			JSON.put("puhelin", asiakas.getPuhelin());
			JSON.put("sposti", asiakas.getSposti());	
			strJSON = JSON.toString();		
		}else{ 
			String hakusana = pathInfo.replace("/", "");
			asiakkaat = dao.listaaKaikki(hakusana);
			strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();	
		}	
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(strJSON);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPost()");
		JSONObject jsonObj = new JsonStrToObj().convert(request); //Muutetaan kutsun mukana tuleva json-string json-objektiksi			
		Henkilo henkilo = new Henkilo();
		
		henkilo.setEtunimi(jsonObj.getString("etunimi"));
		henkilo.setSukunimi(jsonObj.getString("sukunimi"));
		henkilo.setPuhelin(jsonObj.getString("puhelin"));
		henkilo.setSposti(jsonObj.getString("sposti"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		System.out.println(henkilo);
		System.out.println(jsonObj);
		Dao dao = new Dao();			
		if(dao.lisaaAsiakas(henkilo)){ 
			out.println("{\"response\":1}");  
		}else{
			out.println("{\"response\":0}"); 
		}			
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPut()");
		JSONObject jsonObj = new JsonStrToObj().convert(request); 
		String vanhaSposti = jsonObj.getString("vanhasposti");
		Henkilo henkilo = new Henkilo();
		henkilo.setEtunimi(jsonObj.getString("etunimi"));
		henkilo.setSukunimi(jsonObj.getString("sukunimi"));
		henkilo.setPuhelin(jsonObj.getString("puhelin"));
		henkilo.setSposti(jsonObj.getString("sposti"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();			
		if(dao.muutaAsiakas(henkilo, vanhaSposti)){ 
			out.println("{\"response\":1}"); 
		}else{
			out.println("{\"response\":0}");  
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doDelete()");	
		String pathInfo = request.getPathInfo();
		System.out.println("polku: "+pathInfo);
		String poistettavaSposti = pathInfo.replace("/", "");		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();			
		if(dao.poistaAsiakas(poistettavaSposti)){ 
			out.println("{\"response\":1}"); 
		}else{
			out.println("{\"response\":0}");
		}	
	}

}
