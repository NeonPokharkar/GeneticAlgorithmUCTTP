import java.util.*;
public class Population {
	public ArrayList<Schedule> p;
	Population(ArrayList<Schedule> p)
	{
		this.p=p;
		Collections.sort(this.p);
	}
	public void evolve()
	{
		ArrayList<Schedule> ch=RouletteWheel();
		p.addAll(p.get(0).crossover(ch.get(0), ch.get(3)));
		p.addAll(p.get(0).crossover(ch.get(1), ch.get(2)));
		p.remove(4);
		p.remove(4);
		p.remove(4);
		p.remove(4);
		p.get(4).mutate();
		p.get(5).mutate();
		p.get(6).mutate();
		p.get(7).mutate();
		Collections.sort(p);
	}
	public double state()
	{
		System.out.println(p.get(0).getFitnessValue());
		return p.get(0).getFitnessValue();
	}
	public void print()
	{
		p.get(0).printAll();
	}
	public double[] getFitnessProbabilities(ArrayList<Schedule> test)
	{
		double[] soln=new double[test.size()];
		for(int i=0;i<test.size();i++)
		{
			soln[i]=test.get(i).getFitnessValue();
		}
		double total=0;
		for(int i=0;i<soln.length;i++)
		{
			total+=soln[i];
		}
		for(int i=0;i<soln.length;i++)
		{
			soln[i]/=total;
		}
		for(int i=1;i<soln.length;i++)
		{
			soln[i]+=soln[i-1];
		}
		return soln;
	}
	public ArrayList<Schedule> RouletteWheel()
	{
		ArrayList<Schedule> soln=new ArrayList<Schedule>();
		int[] chosen= {-1,-1,-1,-1};
		Random r=new Random();
		double[] probs=getFitnessProbabilities(p);
		while(soln.size()<4)
		{
			double d=r.nextDouble();
			int z=0;
			for(int i=1;i<probs.length;i++)
			{
				if((probs[i-1]<d)&&(probs[i]>d))
				{
					z=i;
				}
			}
			boolean todo=true;
			for(int i=0;i<chosen.length;i++)
			{
				if(chosen[i]==z)
				{
					todo=false;
				}
			}
			if(todo)
			{
				soln.add(p.get(z));
				chosen[soln.size()-1]=z;
			}
		}
		return soln;
	}
}