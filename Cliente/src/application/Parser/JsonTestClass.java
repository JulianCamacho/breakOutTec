package application.Parser;

public class JsonTestClass {
	
	public Integer[][] matrix;
	public Integer lives;
	public Integer ballQuantity;
	public Integer ballSpeed;
	public Integer[] ballPosition;
	public Integer racketPosition;
	public Integer level;
	public Integer score;
	public Integer greenBrickValue;
	public Integer yellowBrickValue;
	public Integer orangeBrickValue;
	public Integer redBrickValue;
	
	public JsonTestClass(Integer[][] matrix, Integer lives, Integer ballQuantity, Integer ballSpeed,
			Integer[] ballPosition, Integer racketPosition, Integer level, Integer score, Integer greenBrickValue,
			Integer yellowBrickValue, Integer orangeBrickValue, Integer redBrickValue) {

		this.matrix = matrix;
		this.lives = lives;
		this.ballQuantity = ballQuantity;
		this.ballSpeed = ballSpeed;
		this.ballPosition = ballPosition;
		this.racketPosition = racketPosition;
		this.level = level;
		this.score = score;
		this.greenBrickValue = greenBrickValue;
		this.yellowBrickValue = yellowBrickValue;
		this.orangeBrickValue = orangeBrickValue;
		this.redBrickValue = redBrickValue;
	}
	
}