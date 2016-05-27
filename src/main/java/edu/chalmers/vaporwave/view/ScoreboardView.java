package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.Sprite;
import edu.chalmers.vaporwave.assetcontainer.SpriteID;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.*;

public class ScoreboardView {

    private Group root;

    private Sprite scoreboardBackground;
    private Canvas scoreboardCanvas;
    private GraphicsContext scoreboardGC;
    private List<Player> playerList;
    private GridPane gridPane;
    private Label[][] playerLabels;

    public ScoreboardView(Group root, Set<Player> players) {
        this(root, players, 0, 0);
    }

    public ScoreboardView(Group root, Set<Player> players, int x, int y) {

        this.root = root;

        int xoffset = x + (int)((Constants.WINDOW_WIDTH / 2) - (Constants.GAME_WIDTH / 2)
                        - (Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE)  + (2 * Constants.DEFAULT_TILE_WIDTH)); // 204
        int yoffset = y + (8 * Constants.DEFAULT_TILE_HEIGHT) - 6; // 122
        int gridPaneYOffset = 81;

        this.playerList = new ArrayList<>();
        this.playerLabels = new Label[players.size()][5];

        for (Player player : players) {
            this.playerList.add(player);
        }
        Collections.sort(this.playerList);

        scoreboardBackground = Container.getSprite(SpriteID.SCOREBOARD_BACKGROUND);
        scoreboardCanvas = new Canvas(scoreboardBackground.getWidth(), scoreboardBackground.getHeight());

        scoreboardCanvas.setLayoutX(xoffset);
        scoreboardCanvas.setLayoutY(yoffset);
        scoreboardCanvas.setVisible(false);

        scoreboardGC = scoreboardCanvas.getGraphicsContext2D();
        scoreboardBackground.render(scoreboardGC, -1);

        gridPane = new GridPane();
        root.getChildren().add(scoreboardCanvas);
        root.getChildren().add(gridPane);

        gridPane.setPrefSize(scoreboardCanvas.getWidth(), scoreboardBackground.getHeight() - gridPaneYOffset);
        gridPane.setLayoutX(xoffset);
        gridPane.setLayoutY(yoffset + gridPaneYOffset);
        gridPane.setHgap(0);
        gridPane.setVgap(58);

        gridPane.setAlignment(Pos.TOP_LEFT);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        col1.setHalignment(HPos.RIGHT);
        col1.setPrefWidth(145);
        gridPane.getColumnConstraints().add(col1);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        col2.setHalignment(HPos.CENTER);
        col2.setPrefWidth(115);
        gridPane.getColumnConstraints().add(col2);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.NEVER);
        col3.setHalignment(HPos.CENTER);
        col3.setPrefWidth(130);
        gridPane.getColumnConstraints().add(col3);

        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.NEVER);
        col4.setHalignment(HPos.CENTER);
        col4.setPrefWidth(130);
        gridPane.getColumnConstraints().add(col4);

        ColumnConstraints col5 = new ColumnConstraints();
        col5.setHgrow(Priority.NEVER);
        col5.setHalignment(HPos.RIGHT);
        col5.setPrefWidth(130);
        gridPane.getColumnConstraints().add(col5);

        for(int i = 0; i < playerList.size(); i++) {
            for (int j = 0; j < playerList.get(i).getPlayerInfo().length; j++) {
                playerLabels[i][j] = new Label(playerList.get(i).getPlayerInfo()[j] + "");
                playerLabels[i][j].setStyle("-fx-font-family: 'Lucida Console'; -fx-text-fill: black;  -fx-font-size: 16;");
                GridPane.setMargin(playerLabels[i][j], new Insets(0, 10, 0, 10));
                gridPane.add(playerLabels[i][j], j, i + 1);
            }
        }
    }

    public void updateScoreboard() {
        for(int i = 0; i < playerList.size(); i++) {
            for (int j = 1; j < playerList.get(i).getPlayerInfo().length; j++) {
                playerLabels[i][j].setText(playerList.get(i).getPlayerInfo()[j] + "");
            }
        }
    }

    public void showScoreboard() {
        updateScoreboard();
        this.scoreboardCanvas.setVisible(true);
        this.gridPane.setVisible(true);
        this.gridPane.toFront();

    }

    public void hideScoreboard() {
        scoreboardCanvas.setVisible(false);
        gridPane.setVisible(false);
    }

    public boolean isShowing() {
        return gridPane.isVisible();
    }

    public void clearScoreboard() {
        this.scoreboardGC.clearRect(0, 0, this.scoreboardCanvas.getWidth(), this.scoreboardCanvas.getHeight());
        this.root.getChildren().remove(this.scoreboardCanvas);
        for (int i = 0; i < this.playerLabels.length; i++) {
            for (int j = 0; j < this.playerLabels[0].length; j++) {
                this.gridPane.getChildren().remove(this.playerLabels[i][j]);
            }
        }
        this.root.getChildren().remove(gridPane);
    }
}
