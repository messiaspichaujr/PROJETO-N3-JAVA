// A classe Livro representa um livro no sistema. Ela contém atributos como id, título, ano de publicação,
// além de referências para um Autor e uma Categoria. Isso permite associar um livro a um autor e a uma categoria específicos.
// Os métodos getters e setters permitem o acesso e a modificação dessas propriedades.

package dominio;

public class Livro {
    private int id;
    private String titulo;
    private int anoPublicacao;
    private Autor autor;
    private Categoria categoria;

    public Livro() {}

    public Livro(int id, String titulo, int anoPublicacao, Autor autor, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.autor = autor;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    } 
    
    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
