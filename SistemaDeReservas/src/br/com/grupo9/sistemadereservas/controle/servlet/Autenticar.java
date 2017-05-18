package br.com.grupo9.sistemadereservas.controle.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.grupo9.sistemadereservas.model.BO.UsuarioBO;
import br.com.grupo9.sistemadereservas.model.PO.UsuarioPO;

/**
 * Servlet implementation class Autenticacao
 */
@WebServlet("/autenticar.do")
public class Autenticar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioBO usuarioBO;
	private UsuarioPO usuarioCapturado;
    private HttpSession sessao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Autenticar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter retorno = response.getWriter();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
		getUsuarioBO().getUsusarioPO().setLogin(request.getParameter("login"));
		getUsuarioBO().getUsusarioPO().setSenha(request.getParameter("senha"));
		if(getUsuarioBO().capturarUsuarioValido() != null){
			setUsuarioCapturado(getUsuarioBO().capturarUsuarioValido());
			setSessao(request.getSession(true));
			if(getUsuarioCapturado() != null){
				if(getUsuarioCapturado().getSenha().equals(getUsuarioBO().getUsusarioPO().getSenha())){
					getSessao().setAttribute("login", getUsuarioCapturado().getLogin());
					getSessao().setAttribute("tipo", getUsuarioCapturado().getTipo());
					retorno.println("{\"loginValido\":1,\"senhaValida\":1}");
				}else{
					retorno.println("{\"loginValido\":1,\"senhaValida\":0}");
				}
			}else{
				retorno.println("{\"loginValido\":0,\"senhaValida\":0}");
			}
		}else{
			retorno.println("{\"loginValido\":0,\"senhaValida\":0}");
		}
	}
	
	private UsuarioBO getUsuarioBO(){
		if(this.usuarioBO == null){
			this.usuarioBO = new UsuarioBO();
		}
		return this.usuarioBO;
	}
	
	private UsuarioPO getUsuarioCapturado(){
		return this.usuarioCapturado;
	}
	
	private void setUsuarioCapturado(UsuarioPO usuarioPO){
		this.usuarioCapturado = usuarioPO;
	}
	
	private void setSessao(HttpSession session){
		this.sessao = session;
	}
	private HttpSession getSessao(){
		return this.sessao;
	}

}