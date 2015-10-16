package main;

import enty.Cruce;
import enty.Descarga;
import enty.Estado;
import enty.Multa;
import enty.TipoCruce;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Conexion;
import util.Dates;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Sql extends util.Sql {

    public Sql(Conexion conexion) throws SQLException {
        super(conexion);
    }

    public static boolean insertMultas(List<Multa> list) {
        Sql bd;
        Multa aux;
        Iterator<Multa> it = list.iterator();

        try {
            bd = new Sql(Variables.con);

            while (it.hasNext()) {
                aux = it.next();
                bd.ejecutar(aux.SQLCrear());
            }

            bd.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static void setEstadoDescarga(int id, Estado estado) {
        Sql bd;
        String query = "UPDATE datagest.descarga SET estadoCruce=" + estado.getValue() + " WHERE idDescarga=" + id;

        try {
            bd = new Sql(Variables.con);
            bd.ejecutar(query);
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean guardarBoletin(String idEdicto, String datos) {
        Sql bd;
        try {
            bd = new Sql(Variables.con);
            bd.ejecutar("UPDATE datagest.descarga SET "
                    + "datos=" + Varios.entrecomillar(datos) + " "
                    + "where idDescarga="
                    + "(select idDescarga from datagest.edicto where "
                    + "idEdicto=" + Varios.entrecomillar(idEdicto) + ")");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static List<Descarga> listaBoe(String query) {
        List<Descarga> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Descarga aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Descarga();
                aux.setId(rs.getInt("idDescarga"));
                aux.setCodigo(rs.getString("idEdicto"));
                aux.setFecha(rs.getDate("fecha"));
                aux.setCsv(rs.getString("csv"));
                aux.setDatos(rs.getString("datos"));
                aux.setEstado(rs.getInt("estadoCruce"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Multa> listaMulta(String query) {
        List<Multa> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Multa aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Multa();
                aux.setId(rs.getInt("id"));
                aux.setFechaPublicacion(Dates.imprimeFecha(rs.getDate("fechaPublicacion")));
                aux.setCodigoBoletin(rs.getString("codigoEdicto"));
                aux.setExpediente(rs.getString("expediente"));
                aux.setFechaMulta(rs.getString("fechaMulta"));
                aux.setNif(rs.getString("nif"));
                aux.setMatricula(rs.getString("matricula"));
                aux.setLineaQuery(rs.getString("linea"));

                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static List<Cruce> listaCruce(TipoCruce tipo, Date fecha) {
        List<Cruce> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Cruce aux;
        String query="";
        
        switch(tipo){
            case TESTRA:
                query="SELECT * FROM datagest.cruce WHERE fechaPublicacion="+Varios.entrecomillar(Dates.imprimeFecha(fecha));
                break;
            case IDBL:
                query="SELECT * FROM historico.cruce WHERE fechaPublicacion="+Varios.entrecomillar(Dates.imprimeFecha(fecha));
                break;
        }

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Cruce();
                aux.setId(rs.getInt("id"));
                aux.setFechaPublicacion(fecha);
                aux.setExpediente(rs.getString("expediente"));
                aux.setNif(rs.getString("nif"));
                aux.setMatricula(rs.getString("matricula"));
                aux.setFechaMulta(rs.getString("fechaMulta"));
                aux.setLinea(rs.getString("linea"));
                aux.setTipo(tipo);
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
