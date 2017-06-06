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

import co.edu.usbcali.demo.logica.IClientesLogica;
import co.edu.usbcali.demo.logica.ITiposDocumentosLogica;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@ManagedBean
@ViewScoped
public class ClientesVista {

	@ManagedProperty("#{clientesLogica}")
	private IClientesLogica clientesLogica;
	
	@ManagedProperty("#{tiposDocumentosLogica}")
	private ITiposDocumentosLogica tiposDocumentosLogica;
	
	private List<Clientes> losClientes;
	
	private InputText txtIdentificacion;
	private SelectOneMenu somTiposDocumentos;
	private InputText txtNombre;
	private InputText txtDireccion;
	private InputText txtTelefono;
	private InputText txtMail;
	
	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	
	private List<SelectItem> losTiposDocumentosSelectItem;
	
	public List<SelectItem> getLosTiposDocumentosSelectItem() {
		if(losTiposDocumentosSelectItem==null){
			losTiposDocumentosSelectItem=new ArrayList<SelectItem>();
			List<TiposDocumentos> losTiposDocumentos=tiposDocumentosLogica.consultar();
			for (TiposDocumentos tiposDocumentos : losTiposDocumentos) {
				losTiposDocumentosSelectItem.add(new SelectItem(tiposDocumentos.getTdocCodigo(), tiposDocumentos.getTdocCodigo()+"-"+tiposDocumentos.getTdocNombre()));
			}
		}
		return losTiposDocumentosSelectItem;
	}

	public void setLosTiposDocumentosSelectItem(List<SelectItem> losTiposDocumentosSelectItem) {
		this.losTiposDocumentosSelectItem = losTiposDocumentosSelectItem;
	}

	public String crearAction(){
		try {
			Long cliId=Long.parseLong(txtIdentificacion.getValue().toString());
			Clientes clientes=new Clientes();
			clientes.setCliId(cliId);
			clientes.setCliDireccion(txtDireccion.getValue().toString());
			clientes.setCliMail(txtMail.getValue().toString());
			clientes.setCliNombre(txtNombre.getValue().toString());
			clientes.setCliTelefono(txtTelefono.getValue().toString());
			
			Long tdocCodigo=Long.parseLong(somTiposDocumentos.getValue().toString());
			TiposDocumentos tiposDocumentos=tiposDocumentosLogica.consultarPorId(tdocCodigo);
			
			clientes.setTiposDocumentos(tiposDocumentos);
			
			clientesLogica.crear(clientes);
			losClientes=null;
			
			FacesContext.getCurrentInstance().addMessage("",new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se creo con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}
	
	public String modificarAction(){
		try {
			Long cliId=Long.parseLong(txtIdentificacion.getValue().toString());
			Clientes clientes=new Clientes();
			clientes.setCliId(cliId);
			clientes.setCliDireccion(txtDireccion.getValue().toString());
			clientes.setCliMail(txtMail.getValue().toString());
			clientes.setCliNombre(txtNombre.getValue().toString());
			clientes.setCliTelefono(txtTelefono.getValue().toString());
			
			Long tdocCodigo=Long.parseLong(somTiposDocumentos.getValue().toString());
			TiposDocumentos tiposDocumentos=tiposDocumentosLogica.consultarPorId(tdocCodigo);
			
			clientes.setTiposDocumentos(tiposDocumentos);
			
			clientesLogica.modificar(clientes);	
			losClientes=null;
			
			FacesContext.getCurrentInstance().addMessage("",new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se modifico con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}
	
	public String borrarAction(){
		try {
			Long cliId=Long.parseLong(txtIdentificacion.getValue().toString());
			Clientes clientes=new Clientes();
			clientes.setCliId(cliId);
			clientes.setCliDireccion(txtDireccion.getValue().toString());
			clientes.setCliMail(txtMail.getValue().toString());
			clientes.setCliNombre(txtNombre.getValue().toString());
			clientes.setCliTelefono(txtTelefono.getValue().toString());
			
			Long tdocCodigo=Long.parseLong(somTiposDocumentos.getValue().toString());
			TiposDocumentos tiposDocumentos=tiposDocumentosLogica.consultarPorId(tdocCodigo);
			
			clientes.setTiposDocumentos(tiposDocumentos);
			
			clientesLogica.borrar(clientes);
			losClientes=null;
			
			FacesContext.getCurrentInstance().addMessage("",new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se borro con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}
	
	public String limpiarAction(){
		txtIdentificacion.resetValue();
		losClientes=null;
		limpiar();
		return "";
	}
	
	public void txtIdentificacionListener(){
		Long cliId=0L;
		Clientes entity=null;
		try {
			cliId=Long.parseLong(txtIdentificacion.getValue().toString());
			entity=clientesLogica.consultarPorId(cliId);
		} catch (Exception e) {}
		
		if(entity==null){
			limpiar();
			
		}else{
			btnCrear.setDisabled(true);
			btnModificar.setDisabled(false);
			btnBorrar.setDisabled(false);
			
			txtDireccion.setValue(entity.getCliDireccion());
			txtMail.setValue(entity.getCliMail());
			txtNombre.setValue(entity.getCliNombre());
			txtTelefono.setValue(entity.getCliTelefono());
			
			somTiposDocumentos.setValue(entity.getTiposDocumentos().getTdocCodigo());
			
		}
		
	}

	private void limpiar() {
		btnCrear.setDisabled(false);
		btnModificar.setDisabled(true);
		btnBorrar.setDisabled(true);
		
		txtDireccion.resetValue();
		txtMail.resetValue();
		txtNombre.resetValue();
		txtTelefono.resetValue();
		somTiposDocumentos.resetValue();
	}

	public InputText getTxtIdentificacion() {
		return txtIdentificacion;
	}

	public void setTxtIdentificacion(InputText txtIdentificacion) {
		this.txtIdentificacion = txtIdentificacion;
	}

	public SelectOneMenu getSomTiposDocumentos() {
		return somTiposDocumentos;
	}

	public void setSomTiposDocumentos(SelectOneMenu somTiposDocumentos) {
		this.somTiposDocumentos = somTiposDocumentos;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputText getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(InputText txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public InputText getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(InputText txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public InputText getTxtMail() {
		return txtMail;
	}

	public void setTxtMail(InputText txtMail) {
		this.txtMail = txtMail;
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

	public IClientesLogica getClientesLogica() {
		return clientesLogica;
	}

	public void setClientesLogica(IClientesLogica clientesLogica) {
		this.clientesLogica = clientesLogica;
	}

	public List<Clientes> getLosClientes() {
		if(losClientes==null){
			losClientes=clientesLogica.consultar();
		}
		return losClientes;
	}

	public void setLosClientes(List<Clientes> losClientes) {
		this.losClientes = losClientes;
	}

	public ITiposDocumentosLogica getTiposDocumentosLogica() {
		return tiposDocumentosLogica;
	}

	public void setTiposDocumentosLogica(ITiposDocumentosLogica tiposDocumentosLogica) {
		this.tiposDocumentosLogica = tiposDocumentosLogica;
	}
	
	
}
