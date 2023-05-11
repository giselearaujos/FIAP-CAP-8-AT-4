package br.com.traveller.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.traveller.beans.Usuario;
import br.com.traveller.dao.DataSource;
import br.com.traveller.dao.UsuarioDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String pagina="";
		try {
			
			DataSource dataSource = new DataSource();
			
			UsuarioDAO usuarioDao = new UsuarioDAO(dataSource);
			
			Usuario userLogin = new Usuario();
			userLogin.setEmail(request.getParameter("email"));
			userLogin.setSenha(request.getParameter("senha"));
			
			Usuario user = usuarioDao.read(userLogin);
			if (user != null) {
			
				request.getSession().setAttribute("Usuario", user);
				pagina = "/dados_usuario.jsp";
			}
			else {
				request.setAttribute("ErroMSG", "Usuario nao encontrado");
				pagina = "/erro.jsp";
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(pagina);
			dispatcher.forward(request, response);
		}
		catch(Exception ex) {
			System.out.println("ERRO on LOGINSERVLET "+ex.getMessage());
		}
		
	}

}
