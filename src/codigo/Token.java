package codigo;

public class Token {
    private final String codigo;
    private final int nota;
    private final int duracao;
    private int instrumento;

    public Token(String codigo, int nota, int duracao) {
        this.codigo = codigo;
        this.nota = nota;
        this.duracao = duracao;
        instrumento=-1;
    }

    public Token(String codigo, int nota, int duracao, int instrumento) {
        this.codigo = codigo;
        this.nota = nota;
        this.duracao = duracao;
        this.instrumento = instrumento;
    }

    @Override
    public String toString() {
        return codigo;
    }

    public boolean isPausa() {
        return (nota < 0);
    }

    public int getNota() {
        return nota;
    }

    public int getDuracao() {
        return duracao;
    }

    public int getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(int instrumento) {
        this.instrumento = instrumento;
    }
}
