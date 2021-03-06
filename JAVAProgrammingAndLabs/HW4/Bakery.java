package HW4;


public class Bakery{
	public static int contents; // on bakery have one total breads num. so, I using class value, which have one data in one class
	private boolean available = false; 
	//when available true, the time that consumer's can consume.
		//when available false, the time that consumer's can't consume.
	
	public synchronized int get(int who, Consumer con) {  //consumer
		while(available == false) {//when available false, it is the time that consumer's can't consume breads
			try {
				wait(); //because it is the time that producer generate breads, the consumer object's thread must be wait. 
				        //to doing this i use the get method's, since wait method must execute in synchronize 
			}catch(InterruptedException ex) {//exception
				System.out.println(ex);//print exception content
			}
		}
		//available is true = the time that consumer's can consume
		contents -= HW4.unitOfCon; //consumer consuming unitOfCon breads at a time. to update the total number of breads, assign the contents variable.
		HW4.textA1.append("\nConsumer "+ who + " got: " + HW4.unitOfCon + " (Total # of Breads = " + contents + ")");
		//print the console to satisfy the output format
		if(contents>=HW4.unitOfCon) { //if the contents is greater or equal than unitOfCon, consumer could consuming the bread.
			available = true; //when available true, it is the time that consumer can consume breads
		}else { //if the contents is not greater than unitOfCon, consumer couldn't consuming the bread. 
			available = false; //when available false, it is the time that consumer can consume breads.
		}
		if(con.getBread() + HW4.unitOfCon >= HW4.total) { //since the getBread's return value is the number of consuming breads, 
			Producer.running = false; //because simulation complete, set running false
			Consumer.running = false; //because simulation complete, set running false
			HW4.textA1.append("\n\n:Done");//print the console to satisfy the output format.
			HW4.textA1.append("\n=> Consumer " + who + " won the game! (first ate "+HW4.total+ " breads)"); //print the console to satisfy the output format. 
		}else {//the case that consumer dosen't satisfy the number of the consuming breads
			notifyAll(); //because this tread is ended, this request lock and one of the other thread start
		}
		return HW4.unitOfCon;//to update the con object's breads number. return unitOfCon (consuming breads at a time)
		
	}
	
	public synchronized void put(int who, int value) { //producer
		contents += value; //producer generate value(random) at a time. to update the total number of breads, assign the contents variable
		if(contents>=HW4.unitOfCon) { //if the contents is greater or equal than unitOfCon, consumer could consuming the bread.
			available = true; //when available true, it is the time that consumer can consume breads
		}else { //if the contents is not greater than unitOfCon, consumer couldn't consuming the bread. 
			available = false; //when available false, it is the time that consumer can consume breads.
		}

		HW4.textA1.append("\nProducer "+ who + " put: "+ value + " (Total # of Breads = " + contents + ")");//print the console to satisfy the output format
		notifyAll(); //because this thread is ended, this request lock and one of the other thread start
	}
}
