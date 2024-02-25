package lsg;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lsg.characters.Hero;
import lsg.characters.Zombie;
import lsg.consumables.food.SuperBerry;
import lsg.graphics.CSSFactory;
import lsg.graphics.ImageFactory;
import lsg.graphics.panes.AnimationPane;
import lsg.graphics.panes.CreationPane;
import lsg.graphics.panes.HUDPane;
import lsg.graphics.panes.TitlePane;
import lsg.graphics.widgets.characters.renderers.CharacterRenderer;
import lsg.graphics.widgets.characters.renderers.HeroRenderer;
import lsg.graphics.widgets.characters.renderers.ZombieRenderer;
import lsg.graphics.widgets.skills.SkillBar;
import lsg.texts.TooltipTexts;
import lsg.utils.Constants;
import lsg.weapons.Sword;
import lsg_api.ConsoleAPI;
import lsg_api.characters.ICharacter;
import lsg_api.characters.IHero;
import lsg_api.characters.IMonster;
import user_mods.ModLoader;

public class LearningSoulsGameApplication extends Application
{
    ////////// SINGLETON //////////
    @Deprecated
    private static LearningSoulsGameApplication instance = null;

    @Deprecated
    public static LearningSoulsGameApplication getInstance()
    {
        ConsoleAPI.warn("LearningSoulsGameApplication.getInstance() is deprecated, and will be removed in a future version. This method will not be replaced.");
        return instance;
    }

    ////////// ATTRIBUTES //////////
    private Scene scene;
    private AnchorPane root;
    private TitlePane gameTitle;
    private CreationPane creationPane;
    private String heroName;
    private AnimationPane animationPane;
    private IHero hero;
    private HeroRenderer heroRenderer;
    private IMonster zombie;
    private ZombieRenderer zombieRenderer;
    private HUDPane hudPane;
    private SkillBar skillBar;
    private final BooleanProperty heroCanPlay = new SimpleBooleanProperty(false);
    private final IntegerProperty score = new SimpleIntegerProperty();
    private ModLoader modLoader;

    ////////// GETTERS //////////
    @Deprecated
    public Hero getHero()
    {
        ConsoleAPI.warn("LearningSoulsGameApplication.getHero() is deprecated and will be removed in a future version. Please use ICharacter.getCharacter(String) instead.");
        return (Hero) hero;
    }
    @Deprecated
    public Zombie getZombie()
    {
        ConsoleAPI.warn("LearningSoulsGameApplication.getZombie() is deprecated and will be removed in a future version. Please use ICharacter.getCharacter(String) instead.");
        return (Zombie) zombie;
    }

    ////////// MAIN //////////
    public static void main(String[] args) { launch(args); }

    ////////// METHODS //////////
    @Override
    public void start(Stage stage)
    {
        instance = this;

        modLoader = ModLoader.getInstance();
        modLoader.loadMods();

        stage.setTitle(Constants.GAME_TITLE);
        root = new AnchorPane();
        scene = new Scene(root, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        stage.setScene(scene);
        stage.resizableProperty().setValue(Boolean.FALSE);

        stage.setOnCloseRequest(event -> {
            modLoader.invoke("stop");
            modLoader.unloadMods();
            System.exit(0);
        });

        buildUI();
        addListeners();
        stage.show();

        modLoader.invoke("awake");
        heroCanPlay.addListener((observable, oldValue, newValue) -> {
            if (newValue) { modLoader.invoke("beginTurn"); }
        });

        startGame();
    }

    private void buildUI()
    {
        scene.getStylesheets().add(CSSFactory.getStyleSheet(Constants.GAME_CSS));
        gameTitle = new TitlePane(scene, Constants.GAME_TITLE);
        root.getChildren().add(gameTitle);
        AnchorPane.setTopAnchor(gameTitle, 10.);
        gameTitle.translateXProperty().bind(scene.widthProperty().subtract(gameTitle.widthProperty()).divide(2));

        creationPane = new CreationPane();
        root.getChildren().add(creationPane);
        AnchorPane.setTopAnchor(creationPane, 0.);
        AnchorPane.setBottomAnchor(creationPane, 0.);
        AnchorPane.setLeftAnchor(creationPane, 0.);
        AnchorPane.setRightAnchor(creationPane, 0.);

        animationPane = new AnimationPane(root);

        hudPane = new HUDPane();

        AnchorPane.setTopAnchor(hudPane, 0.);
        AnchorPane.setBottomAnchor(hudPane, 0.);
        AnchorPane.setLeftAnchor(hudPane, 0.);
        AnchorPane.setRightAnchor(hudPane, 0.);
    }

    private void createSkills()
    {
        skillBar = hudPane.getSkillBar();

        skillBar.setDisable(!heroCanPlay.get());
        heroCanPlay.addListener((observable, oldValue, newValue) -> skillBar.setDisable(!newValue));

        skillBar.getTrigger(0).setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.ATTACK_SKILL)[0]);
        skillBar.getTrigger(0).setAction(this::heroAttack);
        skillBar.getTrigger(0).setTooltip(new Tooltip(TooltipTexts.attackTooltip(hero.getWeapon())));

        skillBar.getTrigger(1).setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.RECUPERATE_SKILL)[0]);
        skillBar.getTrigger(1).setAction(this::heroRecuperate);
        skillBar.getTrigger(1).setTooltip(new Tooltip(TooltipTexts.recuperateTooltip(hero.getLifeRegen(), hero.getStamRegen())));

        skillBar.getConsumableTrigger().setConsumable(hero.getConsumable());
        skillBar.getConsumableTrigger().setAction(this::heroConsume);
        skillBar.getConsumableTrigger().setTooltip(new Tooltip(TooltipTexts.consumeTooltip(hero.getConsumable())));

        scene.setOnKeyReleased(event -> skillBar.process(event.getCode()));
    }

    private void createHero()
    {
        if (heroName == null || heroName.isEmpty() || heroName.replace(" ", "").isEmpty()) { hero = new Hero(); }
        else { hero = new Hero(heroName); }
        hero.setWeapon(new Sword());
        hero.setConsumable(new SuperBerry());
        heroRenderer = animationPane.createHeroRenderer();
        animationPane.initHeroInScene(heroRenderer);
        hudPane.getHeroStatbar().getName().setText(hero.getName());
        // Add padding to the statbar
        hudPane.getHeroStatbar().setPadding(new javafx.geometry.Insets(10, 0, 0, 0));
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

        // Add padding to the statbar
        hudPane.getMonsterStatbar().setPadding(new javafx.geometry.Insets(10, 0, 0, 0));
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
            root.getChildren().remove(creationPane);
            creationPane = null;
            gameTitle.zoomOut(event2 -> play());
        });
    }

    private void play() {
        root.getChildren().add(animationPane);
        root.getChildren().add(hudPane);
        createHero();
        createSkills();
        createMonster(event -> {
                    ModLoader.getInstance().invoke("start");
                    hudPane.getMessagePane().showMessage("Fight !", 4, event1 -> heroCanPlay.setValue(true));
                });
        hudPane.scoreProperty().bind(score);
    }

    /**
     * Methode qui gere l'attaque et le coup porté par un agresseur sur sa cible,
     * aussi bien du poin de vue du modele (Character),
     * que du point de vue de l'animation (CharacterRenderer).
     * @param agressor : le modele de l'attaquant
     * @param agressorR : la representation de l'attaquant (pour l'animation attack)
     * @param target : le modele de la cible
     * @param targetR : la representation de la cible (pour l'animation hurt ou die)
     * @param finishHandler : appele lorsque les calculs et les animations sont terminées
     */
    private void characterAttack(ICharacter agressor, CharacterRenderer agressorR, ICharacter target, CharacterRenderer targetR, EventHandler<ActionEvent> finishHandler)
    {
        try
        {
            int dmg = agressor.attack();

            agressorR.attack(event -> {
                target.getHitWith(dmg);
                if (target.isAlive()) { targetR.hurt(event1 -> finishHandler.handle(null)); }
                else { targetR.die(event1 -> finishHandler.handle(null)); }
            });

        }
        catch (Exception e)
        {
            agressorR.attack(event -> hudPane.getMessagePane().showMessage(e.getMessage(), 1, event1 -> {
                    root.getChildren().remove(hudPane.getMessagePane());
                    finishHandler.handle(null);
                }));
        }
    }

    private void heroAttack()
    {
        modLoader.invoke("heroBeginAttack");
        heroCanPlay.setValue(false);
        characterAttack(hero, heroRenderer, zombie, zombieRenderer, event -> {
            modLoader.invoke("heroFinishAttack");
            finishTurn();
        });
    }

    private void heroRecuperate()
    {
        modLoader.invoke("heroBeginRecuperate");
        heroCanPlay.setValue(false);
        hero.recuperate();
        hudPane.getMessagePane().showMessage("You recuperate life and stamina", 1, event -> {
            modLoader.invoke("heroFinishRecuperate");
            finishTurn();
        });
    }

    private void heroConsume()
    {
        modLoader.invoke("heroBeginConsume");
        heroCanPlay.setValue(false);
        try {
            int life = hero.getLife();
            hero.use(hero.getConsumable());
            System.out.println(hero.getName() + " consumes a " + hero.getConsumable().getName() + " -> +" + (hero.getLife() - life) + " Life");
            hudPane.getMessagePane().showMessage("You consume a " + hero.getConsumable().getName() + "\n\t\t + " + (hero.getLife() - life) + " Life", 1, event -> {
                modLoader.invoke("heroFinishConsume");
                finishTurn();
            });
        } catch (Exception e) {
            hudPane.getMessagePane().showMessage(e.getMessage(), 1, event -> {
                modLoader.invoke("heroFinishConsume");
                finishTurn();
            });
        }
    }

    private void monsterTurn(int action)
    {
        modLoader.invoke("monsterBeginTurn");
        switch (action)
        {
            case 0: monsterAttack( event -> modLoader.invoke("monsterEndTurn")); break;
            // TODO : Add other monster actions
            default: ConsoleAPI.error("[LearningSoulsGameApplication][monsterTurn] : invalid action");
        }
    }

    private void monsterAttack(EventHandler<ActionEvent> finishHandler)
    {
        modLoader.invoke("monsterBeginAttack");

        characterAttack(zombie, zombieRenderer, hero, heroRenderer, event -> {

            modLoader.invoke("monsterFinishAttack");
            finishHandler.handle(null);
            if (hero.isAlive()) { heroCanPlay.setValue(true); }
            else {
                modLoader.invoke("heroDie");
                gameOver();
            }
        });
    }

    private void gameOver()
    {
        ModLoader.getInstance().invoke("stop");
        hudPane.getMessagePane().showMessage("YOU DIED", 2, event -> {
        hudPane.getChildren().clear();
        animationPane.getChildren().clear();
        gameTitle.zoomIn(event1 -> gameTitle.fadeOut(event2 -> {
            modLoader.unloadMods();
            System.exit(0);
        }));
    });
    }

    private void finishTurn()
    {
        if (zombie.isAlive()) { monsterTurn(0); }
        else
        {
            modLoader.invoke("MonsterDie");
            animationPane.getChildren().remove(zombieRenderer);
            score.setValue(score.getValue() + 1);
            createMonster(event -> {
                modLoader.invoke("newMonsterCreate");
                monsterTurn(0);
            });
        }
    }
}
