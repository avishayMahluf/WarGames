//package XML;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//import javafx.application.Application;
//import javafx.scene.Group;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
//import javafx.geometry.HPos;
//import javafx.geometry.Insets;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.Separator;
//import javafx.scene.layout.*;
//
//
//public class TestApplication extends Application {
//
//    private void init(Stage primaryStage) {
//    	
//        Group root = new Group();
//        primaryStage.setScene(new Scene(root));
//       
//        VBox vbox = new VBox();
// 
//        //grid1 places the child by specifying the rows and columns in
//        // GridPane.setContraints()
//        Label grid1Caption = new Label("The Info Game");
//        grid1Caption.setWrapText(true);
//        GridPane grid1 = new GridPane();
//        grid1.setHgap(4);
//        grid1.setVgap(6);
//        grid1.setPadding(new Insets(18, 18, 18, 18));
// 
//        vbox.getChildren().addAll(grid1Caption, grid1, new Separator());
// 
//        //grid2 places the child by influencing the rows and columns themselves
//        //via GridRowInfo and GridColumnInfo. This grid uses the preferred
//        //width/height and max/min width/height.
//        Label grid2Caption = new Label("The Board Game");
//        grid2Caption.setWrapText(true);
//        GridPane grid2 = new GridPane();
//        grid2.setPadding(new Insets(18, 18, 18, 18));
//        grid2.setGridLinesVisible(true);
//        
//        RowConstraints rowinfo = new RowConstraints(40, 40, 40);
//        ColumnConstraints colinfo = new ColumnConstraints(90, 90, 90);
//
//        for (int i = 0; i <= 2; i++) {
//            grid2.getRowConstraints().add(rowinfo);
//        }
//        for (int j = 0; j <= 2; j++) {
//            grid2.getColumnConstraints().add(colinfo);
//        }
//        
//        vbox.getChildren().addAll(grid2Caption, grid2, new Separator());
//
//        //grid3 places the child by influencing the rows and columns
//        //via GridRowInfo and GridColumnInfo. This grid uses the percentages
//        Label grid3Caption = new Label("The button game");
//        grid3Caption.setWrapText(true);
//        GridPane grid3 = new GridPane();
//        grid3.setPadding(new Insets(18, 18, 18, 18));
//        
//        // missile button
//        Button missile_button = new Button("Add missile");
//        GridPane.setConstraints(missile_button, 0, 1);
//        GridPane.setMargin(missile_button, new Insets(10, 10, 10, 10));
//        GridPane.setHalignment(missile_button, HPos.LEFT);
//        
//        // launcher button
//        Button launcher_button = new Button("Add launcher");
//        GridPane.setConstraints(launcher_button, 1, 1);
//        GridPane.setMargin(launcher_button, new Insets(10, 10, 10, 10));
//        GridPane.setHalignment(launcher_button, HPos.CENTER);
//        
//        // destructor button
//        Button destructor_button = new Button("Add destructor");
//        GridPane.setConstraints(destructor_button, 2, 1);
//        GridPane.setMargin(destructor_button, new Insets(10, 10, 10, 10));
//        GridPane.setHalignment(destructor_button, HPos.RIGHT);
//        
//        missile_button.setOnAction(new EventHandler<ActionEvent>() {
//			@Override 
//			public void handle(ActionEvent t) {
//				try {
//					Stage s = new Stage();
//					FXMLLoader f = new FXMLLoader();
//					Parent fxmlRoot;
//					fxmlRoot = (Parent) f.load(new FileInputStream(new File(
//							"bin\\addMissile.fxml")));
//					Scene scene = new Scene(fxmlRoot);
//					scene.getStylesheets().add(
//							TestApplication.class.getResource("..\\welcome2.css")
//							.toExternalForm());
//					s.setScene(scene);
//					s.show();
//					
//				} 
//				catch (FileNotFoundException e) 	{e.printStackTrace();}
//				catch (IOException e) 			{e.printStackTrace();}	
//			}
//			
//		});
//         
//        launcher_button.setOnAction(new EventHandler<ActionEvent>() {
//			@Override 
//			public void handle(ActionEvent t) {
//				try {
//					Stage s = new Stage();
//					FXMLLoader f = new FXMLLoader();
//					Parent fxmlRoot;
//					fxmlRoot = (Parent) f.load(new FileInputStream(new File(
//							"bin\\addLauncher.fxml")));
//					Scene scene = new Scene(fxmlRoot);
//					scene.getStylesheets().add(
//							TestApplication.class.getResource("..\\welcome2.css")
//							.toExternalForm());
//					s.setScene(scene);
//					s.show();
//				} 
//				catch (FileNotFoundException e) 	{e.printStackTrace();}
//				catch (IOException e) 			{e.printStackTrace();}	
//			}
//		});
//        
//        destructor_button.setOnAction(new EventHandler<ActionEvent>() {
//			@Override 
//			public void handle(ActionEvent t) {
//				try {
//					Stage s = new Stage();
//					FXMLLoader f = new FXMLLoader();
//					Parent fxmlRoot;
//					fxmlRoot = (Parent) f.load(new FileInputStream(new File(
//							"bin\\addDestructor.fxml")));
//					Scene scene = new Scene(fxmlRoot);
//					scene.getStylesheets().add(
//							TestApplication.class.getResource("..\\welcome2.css")
//							.toExternalForm());
//					s.setScene(scene);
//					s.show();
//				} 
//				catch (FileNotFoundException e) 	{e.printStackTrace();}
//				catch (IOException e) 			{e.printStackTrace();}
//			}
//		});
//
//        grid3.getChildren().addAll(missile_button, launcher_button, destructor_button);
//        vbox.getChildren().addAll(grid3Caption, grid3);
//        root.getChildren().add(vbox);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        init(primaryStage);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//    	launch(args); 
//    	}
//
//}