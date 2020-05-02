package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String url = request.getRequestURL().toString();
		String ctxPath = request.getContextPath();
		// ctxPath = "/context";
		
		Dao dao = new Dao();
		ArrayList<Henkilo> asiakkaat = null;
		if(pathInfo==null) { 
			asiakkaat = dao.listaaKaikki();
			request.setAttribute("asiakkaat", asiakkaat);			
			String jsp = "/listaaasiakkaat.jsp"; 
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jsp);
			dispatcher.forward(request, response);	
		}else if(pathInfo.indexOf("haeyksi") != -1) {		
			String hakusana = pathInfo.replace("/haeyksi/", "");
			Henkilo asiakas = dao.etsiAsiakas(hakusana);
			request.setAttribute("asiakkaat", asiakas);			
			String jsp = "/listaaasiakkaat.jsp"; 
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jsp);
			dispatcher.forward(request, response);		
		}
		else if (pathInfo.contains("/poista/") == true) {
			url = url.replaceFirst(pathInfo, "");
			url = url + ctxPath;
			String poistettavaSposti = request.getParameter("sposti");					
			dao.poistaAsiakas(poistettavaSposti);
			response.sendRedirect(url + "/asiakkaat");
		}
		
		else{ 
			String hakusana = pathInfo.replace("/", "");
			asiakkaat = dao.listaaKaikki(hakusana);
			request.setAttribute("asiakkaat", asiakkaat);			
			String jsp = "/listaaasiakkaat.jsp"; 
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jsp);
			dispatcher.forward(request, response);	
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPost()");
		Henkilo henkilo = new Henkilo();
		
		henkilo.setEtunimi(request.getParameter("etunimi"));
		henkilo.setSukunimi(request.getParameter("sukunimi"));
		henkilo.setPuhelin(request.getParameter("puhelin"));
		henkilo.setSposti(request.getParameter("sposti"));
		Dao dao = new Dao();			
		dao.lisaaAsiakas(henkilo);
		System.out.println(henkilo);
		response.sendRedirect("asiakkaat/" + henkilo.getSposti());
	}


}
