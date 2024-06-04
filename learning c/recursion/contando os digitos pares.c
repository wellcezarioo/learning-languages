#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdlib.h>

int Pares(int num) 
{
    if (num == 0) 
    {
        return 0;
    } 
    else 
    {
        int ultimoDigito = num % 10;
        int restante = num / 10;
        
        if (ultimoDigito % 2 == 0) 
        {
            return 1 + Pares(restante);
        } 
        else 
        {
            return Pares(restante);
        }
    }
}

int main() {
    int numero;
    scanf("%d", &numero);
    
    int DigitosPares = Pares(numero);
    printf("%d", DigitosPares);
	return 0;
}
