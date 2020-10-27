package application.PlayerAndBall;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;;

public class Player extends Rectangle{

	final String type;
	boolean dead = false;
	
	public Player(Integer x, Integer y, Integer w, Integer h, String type, Color color){
		super(w,h,color);
		
		this.type = type;
		setTranslateX(x);
		setTranslateY(y);
	}
	
	public void moveLeft() {
		setTranslateX(getTranslateX() - 5);
	}
	
	public void moveRight() {
		setTranslateX(getTranslateX() + 5);
	}
	
}
