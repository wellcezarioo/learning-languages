/* question link: https://www.thehuxley.com/problem/2331 */

#include <stdio.h>
#include <stdlib.h>
#define TAM 10

void bubble_sort_recursivo(int array[], int indice_atual, int organizando){
    
    if(organizando == 0){
        return;
    }
    else{
        if(indice_atual == organizando){
            bubble_sort_recursivo(array, 0, organizando - 1);
            return;
        }
        if(array[indice_atual] > array[indice_atual + 1] && indice_atual + 1 <= organizando){
            int temp = array[indice_atual];
            array[indice_atual] = array[indice_atual + 1];
            array[indice_atual + 1] = temp;
        }
        bubble_sort_recursivo(array, indice_atual + 1,organizando);
    }
    return;

}

void contar_frequencia(int *v, int number, int *indice, int tam,int *qntd){
    // v esta ordenado, se o v[*indice] for maior que number, então não há mais number no array
    // se indice == tam, então ja percorremos todo o array
    if(number < v[(*indice)] || (*indice) == tam){
        return;
    }
    else{
        // se number == v[indice], então achamos mais um number no array
        if(number == v[(*indice)]){ 
            (*qntd)++;
        }
        // independente de acharmos um number, precisamos andar pelo array
        (*indice) += 1;
        return contar_frequencia(v, number, indice, tam, qntd);
    }
    return;

}

int resultado(int *v, int indice, int tam){
    if(indice == tam){ 
        return 0;
    }
    else{
        int qntd = 0, number = v[indice];
        contar_frequencia(v, number, &indice, tam, &qntd);
        if(qntd == number) return 1;
        return resultado(v, indice, tam);
    }
}

void ler_r(int * v, int r, int lendo){
    if(lendo == r) return;
    scanf("%d", v + lendo);
    ler_r(v, r, lendo + 1);
    return;
}

void ler_vetor(int *vetor, int indice_atual){
    if(indice_atual == TAM) return;
    scanf("%d", vetor + indice_atual);
    ler_vetor(vetor, indice_atual + 1);
    return;
}

void ler_matriz(int matriz[TAM][TAM], int vetor_atual){
    if(vetor_atual == TAM) return;
    ler_vetor(matriz[vetor_atual], 0);
    ler_matriz(matriz, vetor_atual + 1);
}

int pode_visitar(int matriz[TAM][TAM], int visitados[TAM][TAM], int x, int y ){
    return x >= 0 && x < TAM && y >= 0 && y < TAM && visitados[x][y] == 0 && matriz[x][y] == 1;
}

int fuga(int matriz[TAM][TAM], int visitados[TAM][TAM], int i, int j){
    if(visitados[TAM - 1][TAM - 1] == -1) return 1;

    if(!pode_visitar(matriz, visitados, i, j)) return 0;
    visitados[i][j] = -1;

    // linha reta
    if(pode_visitar(matriz, visitados, i + 1, j)){
        i += 1;
    }
    else if(fuga(matriz, visitados, i, j + 1)){
        j += 1;
    }
    else if(fuga(matriz, visitados, i - 1, j)){
        i -= 1;
    }
    else if(fuga(matriz, visitados, i, j - 1)){
        j -= 1;
    }
    else{
        return 0;
    }
    return fuga(matriz, visitados, i, j);

}

int main(){
    int r;
    scanf("%d", &r);
    int *v = (int *) malloc(r * sizeof(int));
    ler_r(v, r, 0);
    bubble_sort_recursivo(v,0, r - 1);
    int matriz[TAM][TAM];
    int visitados[TAM][TAM] = {0};
    ler_matriz(matriz, 0);
    if(resultado(v,0,r)){
        printf("Vamos nessa, temos que sair rapido...\n");
        if(fuga(matriz, visitados, 0, 0)){
            printf("Tudo nosso.\n");
        }
        else{
            printf("Continuem cavando!\n");
        }
    }
    else{
        printf("Ja nao se pode confiar nos refens como antigamente...\n");
    }
    return 0;
}