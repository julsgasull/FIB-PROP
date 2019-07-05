package main.presentation;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.domini.model.Board;
import main.domini.model.Move;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
* @author Sergi Sendrós
*/


public class chessBoardGUIJavafx
{
    private final   Stage       playFrame; //javafx stage
    private final   Group       group;
    private final   BoardPanel  boardPanel;
    private final   Board       chessBoard;
    public          Scene       s;
    private         Move        movedPiece;

    private CtrlPresentacio presentacio;

    public chessBoardGUIJavafx()
    {
        presentacio = new CtrlPresentacio();

        this.movedPiece= new Move();
        this.group = new Group();
        // this.anchorPane.setLayoutX(new BorderLayout());
        this.chessBoard = presentacio.getMatch().getActualBoard();

        this.boardPanel = new BoardPanel();
        this.group.getChildren().add(this.boardPanel);
        this.s = new Scene(group,600,600);

        this.playFrame = new Stage();
        playFrame.setTitle("Chessy");
        playFrame.initModality(Modality.APPLICATION_MODAL);
        playFrame.setScene(s);
        playFrame.show();
        playFrame.setResizable(false);
        presentacio.getMatch().getTimer().start();
    }

    private void setWinStage()
    {
        if (presentacio.getMatch().getMatchType() == "h1m1" || presentacio.getMatch().getMatchType() == "h1m2")
        {
            presentacio.getMatch().getProblem().addNewRankingLine(presentacio.getActualPlayer().getUsername(),presentacio.getMatch().getAttackerNPlays(),presentacio.getMatch().getTimer().getTime());
            presentacio.updateRanking(presentacio.getMatch().getProblem().getRankingLines(), presentacio.getActualProblem().getProblemID());
        }
        setEndStage("ATTACKER WINS!");
    }

    private void setLoseStage()
    {
        setEndStage("DEFENDER WINS!");
    }

    private void setEndStage(String endText)
    {
        BorderPane bP = new BorderPane();
        // Group g = new Group();
        VBox vb = new VBox();
        Button b = new Button(endText);
        Label l = new Label("Press Space Bar to exit this window");
        l.setFont(new Font("Karla Bold",20));
        l.setTextFill(javafx.scene.paint.Color.WHITE);
        //  b.setPrefSize(,300);
        b.setFont(new Font("Karla Bold",30));
        b.setTextFill(javafx.scene.paint.Color.WHITE);
        b.setStyle("-fx-background-color: linear-gradient(to right, #ffecc0, #d45f69, #d45f69,  #803ee9); -fx-background-radius: 200;");
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(b);

        b.setOnAction(new EventHandler<javafx.event.ActionEvent>()
        {
            @Override
            public void handle(javafx.event.ActionEvent actionEvent)
            {
                playFrame.close();
            }
        });
        Rectangle r = new Rectangle(600,600);
        Stop[] stops = new Stop[] { new Stop(0, Color.valueOf("#090909")), new Stop(1, Color.valueOf("#454545"))};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        r.setFill(lg1);
        bP.getChildren().add(r);
        //bP.getChildren().add(vb);
        bP.setCenter(vb);
        bP.setBottom(l);
        Scene s = new Scene(bP,600,600);
        playFrame.setScene(s);
        playFrame.show();
    }

    private class BoardPanel extends GridPane
    {
        final List<TilePanel> boardTiles;

        BoardPanel()
        {
            super();
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < 8; i++)
            {//board size
                for (int j = 0; j < 8; j++)
                {
                    final TilePanel tilePanel = new TilePanel(this, i * 8 + j);
                    this.boardTiles.add(tilePanel);
                    this.add(tilePanel, j, i);
                }
            }
            for (int i = 0; i < 8; i++)
            {
                this.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
                this.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
            }
        }

        public void drawBoard(final Board board)
        {
            group.getChildren().clear();
            for (final TilePanel tilePanel : boardTiles)
            {
                tilePanel.drawTile(board);
                group.getChildren().add(tilePanel);
            }
            group.requestLayout();
        }
    }

    private class TilePanel extends StackPane
    {

        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId)
        {
            super();
            this.tileId = tileId;
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            this.setPrefSize(8,8);

            this.setOnMouseClicked(mouseEvent ->
            {
                System.out.println("mouse clicked");
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY))
                {
                    System.out.println("right button pressed");
                    movedPiece = new Move();

                }
                else if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
                {
                    System.out.println(movedPiece.getOldX());

                    if (movedPiece.getOldX() == -1)
                    {//first click
                        if (presentacio.getMatch().getActualBoard().hasPieceTile(tileId) && (presentacio.getMatch().getActualBoard().getCellTile(tileId).isWhite() == presentacio.getMatch().isTurn()))
                        {
                            System.out.println(chessBoard.getCellTile(tileId).getName());
                            movedPiece.setPiece(chessBoard.getCellTile(tileId));
                            movedPiece.setOldX(tileId / 8);
                            movedPiece.setOldY(tileId - (tileId / 8) * 8);
                        }
                    }
                    else
                    { //second Click
                        movedPiece.setNewX(tileId / 8);
                        movedPiece.setNewY(tileId - (tileId / 8) * 8);
                        if (movedPiece.getPiece().checkMove(movedPiece.getOldX(), movedPiece.getOldY(), movedPiece.getNewX(), movedPiece.getNewY(), presentacio.getMatch().getActualBoard()) && !(movedPiece.getOldX()==movedPiece.getNewX() && movedPiece.getOldY()==movedPiece.getNewY()))
                        {//comprovar que el moviment)
                            presentacio.getMatch().getActualBoard().updateBoard(movedPiece);//fer moviment

                            if (presentacio.getMatch().isTurn()==presentacio.getMatch().getProblem().whoStarts())presentacio.getMatch().setAttackerNPlays(presentacio.getMatch().getAttackerNPlays()+1);
                            presentacio.getMatch().setTurn(!presentacio.getMatch().isTurn());
                            System.out.println("i made a move");
                            movedPiece = new Move();

                            if (presentacio.getMatch().getActualBoard().isCheckMate(presentacio.getMatch().isTurn()))
                            {
                                System.out.println("its a check mateeee");
                                Platform.runLater(chessBoardGUIJavafx.this::setWinStage);
                            }
                            else if (presentacio.getMatch().getAttackerNPlays() >= presentacio.getMatch().getProblem().getnPlays() && !presentacio.getMatch().getActualBoard().isCheckMate(presentacio.getMatch().isTurn()))
                            {
                                System.out.println("loseeer");
                                Platform.runLater(chessBoardGUIJavafx.this::setLoseStage);
                            }

                            if (presentacio.getMatch().getMatchType() == "h1m1" && presentacio.getMatch().getAttackerNPlays()<presentacio.getMatch().getProblem().getnPlays())
                            {
                                presentacio.getMatch().getTimer().stop();
                                presentacio.getMatch().movePieceAI(!presentacio.getMatch().getProblem().whoStarts());
                                presentacio.getMatch().setTurn(!presentacio.getMatch().isTurn());
                                presentacio.getMatch().getTimer().start();
                            }
                            else if (presentacio.getMatch().getMatchType() == "h1m2" && presentacio.getMatch().getAttackerNPlays()<presentacio.getMatch().getProblem().getnPlays())
                            {
                                presentacio.getMatch().getTimer().stop();
                                presentacio.getMatch().movePieceAI2(!presentacio.getMatch().getProblem().whoStarts());
                                presentacio.getMatch().setTurn(!presentacio.getMatch().isTurn());
                                presentacio.getMatch().getTimer().start();
                            }
                        }
                        else movedPiece = new Move();
                    }
                    Platform.runLater(() -> boardPanel.drawBoard(presentacio.getMatch().getActualBoard()));
                }
                //posar amb task en un futur si dona temps.
            });

        }


        public void drawTile(final Board board)
        {
            assignTileColor();
            assignTilePieceIcon(board);
            requestLayout();
            this.setVisible(true);
        }

        private void assignTilePieceIcon(final Board board)
        {
            this.getChildren().clear();
            if (board.hasPieceTile(this.tileId))
            {
                String s;
                if (board.getCellTile(this.tileId).isWhite()) s = "ProjecteIntelliJ/Data/Images/" + board.getCellTile(this.tileId).getName() + ".png";
                else s = "ProjecteIntelliJ/Data/Images/" + board.getCellTile(this.tileId).getName() + board.getCellTile(this.tileId).getName() + ".png";
                Image image = null;
                try
                {
                    image = new Image(new FileInputStream(s));
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                Label l = new Label();
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(75);
                imageView.setFitWidth(75);
                l.setGraphic(imageView);
                this.getChildren().add(l);
            }
            else
            {
                Rectangle rec = new Rectangle(75,75);
                rec.opacityProperty().set(0);
                this.getChildren().add(rec);
            }
        }

        private void assignTileColor()
        {
            if (    (this.tileId >= 0 && this.tileId < 8)   ||
                    (this.tileId >= 16 && this.tileId < 24) ||
                    (this.tileId >= 32 && this.tileId < 40) ||
                    (this.tileId >= 48 && this.tileId < 56)
                ) setBackground(new Background(new BackgroundFill(this.tileId % 2 == 0 ? javafx.scene.paint.Color.valueOf("#bababa") : javafx.scene.paint.Color.valueOf("#4f4f4f"), CornerRadii.EMPTY, Insets.EMPTY)));
            else if(    (this.tileId >= 8 && this.tileId < 16)  ||
                        (this.tileId >= 24 && this.tileId < 32) ||
                        (this.tileId >= 40 && this.tileId < 48) ||
                        (this.tileId >= 56 && this.tileId < 64)
                    ) setBackground(new Background(new BackgroundFill(this.tileId % 2 == 0 ? javafx.scene.paint.Color.valueOf("#4f4f4f") : javafx.scene.paint.Color.valueOf("#bababa"), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }
}
