import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class EnemyA
{
	public static void main(String[]args)
	{
		int abhp, ahp, aa, el, e, i;
		int enemyLevel = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
		int[] random = new int[];
		Random ran = new Random();
		int enemyHealth = {20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95};
		int enemyAttack = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80};
		for(i = 0; i < 1; e++)
		{
			random[e] = ran.nextInt(16);
			el = random[e]
			abhp = enemyHealth[el]
			int ahp = abhp;
			aa = enemyAttack[el];
		}

	}
}