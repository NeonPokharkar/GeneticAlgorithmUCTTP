import java.util.*;
public class Schedule implements Comparable<Schedule> {
	public int[][][][] sc;
	public int[][] stos,sutosu;
	public int mx,my,mz,mw,mcr;
	Schedule(int nst,int nc,int nt,int nsu,int maxclass,int[][] stos,int[][] sutosu)
	{
		Random r=new Random();
		sc=new int[nst][nc][nt][nsu];
		for(int i=0;i<nst;i++)
		{
			for(int j=0;j<nc;j++)
			{
				for(int k=0;k<nt;k++)
				{
					for(int l=0;l<nsu;l++)
					{
						sc[i][j][k][l]=r.nextInt(2);
					}
				}
			}
		}
		mx=nst;
		my=nc;
		mz=nt;
		mw=nsu;
		this.stos=stos;
		this.sutosu=sutosu;
		mcr=maxclass;
	}
	public boolean isInStudentsList(int student,int subject)
	{
		if(stos[student][subject]>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public int[] getClassesTogether(int subject)
	{
		ArrayList<Integer> subjectstogether=new ArrayList<Integer>();
		for(int i=0;i<sutosu[subject].length;i++)
		{
			if(sutosu[subject][i]>0)
			{
				subjectstogether.add(i);
			}
		}
		int[] soln=new int[subjectstogether.size()];
		for(int i=0;i<soln.length;i++)
		{
			soln[i]=subjectstogether.get(i).intValue();
		}
		return soln;
	}
	public int getNoOfClassesStudent(int student)
	{
		int sum=0;
		for(int i=0;i<stos[student].length;i++)
		{
			sum+=stos[student][i];
		}
		return sum;
	}
	public int getConstraint1Sum(int x,int z)
	{
		int sum=0;
		for(int i=0;i<my;i++)
		{
			for(int j=0;j<mw;j++)
			{
				sum+=sc[x][i][z][j];
			}
		}
		return sum;
	}
	public int getConstraint2Sum(int y,int z)
	{
		int sum=0;
		for(int i=0;i<mx;i++)
		{
			for(int j=0;j<mw;j++)
			{
				sum+=sc[i][y][z][j];
			}
		}
		return sum;
	}
	public int getConstraint3Sum(int y,int z,int w)
	{
		int sum=0;
		for(int i=0;i<mx;i++)
		{
			sum+=sc[i][y][z][w];
		}
		return sum;
	}
	public int getConstraint4Sum(int x)
	{
		int sum=0;
		for(int i=0;i<my;i++)
		{
			for(int j=0;j<mz;j++)
			{
				for(int k=0;k<mw;k++)
				{
					sum+=sc[x][i][j][k];
				}
			}
		}
		return sum;
	}
	public int getConstraint6Sum(int x,int w)
	{
		int sum=0;
		for(int i=0;i<my;i++)
		{
			for(int j=0;j<mz;j++)
			{
				sum+=sc[x][i][j][w];
			}
		}
		return sum;
	}
	public int getConstraint5Sum(int x,int z,int[] w)
	{
		int sum=0;
		for(int i=0;i<w.length;i++)
		{
			for(int j=0;j<my;j++)
			{
				sum+=sc[x][j][z][w[i]];
			}
		}
		return sum;
	}
	public int getFitness1Value()
	{
		int sum=0;
		for(int i=0;i<mx;i++)
		{
			for(int j=0;j<mz;j++)
			{
				int cs=getConstraint1Sum(i,j);
				if(cs>1)
				{
					sum+=cs-1;
				}
				else
				{
					
				}
			}
		}
		return sum;
	}
	public int getFitness2Value()
	{
		int sum=0;
		for(int i=0;i<my;i++)
		{
			for(int j=0;j<mz;j++)
			{
				int cs=getConstraint2Sum(i,j);
				if(cs>mcr)
				{
					sum+=cs-mcr;
				}
			}
		}
		return sum;
	}
	public int getFitness3Value()
	{
		int sum=0;
		for(int i=0;i<my;i++)
		{
			for(int j=0;j<mz;j++)
			{
				int c=0;
				for(int k=0;k<mw;k++)
				{
					int cs=getConstraint3Sum(i,j,k);
					if(cs>0)
					{
						c++;
					}
				}
				if(c>1)
				{
					sum+=c-1;
				}
			}
		}
		return sum;
	}
	public int getFitness4Value()
	{
		int sum=0;
		for(int i=0;i<mx;i++)
		{
			int cs=getConstraint4Sum(i);
			int mv=getNoOfClassesStudent(i);
			if(cs>mv)
			{
				sum+=cs-mv;
			}
			else
			{
				sum+=mv-cs;
			}
		}
		return sum;
	}
	public int getFitness5Value()
	{
		int sum=0;
		for(int i=0;i<mx;i++)
		{
			for(int j=0;j<mz;j++)
			{
				for(int k=0;k<mw;k++)
				{
					int cs=getConstraint5Sum(i,j,getClassesTogether(k));
					if(cs>1)
					{
						sum+=cs-1;
					}
				}
			}
		}
		return sum;
	}
	public int getFitness6Value()
	{
		int sum=0;
		for(int i=0;i<mx;i++)
		{
			for(int j=0;j<mw;j++)
			{
				int cs=getConstraint6Sum(i,j);
				if(isInStudentsList(i,j))
				{
					if(cs<stos[i][j])
					{
						sum+=stos[i][j]-cs;
					}
					else
					{
						sum+=cs-stos[i][j];
					}
				}
				else
				{
					if(cs>0)
					{
						sum+=cs;
					}
				}
			}
		}
		return sum;
	}
	public double getFitnessValue()
	{
		double x=getFitness1Value()+getFitness2Value()+getFitness3Value()+getFitness4Value()+getFitness6Value();
		return 1/(1+x);
	}
	public ArrayList<Schedule> crossover(Schedule s1,Schedule s2)
	{
		Random r=new Random();
		int[][][][] cj=new int[mx][my][mz][mw];
		for(int i=0;i<mx;i++)
		{
			for(int j=0;j<my;j++)
			{
				for(int k=0;k<mz;k++)
				{
					for(int l=0;l<mw;l++)
					{
						cj[i][j][k][l]=r.nextInt(2);
					}
				}
			}
		}
		ArrayList<Schedule> soln=new ArrayList<Schedule>();
		Schedule c1=new Schedule(mx,my,mz,mw,mcr,stos,sutosu);
		Schedule c2=new Schedule(mx,my,mz,mw,mcr,stos,sutosu);
		for(int i=0;i<mx;i++)
		{
			for(int j=0;j<my;j++)
			{
				for(int k=0;k<mz;k++)
				{
					for(int l=0;l<mw;l++)
					{
						if(cj[i][j][k][l]==1)
						{
							c1.sc[i][j][k][l]=s1.sc[i][j][k][l];
							c2.sc[i][j][k][l]=s2.sc[i][j][k][l];
						}
						else
						{
							c1.sc[i][j][k][l]=s2.sc[i][j][k][l];
							c2.sc[i][j][k][l]=s1.sc[i][j][k][l];
						}
					}
				}
			}
		}
		soln.add(c1);
		soln.add(c2);
		return soln;
	}
	public void mutate()
	{
		Random r=new Random();
		for(int i=0;i<mx;i++)
		{
			for(int j=0;j<my;j++)
			{
				for(int k=0;k<mz;k++)
				{
					for(int l=0;l<mw;l++)
					{
						double c=r.nextDouble();
						if(c<0.001)
						{
							if(sc[i][j][k][l]==1)
							{
								sc[i][j][k][l]=0;
							}
							else
							{
								sc[i][j][k][l]=1;
							}
						}
					}
				}
			}
		}
	}
	public int compareTo(Schedule s2)
	{
		if(getFitnessValue()>s2.getFitnessValue())
		{
			return -1;
		}
		else if(getFitnessValue()<s2.getFitnessValue())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	public void print(int student)
	{
		for(int i=0;i<mz;i++)
		{
			for(int j=0;j<my;j++)
			{
				int m=0;
				for(int k=0;k<mw;k++)
				{
					if(sc[student][j][i][k]==1)
					{
						m=k+1;
					}
				}
				System.out.print(" "+m);
			}
			System.out.println();
		}
	}
	public void printAll()
	{
		for(int i=0;i<mx;i++)
		{
			System.out.println(i);
			System.out.println();
			print(i);
		}
	}
}