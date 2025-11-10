package DAO;

import Conexiones.ConexionMySQL;
import Conexiones.ConexionPostgreSQL;

import java.sql.*;

public class FuncionesCombinadasDAO {

    public static void listarTratamientosConEspecialidadesYMedicos() {
        System.out.println("\n*** Listado completo de tratamientos ***\n");

        String sqlPostgre = """
                            select t.id_tratamiento, e.nombre_especialidad, m.nombre_medico
                            from hospital.tratamientos t
                            left join hospital.especialidades e
                            on t.id_especialidad = e.id_especialidad
                            left join hospital.medicos m ON t.id_medico = m.id_medico
                            order by t.id_tratamiento
                            """;
        String sqlMySQL = """
                            select id_tratamiento, nombre_tratamiento, descripcion
                            from tratamientos
                            order by id_tratamiento
                            """;
        try (
                Connection connPostgre = ConexionPostgreSQL.getInstancia().getConexion();
                PreparedStatement psPostgre = connPostgre.prepareStatement(sqlPostgre);
                ResultSet rsPostgre = psPostgre.executeQuery();

                Connection connMySQL = ConexionMySQL.getInstancia().getConexion();
                PreparedStatement psMySQL = connMySQL.prepareStatement(sqlMySQL);
                ResultSet rsMySQL = psMySQL.executeQuery()
        ) {

            while (rsPostgre.next() && rsMySQL.next()) {
                int idTratamientoPostgre = rsPostgre.getInt("id_tratamiento");
                int idTratamientoMysql = rsMySQL.getInt(("id_tratamiento"));

                if (idTratamientoMysql == idTratamientoPostgre) {
                    String nombre = rsMySQL.getString("nombre_tratamiento");
                    String descripcion = rsMySQL.getString("descripcion");
                    String especialidad = rsPostgre.getString("nombre_especialidad");
                    String medico = rsPostgre.getString("nombre_medico");

                    System.out.print("\n          ID: " + idTratamientoMysql +
                            "\n Tratamiento: " + nombre +
                            "\n Descripción: " + descripcion +
                            "\nEspecialidad: " + especialidad +
                            "\n      Médico: " + medico +
                            "\n-------------------------------------------");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los tratamientos: " + e.getMessage());
        }
    }
}
