package co.edu.usbcali.demo.vista;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;

@ManagedBean
@ViewScoped
public class LoginVista {
	
	private InputText txtLogin;
	private Password txtPassword;
	private CommandButton btnIngresar;
	
	public InputText getTxtLogin() {
		return txtLogin;
	}
	public void setTxtLogin(InputText txtLogin) {
		this.txtLogin = txtLogin;
	}
	public Password getTxtPassword() {
		return txtPassword;
	}
	public void setTxtPassword(Password txtPassword) {
		this.txtPassword = txtPassword;
	}
	public CommandButton getBtnIngresar() {
		return btnIngresar;
	}
	public void setBtnIngresar(CommandButton btnIngresar) {
		this.btnIngresar = btnIngresar;
	}
	
	public String ingresarAction(){
		System.out.println(txtLogin.getValue().toString());
		System.out.println(txtPassword.getValue().toString());
		return "";
	}
	
	public void txtLoginListener(){
		if(txtLogin.getValue().toString().equals("")==false){
			btnIngresar.setDisabled(false);
		}else{
			btnIngresar.setDisabled(true);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
