package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.erreurs.ErreurPokemonNonRenseigne;
import com.montaury.pokebagarre.erreurs.ErreurRecuperationPokemon;
import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class BagarreTest {
    private PokeBuildApi fausseApi;
    private Bagarre bagarre;

    @BeforeEach
    void preparer() {
        fausseApi = Mockito.mock(PokeBuildApi.class);
        bagarre = new Bagarre(fausseApi);
    }
    @Test
    void devrait_lever_une_erreur_si_le_premier_pokemon_est_null() {
        // GIVEN
        Throwable erreur = Assertions.catchThrowable(()->bagarre.demarrer(null, "scarabrute"));

        // THEN
        assertThat(erreur).isInstanceOf(ErreurPokemonNonRenseigne.class)
                .hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void devrait_lever_une_erreur_si_le_premier_pokemon_est_vide() {
        // GIVEN
        Throwable erreur = Assertions.catchThrowable(()->bagarre.demarrer("", "scarabrute"));

        // THEN
        assertThat(erreur).isInstanceOf(ErreurPokemonNonRenseigne.class)
                .hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void devrait_lever_une_erreur_si_le_second_pokemon_est_null() {
        // GIVEN
        Throwable erreur = Assertions.catchThrowable(()->bagarre.demarrer("pikachu", null));

        // THEN
        assertThat(erreur).isInstanceOf(ErreurPokemonNonRenseigne.class)
                .hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    void devrait_lever_une_erreur_si_le_second_pokemon_est_vide() {
        // GIVEN
        Throwable erreur = Assertions.catchThrowable(()->bagarre.demarrer("pikachu", ""));

        // THEN
        assertThat(erreur).isInstanceOf(ErreurPokemonNonRenseigne.class)
                .hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    void devrait_echouer_si_erreur_api_avec_premier_pokemon() {
        // GIVEN
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("pikachu")));
        Mockito.when(fausseApi.recupererParNom("scarabrute"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("scarabrute", "url_scarabrute", new Stats(2,3))));
        // WHEN
        CompletableFuture<Pokemon> futurVainqueur = bagarre.demarrer("pikachu", "scarabrute");

        // THEN
        assertThat(futurVainqueur)
                .failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur 'pikachu'");
    }   @Test
    void devrait_echouer_si_erreur_api_avec_second_pokemon() {
        // GIVEN
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url_pikachu", new Stats(1,2))));

        Mockito.when(fausseApi.recupererParNom("scarabrute")).
                thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("scarabrute")));
        // WHEN
        CompletableFuture<Pokemon> futurVainqueur = bagarre.demarrer("pikachu", "scarabrute");

        // THEN
        assertThat(futurVainqueur)
                .failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur 'scarabrute'");
    }

    @Test
    void devrait_retourner_le_premier_pokemon_s_il_est_vainqueur() {
        // GIVEN
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url_pikachu", new Stats(3,4))));
        Mockito.when(fausseApi.recupererParNom("scarabrute"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("scarabrute", "url_scarabrute", new Stats(1,2))));

        // WHEN
        CompletableFuture<Pokemon> futurVainqueur = bagarre.demarrer("pikachu", "scarabrute");

        // THEN
        assertThat(futurVainqueur)
                .succeedsWithin(Duration.ofSeconds(2))
                .satisfies(pokemon -> {
                            assertThat(pokemon.getNom()).isEqualTo("pikachu");
                            assertThat(pokemon.getUrlImage()).isEqualTo("url_pikachu");
                            assertThat(pokemon.getStats().getAttaque()).isEqualTo(3);
                            assertThat(pokemon.getStats().getDefense()).isEqualTo(4);
                        }
                );
    }
    @Test
    void devrait_retourner_le_second_pokemon_s_il_est_vainqueur() {
        // GIVEN
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url_pikachu", new Stats(1,2))));
        Mockito.when(fausseApi.recupererParNom("scarabrute"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("scarabrute", "url_scarabrute", new Stats(3,4))));

        // WHEN
        CompletableFuture<Pokemon> futurVainqueur = bagarre.demarrer("pikachu", "scarabrute");

        // THEN
        assertThat(futurVainqueur)
                .succeedsWithin(Duration.ofSeconds(2))
                .satisfies(pokemon -> {
                            assertThat(pokemon.getNom()).isEqualTo("scarabrute");
                            assertThat(pokemon.getUrlImage()).isEqualTo("url_scarabrute");
                            assertThat(pokemon.getStats().getAttaque()).isEqualTo(3);
                            assertThat(pokemon.getStats().getDefense()).isEqualTo(4);
                        }
                );
    }
}