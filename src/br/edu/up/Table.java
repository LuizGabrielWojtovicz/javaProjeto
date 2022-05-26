package br.edu.up;

public class Table {
	public static void lineTableInter(String tm, String bord)
	{
		for(int i = 0; i < (tm.length() + 3); i++)
		{
			if(i == 0)
			{
				System.out.format("%s", bord.charAt(0));
			}
			else if(i > 0)
			{
				System.out.format("─");
			}
			if(i == (tm.length() + 2))
			{
				System.out.format("%s",bord.charAt(1));
			}
		}
		
	}
	
	public static void table(String txt)
	{
		String bordSup = "╭╮";
		String bordSub = "╰╯";
		
		lineTableInter(txt, bordSup);
		
		System.out.println();
		
		System.out.format("│ %s │", txt);
		
		System.out.println();
		
		lineTableInter(txt, bordSub);
		
	}
}
