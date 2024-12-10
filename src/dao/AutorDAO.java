// Classe responsável pela interação com o banco de dados para a entidade Autor

package dao;

import dominio.Autor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {
    private Connection connection;

    public AutorDAO(Connection connection) {
        this.connection = connection;
    }
 
    public void salvar(Autor autor) throws SQLException {
        String sql = "INSERT INTO Autor (nome, dataNascimento) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, autor.getNome());
            stmt.setString(2, autor.getDataNascimento());
            stmt.executeUpdate();
        }
    }
    
    public Autor buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Autor WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Autor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("dataNascimento")
                    );
                }
            }
        }
        return null;
    }


    public List<Autor> listar() throws SQLException {
        String sql = "SELECT * FROM Autor";
        List<Autor> autores = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Autor autor = new Autor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("dataNascimento") 
                );
                autores.add(autor);
            }
        }
        return autores;
    }
}
