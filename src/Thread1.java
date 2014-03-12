import java.text.MessageFormat;
import java.util.Dictionary;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.naming.BinaryRefAddr;


public class Thread1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//TreeSet<E>
		
		
		
		PriorityQueue<String> queue=new PriorityQueue<String>();
		queue.offer("test");
		queue.offer("abc");
		queue.offer("cet");
		queue.offer("set");
		
		
		System.out.println(queue);
		System.out.println(queue.size());
		System.out.println(queue.poll());
		System.out.println(queue);
		System.out.println(queue.poll());
		System.out.println(queue);
		
		
		Map<String,String> map=new HashMap<String, String>();
		Integer i1=new Integer(1);
		Integer i2=new Integer(2);
		
		String s1="s1";
		String s2="s2";
		map.put("1", s1);
		map.put("2", s2);
		
		System.out.println(map);
		i2=3;
		s2="s3";
		System.out.println(map);
		
		System.out.println(MessageFormat.format("text {0}:{1}", "test1","test2"));;
		for(int i=0;i<10;i++){
		Thread r=new Thread() {
			@Override
			public void run() {
				A a=new A();
				System.out.println(this.getName()+" is working:::count="+a.count);
				try {
					a.add();
					a.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(this.getName()+" finished working:::count="+a.count);
			}
			
		};
		
		
//			Thread t=new Thread(r);
			r.start();
		}

	}

}


class A{
	
	static int count=0;
	
	Lock lock=new ReentrantLock();
	
	public  int get() throws InterruptedException{
		//Thread.sleep(100);
		return count;
	}
	
	public void add() throws InterruptedException{
		
		//boolean l=lock.tryLock();
		synchronized (A.class) {
			//if(l){
				
				Thread.sleep(100);
				++count;
			//}
			//lock.unlock();
	
		}
	}
}

