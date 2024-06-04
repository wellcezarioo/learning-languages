/* resolution of question number 51
link: https://www.thehuxley.com/problem/51?quizId=8833*/

#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdlib.h>

int main() 
{
	int code, quantity;
	double valor;
	scanf("%d%d", &code, &quantity);
	if (code == 1)
	{
		valor = 5.3;
	}
	else if (code == 2)
	{
		valor = 6.0;
	}
	else if (code == 3)
	{ 
		valor = 3.2;	
	}
	else if (code == 4)
	{
		valor = 2.5;	
	}
	
	if (quantity >= 15 || (valor*quantity) >= 40)
	{
		valor = 0.85*(valor*quantity);
	}
	else
	{
		valor = valor*quantity;
	}
	
	printf("R$ %.2lf\n", valor);
	return 0;
}
