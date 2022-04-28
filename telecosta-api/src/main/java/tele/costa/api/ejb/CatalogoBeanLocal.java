package tele.costa.api.ejb;

import java.util.List;
import javax.ejb.Local;
import tele.costa.api.entity.Agencia;
import tele.costa.api.entity.Configuracionpago;
import tele.costa.api.entity.Departamento;
import tele.costa.api.entity.Estadocliente;
import tele.costa.api.entity.Formapago;
import tele.costa.api.entity.Insumos;
import tele.costa.api.entity.Municipio;
import tele.costa.api.entity.Proveedor;
import tele.costa.api.entity.Ruta;
import tele.costa.api.entity.Sector;
import tele.costa.api.entity.Sectorpago;
import tele.costa.api.entity.Tipoatencion;
import tele.costa.api.entity.Tipocarga;
import tele.costa.api.entity.Tipocompra;
import tele.costa.api.entity.Tipodocumentocompra;
import tele.costa.api.entity.Tipopago;
import tele.costa.api.entity.Usuario;

/**
 *
 * @author elfo_
 */
@Local
public interface CatalogoBeanLocal {

    List<Departamento> listDepartamentos();

    List<Municipio> listMunicipioByIdDepartamento(Integer iddepartamento);

    Tipopago findTipoPago(Integer idtipopago);

    Configuracionpago findConfiguracionPago(Integer idconfiguracionpago);

    List<Configuracionpago> ListConfiguracionPago();

    List<Proveedor> listProveedor();

    List<Tipodocumentocompra> listTipoDocumento();

    List<Tipocompra> listTipoCompra();

    List<Formapago> listFormaPago();

    List<Usuario> listaUsuarios();

    List<Sector> listSector();

    List<Ruta> listRuta();

    List<Tipoatencion> listTipoAtencion();

    List<Sectorpago> listSectorPagoByIdMunicipio(Integer idmunicipio);

    List<Municipio> listMunicipioBySanRafaelSanPableRodeo();

    List<Municipio> listMunicipioByIdMunicipio(Integer idMunicipio);

    List<Municipio> listMunicipioBySanpabloAndSanRafael();

    List<Agencia> listAgencias();

    Tipocarga findTipoCarga(Integer idtipocarga);

    Estadocliente findEstadoCliente(Integer idestadocliente);

    List<Insumos> listInsumos();

    Tipocompra findTipoCompra(Integer idtipocompra);

    Tipodocumentocompra findTipoDocumentoCompra(Integer idtipodocumentocompra);

    Formapago findFormaPago(Integer idformapago);

}
