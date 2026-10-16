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
import java.util.Scanner;


public class Aplicacao {

    private final Scanner scanner = new Scanner(System.in);
    private final Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        new Aplicacao().executar();
    }

    public void executar() {
        int opcao = -1;
        do {
            exibirMenu();
            try {
                opcao = lerInteiro("Escolha uma opção: ");
                tratarOpcao(opcao);
            } catch (NumberFormatException e) {                   // RN15
                System.out.println("Entrada inválida. Digite um número inteiro.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
        scanner.close();
    }

    private void exibirMenu() {
        System.out.println();
        System.out.println("===== BIBLIOTECA COMUNITÁRIA =====");
        System.out.println(" 1 - Cadastrar leitor");
        System.out.println(" 2 - Consultar leitor");
        System.out.println(" 3 - Listar leitores");
        System.out.println(" 4 - Atualizar leitor");
        System.out.println(" 5 - Remover leitor");
        System.out.println(" 6 - Cadastrar livro");
        System.out.println(" 7 - Consultar livro");
        System.out.println(" 8 - Listar livros");
        System.out.println(" 9 - Atualizar livro");
        System.out.println("10 - Remover livro");
        System.out.println("11 - Registrar empréstimo");
        System.out.println("12 - Listar empréstimos");
        System.out.println("13 - Registrar devolução");
        System.out.println(" 0 - Sair");
    }

    private void tratarOpcao(int opcao) {
        switch (opcao) {
            case 1: cadastrarLeitor(); break;
            case 2: consultarLeitor(); break;
            case 3: listarLeitores(); break;
            case 4: atualizarLeitor(); break;
            case 5: removerLeitor(); break;
            case 6: cadastrarLivro(); break;
            case 7: consultarLivro(); break;
            case 8: listarLivros(); break;
            case 9: atualizarLivro(); break;
            case 10: removerLivro(); break;
            case 11: registrarEmprestimo(); break;
            case 12: listarEmprestimos(); break;
            case 13: registrarDevolucao(); break;
            case 0: System.out.println("Aplicação encerrada."); break;
            default: System.out.println("Opção inválida.");
        }
    }

    // ------------------------------------------------------------------
    // Leitores
    // ------------------------------------------------------------------

    private void cadastrarLeitor() {
        int codigo = lerInteiro("Código: ");
        String nome = lerTexto("Nome: ");
        String email = lerTexto("E-mail: ");
        biblioteca.cadastrarLeitor(codigo, nome, email);
        System.out.println("Leitor cadastrado com sucesso.");
    }

    private void consultarLeitor() {
        Leitor leitor = biblioteca.buscarLeitor(lerInteiro("Código do leitor: "));
        System.out.println(leitor != null ? leitor : "Registro não encontrado.");
    }

    private void listarLeitores() {
        List<Leitor> leitores = biblioteca.listarLeitores();
        if (leitores.isEmpty()) {
            System.out.println("Nenhum leitor cadastrado.");
            return;
        }
        for (Leitor leitor : leitores) {
            System.out.println(leitor);
        }
    }

    private void atualizarLeitor() {
        int codigo = lerInteiro("Código do leitor: ");
        String nome = lerTexto("Novo nome: ");
        String email = lerTexto("Novo e-mail: ");
        biblioteca.atualizarLeitor(codigo, nome, email);
        System.out.println("Leitor atualizado com sucesso.");
    }

    private void removerLeitor() {
        biblioteca.removerLeitor(lerInteiro("Código do leitor: "));
        System.out.println("Leitor removido com sucesso.");
    }

    // ------------------------------------------------------------------
    // Livros
    // ------------------------------------------------------------------

    private void cadastrarLivro() {
        int codigo = lerInteiro("Código: ");
        String titulo = lerTexto("Título: ");
        String autor = lerTexto("Autor: ");
        biblioteca.cadastrarLivro(codigo, titulo, autor);
        System.out.println("Livro cadastrado com sucesso.");
    }

    private void consultarLivro() {
        Livro livro = biblioteca.buscarLivro(lerInteiro("Código do livro: "));
        System.out.println(livro != null ? livro : "Registro não encontrado.");
    }

    private void listarLivros() {
        List<Livro> livros = biblioteca.listarLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    private void atualizarLivro() {
        int codigo = lerInteiro("Código do livro: ");
        String titulo = lerTexto("Novo título: ");
        String autor = lerTexto("Novo autor: ");
        biblioteca.atualizarLivro(codigo, titulo, autor);
        System.out.println("Livro atualizado com sucesso.");
    }

    private void removerLivro() {
        biblioteca.removerLivro(lerInteiro("Código do livro: "));
        System.out.println("Livro removido com sucesso.");
    }

    // ------------------------------------------------------------------
    // Empréstimos
    // ------------------------------------------------------------------

    private void registrarEmprestimo() {
        int codigo = lerInteiro("Código do empréstimo: ");
        int codigoLeitor = lerInteiro("Código do leitor: ");

        ArrayList<Integer> codigosLivros = new ArrayList<>();
        System.out.println("Informe os códigos dos livros, um por vez (0 para concluir).");
        while (true) {
            int codigoLivro = lerInteiro("Código do livro: ");
            if (codigoLivro == 0) {
                break;
            }
            codigosLivros.add(codigoLivro);
        }

        biblioteca.registrarEmprestimo(codigo, codigoLeitor, codigosLivros);
        System.out.println("Empréstimo registrado com sucesso.");
    }

    private void listarEmprestimos() {
        imprimirEmprestimos("--- Empréstimos abertos ---", biblioteca.listarEmprestimos(true));
        imprimirEmprestimos("--- Empréstimos finalizados ---", biblioteca.listarEmprestimos(false));
    }

    private void imprimirEmprestimos(String titulo, List<Emprestimo> lista) {
        System.out.println(titulo);
        if (lista.isEmpty()) {
            System.out.println("(nenhum)");
            return;
        }
        for (Emprestimo emprestimo : lista) {
            System.out.println(emprestimo);
        }
    }

    private void registrarDevolucao() {
        biblioteca.registrarDevolucao(lerInteiro("Código do empréstimo: "));
        System.out.println("Devolução registrada com sucesso.");
    }

    // ------------------------------------------------------------------
    // Leitura de entradas
    // ------------------------------------------------------------------

    /** Lê um inteiro. Lança NumberFormatException se o texto não for um número. */
    private int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
}