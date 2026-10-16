/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.poo;

/**
 *
 * @author diego
 */

import java.util.ArrayList;
import java.util.List;


public class Biblioteca {

    private final ArrayList<Leitor> leitores = new ArrayList<>();
    private final ArrayList<Livro> livros = new ArrayList<>();
    private final ArrayList<Emprestimo> emprestimos = new ArrayList<>();

    // ------------------------------------------------------------------
    // Leitores
    // ------------------------------------------------------------------

    public void cadastrarLeitor(int codigo, String nome, String email) {
        if (buscarLeitor(codigo) != null) {                       // RN03
            throw new IllegalArgumentException("Já existe um leitor com o código " + codigo + ".");
        }
        leitores.add(new Leitor(codigo, nome, email));            // RN01 e RN02 validadas no construtor
    }

    /** Retorna o leitor ou null se não existir (RN14). */
    public Leitor buscarLeitor(int codigo) {
        for (Leitor leitor : leitores) {
            if (leitor.getCodigo() == codigo) {
                return leitor;
            }
        }
        return null;
    }

    public List<Leitor> listarLeitores() {
        return new ArrayList<>(leitores);
    }

    public void atualizarLeitor(int codigo, String nome, String email) {
        Leitor leitor = buscarLeitor(codigo);
        if (leitor == null) {
            throw new IllegalArgumentException("Leitor não encontrado.");
        }
        leitor.atualizarDados(nome, email);
    }

    public void removerLeitor(int codigo) {
        Leitor leitor = buscarLeitor(codigo);
        if (leitor == null) {
            throw new IllegalArgumentException("Leitor não encontrado.");
        }
        for (Emprestimo emprestimo : emprestimos) {               // RN13
            if (emprestimo.isAberto() && emprestimo.getLeitor() == leitor) {
                throw new IllegalStateException(
                        "Não é possível remover: o leitor possui empréstimo aberto.");
            }
        }
        leitores.remove(leitor);
    }

    // ------------------------------------------------------------------
    // Livros
    // ------------------------------------------------------------------

    public void cadastrarLivro(int codigo, String titulo, String autor) {
        if (buscarLivro(codigo) != null) {                        // RN04
            throw new IllegalArgumentException("Já existe um livro com o código " + codigo + ".");
        }
        livros.add(new Livro(codigo, titulo, autor));
    }

    /** Retorna o livro ou null se não existir (RN14). */
    public Livro buscarLivro(int codigo) {
        for (Livro livro : livros) {
            if (livro.getCodigo() == codigo) {
                return livro;
            }
        }
        return null;
    }

    public List<Livro> listarLivros() {
        return new ArrayList<>(livros);
    }

    public void atualizarLivro(int codigo, String titulo, String autor) {
        Livro livro = buscarLivro(codigo);
        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        livro.atualizarDados(titulo, autor);
    }

    public void removerLivro(int codigo) {
        Livro livro = buscarLivro(codigo);
        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        for (Emprestimo emprestimo : emprestimos) {               // RN13
            if (emprestimo.isAberto() && emprestimo.possuiLivroPendente(livro)) {
                throw new IllegalStateException(
                        "Não é possível remover: o livro está em um empréstimo aberto.");
            }
        }
        livros.remove(livro);
    }

    // ------------------------------------------------------------------
    // Empréstimos
    // ------------------------------------------------------------------

    /**
     * Registra um empréstimo. Todas as validações acontecem antes de alterar
     * qualquer livro, para que um erro não deixe livros marcados como emprestados
     * sem empréstimo correspondente.
     */
    public void registrarEmprestimo(int codigo, int codigoLeitor, List<Integer> codigosLivros) {
        if (codigo <= 0) {                                        // RN02
            throw new IllegalArgumentException("O código deve ser um inteiro positivo.");
        }
        if (buscarEmprestimo(codigo) != null) {                   // RN05
            throw new IllegalArgumentException("Já existe um empréstimo com o código " + codigo + ".");
        }
        Leitor leitor = buscarLeitor(codigoLeitor);
        if (leitor == null) {                                     // RN06
            throw new IllegalArgumentException("Leitor não encontrado. Cadastre-o antes de emprestar.");
        }
        if (codigosLivros == null || codigosLivros.isEmpty()) {   // RN07
            throw new IllegalArgumentException("O empréstimo deve conter ao menos um livro.");
        }

        ArrayList<Livro> selecionados = new ArrayList<>();
        for (int codigoLivro : codigosLivros) {
            Livro livro = buscarLivro(codigoLivro);
            if (livro == null) {
                throw new IllegalArgumentException("Livro " + codigoLivro + " não encontrado.");
            }
            if (selecionados.contains(livro)) {                   // RN09
                throw new IllegalArgumentException(
                        "O livro " + codigoLivro + " foi informado mais de uma vez.");
            }
            if (!livro.isDisponivel()) {                          // RN08
                throw new IllegalStateException(
                        "O livro " + codigoLivro + " (" + livro.getTitulo() + ") não está disponível.");
            }
            selecionados.add(livro);
        }

        Emprestimo emprestimo = new Emprestimo(codigo, leitor);
        for (Livro livro : selecionados) {
            emprestimo.adicionarLivro(livro);                     // RN10: torna o livro indisponível
        }
        emprestimos.add(emprestimo);
    }

    /** Retorna o empréstimo ou null se não existir (RN14). */
    public Emprestimo buscarEmprestimo(int codigo) {
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getCodigo() == codigo) {
                return emprestimo;
            }
        }
        return null;
    }

    public List<Emprestimo> listarEmprestimos(boolean abertos) {
        ArrayList<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.isAberto() == abertos) {
                resultado.add(emprestimo);
            }
        }
        return resultado;
    }

    public void registrarDevolucao(int codigoEmprestimo) {
        Emprestimo emprestimo = buscarEmprestimo(codigoEmprestimo);
        if (emprestimo == null) {
            throw new IllegalArgumentException("Empréstimo não encontrado.");
        }
        emprestimo.finalizar();                                   // RN11 e RN12
    }
}