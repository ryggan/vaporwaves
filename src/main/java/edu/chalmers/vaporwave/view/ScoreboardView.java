package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.Sprite;
import edu.chalmers.vaporwave.assetcontainer.SpriteID;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.PlayerComparator;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.GameType;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Compiles a scorebaord based on a set of players, which is sorted depending on preference.
 * The scoreboard shows all points and kills etc., and is used both mid game and in
 * results screen.
 */
public class ScoreboardView {

    private Group root;

    private Sprite scoreboardBackground;
    private Canvas scoreboardCanvas;
    private GraphicsContext scoreboardGC;
    private List<Player> playerList;
    private GridPane gridPane;
    private Label[][] playerLabels;

    private boolean sortByGameType;
    private GameType gameType;

    public ScoreboardView(Group root, Set<Player> players) {
        this(root, players, 0, 0);
    }

    public ScoreboardView(Group root, Set<Player> players, int x, int y) {
        this(root, players, x, y, false, null);
    }

    public ScoreboardView(Group root, Set<Player> players, int x, int y, boolean sortByGameType, GameType gameType) {

        this.root = root;

        this.sortByGameType = sortByGameType;
        this.gameType = gameType;

        int xoffset = x + (int)((Constants.WINDOW_WIDTH / 2) - (Constants.GAME_WIDTH / 2)
                        - (Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE)  + (2 * Constants.DEFAULT_TILE_WIDTH)); // 204
        int yoffset = y + (8 * Constants.DEFAULT_TILE_HEIGHT) - 6; // 122
        int gridPaneYOffset = 81;

        // Settin up player list and sorting it in different ways, as is needed
        this.playerList = new ArrayList<>();
        this.playerLabels = new Label[players.size()][5];

        for (Player player : players) {
            this.playerList.add(player);
        }
        sortPlayerList();

        // Setting up all graphic elements
        this.scoreboardBackground = Container.getSprite(SpriteID.SCOREBOARD_BACKGROUND);
        this.scoreboardCanvas = new Canvas(this.scoreboardBackground.getWidth(), this.scoreboardBackground.getHeight());

        this.scoreboardCanvas.setLayoutX(xoffset);
        this.scoreboardCanvas.setLayoutY(yoffset);
        this.scoreboardCanvas.setVisible(false);

        this.scoreboardGC = this.scoreboardCanvas.getGraphicsContext2D();
        this.scoreboardBackground.render(this.scoreboardGC, -1);

        this.gridPane = new GridPane();
        root.getChildren().add(this.scoreboardCanvas);
        root.getChildren().add(this.gridPane);

        this.gridPane.setPrefSize(this.scoreboardCanvas.getWidth(), this.scoreboardBackground.getHeight() - gridPaneYOffset);
        this.gridPane.setLayoutX(xoffset);
        this.gridPane.setLayoutY(yoffset + gridPaneYOffset);
        this.gridPane.setHgap(0);
        this.gridPane.setVgap(58);

        this.gridPane.setAlignment(Pos.TOP_LEFT);

        // Specific column constraints for each column
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        col1.setHalignment(HPos.RIGHT);
        col1.setPrefWidth(145);
        this.gridPane.getColumnConstraints().add(col1);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        col2.setHalignment(HPos.CENTER);
        col2.setPrefWidth(115);
        this.gridPane.getColumnConstraints().add(col2);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.NEVER);
        col3.setHalignment(HPos.CENTER);
        col3.setPrefWidth(130);
        this.gridPane.getColumnConstraints().add(col3);

        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.NEVER);
        col4.setHalignment(HPos.CENTER);
        col4.setPrefWidth(130);
        this.gridPane.getColumnConstraints().add(col4);

        ColumnConstraints col5 = new ColumnConstraints();
        col5.setHgrow(Priority.NEVER);
        col5.setHalignment(HPos.RIGHT);
        col5.setPrefWidth(130);
        this.gridPane.getColumnConstraints().add(col5);

        // Setting up labels for every player and every stat
        for(int i = 0; i < this.playerList.size(); i++) {
            for (int j = 0; j < this.playerList.get(i).getPlayerInfo().length; j++) {
                this.playerLabels[i][j] = new Label(this.playerList.get(i).getPlayerInfo()[j] + "");
                this.playerLabels[i][j].setStyle("-fx-font-family: 'Lucida Console'; -fx-text-fill: black;  -fx-font-size: 16;");
                GridPane.setMargin(this.playerLabels[i][j], new Insets(0, 10, 0, 10));
                this.gridPane.add(this.playerLabels[i][j], j, i + 1);
            }
        }
    }

    private void sortPlayerList() {
        // Sort with winner at top, if told so
        if (this.sortByGameType) {
            if (this.gameType == GameType.SURVIVAL || this.gameType == GameType.SCORE_LIMIT) {
                Collections.sort(this.playerList,
                        PlayerComparator.decending(PlayerComparator.getComparator(PlayerComparator.SCORE_SORT)));
            } else if (this.gameType == GameType.CHARACTER_KILLS) {
                Collections.sort(this.playerList,
                        PlayerComparator.decending(PlayerComparator.getComparator(PlayerComparator.CHARACTER_KILL_SORT)));
            } else if (this.gameType == GameType.ENEMY_KILLS) {
                Collections.sort(this.playerList,
                        PlayerComparator.decending(PlayerComparator.getComparator(PlayerComparator.ENEMY_KILL_SORT)));
            }

        // Standard sorting: smallest ID first
        } else {
            Collections.sort(this.playerList, PlayerComparator.getComparator(PlayerComparator.ID_SORT));
        }
    }

    public void updateScoreboard() {
        for(int i = 0; i < this.playerList.size(); i++) {
            for (int j = 1; j < this.playerList.get(i).getPlayerInfo().length; j++) {
                this.playerLabels[i][j].setText(this.playerList.get(i).getPlayerInfo()[j] + "");
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
        this.scoreboardCanvas.setVisible(false);
        this.gridPane.setVisible(false);
    }

    public boolean isShowing() {
        return this.gridPane.isVisible();
    }

    public void clearScoreboard() {
        this.scoreboardGC.clearRect(0, 0, this.scoreboardCanvas.getWidth(), this.scoreboardCanvas.getHeight());
        this.root.getChildren().remove(this.scoreboardCanvas);
        for (int i = 0; i < this.playerLabels.length; i++) {
            for (int j = 0; j < this.playerLabels[0].length; j++) {
                this.gridPane.getChildren().remove(this.playerLabels[i][j]);
            }
        }
        this.root.getChildren().remove(this.gridPane);
    }
}
