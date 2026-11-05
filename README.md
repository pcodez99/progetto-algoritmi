# Progetto algoritmi — pianificazione delle attività

Soluzione 2: ordinamento topologico con algoritmo di Kahn, liste di
adiacenza, array dei gradi entranti, coda FIFO e lista del risultato.
Tempo O(V + E); spazio totale O(V + E).

```sh
javac *.java
java Main < test/esempioInputProfessoeessa.txt
```

Se il risultato contiene meno di V attività, il programma segnala un ciclo
e l’impossibilità di pianificare tutte le attività. Non ricostruisce il ciclo.

Le classi `DirectedCycle` e `DepthFirstOrder` della prima soluzione DFS
restano disponibili per il confronto, ma non vengono chiamate da `Main`.
La versione DFS iniziale è nel commit `82b9531` sul ramo `main`.
Gli input di esempio sono in `test/`; non è presente uno script di test.
Relazione HTML e PDF locali sono esclusi dalla repository e ancora da aggiornare.
