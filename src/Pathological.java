import javafx.application.Application;
import javafx.stage.Stage;
import screen.PathologicalWindow;

public class Pathological extends Application{
	public void start(Stage arg0){
		new PathologicalWindow();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
