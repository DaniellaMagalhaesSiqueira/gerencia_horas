# Aplicativo de gerenciamento de horas de funcionários em Java 17

##### Calcular
Calcula horas de uma folha de ponto com formatação específica (dia, dia da semana, primeira marcação, segunda marcação e pula linha)
O cálculo é feito a partir dos dados informados na tela como número de feriados, carga horária semanal e um texto contendo as informações da folha de ponto. As horas devidas são calculadas com base nos dias úteis menos feriados, vezes carga horaria semanal dividido por 5.
O resultado são horas cumpridas, horas devidas e banco de horas (negativo ou positivo)
##### Cadastrar
Cadastro de funcionários com nome, carga horária semanal, dias da semana em que trabalha e cor
##### Calendário
Calendário do mês e ano atual automaticamente gerado conforme data atual do sistema com datas seguidas do dia da semana e funcionários escalados para o trabalho naquele dia com sua cor cadastrada para gerar visualização rápida.

#### Bibliotecas:
Para aplicação desktop foram utilizadas as bibliotecas javafx 17 e controlx
Para o banco de dados foi utilizado o sqlite