package graphique;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Application.Parameters;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class maingraph extends Application{
	
	private ArrayList<Circle> listeVille;
	private ArrayList<Line> listeTraitRoutes;
	
	  public static void main(String[] args) {
	        Application.launch(args);
	    }
	  
	   private PathTransition generatePathTransition(final Shape shape, final Path path)
	   {
	      final PathTransition pathTransition = new PathTransition();
	      pathTransition.setDuration(Duration.seconds(8.0));
	      pathTransition.setDelay(Duration.seconds(2.0));
	      pathTransition.setPath(path);
	      pathTransition.setNode(shape);
	      pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
	      pathTransition.setCycleCount(0);
	      pathTransition.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent fade) {
				// TODO Auto-generated method stub
			shape.setOpacity(0);
			//shape.fireEvent(fade);
			}});
	      pathTransition.setAutoReverse(false);
	      pathTransition.play();
	      return pathTransition;
	   }
	  

	   
	  
	  private Path generatePathAB(final Circle VilleA, final Circle VilleB) {
		  final Path path = new Path();
		  path.getElements().add(new MoveTo(VilleA.getCenterX(), VilleA.getCenterY()));
		  path.getElements().add(new LineTo(VilleB.getCenterX(), VilleB.getCenterY()));
		  path.setOpacity(3);
		  return path;
	  }
	  private Line generateRouteAB(final Circle VilleA, final Circle VilleB, int width) {
		  Line routeAB = new Line(VilleA.getCenterX(), VilleA.getCenterY(), VilleB.getCenterX(),VilleB.getCenterY());
		  routeAB.setStrokeWidth(width);
		  routeAB.setStroke(Color.GREY);
		  return routeAB;
		  
	  }
	  /**private Line generateDemiRouteAB(final Line LineA, final Line LineB, int width) {
		  Line routeAB = new Line(LineA.getCenterX(), LineA.getCenterY(), LineB.getCenterX(),LineB.getCenterY());
		  routeAB.setStrokeWidth(width);
		  routeAB.setStroke(Color.PINK);
		  return routeAB;
		  
	  }*/
	  

	  private Rectangle generateCar(Path chemin) {
		final Rectangle car = new Rectangle(chemin.getLayoutX(), chemin.getLayoutY(),5.0,5.0);
		generatePathTransition(car, chemin);
		  return car;
	  }
	  
	 public void start(Stage primaryStage) {
	       Group root = new Group();
	       Scene scene = new Scene(root, 1200, 600, Color.WHITE);
	       primaryStage.setScene(scene);
	       primaryStage.setTitle("Ouais le rond");
	       primaryStage.show();
	       listeVille = new ArrayList<Circle>();
	       //(X,Y,taille)
	       listeVille.add(new Circle(200,150,75, Color.GREEN));
	       listeVille.add(new Circle(600, 150, 75, Color.RED));
	       listeVille.add(new Circle(1000,150,75, Color.BLUE));
	       listeVille.add(new Circle(200, 450, 75, Color.CYAN));
	       listeVille.add(new Circle(600, 450, 75, Color.YELLOW));
	       listeVille.add(new Circle(1000, 450, 75, Color.PURPLE));
	       root.getChildren().addAll(listeVille);
	       System.out.println(listeVille.get(0).getCenterX());
	       
	       listeTraitRoutes = new ArrayList<Line>();
           for(Circle i:listeVille) {
        	   for(Circle j:listeVille) {
        		   if(i != j) {	//Ne pas créer  de route ayant la meme ville de depart et d'arrivee
        			   listeTraitRoutes.add(generateRouteAB(i,j,5));
        		   }
        	   }
           }

           /*Line Line1=generateRouteAB(VilleA, VilleB, 5);
           Line Line2=generateRouteAB(VilleA, VilleC, 5);
           Line Line3=generateRouteAB(VilleB, VilleD, 5);
           Line Line4=generateRouteAB(VilleD, VilleE, 15);
           Line Line5=generateRouteAB(VilleE, VilleC, 15);*/
           root.getChildren().addAll(listeTraitRoutes);
           
           
           Group Voitures = new Group();
           
          
          Rectangle Voiture2 = new Rectangle(listeVille.get(0).getCenterX(),listeVille.get(0).getCenterY(),5.0,5.0);
          Voitures.getChildren().addAll(Voiture2);
         
          Button CarCreator = new Button("Generer une voiture");
          
          Label label = new Label("");
          Random rand = new Random();
         
          CarCreator.setOnAction(new EventHandler<ActionEvent>() {
        	  
              @Override
              public void handle(ActionEvent event) {
            	int numberStart;
            	Circle townStart = new Circle();
            	numberStart=rand.nextInt(listeVille.size());
            	townStart = listeVille.get(numberStart);
            	int numberEnd;
            	Circle townEnd = new Circle();
            	do {
            		numberEnd = rand.nextInt(listeVille.size());
            	} while (numberEnd == numberStart);	//La voiture ne doit pas posséder le meme depart et la meme arrive
            	townEnd = listeVille.get(numberEnd);
            	Voitures.getChildren().add(generateCar(generatePathAB(townStart, townEnd)));
              }
          });
         
          root.getChildren().addAll(CarCreator,label);
          root.getChildren().add(Voitures);
         
         Path DC = generatePathAB(listeVille.get(4), listeVille.get(3));
         System.out.println(DC);
         generatePathTransition(Voiture2, DC);
          //path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));

}
}
