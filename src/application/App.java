package application;
	
import definitions.Definitions;
import game.Game;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class App extends Application {
	private Game game;
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{	
			primaryStage.setTitle("MouseMan");
			primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("/textures/icon.png")));
			primaryStage.setResizable(false);
			Definitions.stage=primaryStage;
			BorderPane root = new BorderPane();
			Definitions.root=root;
			Scene scene = new Scene(root,Definitions.WIDTH,Definitions.HEIGHT);
			primaryStage.setScene(scene);
			primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth()-Definitions.WIDTH)/2);
			primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight()-Definitions.HEIGHT)/2);
			game = new Game((int)Definitions.WIDTH,(int)Definitions.HEIGHT);
			primaryStage.show();
			game.start();
		} catch(Exception e) 
		{
			e.printStackTrace();
			stop();
		}
	}
	@Override
	public void stop(){
		System.exit(80);
	}

	
	public static void main(String[] args) {
		launch(args);

	}
}
