# Test del limite della DFS ricorsiva

`stress_stack_overflow.txt` contiene 100000 attività e 99999 dipendenze:
`T0 → T1 → ... → T99999`. Il grafo è aciclico, ma la profondità della
DFS ricorsiva può esaurire lo stack delle chiamate Java.

Dalla cartella del progetto:

```sh
javac *.java
java Main < test/stress_stack_overflow.txt
```

Per riprodurre il problema con uno stack esplicitamente ridotto:

```sh
java -Xss256k Main < test/stress_stack_overflow.txt
```

Il risultato atteso è un arresto con `java.lang.StackOverflowError`
e molte righe relative alle chiamate ricorsive DFS. La soglia dipende
dalla JVM e dalla dimensione dello stack; su questa macchina entrambe
le esecuzioni hanno prodotto l’errore. Non è un test di ciclo orientato.
Il file non rientra nel pattern `input*.txt` di `run_tests.sh`, perché
il suo fallimento è intenzionale.
