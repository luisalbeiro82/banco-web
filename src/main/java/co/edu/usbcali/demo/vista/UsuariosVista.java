package co.edu.usbcali.demo.vista;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import co.edu.usbcali.demo.logica.ITiposUsuariosLogica;
import co.edu.usbcali.demo.logica.IUsuariosLogica;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@ManagedBean
@ViewScoped
public class UsuariosVista {

	@ManagedProperty("#{usuariosLogica}")
	private IUsuariosLogica usuariosLogica;

	@ManagedProperty("#{tiposUsuariosLogica}")
	private ITiposUsuariosLogica tiposUsuariosLogica;

	private List<Usuarios> losUsuarios;

	private InputText txtIdentificacion;
	private InputText txtLogin;
	private SelectOneMenu somTiposUsuarios;
	private InputText txtNombre;
	private InputText txtClave;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;

	private List<SelectItem> losTiposUsuariosSelectItem;

	public List<SelectItem> getLosTiposUsuariosSelectItem() {
		if (losTiposUsuariosSelectItem == null) {
			losTiposUsuariosSelectItem = new ArrayList<SelectItem>();
			List<TiposUsuarios> losTiposUsuarios = tiposUsuariosLogica.consultar();
			for (TiposUsuarios tiposUsuarios : losTiposUsuarios) {
				losTiposUsuariosSelectItem.add(new SelectItem(tiposUsuarios.getTusuCodigo(),
						tiposUsuarios.getTusuCodigo() + "-" + tiposUsuarios.getTusuNombre()));
			}
		}
		return losTiposUsuariosSelectItem;
	}
	

	public void setLosTiposUsuariosSelectItem(List<SelectItem> losTiposUsuariosSelectItem) {
		this.losTiposUsuariosSelectItem = losTiposUsuariosSelectItem;
	}
	

	public String crearAction() {
		try {
			Long cedula = Long.parseLong(txtIdentificacion.getValue().toString());
			Usuarios usuarios = new Usuarios();
			usuarios.setUsuCedula(cedula);
			usuarios.setUsuLogin(txtLogin.getValue().toString());
			usuarios.setUsuNombre(txtNombre.getValue().toString());
			usuarios.setUsuClave(txtClave.getValue().toString());

			Long tipoUsu = Long.parseLong(somTiposUsuarios.getValue().toString());
			TiposUsuarios tiposUsuarios = tiposUsuariosLogica.consultarPorTipoUsu(tipoUsu);

			usuarios.setTiposUsuarios(tiposUsuarios);

			usuariosLogica.crear(usuarios);
			losUsuarios = null;

			FacesContext.getCurrentInstance().addMessage("",new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se creo con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	
	public String modificarAction() {
		try {
			Long cedula = Long.parseLong(txtIdentificacion.getValue().toString());
			Usuarios usuarios = new Usuarios();
			usuarios.setUsuCedula(cedula);
			usuarios.setUsuLogin(txtLogin.getValue().toString());
			usuarios.setUsuNombre(txtNombre.getValue().toString());
			usuarios.setUsuClave(txtClave.getValue().toString());

			Long tusuCodigo = Long.parseLong(somTiposUsuarios.getValue().toString());
			TiposUsuarios tiposUsuarios = tiposUsuariosLogica.consultarPorTipoUsu(tusuCodigo);

			usuarios.setTiposUsuarios(tiposUsuarios);

			usuariosLogica.modificar(usuarios);
			losUsuarios = null;

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se modifico con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public String borrarAction() {
		try {
			Long cedula = Long.parseLong(txtIdentificacion.getValue().toString());
			Usuarios usuarios = new Usuarios();
			usuarios.setUsuCedula(cedula);
			usuarios.setUsuLogin(txtLogin.getValue().toString());
			usuarios.setUsuNombre(txtNombre.getValue().toString());
			usuarios.setUsuClave(txtClave.getValue().toString());

			Long tusuCodigo = Long.parseLong(somTiposUsuarios.getValue().toString());
			TiposUsuarios tiposUsuarios = tiposUsuariosLogica.consultarPorTipoUsu(tusuCodigo);

			usuarios.setTiposUsuarios(tiposUsuarios);

			usuariosLogica.borrar(usuarios);
			losUsuarios = null;

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se borro con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public String limpiarAction() {
		txtIdentificacion.resetValue();
		losUsuarios = null;
		limpiar();
		return "";
	}

	public void txtIdentificacionListener() {
		Long cedula = 0L;
		Usuarios entity = null;
		try {
			cedula = Long.parseLong(txtIdentificacion.getValue().toString());
			entity = usuariosLogica.consultarPorCed(cedula);
		} catch (Exception e) {
		}

		if (entity == null) {
			limpiar();
		} else {
			btnCrear.setDisabled(true);
			btnModificar.setDisabled(false);
			btnBorrar.setDisabled(false);

			txtLogin.setValue(entity.getUsuLogin());
			txtNombre.setValue(entity.getUsuNombre());
			txtClave.setValue(entity.getUsuClave());

			somTiposUsuarios.setValue(entity.getTiposUsuarios().getTusuCodigo());

		}

	}

	private void limpiar() {
		btnCrear.setDisabled(false);
		btnModificar.setDisabled(true);
		btnBorrar.setDisabled(true);

		txtLogin.resetValue();
		txtNombre.resetValue();
		txtClave.resetValue();

		somTiposUsuarios.resetValue();
	}

	public InputText getTxtIdentificacion() {
		return txtIdentificacion;
	}

	public void setTxtIdentificacion(InputText txtIdentificacion) {
		this.txtIdentificacion = txtIdentificacion;
	}

	

	public SelectOneMenu getSomTiposUsuarios() {
		return somTiposUsuarios;
	}


	public void setSomTiposUsuarios(SelectOneMenu somTiposUsuarios) {
		this.somTiposUsuarios = somTiposUsuarios;
	}


	public List<Usuarios> getLosUsuarios() {
		return losUsuarios;
	}


	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputText getTxtLogin() {
		return txtLogin;
	}

	public void setTxtLogin(InputText txtLogin) {
		this.txtLogin = txtLogin;
	}

	

	public InputText getTxtClave() {
		return txtClave;
	}


	public void setTxtClave(InputText txtClave) {
		this.txtClave = txtClave;
	}


	public CommandButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnCrear(CommandButton btnCrear) {
		this.btnCrear = btnCrear;
	}

	public CommandButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(CommandButton btnModificar) {
		this.btnModificar = btnModificar;
	}

	public CommandButton getBtnBorrar() {
		return btnBorrar;
	}

	public void setBtnBorrar(CommandButton btnBorrar) {
		this.btnBorrar = btnBorrar;
	}

	public IUsuariosLogica getUsuariosLogica() {
		return usuariosLogica;
	}

	public void setusuariosLogica(IUsuariosLogica usuariosLogica) {
		this.usuariosLogica = usuariosLogica;
	}

	public List<Usuarios> getUsuarios() {
		if (losUsuarios == null) {
			losUsuarios = usuariosLogica.consultar();
		}
		return losUsuarios;
	}

	public void setLosUsuarios(List<Usuarios> losUsuarios) {
		this.losUsuarios = losUsuarios;
	}

	public ITiposUsuariosLogica getTiposUsuariosLogica() {
		return tiposUsuariosLogica;
	}

	public void setTiposUsuariosLogica(ITiposUsuariosLogica TiposUsuariosLogica) {
		this.tiposUsuariosLogica = TiposUsuariosLogica;
	}

}
