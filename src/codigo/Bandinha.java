package codigo;

import javax.sound.midi.*;

public class Bandinha {
    private final Parser[] parsers;
    private final Synthesizer sintetizador;
    private final MidiChannel[] canais;
    private final Instrument[] instrumentos;
    private volatile int canaisTocando;

    public Bandinha(int duracaoBase, String... partituras) throws MidiUnavailableException {
        parsers = new Parser[partituras.length];

        for (int i = 0; i < partituras.length; i++) {
            parsers[i] = new Parser(partituras[i], duracaoBase);
        }

        sintetizador = MidiSystem.getSynthesizer();

        sintetizador.open();

        // Carrega todos os instrumentos padrão do sistema
        instrumentos = sintetizador.getDefaultSoundbank().getInstruments();

        //for(int i=0; i<instrumentos.length;i++) {
        //    System.out.println(instrumentos[i]);
        //}

        // Descobre quantos canais o sintetizador suporta
        // (quanto mais canais, mais notas/instrumentos simultâneos
        // podem ser tocados)
        canais = sintetizador.getChannels();

        if (canais.length < partituras.length) {
            throw new RuntimeException("Há mais partituras do que canais");
        }

        sintetizador.loadAllInstruments(sintetizador.getDefaultSoundbank());

    }

    private void tocar(int canal) {
        try {
            Token token;
            int instAtual = 0;

            canais[canal].programChange(instrumentos[0].getPatch().getProgram());

            while ((token = parsers[canal].proximoToken()) != null) {
                System.out.print(token);

                if (!token.isPausa()) {
                    if (token.getInstrumento() >= 0 && token.getInstrumento() != instAtual) {
                        instAtual = token.getInstrumento();
                        canais[canal].programChange(instrumentos[instAtual].getPatch().getProgram());
                    }
                    canais[canal].noteOn(token.getNota(), 100);
                }

                Thread.sleep(token.getDuracao());

                if (!token.isPausa()) {
                    canais[canal].noteOff(token.getNota());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tocar() throws InterruptedException {
        final Thread[] threads = new Thread[parsers.length];
        canaisTocando = parsers.length;
        for (int i = 0; i < parsers.length; i++) {
            final int canalAtual = i;
            threads[i] = new Thread() {
                @Override
                public void run() {
                    tocar(canalAtual);
                    synchronized (threads) {
                        canaisTocando--;
                        threads.notifyAll();
                    }
                }
            };
        }
        for (int i = 0; i < parsers.length; i++) {
            threads[i].start();
        }
        synchronized (threads) {
            while (canaisTocando > 0) {
                threads.wait();
            }
        }
        Thread.sleep(1000);
    }
}
