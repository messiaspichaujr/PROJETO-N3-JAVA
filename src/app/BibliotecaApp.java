//responsável pelo fluxo e é a classe principal.

package app;

import dao.AutorDAO;
import dao.CategoriaDAO;
import dao.LivroDAO;
import dominio.Autor;
import dominio.Categoria;
import dominio.Livro;
import service.BibliotecaService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BibliotecaApp {
    
	public static void main(String[] args) {
		
		//conexão com o banco
    	try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "")) {
    		
    		// criação das instâncias das classes DAO responsáveis pela persistência dos dados no banco
            AutorDAO autorDAO = new AutorDAO(connection);
            CategoriaDAO categoriaDAO = new CategoriaDAO(connection);
            LivroDAO livroDAO = new LivroDAO(connection);
            BibliotecaService bibliotecaService = new BibliotecaService(livroDAO);

            Scanner scanner = new Scanner(System.in);
            int opcao;
            
            do {
                exibirMenu();
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> cadastrarAutor(scanner, autorDAO);
                    case 2 -> cadastrarCategoria(scanner, categoriaDAO);
                    case 3 -> cadastrarLivro(scanner, bibliotecaService, autorDAO, categoriaDAO);
                    case 4 -> listarLivros(bibliotecaService);
                    case 0 -> System.out.println("Saindo do sistema...");
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } while (opcao != 0);

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
    
	//menu para o user
    private static void exibirMenu() {
        
    	System.out.println("\n=== Sistema de Biblioteca ===");
        System.out.println("1. Cadastrar Autor");
        System.out.println("2. Cadastrar Categoria");
        System.out.println("3. Cadastrar Livro");
        System.out.println("4. Listar Livros");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarAutor(Scanner scanner, AutorDAO autorDAO) throws SQLException {
        
    	System.out.print("Digite o nome do autor: ");
        String nome = scanner.nextLine();

        String dataNascimento = null;
        while (dataNascimento == null) {
            System.out.print("Digite a data de nascimento do autor (YYYY-MM-DD): ");
            String entradaData = scanner.nextLine();

            // Regra de negócio de validação de data
            if (isDataValida(entradaData)) {
                dataNascimento = entradaData;
            } else {
                System.out.println("Formato de data inválido. Por favor, insira no formato YYYY-MM-DD.");
            }
        }

        Autor autor = new Autor(0, nome, dataNascimento);
        autorDAO.salvar(autor);
        System.out.println("Autor cadastrado com sucesso!");
    }
    
    // metodo para a validação de data, isDataValida
    
    private static boolean isDataValida(String data) {
        
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try {
            format.parse(data); // tenta converter a data para o formato correto
            return true;
        } catch (ParseException e) {
            return false; // retorna false se a data for inválida
        }
    }

    private static void cadastrarCategoria(Scanner scanner, CategoriaDAO categoriaDAO) throws SQLException {
        
    	System.out.print("Digite o nome da categoria: ");
        String nome = scanner.nextLine();

        Categoria categoria = new Categoria(0, nome);
        categoriaDAO.salvar(categoria);
        System.out.println("Categoria cadastrada com sucesso!");
    }

    private static void cadastrarLivro(Scanner scanner, BibliotecaService bibliotecaService, AutorDAO autorDAO, CategoriaDAO categoriaDAO) throws SQLException {
        
    	System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();
        System.out.print("Digite o ano de publicação: ");
        int anoPublicacao = scanner.nextInt();
        scanner.nextLine();

        // Regra de negócio: Verifica se o título do livro não está vazio
        
        if (titulo == null || titulo.isEmpty()) {
            System.out.println("O título do livro não pode estar vazio.");
            return;
        }

        System.out.println("Autores disponíveis:");
        List<Autor> autores = autorDAO.listar();
        for (Autor autor : autores) {
            System.out.println(autor.getId() + " - " + autor.getNome());
        }
        
        //relação de classe com o autor, solicitação de id
        System.out.print("Escolha o ID do autor: ");
        
        int idAutor = scanner.nextInt();
        scanner.nextLine();
        Autor autor = autorDAO.buscarPorId(idAutor);
        
        if (autor == null) {
            System.out.println("Erro: Autor com ID " + idAutor + " não encontrado.");
            return;
        }

        System.out.println("Categorias disponíveis:");
        List<Categoria> categorias = categoriaDAO.listar();
        for (Categoria categoria : categorias) {
            System.out.println(categoria.getId() + " - " + categoria.getNome());
        }
        
        System.out.print("Escolha o ID da categoria: ");
        int idCategoria = scanner.nextInt();
        scanner.nextLine();
        Categoria categoria = categoriaDAO.buscarPorId(idCategoria);
        
        if (categoria == null) {
            System.out.println("Erro: Categoria com ID " + idCategoria + " não encontrada.");
            return;
        }

        Livro livro = new Livro(0, titulo, anoPublicacao, autor, categoria);
        
        bibliotecaService.salvarLivro(livro);
        
        System.out.println("Livro cadastrado com sucesso!");
    }

    private static void listarLivros(BibliotecaService bibliotecaService) throws SQLException {
    	
        System.out.println("\n=== Lista de Livros ===");
        
        List<Livro> livros = bibliotecaService.listarLivros();
        
        for (Livro livro : livros) {
        	
            System.out.println(livro.getId() + " - " + livro.getTitulo() +
                    " (" + livro.getAnoPublicacao() + ") - Autor: " +
                    livro.getAutor().getNome() + ", Categoria: " +
                    livro.getCategoria().getNome());
        }
    }
}
