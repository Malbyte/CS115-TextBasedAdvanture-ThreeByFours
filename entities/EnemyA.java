import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class EnemyA extends enemy
{
	public static void enemies(String[]args)
	{
		int aa, el, i;
		i = 0;
		int[] enemyLevel = {1, 2, 3, 4, 5, 6, 7, 8};
		Random ran = new Random();
		int[] enemyHealth = {20, 25, 30, 35, 40, 45, 50, 55};
		int[] enemyAttack = {5, 10, 15, 20, 25, 30, 35, 40};
		int e = ran.nextInt((8 - 1)+1);
		System.out.println(e);
		int abhp = enemyHealth[e];
		int ahp = abhp;
		aa = enemyAttack[e];
	}
}
