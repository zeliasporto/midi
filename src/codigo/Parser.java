package codigo;

import java.util.Hashtable;

import static java.lang.Integer.parseInt;

public class Parser {
    private final String codigo;
    private final int duracaoBase;
    private int posicao;
    private final Hashtable<Character, Integer> tabela;

    public Parser(String codigo, int duracaoBase) {
        this.codigo = codigo;
        this.duracaoBase = duracaoBase;
        posicao = 0;

        tabela = new Hashtable<>();
        tabela.put('_', -1);
        tabela.put('c', 0);
        tabela.put('d', 2);
        tabela.put('e', 4);
        tabela.put('f', 5);
        tabela.put('g', 7);
        tabela.put('a', 9);
        tabela.put('b', 11);
    }

    private boolean consumirComentario(char n) {
        if (n != '/') {
            return false;
        }

        while (posicao < codigo.length() && (n = codigo.charAt(posicao)) != '\n') {
            posicao++;
        }

        return true;
    }

    public Token proximoToken() {
        int posicaoInicial;
        char n;
        Token token;

        do {
            if (posicao >= codigo.length()) {
                return null;
            }
            do {
                posicaoInicial = posicao;
                n = codigo.charAt(posicao);
                posicao++;
            } while (Character.isWhitespace(n) && posicao < codigo.length());

            if (posicao >= codigo.length()) {
                return null;
            }
        } while (consumirComentario(n));

        int duracao = duracaoBase;
        if (Character.isUpperCase(n)) {
            duracao *= 2;
            n = Character.toLowerCase(n);
        }

        int nota;

        try {
            nota = tabela.get(n);
        } catch (Exception e) {
            throw new RuntimeException("Nota inválida: " + n);
        }

        if (nota < 0) {
            return new Token(codigo.substring(posicaoInicial, posicao), nota, duracao);
        }

        if (posicao >= codigo.length()) {
            throw new RuntimeException("Falta dígito modificador");
        }

        n = codigo.charAt(posicao);
        posicao++;

        if (n < '0' || n > '9') {
            throw new RuntimeException("Modificador não era um número válido: " + n);
        }

        //nota += (12 * Integer.parseInt(String.valueOf(n)));
        nota += (12 * (n - '0'));

        if (posicao < codigo.length()) {
            if (codigo.charAt(posicao) == '#') {
                posicao++;
                nota++;
            }
        }

        if (posicao < codigo.length()) {
            if (codigo.charAt(posicao) == '(') {
                String durac="";
                posicao++;
                for(; codigo.charAt(posicao)!= ')';posicao++){
                    durac+=String.valueOf(codigo.charAt(posicao));
                }
                posicao++;
                duracao=parseInt(durac);
            }
            if (codigo.charAt(posicao) == '[') {
                String inst="";
                posicao++;
                for(; codigo.charAt(posicao)!= ']';posicao++){
                    inst+=String.valueOf(codigo.charAt(posicao));
                }
                posicao++;
                int instrumento = parseInt(inst);
                token = new Token(codigo.substring(posicaoInicial, posicao), nota, duracao, instrumento);
            } else {
                token = new Token(codigo.substring(posicaoInicial, posicao), nota, duracao);
            }
        } else {
            token = new Token(codigo.substring(posicaoInicial, posicao), nota, duracao);
        }
        return token;
    }
}

