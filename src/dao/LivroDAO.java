// Classe responsável pela interação com o banco de dados para a entidade Livro

package dao;

import dominio.Autor;
import dominio.Categoria;
import dominio.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
	
    private Connection connection;

    public LivroDAO(Connection connection) {
        this.connection = connection;
    }
 
 // Regra de Negócio 1: "Não permitir adicionar livros com a mesma combinação de título e autor."
    public boolean verificarDuplicidade(String titulo, Autor autor) throws SQLException {
    	
        String sql = "SELECT COUNT(*) FROM Livro WHERE titulo = ? AND idAutor = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setInt(2, autor.getId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    
    public void salvar(Livro livro) throws SQLException {
        String sql = "INSERT INTO Livro (titulo, anoPublicacao, idAutor, idCategoria) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setInt(2, livro.getAnoPublicacao());
            stmt.setInt(3, livro.getAutor().getId());
            stmt.setInt(4, livro.getCategoria().getId());
            stmt.executeUpdate();
        }
    }

    public List<Livro> listar() throws SQLException {
    	
        String sql = "SELECT l.*, a.nome AS autorNome, c.nome AS categoriaNome " +
                     "FROM Livro l " +
                     "INNER JOIN Autor a ON l.idAutor = a.id " +
                     "INNER JOIN Categoria c ON l.idCategoria = c.id";
        List<Livro> livros = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Autor autor = new Autor(rs.getInt("idAutor"), rs.getString("autorNome"), null);
                Categoria categoria = new Categoria(rs.getInt("idCategoria"), rs.getString("categoriaNome"));
                Livro livro = new Livro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getInt("anoPublicacao"),
                    autor,
                    categoria
                );
                livros.add(livro);
            }
        }
        return livros;
    }

    public void atualizar(Livro livro) throws SQLException {
        String sql = "UPDATE Livro SET titulo = ?, anoPublicacao = ?, idAutor = ?, idCategoria = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setInt(2, livro.getAnoPublicacao());
            stmt.setInt(3, livro.getAutor().getId());
            stmt.setInt(4, livro.getCategoria().getId());
            stmt.setInt(5, livro.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM Livro WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
