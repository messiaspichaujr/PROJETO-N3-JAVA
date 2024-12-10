// A classe Autor representa um autor de livros. Ela contém os atributos básicos para identificar o autor,
// como id, nome e data de nascimento. 
// Também possui métodos de acesso (getters e setters) para manipulação desses dados.

package dominio;

public class Autor {
    private int id;
    private String nome;
    private String dataNascimento; 

    // Construtor vazio
    public Autor() {}
    
    // Construtor com parâmetros
    public Autor(int id, String nome, String dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    // Getters e setters
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

    public String getDataNascimento() {
        return dataNascimento; // Atualizado para retornar String
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento; // Atualizado para aceitar String
    }
}
