/**
 * pokemon pas en base
 * pokemon pas recuperer
 * erreur dans la recuperation des stats
 * attaque pas en int (erreur / pas erreur)
 * attaque = 0 (erreur / pas erreur)
 * si attaque =, defense = 0 (erreur / pas erreur)
 * pas de pokemon renseigne
 * manque 1er pokemon
 * manque 2eme pokemon
 * meme pokemon
 *
 *
 * attaque superieur (erreur / pas erreur) *
 * si attaque =, defense superieur (erreur / pas erreur) *
 * si tout =, le premier est pris (erreur / pas erreur) *
 *
 *

 */

package com.montaury.pokebagarre.fixtures.metier;

import com.montaury.pokebagarre.metier.Pokemon;
import com.montaury.pokebagarre.metier.Stats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {
    @Test
    void pokemon1_devrait_gagner_car_il_a_une_attaque_plus_haute(){
        //GIVEN
        Pokemon pkmn1 = new Pokemon("pkmn1", "/", new Stats(10, 10));
        Pokemon pkmn2 = new Pokemon("pkmn2", "/", new Stats(5, 10));

        //WHEN
        boolean resultat = pkmn1.estVainqueurContre(pkmn2);

        //THEN
        assertEquals(true, resultat);
    }

    @Test
    void pokemon2_devrait_gagner_car_il_a_une_attaque_plus_haute(){
        //GIVEN
        Pokemon pkmn1 = new Pokemon("pkmn1", "/", new Stats(10, 10));
        Pokemon pkmn2 = new Pokemon("pkmn2", "/", new Stats(15, 10));

        //WHEN
        boolean resultat = pkmn1.estVainqueurContre(pkmn2);

        //THEN
        assertEquals(false, resultat);
    }

    @Test
    void pokemon1_devrait_gagner_car_il_a_une_defense_plus_haute(){
        //GIVEN
        Pokemon pkmn1 = new Pokemon("pkmn1", "/", new Stats(10, 15));
        Pokemon pkmn2 = new Pokemon("pkmn2", "/", new Stats(10, 10));

        //WHEN
        boolean resultat = pkmn1.estVainqueurContre(pkmn2);

        //THEN
        assertEquals(true, resultat);
    }

    @Test
    void pokemon2_devrait_gagner_car_il_a_une_defense_plus_haute(){
        //GIVEN
        Pokemon pkmn1 = new Pokemon("pkmn1", "/", new Stats(10, 10));
        Pokemon pkmn2 = new Pokemon("pkmn2", "/", new Stats(10, 15));

        //WHEN
        boolean resultat = pkmn1.estVainqueurContre(pkmn2);

        //THEN
        assertEquals(false, resultat);
    }

    @Test
    void pokemon1_devrait_gagner_car_il_est_le_premier(){
        //GIVEN
        Pokemon pkmn1 = new Pokemon("pkmn1", "/", new Stats(10, 10));
        Pokemon pkmn2 = new Pokemon("pkmn2", "/", new Stats(10, 10));

        //WHEN
        boolean resultat = pkmn1.estVainqueurContre(pkmn2);

        //THEN
        assertEquals(true, resultat);
    }


}