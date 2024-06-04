# question link: https://www.thehuxley.com/problem/4280

# classe passageiro

class passageiro:
    def __init__(self, rg: str, nasc: str, passagem: str, p_nasc: str, assento: str):
        self.__rg = rg
        self.__nasc = nasc
        self.passagem = passagem
        self.__p_nasc = p_nasc
        self._assento = assento

    # metodos getters

    def get_rg(self):
        return self.__rg

    def get_nasc(self):
        return self.__nasc

    def get_passagem(self):
        return self.passagem

    def get_p_nasc(self):
        return self.__p_nasc

    def get_assento(self):
        return self._assento

    # metodos comuns

    def ver_rg(self):
        return self.__rg == "RG"

    def ver_passagem(self):
        return self.passagem == "Passagem"

    def ver_data_nasc(self):
        return self.__nasc == self.__p_nasc
        
def verificar_passagens(passageiros):
    for p in passageiros:
        if not p.ver_rg():
            print("a saida e nessa direção")
        elif not p.ver_passagem():
            print("a recepição e nessa direção")
        elif not p.ver_data_nasc():
            print("190")
        else:
            print("o seu assento e " + p.get_assento() + " tenha um bom dia")

def ler_passageiros():
    qntd = int(input())
    passageiros = []
    for _ in range(qntd):
        rg = input().strip()
        nasc = input().strip()
        passagem = input().strip()
        p_nasc = input().strip()
        assento = input().strip()
        passageiros.append(passageiro(rg, nasc, passagem, p_nasc, assento))

    verificar_passagens(passageiros)

ler_passageiros()
