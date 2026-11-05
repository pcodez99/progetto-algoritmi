# Progetto algoritmi — pianificazione delle attività

Soluzione 2: ordinamento topologico con algoritmo di Kahn, liste di
adiacenza, array dei gradi entranti, coda FIFO e lista del risultato.
Tempo O(V + E); spazio totale O(V + E).

```sh
javac *.java
java Main < test/esempioInputProfessoeessa.txt
```

Se il risultato contiene meno di V attività, il programma segnala un ciclo
e l’impossibilità di pianificare tutte le attività. Solo in questo caso
`DirectedCycle` esegue DFS e ricostruisce un ciclo, stampato con i nomi
delle attività e le frecce fra di esse.

Sui DAG viene eseguito soltanto Kahn. La DFS ricorsiva viene chiamata
solo dopo una pianificazione incompleta; su grafi ciclici molto profondi
resta possibile `StackOverflowError`. `DirectedCycle` è stata ripristinata
da Git senza modificarla.
La versione DFS iniziale è nel commit `82b9531` sul ramo `main`.
Gli input di esempio sono in `test/`;
