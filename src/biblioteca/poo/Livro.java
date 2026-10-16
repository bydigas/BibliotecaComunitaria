/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.poo;

/**
 *
 * @author diego
 */

public class Livro {

    private final int codigo;
    private String titulo;
    private String autor;
    private boolean disponivel;

    public Livro(int codigo, String titulo, String autor) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("O código deve ser um inteiro positivo.");
        }
        this.codigo = codigo;
        this.titulo = textoObrigatorio(titulo, "título");
        this.autor = textoObrigatorio(autor, "autor");
        this.disponivel = true;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    /**
     * Atualiza título e autor. Só altera o objeto se os dois valores forem válidos.
     */
    public void atualizarDados(String novoTitulo, String novoAutor) {
        String tituloValidado = textoObrigatorio(novoTitulo, "título");
        String autorValidado = textoObrigatorio(novoAutor, "autor");
        this.titulo = tituloValidado;
        this.autor = autorValidado;
    }

    /** Marca o livro como emprestado (RN08 e RN10). */
    public void emprestar() {
        if (!disponivel) {
            throw new IllegalStateException("O livro \"" + titulo + "\" não está disponível.");
        }
        disponivel = false;
    }

    /** Marca o livro como disponível novamente (RN11). */
    public void devolver() {
        disponivel = true;
    }

    private static String textoObrigatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("O campo " + campo + " é obrigatório.");
        }
        return valor.trim();
    }

    @Override
    public String toString() {
        return "Livro [código=" + codigo + ", título=" + titulo + ", autor=" + autor
                + ", " + (disponivel ? "disponível" : "emprestado") + "]";
    }
}