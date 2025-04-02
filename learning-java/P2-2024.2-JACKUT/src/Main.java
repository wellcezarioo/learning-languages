import easyaccept.EasyAccept;


public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            String[] args2 = { "br.ufal.ic.p2.jackut.Facade", "tests/us" + ( i + 1 ) + "_1.txt" };
            String[] args3 = { "br.ufal.ic.p2.jackut.Facade", "tests/us" + ( i + 1 ) + "_2.txt" };
            EasyAccept.main(args2);
            EasyAccept.main(args3);
        }
    }
}