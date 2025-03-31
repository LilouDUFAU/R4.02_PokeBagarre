package com.montaury.pokebagarre.ui;
import java.util.concurrent.TimeUnit;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
@ExtendWith(ApplicationExtension.class)
class PokeBagarreAppTest {
    private static final String IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1 = "#nomPokemon1";
    private static final String IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2 = "#nomPokemon2";
    private static final String IDENTIFIANT_BOUTON_BAGARRE = ".button";
    @Start
    private void start(Stage stage) {
        new PokeBagarreApp().start(stage);
    }

    @Test
    void devrait_afficher_une_erreur_si_meme_pkmns_renseignes(FxRobot robot) {
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("MewTwo");
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("MewTwo");
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Impossible de faire se bagarrer un pokémon avec lui même");
    }

    @Test
    void devrait_afficher_une_erreur_si_pkmns_inconnu(FxRobot robot){
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("pohp");
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("poij");
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(()->
                assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Impossible de recuperer les details sur 'pohp'"));
    }

    @Test
    void devrait_determiner_le_vainqueur_quand_les_deux_sont_renseignes_et_differents (FxRobot robot) {
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("MewTwo");
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Pikachu");
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(()->
                assertThat(getResultatBagarre(robot)).isEqualTo("Le vainqueur est: MewTwo"));
    }


    private static String getResultatBagarre(FxRobot robot) {
        return robot.lookup("#resultatBagarre").queryText().getText();
    }
    private static String getMessageErreur(FxRobot robot) {
        return robot.lookup("#resultatErreur").queryLabeled().getText();
    }
}