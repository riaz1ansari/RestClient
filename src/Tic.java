import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Tic {

	static String matrix[][] = new String[3][3];
	static String cpymatrix[][] = new String[3][3];

	static Map<String, String> map = new HashMap<String, String>();
	
	static String aTurn = "X";
	static String bTurn = "O";

	static boolean isaTurn = false;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		initMatrix();
		printMatrix();
		boolean doContinue = true;
		String readData = "";
		

		do {

			System.out.println("enter number:::");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			readData = br.readLine();
			if (readData.contains("r")) {
				System.out.println("Reseting.........");
				initMatrix();
				printMatrix();
			} else if (readData.contains("q")) {
				doContinue = false;
				System.out.println("Exiting.........");
			}else{

				if (!map.containsKey(readData)) {
					System.out.println("place already filled....");
				} else {

					String value = map.get(readData);
					map.remove(readData);
					int x = Integer.parseInt(value.substring(0, 1));
					int y = Integer.parseInt(value.substring(1));

					if (isaTurn) {
						matrix[x][y] = aTurn;
						cpymatrix[x][y] = aTurn;
						isaTurn = false;
					} else {
						matrix[x][y] = bTurn;
						cpymatrix[x][y] = bTurn;
						isaTurn = true;
					}
					
					printMatrix();
					
					if(!"".equals(decideWinner(readData))){
						System.out.println("winner....");
						initMatrix();
						printMatrix();
					}
					
				}
			}

		} while (doContinue);
	}

	static void printMatrix() {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (j < 2) {
					System.out.print(matrix[i][j] + "  |  ");

				} else
					System.out.print(matrix[i][j]);
			}
			System.out.println();
			if (i < 2)
				System.out.print("**********************");
			System.out.println();

		}
	}
	
	static String decideWinner(String input) {
		
		String check=aTurn;
		if(!isaTurn){
			check=bTurn;
		}
		
		String result="";
		
		int k=Integer.parseInt(input);
		
		switch (k) {
		case 1:
			//System.out.println("case 1:::::");
			if(cpymatrix[0][1] == cpymatrix[0][2] || cpymatrix[1][1] == cpymatrix[2][2] || cpymatrix[1][0] == cpymatrix[2][0]){
				result="win...";
			}
					
			break;
		case 2:
			
			if(cpymatrix[1][1] == cpymatrix[2][1] || cpymatrix[0][0]== cpymatrix[0][2]){
				result="win...";
			}
			
			break;
		case 3:
			
			if(cpymatrix[0][0] == cpymatrix[0][1] || cpymatrix[2][2]== cpymatrix[1][2] || cpymatrix[1][1]== cpymatrix[2][0]){
				result="win...";
			}
			
			break;
		case 4:
			if(cpymatrix[0][0] == cpymatrix[2][0] || cpymatrix[1][1]== cpymatrix[1][2]){
				result="win...";
			}
			break;
		case 5:
			
			if(cpymatrix[0][0] == cpymatrix[2][0] || cpymatrix[1][1]== cpymatrix[1][2]){
				result="win...";
			}
			
			break;
		case 6:
			
			break;
		case 7:
			
			break;
		case 8:
			
			break;
		case 9:
			
			break;

		default:
			break;
		}
		
		
		return result;
	}

	static void initMatrix() {

		isaTurn = false;
		int count=1;
		map.put("1", "00");
		map.put("2", "01");
		map.put("3", "02");
		map.put("4", "10");
		map.put("5", "11");
		map.put("6", "12");
		map.put("7", "20");
		map.put("8", "21");
		map.put("9", "22");

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matrix[i][j] = new String("   ");
				cpymatrix[i][j] = new String(""+ (count++));
				

			}
		}
	}

}
