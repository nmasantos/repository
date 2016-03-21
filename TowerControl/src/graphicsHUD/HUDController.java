package graphicsHUD;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class HUDController {

	@FXML
	private Canvas canvas;
	
	@FXML
	private Group group;
	
	private double width;
	private double height;
	
	private LinearGradient skyColor;
	private LinearGradient groundColor;
	
	private String altitude = "";
	private String remainBatt = "";
	private String battVolt = "";
	private String gpsFix = "";

	private double yaw = 0, roll = Double.NaN, pitch = 0;
	
	private double planeSize = 10;
	
	public void paint() {
		
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();

		skyColor = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] { 
	    		            new Stop(0, Color.rgb(0,130,214)), 
	    		            new Stop(1, Color.rgb(44,177,225))});

		 
		groundColor = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] { 
	    		            new Stop(0, Color.rgb(75,187,161)), 
	    		            new Stop(1, Color.rgb(0, 143, 99))});
		

		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		
		
		drawPitch(gc);
		drawYaw(gc);
		drawRoll(gc);
		drawPlane(gc);
		
	}
	
	

	public void drawPitch(GraphicsContext gc) {
		
		int step = 20;
		int angle = 80;
//		
		pitch=10;
//		group.setTranslateX(0);
//		group.setTranslateY(pitch * (step/5));
		
		
		//group.setRotate(-roll);
		
		Rectangle ground = new Rectangle(0, height/2-pitch, width, height *5 );
		ground.setFill(groundColor);
		group.getChildren().add(ground);
		
		Rectangle sky = new Rectangle(0, -500 , width, height/2+500-pitch);
		sky.setFill(skyColor);
		group.getChildren().add(sky);
		
		Rectangle whiteBar = new Rectangle();
		
		//Vertical grid
		Line line = new Line(0, height/2, width, height/2);
		line.setFill(Color.WHITE);
		
		
		for(int i = 80; i < step*40; i+=step){
			
			if(i !=0){
				if(i % (2 * step) == 0){
					Line whiteLine = new Line(width/2 - 50, i-pitch, width/2 + 50, i-pitch);
					whiteLine.setStroke(Color.WHITE);
					group.getChildren().add(whiteLine);
					
					Text text = new Text(width/2 - 70, i-pitch, angle + "");
					text.setStroke(Color.WHITE);
					group.getChildren().add(text);

					System.out.println(i + " " + text.getText());
					
					angle-=10;
					
				} else {
					Line whiteLine= new Line(width/2 - 20, i-pitch, width/2 + 20, i-pitch);
					whiteLine.setStroke(Color.WHITE);
					group.getChildren().add(whiteLine);
				}
			}
		}
		
		// ground coordinates 0, height/2, width, height/2
		
	}



	public void drawPlane(GraphicsContext gc) {
		
		
		Circle circle = new Circle(width/2, height/2, planeSize);
		circle.setStroke(Color.RED);
		circle.setStrokeWidth(2);
		circle.setFill(null);
		
		Line wingRight = new Line(width/2 + planeSize, height/2, width/2 + 30, height/2);
		wingRight.setStroke(Color.RED);
		wingRight.setStrokeWidth(2);
		
		Line wingLeft = new Line(width/2 - planeSize, height/2,width/2 - 30, height/2);
		wingLeft.setStroke(Color.RED);
		wingLeft.setStrokeWidth(2);
		
		group.getChildren().add(circle);
		group.getChildren().add(wingRight);
		group.getChildren().add(wingLeft);
		
	}

	public void drawYaw(GraphicsContext gc) {
		
	/*	Rectangle sky= new Rectangle(0, 0, width, height/2);
		sky.setFill(skyColor);
		
		group.getChildren().add(sky);
		*/
		Rectangle rect = new Rectangle(0, 0, width, 50);
		rect.setStroke(Color.WHITE);
		rect.setFill(null);
		
		group.getChildren().add(rect);
		
		double centerDegrees = yaw;
		double numDegreesToShow = 50;
		double degreesPerPixel = (double) width / numDegreesToShow;
		
		double mod = yaw % 5;
		
		for (double angle = (centerDegrees - mod) - numDegreesToShow/2.0; angle <= (centerDegrees - mod) + numDegreesToShow /2.0; angle += 5){
			
			
			
			double workAngle = (angle + 360.0);
			while (workAngle >= 360)
				workAngle -= 360.0;
			
			int distanceToCenter = (int) ((angle - centerDegrees) * degreesPerPixel);
			
			
			Line bigLine = new Line(width / 2 + distanceToCenter, 10, width/2 + distanceToCenter, 30);
			bigLine.setStroke(Color.WHITE);
			group.getChildren().add(bigLine);
			
			Line smallLine = new Line(width / 2 + distanceToCenter + 20, 15, width/2 + distanceToCenter + 20, 25);
			smallLine.setStroke(Color.WHITE);
			group.getChildren().add(smallLine);
			
			if(workAngle % 90 == 0){
				String compass[] = {"N", "E", "S", "W"};
				int index = (int) workAngle/90;
				
				Text text = new Text(width / 2 + distanceToCenter - 5, 40, compass[index]);
				text.setStroke(Color.WHITE);
				
				group.getChildren().add(text);
				
			} else {
				Text text = new Text(width / 2 + distanceToCenter - 5, 40, (int) workAngle + "");
				text.setStroke(Color.WHITE);
				
				group.getChildren().add(text);
			}
			
			Line centerLine = new Line(width/2,5 , width/2, 20);
			centerLine.setStroke(Color.RED);
			
			group.getChildren().add(centerLine);
		}
		
	}
		
	public void drawRoll(GraphicsContext gc) {
		
		int centerX = (int) ((double) width * 0.5);
		
		Arc arc = new Arc(centerX , height/3 - 30, 120, 45, 0, 180);
		arc.setStroke(Color.WHITE);
		arc.setStrokeWidth(3);
		arc.setFill(null);
		
		group.getChildren().add(arc);
		
		
		
		
		float centerY = (float) (height/3 - 30 + 48);
		
		for(int i = -45; i <= 45; i += 15){
			
			float dx = (float) Math.sin(i * Math.PI/180) * centerX;
			float dy = (float) Math.cos(i * Math.PI/180) * centerX;
			
			Line line = new Line(dx, centerY - dy, (dx + (dx / 25)), centerY
					- (dy + dy / 25));
			
		}
		
	}

	
}
