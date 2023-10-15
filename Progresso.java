package dados;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;



/*Classe para uma barra de progresso já encaixada num JFrame, tornado possível
* várias JProgressBar executando a sua tarefa sem interferir uma na outra
* e inclusive com cores diferentes tanto na barra quanto na String
*
* Class to a progress bar fitted into a JFrame, making it possible to have
* many JProgressBar executing its task without interfering with each other
* and inclusive with differing colors both on the bar and on the String
*
*
* Como usar:	Instancie Progresso na thread da tarefa longa 
*                (não no thread padrão de eventos) assim
*		exemplo1: Progresso dog = new Progresso
*                               (100, Color.BLUE, Color.GRAY);
*		exemplo2: Progresso mcdonalds = new Progresso
*                   (arrayList.size(), Color.RED, Color.YELLOW);
*		Se não quiser alterar as cores, apenas inicialize com o número 
*               limite. Exemplo:  Progresso padrao = new Progresso(100);
*
*		Ou use a barra em modo indetermidado, colocando 0 ou um número 
*                   negativo ou nada no construtor:
*		exemplo:  Progresso ind = new Progresso
*                           (-10, Color.BLACK, Color.WHITE);
*		ATENÇÃO:  Não use o endGame() com JProgressBars indeterminadas, 
*                           isso daria um loop infinito, use 
*                           endGameIndeterminate() no lugar deste.
*
*		Mostre a janela com uma String opcional qualquer que vai ser o 
*               título do JFrame:
*		exemplo1: dog.mostrar("Processando...");
*		exemplo2: ind.mostrar();
*
*		Atualize a barra chamando dog.setProgresso(); atualiza de 1 em 1.
*		Ou coloque um número inteiro para progredir a barra de outra forma.
*		Não é necessário criar outro iterador, use o que tiver na sua tarefa.
*		exemplo: for(contador = 0; contador <= 100; contador++)
*                           {//qualquer coisa que seja para processar
*                            dog.setProgresso();
*                           }
*		OBS: a barra alerta se o progresso passar do limite máximo...
*
*		ao término da tarefa, chame o p.endGame() para dar o dispose() 
*               na janela da JProgressBar
*		Opcional: use uma String no dog.endGame("Terminou"), esta será 
*               aplicada na JProgressBar
*		exemplo: for(contador = 0; contador <= 100; contador++)
*				{//qualquer coisa que seja para processar
* 				 dog.setProgresso();
*				}
*			 dog.endGame("au, au");
*
*
* How to use:	Initialize Progresso in the long executing task thread 
*               (not on the event dispatch thread) like this
*			example1: Progresso dog = new Progresso
*                                   (100, Color.BLUE, Color.GRAY);
*			example2: Progresso mcdonalds = new Progresso
*                                   (arrayList.size(), Color.RED, Color.YELLOW);
*		If you don't want to change the colors, just initialize with the 
*               end point
*		example: Progresso default = new Progresso(100);
*
*		or use the bar in indeterminate mode, putting 0 or a negative 
*               number or nothing in the contructor:
*		Example: Progresso ind = new Progresso
*                               (-10, Color.BLACK, Color.WHITE);
*		WARNING: Don't use endGame() for indeterminate JProgressBars, 
*                        it would give a forever loop,
*			 use endGameIndeterminate() instead.
*
*		Show the window with an optional String, if any is given, to be 
*               the window's title
*		example1: dog.mostrar("Processing...");
*		example2: ind.mostrar();
*
*		Update the bar by calling dog.setProgresso(); 	that updates 1 by 1.
*		Or input an integer number on that method to set progress the 
*               way you see fit.
*		It ain't necessary to create another iterator, just use that 
*               what exists on your task.
*		example: for(contador = 0; contador <= 100; contador++)
*				{//qualquer coisa que seja para processar
* 				 dog.setProgresso();
*				}
*
*		PS: the bar alerts if you have gone out of bounds...
*
*		At ending of the task, call dog.endGame() to dispose() the 
*               JProgressBar's window
*		Optional: use an String on dog.endGame("Finished"), that will be 
*               set on the JProgressBar
*		example: for(contador = 0; contador <= 100; contador++)
*				{//wathever to be processed
* 				 dog.setProgresso();
*				}
*			 dog.endGame("woof, woof");
*
*
*
* Feito no Brasil por Vinícius
* Made in Brazil by Vinícius
*/

public class Progresso
{   
	public JFrame frame;
    public JProgressBar barra;
    public int progresso = 0;
    private int full = 0;
    public boolean isIndeterminate = false;

    public Progresso()
	/* Cria uma barra de progresso sem término determinado
	 * 
	 */
    {
		barra = new JProgressBar();
        barra.setIndeterminate(true);
    }


    public Progresso(int end)
	{
		barra = new JProgressBar();

        if(end <= 0)
		{
			barra.setIndeterminate(true);
		}
        else
		{
			this.full = end;
			barra.setMinimum(0);
            barra.setMaximum(end);
            barra.setValue(0);
            barra.setStringPainted(true);
            barra.setString("0,00%");
        }
	}


    public Progresso(int end, Color fgd, Color bkgd)
	{
		/* Cria uma barra de progresso com fim definido e a posibilidade de alterar as cores da barra e do texto exibido
		 * @param end {@code end} é o término da barra a ser incrementado com setProgresso() no loop da tarefa
		 * @param fgd {@code fgd} é a cor do texto a ser exibido
		 * @param bkgd {@code bkgd} é a cor da barra a ser exibida
		 */
		UIManager.put("ProgressBar.selectionBackground", fgd);
        UIManager.put("ProgressBar.selectionForeground", bkgd);
        UIManager.put("ProgressBar.foreground", fgd);
        UIManager.put("ProgressBar.background", bkgd);

        barra = new JProgressBar();

        if(end <= 0)
		{
			barra.setIndeterminate(true);
        }

        else
		{
			this.full = end;
			barra.setMinimum(0);
            barra.setMaximum(end);
            barra.setValue(0);
            barra.setStringPainted(true);
            barra.setString("0,00%");   			       
        }
		this.mostrar();
	}


    public synchronized void mostrar(String texto)
	{
		frame = new JFrame(texto);
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(400, 55));
		JPanel p = new JPanel(new GridLayout(1, 1));
		p.add(barra);
		frame.add(p);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


    public synchronized void mostrar()
	{
		frame = new JFrame("Aguarde...");
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(400, 55));
		JPanel p = new JPanel(new GridLayout(1, 1));
		p.add(barra);
		frame.add(p);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


    public synchronized void setFull(int qtd)
	{
		full = 0;
		full = qtd;
	}


    public synchronized void setFull()
    {
		if(isIndeterminate == false)
        {
			progresso = full;
        }
    }
    
	
    public void endGame()
	{
		/*
		 * Este método termina imediatamente a barra de progresso
		 */
		if(progresso == full && isIndeterminate == false)
	 	{
			barra.setString("100.00%");
            try
			{
				Thread.sleep(1000);
            }catch(Exception ignore)
			{
				new Info().exception(ignore);
			}
			frame.dispose();
			progresso = 0;
	 	}
	}


    public void endGame(String texto)
	{
		/*
		 * Este método termina imediatamente a barra de progresso, com um texto personalizado a ser exibido
		 * @param texto {@code texto} é a saída personalizada na barra de progresso
		 */
		if(isIndeterminate == false && progresso == full)
	 	{
			barra.setString(texto);
			try
			{
				Thread.sleep(1000);
			}catch(Exception ignore)
			{
				new Info().exception(ignore);
			}
			frame.dispose();
			progresso = 0;
	 	} 
		else   
		{
			endGameIndeterminate();
        }
	}
    

    public void endGame(String texto, int tempo)
	{
		/* Este método termina imediatamente a barra de progresso, com um texto personalizado a ser exibido com tempo de espera
		 * @param texto {@code texto} é a saída personalizada na barra de progresso
		 * @param tempo {@code tempo} é a quantidade em milissegundos para a janela esperar antes de fechar
		 */
		if(isIndeterminate == false)            
		{
			if(progresso != full)
				progresso = full;

			barra.setString(texto);
			try
			{
				Thread.sleep(tempo);
			}catch(Exception ignore)
			{
				
			}
			frame.dispose();
			progresso = 0;
		}
        else
		{
			endGameIndeterminate(texto, tempo);
        }
	}


    public synchronized void endGameIndeterminate()
	{
		/* Este método termina imediatamente a barra de progresso que iniciou sem contador 
		 */
		frame.dispose();
		progresso = 0;
	}


    public synchronized void endGameIndeterminate(String texto)
	{
		/* Este método termina imediatamente a barra de progresso que iniciou sem contador com texto personalizado
		 * @param texto {@code texto} é a saída personalizada
		 */
		barra.setStringPainted(true);
		barra.setString(texto);
		try
		{
			Thread.sleep(1000);
		}catch(Exception ignore)
		{
			new Info().exception(ignore);
		}
		frame.dispose();
		progresso = 0;
	}
    

    public synchronized void endGameIndeterminate(String texto, int tempo)
	{
		/* Este método termina imediatamente a barra de progresso que iniciou sem contador com texto personalizado e tempo definido
		 * @param texto {@code texto} é a saída personalizada
		 * @param tempo {@code tempo} é a quantidade de milissegundos que a janela vai esperar antes de fechar
		 */
		barra.setStringPainted(true);
		barra.setString(texto);
		try
		{
			Thread.sleep(tempo);
		}
		catch(Exception ignore)
		{
			ignore.printStackTrace();
		}
		frame.dispose();
		progresso = 0;
	}


    public synchronized void setProgresso()
	{
		/* Este método incrementa o contador da barra de progresso de 1 em 1
		 * 
		 */
		this.progresso ++;
		if(progresso < full)
		{
			barra.setValue(progresso);
			barra.setString(String.format("%.2f", barra.getPercentComplete() * 100) + "%");
			barra.repaint();
		}	
		else 
		{
			if(progresso == full)
			{
				barra.setValue(full);
			}
			else
			{
				barra.setString("Sobrecarga / Overload");		
				//Isso não deveria acontecer / That's not meant to happen
				barra.setStringPainted(true);
				endGame();
			}
		}
	}


    public synchronized void setProgresso(int qtd)
	{
		/* Este método incrementa o contador da barra de progresso em uma quantidade qualquer
		 * @param qtd {@code qtd} é o passo que será incrementado na barra
		 */
		this.progresso += qtd;
	 	if(progresso < full)
		{
			barra.setValue(progresso);
			barra.setString(String.format("%.2f", barra.getPercentComplete() * 100) + "%");
			barra.repaint();
		}	
		else 
		{
			if(progresso == full)
			{
				barra.setValue(full);
			}
			else
			{
				barra.setString("Sobrecarga / Overload");		
				//Isso não deveria acontecer / That's not meant to happen
				barra.setStringPainted(true);
				endGameIndeterminate();
			}
		}
	}
}