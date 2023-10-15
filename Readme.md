# Progresso
- Essa é uma barra de progresso já montada na GUI, ao usar o contrutor *Progresso p = new Progresso()*  e *p.mostrar(String título)* mostra a janela com o título passado no parâmetro;
- Serve para mostrar o progresso de execução em tarefas concorrentes (ou seja, fora da Event Dispatcher Thread);
- Tem uma variável interna que incrementa a barra com o método *p.setProgresso(int x)* em qualquer passo, e se o x for omitido é ++;
    - Se por acaso o final for zero ou negativo, ou progresso > final o *isIndeterminate* se torna *true*;
    - É possível alterar as cores dentro da barra passando *java.awt.Color* como parâmetros adicionais: *Progresso p = new Progresso(int x, Color foreground, Color background)*
- Tem também o método *p.seFull()* que traz a barra para 100% independentemente do contador
- O método *p.endGame("String text")* termina a barra com um delay de 1s com um texto opcional ou simplesmente o 100% da barra e destrói o objeto.