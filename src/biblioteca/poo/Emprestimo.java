/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.poo;

/**
 *
 * @author diego
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Emprestimo {

    private final int codigo;
    private final LocalDate dataEmprestimo;
    private boolean aberto;
    private final Leitor leitor;
    private final ArrayList<ItemEmprestimo> itens;

    public Emprestimo(int codigo, Leitor leitor) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("O código deve ser um inteiro positivo.");
        }
        if (leitor == null) {
            throw new IllegalArgumentException("O leitor do empréstimo é obrigatório.");
        }
        this.codigo = codigo;
        this.leitor = leitor;
        this.dataEmprestimo = LocalDate.now();
        this.aberto = true;
        this.itens = new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public boolean isAberto() {
        return aberto;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public List<ItemEmprestimo> getItens() {
        return Collections.unmodifiableList(itens);
    }

    /** Indica se o livro já faz parte deste empréstimo (RN09). */
    public boolean contemLivro(Livro livro) {
        for (ItemEmprestimo item : itens) {
            if (item.getLivro() == livro) {
                return true;
            }
        }
        return false;
    }

    /** Indica se o livro está neste empréstimo e ainda não foi devolvido (RN13). */
    public boolean possuiLivroPendente(Livro livro) {
        for (ItemEmprestimo item : itens) {
            if (item.getLivro() == livro && !item.isDevolvido()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adiciona um livro ao empréstimo e o torna indisponível.
     * Retorna false se o livro for nulo, repetido (RN09) ou indisponível (RN08).
     */
    public boolean adicionarLivro(Livro livro) {
        if (!aberto || livro == null || contemLivro(livro) || !livro.isDisponivel()) {
            return false;
        }
        livro.emprestar();
        itens.add(new ItemEmprestimo(livro));
        return true;
    }

    /** Devolve todos os livros e encerra o empréstimo (RN11 e RN12). */
    public void finalizar() {
        if (!aberto) {
            throw new IllegalStateException("O empréstimo " + codigo + " já foi finalizado.");
        }
        for (ItemEmprestimo item : itens) {
            item.registrarDevolucao();
        }
        aberto = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Empréstimo ").append(codigo)
          .append(" | ").append(aberto ? "ABERTO" : "FINALIZADO")
          .append(" | data: ").append(dataEmprestimo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
          .append("\n  Leitor: ").append(leitor.getCodigo()).append(" - ").append(leitor.getNome())
          .append("\n  Livros:");
        for (ItemEmprestimo item : itens) {
            sb.append("\n    ").append(item);
        }
        return sb.toString();
    }
}