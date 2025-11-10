package DAO;

import Conexiones.ConexionPostgreSQL;

import java.sql.*;

public class SalasTratamientosPostgreDAO {

    public static void listarTratamientosPorSala() {
        String sql = """
                    select s.nombre_sala as "nombre de la sala", count(st.id_tratamiento) as "cantidad de tratamientos"
                    from hospital.salas s
                    left join hospital.salas_tratamientos st
                    on s.id_sala = st.id_sala
                    group by "nombre de la sala"
                    order by "cantidad de tratamientos" desc
                    """;

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n*** Total de Tratamientos por sala ***");
            System.out.println("\nNombre de la sala\t Cantidad de tratamientos");
            while (rs.next()) {
                System.out.println("\t" + rs.getString("Nombre de la sala") + "        ->\t\t" + rs.getInt("Cantidad de tratamientos"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el total de las citas: " + e.getMessage());
        }
    }
}


