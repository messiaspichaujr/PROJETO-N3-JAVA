// Valida o título, autor e categoria do livro antes de salvar no banco

package service;

import dao.LivroDAO;
import dominio.Livro;

import java.sql.SQLException;
import java.util.List;

public class BibliotecaService {
    private LivroDAO livroDAO;

    public BibliotecaService(LivroDAO livroDAO) {
        this.livroDAO = livroDAO;
    }
    
    public void salvarLivro(Livro livro) throws SQLException {
    	
        // Validação antes de salvar
    	
        if (livro.getTitulo() == null || livro.getTitulo().isEmpty()) {
        	
            throw new IllegalArgumentException("O título do livro não pode estar vazio.");
        }
        if (livro.getAutor() == null) {
            throw new IllegalArgumentException("O livro deve ter um autor associado.");
        }
        if (livro.getCategoria() == null) {
            throw new IllegalArgumentException("O livro deve ter uma categoria associada.");
        }

        livroDAO.salvar(livro);
    }

    public List<Livro> listarLivros() throws SQLException {
    	
        return livroDAO.listar();
    }
}
