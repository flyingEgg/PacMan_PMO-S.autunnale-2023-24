# Repository del progetto di PMO per la s. autunnale 2023/24

## Autori
- **Christian Giaconi** - 314045
- **Giacomo Rossi** - 314671

## Funzionalità Principali

- **Controllo di Pac-Man**: Utilizzare le frecce direzionali per controllare Pac-Man attraverso il labirinto.
- **Fantasmi**: Evitare i fantasmi che pattugliano il labirinto. Se un fantasma tocca Pac-Man, il giocatore perde una vita.
- **Power-Ups**: Mangiare i pallini grandi per ottenere temporaneamente l'abilità di mangiare i fantasmi.
- **Livelli**: Avanzare attraverso vari livelli con difficoltà crescente.
- **Sistema di punteggio**: Accumulare punti mangiando palline e fantasmi.

## Requisiti

- **Java 8** o superiore
- IDE come **Eclipse** o **IntelliJ IDEA** (opzionale)

## Guida all'Installazione

1. **Clonare il repository**:
    ```bash
    git clone https://github.com/flyingEgg/PacMan_PMO-S.autunnale-2023-24.git
    ```
2. **Navigare nella directory del progetto**:
    ```bash
    cd PacMan_PMO-S.autunnale-2023-24
    ```
3. **Compilare il progetto**:
   Se si sta utilizzando un IDE, importare semplicemente il progetto e compilarlo. Se si usa la riga di comando:
    ```bash
    javac -d out -sourcepath src src/main/java/app/Main.java src/main/java/controller/*.java src/model/*.java src/view/*.java
    ```
4. **Esegui il gioco**:
    ```bash
    java -cp out main.java.app.Main
    ```

## Utilizzo

Una volta avviata l'applicazione, utilizzare le frecce direzionali per muovere Pac-Man nel labirinto.
Cercare di mangiare tutte le palline per completare il livello.
Evitare i fantasmi, a meno che non si abbia mangiato un power-up, che permetterà di mangiarli temporaneamente.