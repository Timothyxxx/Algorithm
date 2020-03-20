import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
��ƽ����n����������Ը���
������:����һ��ƽ����������ľ���ֻ������������
��int�;���,�Ҳ�������ͬ��������������ĵ�
ƽ�����Ӷ�O(nlgnlgn)
*/
public class Main {
	private static final int INFINITY = 1000000000;
	public static int CalculatePairs(node[] A,int n) {
		//һ�����
		if (n==1) {
			return 0;
		}
		if (n==2) {
			return 1;
		}
		//�ݹ����
		int i,j,r1,r2,r3=0,t1,t2;
		node[] left = new node[(n+1)/2];
		node[] right = new node[n/2];
		//���ֽ׶�O(n)
		for (i=0;i<(n+1)/2;i++){
			left[i] = new node(A[i].x,A[i].y);
		}
		for (;i<n;i++) {
			right[i-(n+1)/2] = new node(A[i].x,A[i].y);
		}
		//����׶�2T(n/2)
		r1 = CalculatePairs(left,(n+1)/2);
		r2 = CalculatePairs(right,n/2);
		//�ϲ��׶Σ���������ѭ����ʵ�����ڲ�ѭ��ÿ���������һ���֣�ƽ�����Ӷ�O(nlgn)
		TreeSet<Integer> s = new TreeSet<Integer>();
		range[] R = new range[(n+1)/2];
		for (i=(n-1)/2;i>=0;i--) {
			s.add(A[i].y);
			try {
				t1 = s.lower(A[i].y);
			}catch (NullPointerException e) {
				t1 = -INFINITY;
			}
			try {
				t2 = s.higher(A[i].y);
			}catch (NullPointerException e) {
				t2 = INFINITY;
			}			
			R[i] = new range(t1,t2);
		}
		
		int Count1 = n/2;
		for (i=(n-1)/2;i>=0;i--) {
			int Max = R[i].max;
			int Min = R[i].min;
			int Count2 = 0;
			for (j=0;j<Count1;j++) {
				if (right[j].y<Max && right[j].y>Min) {
					right[Count2] = right[j];
					Count2++;
					if (right[j].y>=left[i].y) {
						Max = right[j].y;
					}
					if (right[j].y<=left[i].y) {
						Min = right[j].y;
					}
				}
			}
			r3 += Count2;
			Count1 = Count2;
		}
		return r1+r2+r3;
	}
	public static void main(String[] args) {
		int i,j,n,t1,t2;
		Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        node[] A = new node[n];
        for (i=0;i<n;i++) {
        	t1 = scanner.nextInt();
        	t2 = scanner.nextInt();
        	A[i] = new node(t1,t2);
        }
        Arrays.sort(A,new cmp());
    	System.out.print(CalculatePairs(A,n));
	}
}
class node{
	public int x;
	public int y;
	public node(int x,int y) {
		this.x=x;
		this.y=y;
	}
}
class cmp implements Comparator<node>{
	@Override
	public int compare(node o1, node o2) {
		if (o1.x<o2.x || (o1.x==o2.x && o1.y<o2.y)) {
			return -1;
		}
		return 1;
	}	
}
class range{
	public int min;
	public int max;
	public range(int min,int max) {
		this.min = min;
		this.max = max;
	}
}