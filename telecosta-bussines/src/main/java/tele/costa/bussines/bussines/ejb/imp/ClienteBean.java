package tele.costa.bussines.bussines.ejb.imp;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.log4j.Logger;
import tele.costa.api.ejb.ClienteBeanLocal;
import tele.costa.api.entity.Cliente;

/**
 *
 * @author elfo_
 */
@Singleton
public class ClienteBean implements ClienteBeanLocal {

    private static final Logger log = Logger.getLogger(ClienteBean.class);

    @PersistenceContext(unitName = "TeleCostaPU")
    EntityManager em;

    @Resource
    private EJBContext context;

    private void processException(Exception ex) {
        log.error(ex.getMessage(), ex);
    }

    private String getConstraintViolationExceptionAsString(ConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder();
        sb.append("Error de validación:\n");
        for (ConstraintViolation c : ex.getConstraintViolations()) {
            sb.append(String.format("[bean: %s; field: %s; message: %s; value: %s]",
                    c.getRootBeanClass().getName(),
                    c.getPropertyPath().toString(),
                    c.getMessage(), c.getInvalidValue()));
        }
        return sb.toString();
    }

    @Override
    public List<Cliente> ListClientes() {
        List<Cliente> lst = em.createQuery("SELECT qj FROM Cliente qj where qj.activo = true ", Cliente.class)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }

        return lst;
    }

    @Override
    public Cliente saveCliente(Cliente cliente) {
        try {
            cliente.setActivo(true);
            cliente.setFechacreacion(new Date());
            em.persist(cliente);
            em.flush();
            return (cliente);
        } catch (ConstraintViolationException ex) {
            String validationError = getConstraintViolationExceptionAsString(ex);
            log.error(validationError);
            context.setRollbackOnly();
            return null;
        } catch (Exception ex) {
            processException(ex);
            context.setRollbackOnly();
            return null;
        }
    }

    @Override
    public Cliente updateCliente(Cliente cliente) {
        try {
            em.merge(cliente);
            em.flush();
            return (cliente);
        } catch (ConstraintViolationException ex) {
            String validationError = getConstraintViolationExceptionAsString(ex);
            log.error(validationError);
            context.setRollbackOnly();
            return null;
        } catch (Exception ex) {
            processException(ex);
            context.setRollbackOnly();
            return null;
        }
    }

    @Override
    public List<Cliente> ListClientesByNombre(String nombre) {
        if (nombre == null) {
            return null;
        }

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.nombres like :nombre ", Cliente.class)
                .setParameter("nombre", '%' + nombre + '%')
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public List<Cliente> ListClientesByCui(String cui) {
        if (cui == null) {
            return null;
        }

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.cui =:cui ", Cliente.class)
                .setParameter("cui", cui)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    @Override
    public Cliente findClienteById(Integer idcliente) {
        if (idcliente == null) {
            return null;
        }

        List<Cliente> lst = em.createQuery("SELECT col FROM Cliente col WHERE col.idcliente =:idcliente ", Cliente.class)
                .setParameter("idcliente", idcliente)
                .getResultList();

        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }
    
     public Response<NpCargaMasiva> cargarDatosArchivo(InputStream inputStream, NpCargaMasiva cargaMasiva, Boolean buscarPorNit) {
        try {
            Integer totalRegistros = 0;
            Integer totalExitosos = 0;
            Integer totalFilas = 0;
            BigDecimal montoTotalExitoso = new BigDecimal("0.00");
            List<NpDetalleCargaMasiva> listaDetalle = new ArrayList();

            XSSFWorkbook workbook;
            try {
                workbook = new XSSFWorkbook(inputStream);
            } catch (Exception ex) {
                processException(ex);
                context.setRollbackOnly();
                return new Response<>(ResponseStatus.ERROR, "Sucedio un error al hacer el XSSFWorkbook: " + ex.getMessage());
            }

            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            Row row;

            Integer numFil = 0;
            while (rowIterator.hasNext()) {
                numFil++;
                row = rowIterator.next();
//                log.debug("getTieneEncabezado: " + cargaMasiva.getTieneEncabezado());
//                if (numFil != 1 || !cargaMasiva.getTieneEncabezado()) {
                if (cargaMasiva.getTieneEncabezado() && numFil == 1) {continue;}
                totalFilas++;
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell celda;

                    NclEmpleado idEmpleado = null;
                    BigDecimal monto = null;
                    String observacionesDetalle = null;

                    String observacion = new String();
                    Boolean error = Boolean.TRUE;
                    Boolean celdaVacia = Boolean.FALSE;
                    DataFormatter df = new DataFormatter();
                    Integer contadorColumna = 0;
                    while (cellIterator.hasNext()) {
                        celda = cellIterator.next();
                        contadorColumna++;
                        if (contadorColumna == 1) {
                            try {

//                                DecimalFormat decimalFormat = new DecimalFormat("#");
                                String form = null;
//                                if (celda.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                                    form = decimalFormat.format(celda.getNumericCellValue());
//                                } else if (celda.getCellType() == Cell.CELL_TYPE_STRING) {
//                                    form = decimalFormat.format(celda.getStringCellValue());
//                                } else {
//                                    log.error("Error en cargarDatosArchivo / Tipo de Celda no aceptado: " + celda.getCellType());
//                                    if (celda.toString().isEmpty()) {
//                                        celdaVacia = Boolean.TRUE;
//                                        break;
//                                    }
//                                    observacion = observacion.concat(" La primer celda no es de tipo numerico: " + celda.toString());
//                                    error = Boolean.TRUE;
//                                }
                                form = df.formatCellValue(celda);

                                if (form != null && !form.isEmpty() && !buscarPorNit) {
                                    Integer nip = Integer.parseInt(form);
                                    Response<NclEmpleado> empleadoR = empleadoBean.findEmpleadoByNip(nip);
                                    if (!empleadoR.isOk()) {

                                        empleadoR = empleadoBean.findEmpleadoByNipInactivo(nip);
                                        if (!empleadoR.isOk()) {
                                            observacion = observacion.concat(" No se encontro el nip del empleado: " + celda.toString());
                                            error = Boolean.TRUE;
                                        } else {
                                            idEmpleado = empleadoR.getValue();
                                            error = Boolean.FALSE;
                                        }

                                    } else {
                                        idEmpleado = empleadoR.getValue();
                                        error = Boolean.FALSE;
                                    }

                                } else if (form != null && !form.isEmpty() && buscarPorNit) {

                                    Response<NclEmpleado> empleadoR = empleadoBean.findEmpleadoByNit(form);
                                    if (!empleadoR.isOk()) {

                                        observacion = observacion.concat(" No se encontro el nit del empleado: " + celda.toString());
                                        error = Boolean.TRUE;

                                    } else {
                                        idEmpleado = empleadoR.getValue();
                                        error = Boolean.FALSE;
                                    }

                                }

                            } catch (Exception ex) {
                                observacion = observacion.concat(" Sucedio un error al convertir el valor a Entero para el nip: " + celda.toString());
                                error = Boolean.TRUE;
                            }

                        } else if (!cargaMasiva.getIdTipoCarga().getIdTipoCarga().equals(TipoCarga.ELIMINACION.getValue()) && contadorColumna == 2) {
                            try {
                                BigDecimal bd = null;
                                if (celda.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    bd = new BigDecimal(celda.getNumericCellValue());
                                    log.debug("MONTO NUMERICO: " + bd);
                                } else if (celda.getCellType() == Cell.CELL_TYPE_STRING) {
                                    bd = new BigDecimal(celda.getStringCellValue());
                                    log.debug("MONTO NUMERICO: " + bd);
                                } else {
                                    observacion = observacion.concat(" La segunda celda no es de tipo numerica o texto: " + celda.toString());
                                    error = Boolean.TRUE;
                                }
                                if (bd != null) {
                                    bd = bd.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                                    log.debug("MONTO: " + bd);
                                    if (bd.equals(new BigDecimal("0.00")) || bd.compareTo(new BigDecimal("0.00")) == -1) {
                                        observacion = observacion.concat(" El monto no es valido, es negativo o es igual a 0.00: " + celda.toString());
                                        error = Boolean.TRUE;
                                    } else if (cargaMasiva.getIdIngresoDescuento() != null && cargaMasiva.getIdIngresoDescuento().getIdTipoValor().getIdTipoValor().equals(TipoValor.PORCENTAJE_FIJO.getValue())) {
                                        if (bd.compareTo(new BigDecimal("100")) == 1) {
                                            observacion = observacion.concat(" El monto es de tipo valor porcentaje fijo y es mayor a 100 : " + celda.toString());
                                            error = Boolean.TRUE;
                                        } else {
                                            monto = bd;
                                        }
                                    } else {
                                        monto = bd;
                                    }
                                }
                            } catch (Exception ex) {
                                observacion = observacion.concat(" Sucedio un error al convertir el valor de la segunda celda a un monto valido: " + celda.toString());
                                error = Boolean.TRUE;
                            }
                        } else if (!cargaMasiva.getIdTipoCarga().getIdTipoCarga().equals(TipoCarga.ELIMINACION.getValue()) && contadorColumna == 3) {
                            try {
                                String observaciones = null;
                                if (celda.getCellType() == Cell.CELL_TYPE_STRING) {
                                    observaciones = celda.getStringCellValue();
                                } else if (celda.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    observaciones = toText(celda.getNumericCellValue());
                                } else {
                                    observacion = observacion.concat(" La tercer celda no es de tipo texto o número: " + celda.toString());
                                    error = Boolean.TRUE;
                                }
                                if (observaciones != null) {

                                    if (observaciones.length() > 2000) {
                                        observacion = observacion.concat(" El tamaño de las observaciones es mayor a 2000 ");
                                        error = Boolean.TRUE;
                                    } else {
                                        observacionesDetalle = observaciones;
                                    }
                                }
                            } catch (Exception ex) {
                                observacion = observacion.concat(" Sucedio un error al convertir el valor de la segunda celda a un monto valido: " + celda.toString());
                                error = Boolean.TRUE;
                            }
                        }
                    }
                    
                    if (celdaVacia) {
                        continue;
                    }
                    
                    totalRegistros++;
                    NpDetalleCargaMasiva detalle = new NpDetalleCargaMasiva();
                    detalle.setIdEmpleado(idEmpleado);
                    detalle.setMonto(monto);
                    detalle.setActivo(true);
                    
                    if (!error) {
                        totalExitosos++;
                    }
                    if (detalle.getMonto() == null || error) {
                        detalle.setMonto(new BigDecimal("0.00"));
                    } else {
                        montoTotalExitoso = montoTotalExitoso.add(detalle.getMonto());
                    }
                    detalle.setIdCargaMasiva(cargaMasiva);
                    detalle.setError(error);
                    detalle.setFila(totalRegistros);
                    if (observacion != null && !observacion.isEmpty()) {
                        detalle.setObservaciones(observacion);
                    }
//                    if (!celdaVacia) {
                        listaDetalle.add(detalle);
//                    }
//                }
            }
            cargaMasiva.setCantidadDetallesValidos(totalExitosos);
            cargaMasiva.setCantidadDetallesRegistrados(totalRegistros);
            cargaMasiva.setCantidadDetallesEnArchivo(totalFilas);
            cargaMasiva.setNpDetalleCargaMasivaList(listaDetalle);
            montoTotalExitoso = montoTotalExitoso.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            log.debug("Monto exitoso: " + montoTotalExitoso);
            cargaMasiva.setMontoTotalExitosos(montoTotalExitoso);
            cargaMasiva.setTotalRegistros(totalRegistros);
            cargaMasiva.setTotalExitosos(totalExitosos);
            workbook.close();
            return new Response<>(cargaMasiva, ResponseStatus.OK_INSERT, "Carga masiva registrada exitosamente");
        } catch (NullPointerException npe) {
            processException(npe);
            context.setRollbackOnly();
            return new Response<>(ResponseStatus.ERROR, "Sucedio un error con informacion nula");
        } catch (ConstraintViolationException ex) {
            String validationError = getConstraintViolationExceptionAsString(ex);
            log.error(validationError);
            context.setRollbackOnly();
            return new Response<>(ResponseStatus.ERROR_PARAMS, "Sucedio un error con la informacion: " + validationError);
        } catch (Exception ex) {
            processException(ex);
            context.setRollbackOnly();
            return new Response<>(ResponseStatus.ERROR, "Sucedio un error: " + ex.getMessage());
        }

    }


}
