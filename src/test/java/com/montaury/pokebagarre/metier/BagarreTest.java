package com.montaury.pokebagarre.metier;
import com.montaury.pokebagarre.erreurs.ErreurRecuperationPokemon;
import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.mock.*;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class BagarreTest {
    //Echec de recuperation du premier pokemon → recupererParNom
    //Pokemon 1 a une meilleure attaque et gagne → estVainqueurContre
    //Echec de recuperation du second pokemon → recupererParNom
    //Pokemon 1 a une meilleure attaque et gagne → estVainqueurContre

    @Test
    void demarrer_erreur_quand_pkm1_nom_vide() {
        // given
        Pokemon pkmn1 = new Pokemon("", "/", new Stats(10, 10));
        // when
        Throwable thrown = catchThrowable(() -> nomVide1(pkmn1));
        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le nom du Pokémon ne peut pas être vide");
    }
    public void nomVide1(Pokemon pkmn1) {
        if (pkmn1.getNom().isEmpty()) {
            throw new IllegalArgumentException("Le nom du Pokémon ne peut pas être vide");
        } else {
            System.out.println("Le combat commence !");
            Pokemon pkmn2 = new Pokemon("Pikachu", "/", new Stats(50, 10));
            Bagarre combat = new Bagarre();
            combat.demarrer(pkmn1.getNom(),pkmn2.getNom());
        }
    }


    @Test
    void demarrer_erreur_quand_pkm2_nom_vide() {
        // given
        Pokemon pkmn2 = new Pokemon("", "/", new Stats(10, 10));
        // when
        Throwable thrown = catchThrowable(() -> nomVide2(pkmn2));
        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le nom du Pokémon ne peut pas être vide");
    }
    public void nomVide2(Pokemon pkmn2) {
        if (pkmn2.getNom().isEmpty()) {
            throw new IllegalArgumentException("Le nom du Pokémon ne peut pas être vide");
        } else {
            System.out.println("Le combat commence !");
            Pokemon pkmn1 = new Pokemon("Pikachu", "/", new Stats(50, 10));
            Bagarre combat = new Bagarre();
            combat.demarrer(pkmn1.getNom(),pkmn2.getNom());
        }
    }


    @Test
    void demarrer_erreur_quand_pkm1_nom_null() {
        // given
        Pokemon pkmn1 = new Pokemon(null, "/", new Stats(10, 10));
        // when
        Throwable thrown = catchThrowable(() -> nomNull1(pkmn1));
        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le nom du Pokémon ne peut pas être null");
    }
    public void nomNull1(Pokemon pkmn1) {
        if (pkmn1.getNom() == null) {
            throw new IllegalArgumentException("Le nom du Pokémon ne peut pas être null");
        } else {
            System.out.println("Le combat commence !");
            Pokemon pkmn2 = new Pokemon("Pikachu", "/", new Stats(50, 10));
            Bagarre combat = new Bagarre();
            combat.demarrer(pkmn1.getNom(), pkmn2.getNom());
        }
    }


    @Test
    void demarrer_erreur_quand_pkm2_nom_null() {
        // given
        Pokemon pkmn2 = new Pokemon(null, "/", new Stats(10, 10));
        // when
        Throwable thrown = catchThrowable(() -> nomNull2(pkmn2));
        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le nom du Pokémon ne peut pas être null");
    }
    public void nomNull2(Pokemon pkmn2) {
        if (pkmn2.getNom() == null) {
            throw new IllegalArgumentException("Le nom du Pokémon ne peut pas être null");
        } else {
            System.out.println("Le combat commence !");
            Pokemon pkmn1 = new Pokemon("Pikachu", "/", new Stats(50, 10));
            Bagarre combat = new Bagarre();
            combat.demarrer(pkmn1.getNom(), pkmn2.getNom());
        }
    }


    @Test
    void demarrer_erreur_quand_pkm1_pkm2_meme_nom() {
        // given
        Pokemon pkmn1 = new Pokemon("Pikachu", "/", new Stats(10, 10));
        Pokemon pkmn2 = new Pokemon("Pikachu", "/", new Stats(10, 10));
        // when
        Throwable thrown = catchThrowable(() -> memePkmn(pkmn1,pkmn2));
        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Les pokemons doivent etre differents");
    }
    public void memePkmn(Pokemon pkmn1,Pokemon pkmn2) {
        if (pkmn1.getNom().equals(pkmn2.getNom())) {
            throw new IllegalArgumentException("Les pokemons doivent etre differents");
        } else {
            System.out.println("Le combat commence !");
            Bagarre combat = new Bagarre();
            combat.demarrer(pkmn1.getNom(), pkmn2.getNom());
        }
    }


    @Test
    void demarrer_erreur_quand_pkm1_non_trouve() {
        // given

        //when

        //then
    }

    @Test
    void demarrer_erreur_quand_pkm2_non_trouve() {
        // given
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url1", new Stats(1, 2))));
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("pikachu")));
        // when


        // then
    }

    @Test
    void demarrer_renvoie_pkm1_quand_pkm1_ok_gagne_pkm2_ok() {
        // given
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url1", new Stats(1, 2))));
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("pikachu")));
        // when

        //then

    }

    @Test
    void demarrer_renvoie_pkm1_quand_pkm1_ok_perd_pkm2_ok() {
        // given
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url1", new Stats(1, 2))));
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("pikachu")));
        // when

        //then

    }
}