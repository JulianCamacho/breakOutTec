package application;	
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import application.Bricks.Brick;
import application.Bricks.BrickFactory;
import application.Bricks.BrickType;
import application.Bricks.LiveBrick;
import application.PlayerAndBall.Ball;
import application.PlayerAndBall.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class Main extends Application {
	
	//Constantes del juego
	private static final Integer WIDTH = 800;
	private static final Integer HEIGHT = 600;
	private static final Integer BALLQUANTITY = 1;
	private static final Integer PLAYERY = 500;
	private static final Integer PLAYERHEIGHT = 5;
	
	//variables
	private Integer[][] matrix = new Integer[14][8];
	private Integer lives = 3;
	private Integer racketLenght = 200;
	private Integer ballSpeed = 3;
	private Integer[] ballPosition = new Integer[2];
	private Integer racketPosition = 400;
	private Integer score = 0;
	private Integer level = 0;
	boolean gameOver = false;
	
	//Ventana principal
	BorderPane root = new BorderPane();
	
	//Elementos del juego
	private Player player = new Player(racketPosition, PLAYERY, racketLenght, PLAYERHEIGHT, "player", Color.BLUE);
	private ArrayList<Ball> balls = new ArrayList<Ball>();
	private ArrayList<Brick> bricks = new ArrayList<Brick>();
	
	//Factoría de bricks
	private BrickFactory factory = new BrickFactory();
	
	/*Función que crea el contenido de la ventana en ese momento
	 * Entradas: -
	 * Salidas: Objeto tipo Parent (JavaFX)
	 * Restricciones: -
	 */

	private Parent createContent() {
		
		root.setPrefSize(WIDTH, HEIGHT);
		
		root.getChildren().add(player);
		
		Ball ball = new Ball(WIDTH/2, racketPosition-50, 15, ballSpeed, Color.AQUA);
		balls.add(ball);
		
		root.getChildren().add(ball);
		
		//Se comporta como un mainloop del juego, update se usa
		//como la función que actualiza el juego
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
		//check if balls bounds on window
		for(int j = 0; j < balls.size(); j++){
			
			Ball ball = balls.get(j);
			
			ball.moveX(WIDTH);
			
			//Si la bola choca con el jugador, osea la raqueta
			if(player.getBoundsInParent().intersects(ball.getBoundsInParent())) {
				ball.changeDirY();
			}
			
			//Revisar alguna la bola choca contra algún ladrillo
			for(int i = 0; i < bricks.size(); i++) {
				
				if(ball.getBoundsInParent().intersects(bricks.get(i).getBoundsInParent())) {
					String action = bricks.get(i).performAction();
					brickAction(action);
					root.getChildren().remove(bricks.get(i));
					bricks.remove(i);
					ball.changeDirY();
				}
			}
			
			ball.moveY(WIDTH);
		}
	}
	
	//Función que se encarga de crear una matriz
	public void createMatrix() {
		//Crea la matriz de bloques
		for(int y = 0; y < 8; y++) {
			for(int x = 0; x < 14; x++) {
				
				BrickType type = BrickType.BALL;
				Color color = Color.GRAY;
				
				if(0 <= y && y <= 1) {
					type = BrickType.BALL;
					color = Color.RED;
				}
				
				if(2 <= y && y <= 3) {
					type = BrickType.DECVEL;
					color = Color.BLACK;
				}
				
				if(4 <= y && y <= 5) {
					type = BrickType.DOUBLESIZE;
					color = Color.YELLOW;
				}
				
				if(6 <= y && y <= 7) {
					type = BrickType.BALL;
					color = Color.PURPLE;
				}
				
				//Integer iInteger = new Integer(iInt);
				Brick brick = factory.getBrick(type, WIDTH/14 * x, y * 20 + y , WIDTH/14 - 1, 20, 100, color);
				bricks.add(brick);
				root.getChildren().add(brick);
			}
		}
	}
	
	//Función que se encarga decambiar variables del juego
	public void brickAction(String action) {
		switch(action) {
			case "LiveBrick":
				this.lives++;
				System.out.println(this.lives);
				break;
			case "Ballbrick":
				System.out.println("crea bola");
				Ball ball = new Ball(WIDTH/2, racketPosition-50, 15, ballSpeed, Color.AQUA);
				balls.add(ball);
				root.getChildren().add(ball);
				break;
			case "DecreaseVelBrick":
				balls.forEach(b ->{
					if(b.getSpeed() > 1) {
						b.IncreaseSpeed(-1);
					}
				});
				break;
			case "IncreaseVelBrick":
				balls.forEach(b ->{
					if(b.getSpeed() > 1) {
						b.IncreaseSpeed(1);
					}
				});
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
	
	
	//Función inicializadora del juego
	@Override
	public void start(Stage stage) {
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
