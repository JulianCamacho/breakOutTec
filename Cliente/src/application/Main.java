package application;	
import java.util.ArrayList;

import application.Bricks.Brick;
import application.Bricks.BrickFactory;
import application.Bricks.BrickType;
import application.Parser.JsonParser;
import application.Parser.JsonTestClass;
import application.PlayerAndBall.Ball;
import application.PlayerAndBall.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Main extends Application {
	
	//Constantes del juego
	private static final Integer WIDTH = 800;
	private static final Integer HEIGHT = 600;
	private static final Integer PLAYERY = 500;
	private static final Integer PLAYERHEIGHT = 5;
	private static final Integer BALLSPAWNX = WIDTH/2;
	private static final Integer BALLSPAWNY = 350;
	private static final Integer DEFAULTPOINTSBRICK = 100;
	
	//variables
	private Integer[][] matrix;
	private Integer lives = 0;
	private Integer ballQuantity = 0;
	private Integer ballSpeed = 0;
	private Integer racketLenght = 0;
	private Integer racketPosition = 0;
	private Integer level = 0;
	private Integer score = 0;
	private Integer greenBrickValue = 0;
	private Integer yellowBrickValue = 0;
	private Integer orangeBrickValue = 0;
	private Integer redBrickValue = 0;
	
	boolean gameOver = false;
	
	//Ventana principal
	BorderPane root = new BorderPane();
	
	//Elementos del juego
	private Player player; 
	private ArrayList<Ball> balls = new ArrayList<Ball>();
	private ArrayList<Brick> bricks = new ArrayList<Brick>();
	
	//Factoría de bricks
	private BrickFactory factory = new BrickFactory();
	
	//WIdgets de estadisticas del juego
	
	Text scoreLabel = new Text("Score: " + Integer.toString(this.score));
	Text livesLabel = new Text("Lives: " + Integer.toString(this.lives));
	Text levelLabel = new Text("Level: " + Integer.toString(this.level));
	
	//Parser del juego en formato JSON
	JsonParser parser = new JsonParser();
	
	/*Función que crea el contenido de la ventana en ese momento
	 * Entradas: -
	 * Salidas: Objeto tipo Parent (JavaFX)
	 * Restricciones: -
	 */

	private Parent createContent() {
		
		HBox stats = new HBox();
		
		Button button = new Button("Prueba");
		
		stats.getChildren().addAll(scoreLabel, livesLabel, levelLabel);
		
		root.setTop(stats);
		
		root.setPrefSize(WIDTH, HEIGHT);
		
		root.getChildren().add(player);
		
		spawnBall();
		
		//Se comporta como un mainloop del juego, update se usa como la función que actualiza
		//el juego
		AnimationTimer timer = new AnimationTimer() {
			public void handle(long now) {
				update();
			}
		};
		
		timer.start();
		
		createMatrix();
		
		return root;
		
	}
	
	/*Función que se encarga de actualizar elementos del juego
	 * 
	 */
	
	private void update() {
		
		//Si lives < 0, el juego termina
		if(this.lives < 0) {
			System.out.println("Perdió");
		}
		
		//check if balls bounds on window
		for(int j = 0; j < balls.size(); j++){
			
			Ball ball = balls.get(j);
			ball.moveX(WIDTH);
			ball.moveY(WIDTH);
			
			//Si alguna bola pasa por debajo de la posición Y de la raqueta
			
			if(ball.getCenterY() > PLAYERY) {
				root.getChildren().remove(balls.get(j));
				balls.remove(j);
				this.lives--;
			}
			
			//Si la bola choca con el jugador, osea la raqueta
			if(player.getBoundsInParent().intersects(ball.getBoundsInParent())) {
				ball.changeDirY();
			}
			
			//Revisar alguna la bola choca contra algún ladrillo
			for(int i = 0; i < bricks.size(); i++) {
				
				if(ball.getBoundsInParent().intersects(bricks.get(i).getBoundsInParent())) {
					String action = bricks.get(i).performAction();
					Integer points = bricks.get(i).getPoints();
					brickAction(action, points);
					root.getChildren().remove(bricks.get(i));
					bricks.remove(i);
					ball.changeDirY();
				}
			}
			
		}
		
		scoreLabel.setText("Score: " + Integer.toString(this.score));
		livesLabel.setText("Lives: " + Integer.toString(this.lives));
		levelLabel.setText("Level: " + Integer.toString(this.level));
		
	}
	
	//Función que se encarga de crear una matriz
	public void createMatrix() {
		//Crea la matriz de bloques
		for(int y = 0; y < 8; y++) {
			for(int x = 0; x < 14; x++) {
				
				BrickType type = BrickType.NORMAL;
				Integer points = 100;
				Color color = Color.GRAY;
				
				if(matrix[y][x] < 5) {
					type = BrickType.NORMAL;
					color = Color.GRAY;
					points = DEFAULTPOINTSBRICK;
				}
				
				if(matrix[y][x] == 5) {
					type = BrickType.LIFE;
					color = Color.GREEN;
					points = this.greenBrickValue;
				}
				
				if(matrix[y][x] == 6) {
					type = BrickType.BALL;
					color = Color.YELLOW;
					points = this.yellowBrickValue;
				}
				
				if(matrix[y][x] == 7) {
					type = BrickType.INCVEL;
					color = Color.ORANGE;
					points = this.orangeBrickValue;
				}
				
				if(matrix[y][x] == 8) {
					type = BrickType.DECVEL;
					color = Color.PURPLE;
					points = this.orangeBrickValue;
				}
				
				if(matrix[y][x] == 9) {
					type = BrickType.DOUBLESIZE;
					color = Color.RED;
					points = this.redBrickValue;
				}
				if(matrix[y][x] == 10) {
					type = BrickType.MIDSIZE;
					color = Color.BLACK;
					points = this.redBrickValue;
				}
				
				//Integer iInteger = new Integer(iInt);
				Brick brick = factory.getBrick(type, WIDTH/14 * x, y * 20 + y + 100 , WIDTH/14 - 1, 20, points, color);
				bricks.add(brick);
				root.getChildren().add(brick);
			}
		}
	}
	
	private void spawnBall() {
		Ball ball = new Ball(BALLSPAWNX, BALLSPAWNY, 15, ballSpeed, Color.AQUA);
		balls.add(ball);
		root.getChildren().add(ball);
		ballQuantity++;
	}
	
	//Función que se encarga decambiar variables del juego cuando un bloque se rompe
	public void brickAction(String action, Integer points) {
		score += points;
		switch(action) {
			case "LiveBrick":
				this.lives++;
				System.out.println(this.lives);
				break;
			case "Ballbrick":
				spawnBall();
				System.out.println("crea bola");
				break;
			case "DecreaseVelBrick":
				balls.forEach(b ->{
					if(b.getSpeed() > 1) {
						b.IncreaseSpeed(-1);
					}
				});
				ballSpeed--;
				break;
			case "IncreaseVelBrick":
				balls.forEach(b ->{
					if(b.getSpeed() > 1) {
						b.IncreaseSpeed(1);
					}
				});
				ballSpeed++;
				break;
			case "RacketDoubleSizeBrick":
				racketLenght *= 2;
				player.setWidth(racketLenght);
				break;
			case "MidSizeBrick":
				racketLenght /= 2;
				player.setWidth(racketLenght);
				break;
		}
		
	}
	
	
	public void setupGame() {
		
		JsonTestClass json = parser.deserializeJson();
		this.matrix = json.matrix;
		this.ballQuantity = json.ballQuantity;
		//this.ballSpeed = json.ballSpeed;
		this.ballSpeed = 3;
		this.racketLenght = 200;
		//this.racketPosition = json.racketPosition;
		this.racketPosition = 400;
		this.level = json.level;
		this.score = json.score;
		this.greenBrickValue = json.greenBrickValue;
		this.yellowBrickValue = json.yellowBrickValue;
		this.orangeBrickValue = json.orangeBrickValue;
		this.redBrickValue = json.redBrickValue;
		
		player = new Player(racketPosition, PLAYERY, racketLenght, PLAYERHEIGHT, "player", Color.BLUE);
		
		System.out.println(ballQuantity);
	}
	
	//Función inicializadora del juego
	@Override
	public void start(Stage stage) {
		
		setupGame();
		
		Scene scene = new Scene(createContent());
		  
		scene.setOnKeyPressed(e ->{
			switch(e.getCode()) {
			 	case A:
			  		player.moveLeft();
			  		break;
			  	case D: 
			  		player.moveRight();
			  		break;
			  	
			  }
		  });
		  
		  stage.setScene(scene);
		  stage.setTitle("breakOutTec");
		  stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
