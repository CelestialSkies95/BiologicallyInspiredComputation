package NeuralNetwork;

import java.text.NumberFormat;

//By Deaven Howarth and Kimberley Stewart
public class NN {
	
	
/*	private static int PopulationSize=10;
	private static int MaxGenerations=20;
	private static int MAX_POPULATION = 1;
	private static int MAX_VARIABLES = 6;
	
	private static int[][] CurrentPopulation;
	private static int[][] CurrentX;
	private static int[][] CurrentY;
	private static double[] fitness;
	private static int bestSolution;
	private static double bestFitness;
	private static Random random = new Random();
	
	public static void main(String[] args) {
		int generation;
		CurrentPopulation = new int[MAX_POPULATION][MAX_VARIABLES];
		CurrentX = new int[MAX_POPULATION][MAX_VARIABLES];
		CurrentY = new int[MAX_POPULATION][MAX_VARIABLES];
		initialiseThePopulation();
		
		
	}
	
	private static int getRandomNumberBetween(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static int getRandomNumberFrom(int min, int max) {
        return getRandomNumberBetween(min, max+1);
    }
    
	private static void initialiseThePopulation(){
		int x;
		int y;
		bestFitness = 0.0;
		int solution;
		
		//build the chromosome
		for (int j=0;j<5;j++) {
			x = getRandomNumberBetween(1,9);
			y = getRandomNumberBetween(1,9);
			solution = (x*x)+(y*y);
			CurrentPopulation[0][j] = solution;
			CurrentX[0][j] = x;
			CurrentY[0][j] = y;
		}			
		for (int j = 0; j < 5; j++) {
            System.out.print(CurrentPopulation[0][j] + " ");
        }
	}
	*/
	
	public static void main(String args[]) {
		int Input[][] = {
			{1,1},{1,2},{1,3},{1,4},{1,5},{1,6},{1,7},{1,8},{1,9},
			{2,1},{2,2},{2,3},{2,4},{2,5},{2,6},{2,7},{2,8},{2,9},
			{3,1},{3,2},{3,3},{3,4},{3,5},{3,6},{3,7},{3,8},{3,9},
			{4,1},{4,2},{4,3},{4,4},{4,5},{4,6},{4,7},{4,8},{4,9},
			{5,1},{5,2},{5,3},{5,4},{5,5},{5,6},{5,7},{5,8},{5,9},
			{6,1},{6,2},{6,3},{6,4},{6,5},{6,6},{6,7},{6,8},{6,9},
			{7,1},{7,2},{7,3},{7,4},{7,5},{7,6},{7,7},{7,8},{7,9},
			{8,1},{8,2},{8,3},{8,4},{8,5},{8,6},{8,7},{8,8},{8,9},
			{9,1},{9,2},{9,3},{9,4},{9,5},{9,6},{9,7},{9,8},{9,9},
		};
		int Ideal[][] = {
			{2} ,{5} ,{10},{17},{26}, {37} ,{50} ,{65} ,{82},
			{5} ,{8} ,{13},{20},{29}, {40} ,{53} ,{68} ,{85},
			{10},{13},{18},{25},{34}, {45} ,{58} ,{73} ,{90},
			{17},{20},{25},{32},{41}, {52} ,{65} ,{80} ,{97},
			{26},{29},{34},{41},{50}, {61} ,{74} ,{89} ,{106},
			{37},{40},{45},{52},{61}, {72} ,{85} ,{100},{117},
			{50},{53},{58},{65},{74}, {85} ,{98} ,{113},{130},
			{65},{68},{73},{80},{89}, {100},{113},{128},{145},
			{82},{85},{90},{97},{106},{117},{130},{145},{162},
		};
		System.out.println("learn:");
		Network network = new Network(2,1,1,0.7,0.9);
		NumberFormat Percent = NumberFormat.getPercentInstance();
		
		Percent.setMinimumFractionDigits(6);
		for(int i=0;i<10000;i++) {
			for(int j=0;j<Input.length;j++) {
				network.computeOutputs(Input[j]);
			    network.calcError(Ideal[j]);
			    network.learn();
			}
			System.out.println( "Trial #" + i + ",Error:" +
			             Percent.format(network.getError(Input.length)) );
		}

		System.out.println("Recall:");
		for(int i=0;i<Input.length;i++) {
			for(int j=0;j<Input[0].length;j++) {
				System.out.print(Input[i][j] +":" );
			}

			double out[] = network.computeOutputs(Input[i]);
			System.out.println("="+out[0]);
		}
	}
}
 