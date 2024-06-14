# Programação orientada a objetos

# visibilidade:
"""visibilidade, em uml são representadas por +, - e #
+: público, todos podem mexer
-: privado, só eu (a classe) posso mexer
#: protegido, só a mãe ( a classe) e os seus filhos (subclasses) podem mexer"""

# Classe: uma fôrma

# Atributos, Metodos e estado
"""todo objeto precisa ter caracteristicas, comportamentos e estado, 
as caracteriscas, em poo, são chamadas de atributos. os comportamentos, de metodos
e o estado, permanece com esse nome"""

"""podemos também lembrar da definição como, os atributos sendo o que o objeto é
os metodos, o que ele faz e o estado, como ele está"""


class Notebook:
    # Atributos: como o objeto é

    # Método construtor:

    """permite que a classe receba parâmetros e inicialize os atributos
    de instância ao criar um novo objeto."""

    def __init__(self, marca, bateria, status):
        # atributos de intância: especifico para os objetos da classe
        self.marca = marca
        self.bateria = bateria
        self.status = status

    # atributos de classe: comum para todos os objetos da classe
    eletronico = True

    # metodos, o que o objeto faz

    '''metodos especiais, getters e setters são modificadores, que podem acessar e modificar
     atributos privados e protegidos, desde que a classe tenha acesso a esses atributos'''

    # modificadores especiais
    def get_status(self):
        print('o notebook esta ligado\n' if self.status else 'o notebook está desligado!\n')

    def set_status(self, status):
        if status and self.status:
            print('o notebook já está ligado!\n')
            return
        elif not status and not self.status:
            print('o notebook já está desligado!\n')
            return
        elif status:
            print('ligando o notebook\n')
        elif not status:
            print('desligando o notebook\n')
        self.status = status

    def get_marca(self):
        print(f'a marca do notebook é {self.marca}\n')

    def get_bateria(self):
        print(f'a bateria esta em {self.bateria}% atualmente\n')

    # o estado de um objeto se refere a como esle esta em determinado momento


def novo_notebook():
    print('qual a marca do notebook?\n')
    marca = input()
    print('qual a porcentagem atual da bateria?\n')
    bateria = int(input())

    return Notebook(marca, bateria, False)


meu_notebook = novo_notebook()
meu_notebook.set_status(True)
meu_notebook.get_marca()
meu_notebook.get_bateria()

# pilares da programação:
'''os três pilares da programação orientada a objetos São: o encapsulamento, a herança e o polimorfismo'''
# alguns autores consideram a abstração com um pilar, mas a abstração pode estar em encapsulamento também

# encapsulamento:
'''encapsular é ocultar partes independentes da implementação, permitindo construir partes
invisíveis ao mundo exterior'''

'''o encapsulamento permite a abstração, segurança, reutiliazação do código, flexibilidade e 
controle de acesso'''

# inteface:
'''lista de serviços fornecidos por um componente. 
É o contato com o mundo exterior, que define o que pode ser feito
com um objeto dessa classe.'''
"""a interface é compostas pelos metodos públicos"""


class Lutador:
    # metodo construtor

    def __init__(self, nome, nacionalidade, idade, altura, peso, vitorias,
                 derrotas, empates):
        self.__categoria = None
        self.__peso = None
        self.__nome = nome
        self.__nacionalidade = nacionalidade
        self.__idade = idade
        self.__altura = altura
        self.set_peso(peso)  # atribui o peso e define a categoria
        self.__vitorias = vitorias
        self.__derrotas = derrotas
        self.__empates = empates

    # metodos
    def apresentar(self):
        print(f'nome: {self.get_nome()}\n')
        print(f'nacionalidade: {self.get_nacionalidade()}\n')
        print(f'idade: {self.get_idade()} anos\n')
        print(f'altura: {self.get_altura():.2}\n')
        print(f'peso: {self.get_peso():.2} KG\n')
        print(f'ganhou: {self.get_vitorias()} lutas\n')
        print(f'perdeu: {self.get_derrotas()} lutas\n')
        print(f'empatou: {self.get_empates()} lutas\n')

    def status(self):
        print(10 * "-=-")
        print(" status ")
        print(20 * "-=-")
        print('\n')
        print(f'nome: {self.get_nome()}\n')
        print(f'categoria: peso {self.get_categoria()}\n')
        print(f'ganhou: {self.get_vitorias()} lutas\n')
        print(f'perdeu: {self.get_derrotas()} lutas\n')
        print(f'empatou: {self.get_empates()} lutas\n')
        print(20 * "-=-")
        print('\n')

    def ganhar_luta(self):
        self.__vitorias += 1

    def perder_luta(self):
        self.__derrotas += 1

    def empatar_luta(self):
        self.__empates += 1

    # metodos especiais

    def get_nome(self):
        return self.__nome

    def set_nome(self, nome):
        self.__nome = nome

    def get_nacionalidade(self):
        return self.__nacionalidade

    def set_nacionalidade(self, nacionalidade):
        self.__nacionalidade = nacionalidade

    def get_idade(self):
        return self.__idade

    def set_idade(self, idade):
        self.__idade = idade

    def get_altura(self):
        return self.__altura

    def set_altura(self, altura):
        self.__altura = altura

    def get_peso(self):
        return self.__peso

    def set_peso(self, peso):
        self.__peso = peso
        self.__set_categoria(peso)

    def get_categoria(self):
        return self.__categoria

    def __set_categoria(self, peso):
        if 52.2 <= peso <= 70.3:
            self.__categoria = "Leve"
        elif peso <= 83.9:
            self.__categoria = "Médio"
        elif peso <= 120.2:
            self.__categoria = "Pesado"
        else:
            self.__categoria = "Invalido"

    def get_vitorias(self):
        return self.__vitorias

    def set_vitorias(self, vitorias):
        self.__vitorias = vitorias

    def get_derrotas(self):
        return self.__derrotas

    def set_derrotas(self, derrotas):
        self.__derrotas = derrotas

    def get_empates(self):
        return self.__empates

    def set_empates(self, empates):
        self.__empates = empates


def novo_lutador():
    nome = input("qual o nome do lutador?\n")
    nacionalidade = input("qual a nacionalidade do lutador?\n")
    idade = int(input("qual a idade do lutador?\n"))
    altura = float(input("qual a altura do lutador?\n"))
    peso = float(input("qual o peso do lutador?\n"))
    vitorias = int(input("quantas vitorias o lutador tem?\n"))
    derrotas = int(input("quantas derrotas o lutador tem?\n"))
    empates = int(input("quantos empates o lutador tem?\n"))

    return Lutador(nome, nacionalidade, idade, altura, peso, vitorias,
                   derrotas, empates)


class Luta:
    def __init__(self, desafiante, desafiado, rounds, aprovada):
        self.__desafiante = desafiante
        self.__desafiado = desafiado
        self.__rounds = rounds
        self.__aprovada = aprovada

    # metodos
    """def marcar_luta(self):
    def lutar(self):"""

    # metodos especias

    def get_desafiante(self):
        return self.__desafiante

    def set_desafiante(self, desafiante):
        self.__desafiante = desafiante

    def get_desafiado(self):
        return self.__desafiado

    def set_desafiado(self, desafiado):
        self.__desafiado = desafiado

    def get_rounds(self):
        return self.__rounds

    def set_rounds(self, rounds):
        self.__rounds = rounds

    def get_aprovada(self):
        return self.__aprovada

    def set_aprovada(self, aprovada):
        self.__aprovada = aprovada


# herança:

"""permite basear uma nova classe na definição de 
uma outra classe previamente existente. as heranças serão 
aplicadas tanto para carcteristicas, quanto para comportamentos"""

"""a classe que vai passar as caracteristicas e comportamentos é chamada
de classe mãe, progenitora ou superclasse. a classe que vai receber 
é chamada de classe filha ou subclasse"""

"""o encapsulamento e a herança são pilares independentes um do outro"""

# Arvore de heranças:

"""podemos criar uma arvore de heranças com as nossas classes, a primeiro progenitora é a raiz
e as classes que não possui filhas são as folhas"""

# tipos de herança:

# herança de implementação ou herança pobre:
"""quando uma classe apenas herda atributos e metodos de sua 
superclasse e não adiciona nenhum atributo ou metodo novo"""

# herança por diferença ou por adição ou extensão:
"""quando uma classe herda atributos e metodos da sua 
superclasse e adiciona atributos ou metodos novos"""

# generalização e especialização:

"""quando criamos uma classe filha dizemos que a nova classe é uma especialização da classe mãe,
quando nos referimos a uma superclasse dizemos que ela é uma generalização daquela filha"""

# classes e metodos abstratos e finais:

# classe abstrata:

"""uma classe abstrata não pode ser instanciada, ou seja, só pode servir como progenitora,
no entanto, nós podemos instanciar as classes filhas das classes abstratas"""

# metodo abstrato:
"""um método abstrato não é implementado na sua classe progenitora, ele é apenas declarado e
obrigatoriamente implementado em todas as classes filhas. 
se uma classe tem algum método abstrato, então ela é abstrata"""

# classe final:
"""uma classe final não pode ser herdada, ela é obrigatoriamente uma folha"""

# metodo final:
"""não pode ser sobrescrito, ele é obrigatóriamente herdado"""


class Pessoa:

    def __init__(self, nome, idade, sexo):
        # atributos

        self.__nome = nome
        self.__idade = idade
        self.__sexo = sexo

    # metodos

    def fazer_aniversario(self):
        self.__idade += 1

    # metodos especiais

    def get_nome(self):
        return self.__sexo

    def set_nome(self, nome):
        self.__nome = nome

    def get_idade(self):
        return self.__idade

    def set_idade(self, idade):
        self.__idade = idade

    def get_sexo(self):
        return self.__sexo

    def set_sexo(self, sexo):
        self.__sexo = sexo


# a nova classe aluno é uma subclasse de pessoa

class Aluno(Pessoa):

    def __init__(self, matricula, curso, nome, idade, sexo):
        # atributos

        # metodo construtor da superclasse
        super().__init__(nome, idade, sexo)
        self.__matricula = matricula
        self.__curso = curso

    # metodos

    def trancar_matricula(self):
        self.set_matricula(self.get_matricula() + " - trancada")

    # metodos especiais

    def get_matricula(self):
        return self.__matricula

    def set_matricula(self, matricula):
        self.__matricula = matricula

    def get_curso(self):
        return self.__curso

    def set_curso(self, curso):
        self.__curso = curso


# polimorfismo:
"""permite que um mesmo nome represente vários comportamentos diferentes"""

# tipos de polimorfismo:

# polimorfismo de sobreposição:
"""acontece quando substituimos um método de uma superclasse na sua subclasse, 
usando a mesma assinatura """

# polimorfismo de sobrecarga:
"""acontece quando uma mesma classe tem varios metodos com mesmo nome e assinaturas diferentes, 
diferentes numeros de parametros e tipos de parametros"""