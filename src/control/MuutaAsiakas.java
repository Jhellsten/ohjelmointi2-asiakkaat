package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Henkilo;
import model.dao.Dao;


@WebServlet("/muutaasiakas")
public class MuutaAsiakas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public MuutaAsiakas() {
        super();
        System.out.println("Asiakkaat.Henkilot()");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doGet()");
		String sposti = request.getParameter("sposti");
		System.out.println("sposti: "+sposti);	
		Dao dao = new Dao();
		Henkilo asiakas = dao.etsiAsiakas(sposti);
		request.setAttribute("asiakas", asiakas);			
		String jsp = "/muutaasiakas.jsp"; 
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jsp);
		dispatcher.forward(request, response);		
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPost()");
		Henkilo henkilo = new Henkilo();
		henkilo.setEtunimi(request.getParameter("etunimi"));
		henkilo.setSukunimi(request.getParameter("sukunimi"));
		henkilo.setPuhelin(request.getParameter("puhelin"));
		henkilo.setSposti(request.getParameter("sposti"));
		Dao dao = new Dao();			
		dao.muutaAsiakas(henkilo, request.getParameter("vanhasposti"));
		response.sendRedirect("asiakkaat/" + henkilo.getSposti());
	}
}
