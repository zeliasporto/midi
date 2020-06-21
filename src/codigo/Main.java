package codigo;

import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.*;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        try {
            Object[] options = {
                    "Tocar música pronta",
                    "Criar música própria"
            };

            int op = JOptionPane.showOptionDialog(null, "Escolha o que deseja fazer", "Música",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[0]);

            if (op == 0) {
                musicaPronta();
            } else {
                musicaPropria();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void musicaPronta() {
        Object[] possibleValues = {
                "Titanic",
                "Don't start now",
                "Addams family theme",
                "Megalovania",
                "Amor de que"
        };
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Escolha a música", "Música",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);

        int t1 = parseInt(JOptionPane.showInputDialog("Digite o tempo: \nSugestões \nTitanic -> 700\n" +
                "Don't start now -> 300\n" +
                "The addams family theme -> 200\n" +
                "Megalovania -> 250"));

        String[][] musicas = {
                {
                        "f5f5f5f5e5f5_f5e5f5_g5A5_G5_F5f5f5f5e5f5_g5D5_C5_f5g5A5_a5g5f5g5C6_A5d6c6C6a5G5_f5f5f5f5e5f5_g5D5_C5"
                },
                {
                        "a5a5A5a5_C6_A5__G5_F5__c5c5d5f5g5A5C5c5_a5a5A5a5_C6_A5__G5_F5_c5c5d5f5g5A5C5c5__c5f5e5d5d5_E5d5_f5e5d5d5_G5F5a5g5f5F5G5f5g5A5"
                },
                {
                        "f5g5a5a5#______g5a5b5c6_______g5a5b5c6__g5a5b5c6__f5g5a5a5#_______f5_a5#D6_a5#_g5_e5C6_a5#A5C6A5_f5_D5a5#_f5_a5#D6A5#g5_e5C6_a5#_a5F5_g5A5A5#",
                        "f5[123]g5a5a5#______g5a5b5c6_______g5a5b5c6__g5a5b5c6__f5g5a5a5#_______f5_a5#D6_a5#_g5_e5C6_a5#A5C6A5_f5_D5a5#_f5_a5#D6A5#g5_e5C6_a5#_a5F5_g5A5A5#"
                },
                {
                        "d5d5d6_A5_g5#_g5_F5d5f5g5c5c5d6_A5_g5#_g5_F5d5f5g5a4a4d6_A5_g5#_g5_F5d5f5g5g4#g4#d6_A5_g5#_g5_F5d5f5g5",
                },
                {
                        "d5 f5 _ c6\n" +
                                "/ essa é uma música de que!\n" +
                                "c6 a5 a5 c6 a5 a5 g5 _ f5 c6 a5 a5 _\n" +
                                "/ sério!\n" +
                                "a5 c6 a5 g5 f5"
                }
        };
        //"eeEe_G1_E__D_CggacdEGg_eeEe_G1__ED_C_ggacdEGg__gcbaa_Ca_gcaa_DCedgGDcdE",

        Hashtable<String, Integer> m = new Hashtable<>();
        m.put("Titanic", 0);
        m.put("Don't start now", 1);
        m.put("Addams family theme", 2);
        m.put("Megalovania", 3);
        m.put("Amor de que", 4);

        tocar(t1, musicas[m.get(selectedValue)]);
    }

    private static void musicaPropria() {
        ArrayList<String> partituras = new ArrayList<String>();
        String partitura = "";
        JTextField titulo = new JTextField();
        JTextField t1 = new JTextField();
        int t2 = 0;

        Object[] criacao = {
                "Título da música:", titulo,
                "Tempo base da música:", t1,
        };

        int option = JOptionPane.showConfirmDialog(null, criacao, "Input", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            JTextField nota = new JTextField();
            Object[] simnao = {"Não", "Sim"};
            JTextField t = new JTextField();
            JTextField inst = new JTextField();
            JTextField oitava = new JTextField();
            JComboBox<Object> sust = new JComboBox<Object>(simnao);
            Object[] message = {
                    "Nota:", nota,
                    "Oitava da nota:", oitava,
                    "A nota é sustenida?", sust,
                    "Se não for o padrão, digite a duração da nota:", t,
                    "Se for mudar o instrumento, digite o código dele: ", inst
            };

            Object[] opcao = {"Cancelar", "Preview", "Finalizar", "Próxima"};
            Object[] c = {"Sim", "Não"};
            String text = t1.getText();
            t2 = Integer.parseInt(text);
            int opt = 3;
            FOR: for (;;) {
                opt = JOptionPane.showOptionDialog(null, message, "Música",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION,
                        null, opcao, opcao[0]);
                switch (opt) {
                    case 1:
                        tocar(t2, partitura);
                        break;
                    case 2:
                    case 3:
                        partitura += nota.getText();
                        partitura += oitava.getText();
                        if (sust.getSelectedItem().equals("Sim")) {
                            partitura += "#";
                        }
                        if (!t.getText().equals("")) {
                            partitura += "(" + t.getText() + ")";
                        }
                        if (!inst.getText().equals("")) {
                            partitura += "[" + inst.getText() + "]";
                        }
                        if (opt == 3) {
                            break;
                        }
                        int cc = JOptionPane.showOptionDialog(null, "Deseja adicionar uma partitura secundária?", "Música",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, c, c[0]);
                        partituras.add(partitura);
                        partitura = "";
                        if (cc == 1)
                            break FOR;
                        break;
                    default:
                        return;
                }
            }
            tocar(t2, partituras.toArray(new String[partituras.size()]));
        }
    }

    private static void tocar(int duracaoBase, String... partituras) {
        try {
            Bandinha bandinha = new Bandinha(duracaoBase, partituras);
            bandinha.tocar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

