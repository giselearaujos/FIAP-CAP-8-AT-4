package br.com.traveller.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.traveller.beans.Cidade;
import br.com.traveller.beans.Usuario;
import br.com.traveller.dao.CidadeDAO;
import br.com.traveller.dao.DataSource;
import br.com.traveller.dao.UsuarioDAO;


@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet{ 
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paginaDestino="";
		try {
			
			if (request.getSession().getAttribute("Usuario") != null) {
				String txtNome = request.getParameter("nome");
				String txtEmail = request.getParameter("email");
				String txtSenha = request.getParameter("senha");
				String txtNascimento = request.getParameter("data_nascimento");
				String txtPerfil  = request.getParameter("acesso");
				String txtCidade = request.getParameter("cidade");
				String txtGenero = request.getParameter("genero");
				String txtFoto = request.getParameter("foto");
				String hdnId = request.getParameter("id");
				
				Usuario usuario = new Usuario();
				usuario.setNome(txtNome);
				usuario.setEmail(txtEmail);
				usuario.setSenha(txtSenha);
				usuario.setNascimento(LocalDate.parse(txtNascimento));
				usuario.setNivel(txtPerfil);
				usuario.setGenero(txtGenero);
				usuario.setFoto(txtFoto);
				usuario.setId(Integer.parseInt(hdnId));
				DataSource dataSource = new DataSource();
				
				//Buscando cidade no BD
				CidadeDAO cidadeDao = new CidadeDAO(dataSource);
				Cidade filtroCidade = new Cidade();
				filtroCidade.setCidade(txtCidade);
				Cidade cidade = cidadeDao.read(filtroCidade);
				dataSource.getConnection().close();
				if(cidade == null) {
					paginaDestino = "/erro.jsp";
					request.setAttribute("ErroMSG", "Erro ao realizar cadastro! Cidade não encontrada");
				}
				else {
					dataSource = new DataSource();
					usuario.setCidade(cidade);
					usuario.setUltimoAcesso(LocalDate.now());
					UsuarioDAO usuarioDao = new UsuarioDAO(dataSource);
					usuarioDao.update(usuario);
					dataSource.getConnection().close();
					
					request.getSession().setAttribute("Usuario", usuario);
					paginaDestino = "/dados_usuario.jsp";
				}
				
			}
			else {
				paginaDestino = "/erro.jsp";
				request.setAttribute("ErroMSG", "Erro ao realizar cadastro!");
			}
		
		}
		catch(Exception ex) {
			paginaDestino = "/erro.jsp";
			request.setAttribute("ErroMSG", "Erro desconhecido no cadastro");
			ex.printStackTrace();
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(paginaDestino);
		dispatcher.forward(request, response);
		
	}

}
