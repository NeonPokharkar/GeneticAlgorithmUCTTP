import java.util.*;

public class Main{
	static Population pk;
	public static void main(String... args)
	{
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run()
			{
				System.out.print("Program Terminated");
				pk.print();
			}
		}, "Shutdown-thread"));
		new Main();
		int nst=3,nc=3,nt=25,nsu=15,maxclass=25;
		int[][] stos= {
				{0,3,0,7,0,0,1,0,0,5,0,0,0,0,9},
				{1,0,5,0,0,0,0,0,6,0,0,4,1,8,0},
				{0,0,0,0,7,3,0,7,0,0,8,0,0,0,0}
		};
		int[][] sutosu= {
				{1,0,0,0,0,0,0,0,0,0,0,0,1,1,0},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
				{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,1,0,0,0,0,0,0,1,0,0,0},
				{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
				{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
				{0,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
				{0,0,0,0,1,0,0,0,0,0,0,1,0,0,0},
				{1,0,0,0,0,0,0,0,0,0,0,0,1,1,0},
				{1,0,0,0,0,0,0,0,0,0,0,0,1,1,0},
				{0,0,0,0,0,0,1,0,0,0,0,0,0,0,1}
		};
		ArrayList<Schedule> p=new ArrayList<Schedule>();
		for(int i=0;i<8;i++)
		{
			Schedule s=new Schedule(nst,nc,nt,nsu,maxclass,stos,sutosu);
			p.add(s);
		}
		pk=new Population(p);
		while(pk.state()<1)
		{
			pk.evolve();
			pk.print();
		}
		pk.print();
	}
}
