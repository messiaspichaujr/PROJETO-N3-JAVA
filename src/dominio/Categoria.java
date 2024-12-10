// A classe Categoria representa uma categoria de livros. Ela possui um identificador (id) e um nome.
// A classe é responsável por associar livros a diferentes tipos ou gêneros, como "Ficção", "Não-ficção", "Tecnologia", etc.
// Os métodos getters e setters são usados para acessar e modificar os dados da categoria.

package dominio;

public class Categoria {
    private int id;
    private String nome;

    public Categoria() {}

    public Categoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
