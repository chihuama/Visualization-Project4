public enum TweetType {
	AllTweet(0) {

		@Override
		public String label() {
			return "General Tweet";
		}

		@Override
		public int red() {
			// TODO Auto-generated method stub
			return 40;
		}

		@Override
		public int green() {
			// TODO Auto-generated method stub
			return 10;
		}

		@Override
		public int blue() {
			// TODO Auto-generated method stub
			return 200;
		}
	},
	StomachProblem(1) {

		@Override
		public String label() {
			return "Stomach Problem";
		}

		@Override
		public int red() {
			// TODO Auto-generated method stub
			return 241;
		}

		@Override
		public int green() {
			// TODO Auto-generated method stub
			return 93;
		}

		@Override
		public int blue() {
			// TODO Auto-generated method stub
			return 53;
		}

	},
	FluProblem(2) {


		@Override
		public String label() {
			return "Flu Problem";
		}

		@Override
		public int red() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int green() {
			// TODO Auto-generated method stub
			return 230;
		}

		@Override
		public int blue() {
			// TODO Auto-generated method stub
			return 0;
		}

	},	
	carCrash(3) {


		@Override
		public String label() {
			return "Car Crash";
		}

		@Override
		public int red() {
			// TODO Auto-generated method stub
			return 255;
		}

		@Override
		public int green() {
			// TODO Auto-generated method stub
			return 255;
		}

		@Override
		public int blue() {
			// TODO Auto-generated method stub
			return 255;
		}

	},
	planeCrash(4) {


		@Override
		public String label() {
			return "Plane Crash";
		}

		@Override
		public int red() {
			// TODO Auto-generated method stub
			return 255;
		}

		@Override
		public int green() {
			// TODO Auto-generated method stub
			return 255;
		}

		@Override
		public int blue() {
			// TODO Auto-generated method stub
			return 255;
		}

	},
	truckCrash(5) {


		@Override
		public String label() {
			return "Truck Crash";
		}

		@Override
		public int red() {
			// TODO Auto-generated method stub
			return 255;
		}

		@Override
		public int green() {
			// TODO Auto-generated method stub
			return 255;
		}

		@Override
		public int blue() {
			// TODO Auto-generated method stub
			return 255;
		}

	},
	bombTweet(6) {


		@Override
		public String label() {
			return "Bomb Tweet";
		}

		@Override
		public int red() {
			// TODO Auto-generated method stub
			return 255;
		}

		@Override
		public int green() {
			// TODO Auto-generated method stub
			return 255;
		}

		@Override
		public int blue() {
			// TODO Auto-generated method stub
			return 255;
		}

	},
	heatTweet(7) {


		@Override
		public String label() {
			return "Heat Advisory";
		}

		@Override
		public int red() {
			// TODO Auto-generated method stub
			return 255;
		}

		@Override
		public int green() {
			// TODO Auto-generated method stub
			return 255;
		}

		@Override
		public int blue() {
			// TODO Auto-generated method stub
			return 255;
		}

	},
	riotTweet(8) {


		@Override
		public String label() {
			return "Riot/Looting";
		}

		@Override
		public int red() {
			// TODO Auto-generated method stub
			return 255;
		}

		@Override
		public int green() {
			// TODO Auto-generated method stub
			return 255;
		}

		@Override
		public int blue() {
			// TODO Auto-generated method stub
			return 255;
		}

	},
	;
	private int value;


	public abstract String label();

	public abstract int red();
	public abstract int green();
	public abstract int blue();
	
	private TweetType(int value) {

		this.value = value;

	}
}
