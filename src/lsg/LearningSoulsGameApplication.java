package lsg;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lsg.characters.Hero;
import lsg.characters.Zombie;
import lsg.graphics.CSSFactory;
import lsg.graphics.ImageFactory;
import lsg.graphics.panes.AnimationPane;
import lsg.graphics.panes.CreationPane;
import lsg.graphics.panes.HUDPane;
import lsg.graphics.panes.TitlePane;
import lsg.graphics.widgets.characters.renderers.HeroRenderer;
import lsg.graphics.widgets.characters.renderers.ZombieRenderer;
import lsg.graphics.widgets.skills.SkillBar;
import lsg.graphics.widgets.texts.GameLabel;
import lsg.utils.Constants;
import lsg.weapons.Sword;

public class LearningSoulsGameApplication extends Application
{
    private Scene scene;
    private AnchorPane root;
    private TitlePane gameTitle;
    private CreationPane creationPane;
    private String heroName;
    private AnimationPane animationPane;
    private Hero hero;
    private HeroRenderer heroRenderer;
    private Zombie zombie;
    private ZombieRenderer zombieRenderer;
    private HUDPane hudPane;
    private SkillBar skillBar;
    private BooleanProperty heroCanPlay = new SimpleBooleanProperty(false);

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle(Constants.GAME_TITLE);
        root = new AnchorPane();
        scene = new Scene(root, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        stage.setScene(scene);
        stage.resizableProperty().setValue(Boolean.FALSE);
        buildUI();
        addListeners();
        stage.show();
        startGame();
    }

    private void buildUI()
    {
        scene.getStylesheets().add(CSSFactory.getStyleSheet(Constants.GAME_CSS));
        gameTitle = new TitlePane(scene, Constants.GAME_TITLE);
        root.getChildren().add(gameTitle);
        AnchorPane.setTopAnchor(gameTitle, 10.);
        // Center the title
        gameTitle.translateXProperty().bind(scene.widthProperty().subtract(gameTitle.widthProperty()).divide(2));

        creationPane = new CreationPane();
        root.getChildren().add(creationPane);
        AnchorPane.setTopAnchor(creationPane, 0.);
        AnchorPane.setBottomAnchor(creationPane, 0.);
        AnchorPane.setLeftAnchor(creationPane, 0.);
        AnchorPane.setRightAnchor(creationPane, 0.);

        animationPane = new AnimationPane(root);

        hudPane = new HUDPane();
        // Ancrez pour qu’il occupe tout l’espace
        AnchorPane.setTopAnchor(hudPane, 0.);
        AnchorPane.setBottomAnchor(hudPane, 0.);
        AnchorPane.setLeftAnchor(hudPane, 0.);
        AnchorPane.setRightAnchor(hudPane, 0.);
    }

    private void createSkills()
    {
        skillBar = hudPane.getSkillBar();

        skillBar.setDisable(heroCanPlay.getValue());
        heroCanPlay.addListener((observable, oldValue, newValue) -> skillBar.setDisable(!newValue));

        skillBar.getTrigger(0).setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.ATTACK_SKILL)[0]);
        skillBar.getTrigger(0).setAction(() -> System.out.println("ATTACK"));
        scene.setOnKeyReleased(event -> skillBar.process(event.getCode()));
    }

    private void createHero()
    {
        hero = new Hero(heroName);
        hero.setWeapon(new Sword());
        heroRenderer = animationPane.createHeroRenderer();
        animationPane.initHeroInScene(heroRenderer);
        hudPane.getHeroStatbar().getName().setText(hero.getName());
        // Add padding to the statbar
        hudPane.getHeroStatbar().setPadding(new javafx.geometry.Insets(25, 0, 0, 0));
        hudPane.getHeroStatbar().getLifeBar().progressProperty().bind(hero.lifeRateProperty());
        hudPane.getHeroStatbar().getStaminaBar().progressProperty().bind(hero.staminaRateProperty());
    }

    private void createMonster(EventHandler<ActionEvent> finishedHandler)
    {
        zombie = new Zombie();
        zombieRenderer = animationPane.createZombieRenderer();
        animationPane.initMonsterInScene(zombieRenderer, finishedHandler);
        hudPane.getMonsterStatbar().getName().setText(zombie.getName());
        Image[] imgs = ImageFactory.getSprites(ImageFactory.SPRITES_ID.ZOMBIE_HEAD);
        if (imgs != null)
        {
            hudPane.getMonsterStatbar().getAvatar().setImage(imgs[0]);
            hudPane.getMonsterStatbar().getAvatar().setRotate(35);
        }
        hudPane.getMonsterStatbar().flip();
        // Add padding to the statbar
        hudPane.getMonsterStatbar().setPadding(new javafx.geometry.Insets(25, 0, 0, 0));
        hudPane.getMonsterStatbar().getLifeBar().progressProperty().bind(zombie.lifeRateProperty());
        hudPane.getMonsterStatbar().getStaminaBar().progressProperty().bind(zombie.staminaRateProperty());
    }

    private void startGame()
    {
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override public void run()
            {
                gameTitle.zoomIn(event1 -> creationPane.fadeIn(event2 -> ImageFactory.preloadAll((() -> System.out.println("Preloading done")))));
            }
        }, Constants.TITLE_ANIMATION_DELAY);
    }

    private void addListeners()
    {
        creationPane.getNameField().setOnAction(event1 ->
        {
            heroName = creationPane.getNameField().getText();
            System.out.println("Hero name is: " + heroName);
            if (heroName != null && !heroName.isEmpty())
            {
                root.getChildren().remove(creationPane);
                creationPane = null;
                gameTitle.zoomOut(event2 -> play());
            }
        });
    }

    private void play() {
        root.getChildren().add(animationPane);
        root.getChildren().add(hudPane);
        createHero();
        createSkills();
        createMonster(event -> hudPane.getMessagePane().showMessage("Fight !", 4,
                //event1 -> hudPane.getMessagePane().showMessage("Hero attacks !", 0, event2 -> test())));
                event1 -> heroCanPlay.setValue(true)));
    }

    private void test()
    {
        try {
            int dmg = hero.attack();
            System.out.println(hero.getName() + " attack " + zombie.getName() +
                    " with " + hero.getWeapon().getName() + " (" + dmg +
                    ") -> Effective DMG: " + zombie.getHitWith(dmg) + " PV");
        } catch (Exception e) {
            hudPane.getMessagePane().showMessage(e.getMessage(), 1, event -> root.getChildren().remove(hudPane.getMessagePane()));
        }
    }
}
