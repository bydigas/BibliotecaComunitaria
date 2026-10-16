/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.poo;

/**
 *
 * @author diego
 */

public class ItemEmprestimo {

    private final Livro livro;
    private boolean devolvido;

    public ItemEmprestimo(Livro livro) {
        if (livro == null) {
            throw new IllegalArgumentException("O livro do item é obrigatório.");
        }
        this.livro = livro;
        this.devolvido = false;
    }

    public Livro getLivro() {
        return livro;
    }

    public boolean isDevolvido() {
        return devolvido;
    }

    /** Registra a devolução do item e libera o livro (RN11). */
    public void registrarDevolucao() {
        if (!devolvido) {
            livro.devolver();
            devolvido = true;
        }
    }

    @Override
    public String toString() {
        return livro.getCodigo() + " - " + livro.getTitulo()
                + (devolvido ? " (devolvido)" : " (pendente)");
    }
}