package NeuralNetwork;

public class Network {
	
	protected int InputNo;
	protected int HiddenNo;
	protected int OutputNo;
	protected int NeuronNo;
	protected int WeightNo;
	
	protected double GlobalError;
	protected double LearnSpeed;
	protected double Momentum;
	protected double Previous[];
	protected double Matrix[];
	protected double Thresholds[];
	protected double AccMatrixDelta[];
	protected double MatrixDelta[];
	protected double AccThresholdDelta[];
	protected double ThresholdDelta[];
	protected double Error[];
	protected double ErrorDelta[];

	public Network(int InputNo, int HiddenNo, int OutputNo, double LearnSpeed, double Momentum) {
		this.InputNo = InputNo;
		this.HiddenNo = HiddenNo;
		this.OutputNo = OutputNo;
		this.LearnSpeed = LearnSpeed;
		this.Momentum = Momentum;
		
		NeuronNo = InputNo+HiddenNo+OutputNo;
		WeightNo = (InputNo*HiddenNo)+(HiddenNo*OutputNo);
		Previous = new double[NeuronNo];
		Matrix = new double[WeightNo];
		MatrixDelta = new double[WeightNo];
		Thresholds = new double[NeuronNo];
		ErrorDelta = new double[NeuronNo];
		Error = new double[NeuronNo];
		AccThresholdDelta = new double[NeuronNo];
		AccMatrixDelta = new double[WeightNo];
		ThresholdDelta = new double[NeuronNo];
		
		reset();
		
	}
	
	public double getError(int Length) {
		double Err = Math.sqrt(GlobalError/(Length*OutputNo));
		GlobalError = 0;
		return Err;
	}
	
	public double threshold(double Sum) {
		return 1.0/(1+Math.exp(-1.0*Sum));
	}
	
	public double []computeOutputs(int[] input) {
		int a,b;
		final int HiddenIndex = InputNo;
		final int OutIndex = InputNo+HiddenNo;
		for(a=0;a<InputNo;a++) {
			Previous[a] = input[a];
		}
		int Layer = 0;
		for(a=HiddenIndex;a<OutIndex;a++) {
			double Sum = Thresholds[a];
			for(b=0;b<InputNo;b++) {
				Sum += Previous[b]*Matrix[Layer++];
			}
			Previous[a] = threshold(Sum);
		}
		double Result[] = new double[OutputNo];
		for(a=OutIndex;a<NeuronNo;a++) {
			double Sum = Thresholds[a];
			for(b=HiddenIndex;b<OutIndex;b++) {
				Sum += Previous[b]*Matrix[Layer++];
			}
			Previous[a] = threshold(Sum);
			Result[a-OutIndex] = Previous[a];
		}
		return Result;
	}
	
	public void calcError(int[] ideal) {
		int a,b;
		final int HiddenIndex = InputNo;
		final int OutIndex = InputNo + HiddenNo;

		  // clear hidden layer errors
		for(a = InputNo;a<NeuronNo;a++) {
		   Error[a] = 0;
		}
		
		// layer errors and deltas for output layer
		for(a=OutIndex;a<NeuronNo;a++) {
			Error[a] = ideal[a-OutIndex]-Previous[a];
			GlobalError += Error[a]*Error[a];
			ErrorDelta[a] = Error[a]*Previous[a]*(1-Previous[a]);
		}

		// hidden layer errors
		int HLayerErr = InputNo*HiddenNo;
		for(a=OutIndex;a<NeuronNo;a++) {
			for(b=HiddenIndex;b<OutIndex;b++) {
				AccMatrixDelta[HLayerErr] += ErrorDelta[a]*Previous[b];
				Error[b] += Matrix[HLayerErr] * ErrorDelta[a];
				HLayerErr++;
			}
			AccThresholdDelta[a] += ErrorDelta[a];
		}

		// hidden layer deltas
		for(a=HiddenIndex;a<OutIndex;a++) {
			ErrorDelta[a] = Error[a]*Previous[a]*(1-Previous[a]);
		}

		// input layer errors
		HLayerErr = 0; // offset into weight array
		for(a=HiddenIndex;a<OutIndex;a++) {
			for(b=0;b<HiddenIndex;b++) {
				AccMatrixDelta[HLayerErr] += ErrorDelta[a]*Previous[b];
				Error[b] += Matrix[HLayerErr]*ErrorDelta[a];
				HLayerErr++;
			}
			AccThresholdDelta[a] += ErrorDelta[a];
		}
	}

	/**
	* Modify the weight matrix and thresholds based on the last call to
	* calcError.
	*/
	public void learn() {
		int a;
		// process the matrix
		for(a=0;a<Matrix.length;a++) {
			MatrixDelta[a] = (LearnSpeed*AccMatrixDelta[a])+(Momentum*MatrixDelta[a]);
			Matrix[a] += MatrixDelta[a];
			AccMatrixDelta[a] = 0;
		}

		// process the thresholds
		for(a=InputNo;a<NeuronNo;a++) {
			ThresholdDelta[a] = LearnSpeed*AccThresholdDelta[a]+(Momentum*ThresholdDelta[a]);
			Thresholds[a] += ThresholdDelta[a];
			AccThresholdDelta[a] = 0;
		}
	}

	/**
	* Reset the weight matrix and the thresholds.
	*/
	public void reset() {
		int a;
		for(a=0;a<NeuronNo;a++) {
			Thresholds[a] = 0.5-(Math.random());
			ThresholdDelta[a] = 0;
			AccThresholdDelta[a] = 0;
		}
		for(a=0;a<Matrix.length;a++) {
			Matrix[a] = 0.5-(Math.random());
			MatrixDelta[a] = 0;
		   	AccMatrixDelta[a] = 0;
		}
	}
		
}
