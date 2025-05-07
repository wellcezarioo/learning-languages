import easyaccept.EasyAccept;

public class Main {
    public static void main(String[] args) {
        // Executa os testes us1_1.txt até us9_2.txt
        for (int i = 1; i <= 9; i++) {
            EasyAccept.main(new String[]{
                    "br.ufal.ic.p2.jackut.Facade",
                    "tests/us" + i + "_1.txt"
            });
            EasyAccept.main(new String[]{
                    "br.ufal.ic.p2.jackut.Facade",
                    "tests/us" + i + "_2.txt"
            });
        }
    }
}
