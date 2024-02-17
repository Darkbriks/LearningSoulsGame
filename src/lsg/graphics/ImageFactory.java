package lsg.graphics;

import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ImageFactory {

    public static enum SPRITES_ID {

        HERO_HEAD                   ("images/craftpix/_PNG/3_KNIGHT/3_head_.png"),
        HERO_ATTACK_ANIMATION       ("images/craftpix/_PNG/3_KNIGHT/_ATTACK/"),
        HERO_DIE_ANIMATION          ("images/craftpix/_PNG/3_KNIGHT/_DIE/"),
        HERO_HURT_ANIMATION         ("images/craftpix/_PNG/3_KNIGHT/_HURT/"),
        HERO_IDLE_ANIMATION         ("images/craftpix/_PNG/3_KNIGHT/_IDLE/"),
        HERO_WALK_ANIMATION         ("images/craftpix/_PNG/3_KNIGHT/_WALK/"),

        ZOMBIE_HEAD                 ("images/craftpix/_PNG/Zombie1/bodyparts/head.png"),
        ZOMBIE_ATTACK_ANIMATION     ("images/craftpix/_PNG/Zombie1/_ATTACK/"),
        ZOMBIE_DIE_ANIMATION        ("images/craftpix/_PNG/Zombie1/_DIE/"),
        ZOMBIE_HURT_ANIMATION       ("images/craftpix/_PNG/Zombie1/_HURT/"),
        ZOMBIE_IDLE_ANIMATION       ("images/craftpix/_PNG/Zombie1/_IDLE/"),
        ZOMBIE_WALK_ANIMATION       ("images/craftpix/_PNG/Zombie1/_WALK/"),

        ATTACK_SKILL                ("images/Hero Skill Free/Power of blessing.png"),
        RECUPERATE_SKILL            ("images/Hero Skill Free/Drain mana.png"),

        SUPER_BERRY                 ("images/Basic RPG Item Free/Berry_03.png"),
        SMALL_STAM_POTION           ("images/Basic RPG Item Free/Small Potion_01.png")
        ;

        private final String path ;
        SPRITES_ID(String path) { this.path = path ; }
    }

    private static final HashMap<SPRITES_ID, Image[]> sprites = new HashMap<>() ;

    /**
     * Permet de précharger toutes les images du jeu en tâche de fond
     * @param finishedHandler : un handler déclenché lorsque le préchargement est terminé
     *                          (peut être null)
     */
    public static void preloadAll(Runnable finishedHandler){
        new Thread(()-> {
            try {
                for (SPRITES_ID id : SPRITES_ID.values()) {
                    load(id);
                }
                if(finishedHandler != null) finishedHandler.run();
            } catch (URISyntaxException | IOException e) {
                System.err.println("Error in ImageFactory.preloadAll() : " + e.getMessage());
            }
        }).start();
    }

    /**
     * Permet de récupérer les images
     * @param id : le code désignant une ou un ensemble d'images
     * @return : un tableau contenant la ou les images demandées
     */
    public static Image[] getSprites(SPRITES_ID id){
        Image[] images = sprites.get(id) ;
        if(images == null) {
            try {
                images = load(id) ;
            } catch (URISyntaxException | IOException e) {
                System.err.println("Error in ImageFactory.getSprites() : " + e.getMessage());
            }
        }
        return images ;
    }

    private static Image[] load(SPRITES_ID id) throws URISyntaxException, IOException {
        String pathName = id.path ;
        Path path = Paths.get(Objects.requireNonNull(ImageFactory.class.getResource(pathName)).toURI()) ;
        Image[] images ;
        if(Files.isDirectory(path)){
            List<Path> paths = Files.list(path).filter(f -> !(Files.isDirectory(f))).sorted().collect(Collectors.toList()) ;
            images = new Image[paths.size()] ;
            int i=0 ;
            for (Path p: paths){
                String name = pathName + p.getFileName() ;
                images[i] = new Image(Objects.requireNonNull(ImageFactory.class.getResource(name)).toExternalForm());
                //int finalI = i;
                i++ ;
            }
        }else{
            images = new Image[]{ new Image(Objects.requireNonNull(ImageFactory.class.getResource(id.path)).toExternalForm()) };
        }

        ImageFactory.sprites.put(id, images) ;
        return images ;
    }

}
