# Limite della DFS ricorsiva

Nella prima soluzione DFS, una catena di 100000 attività ha prodotto
`StackOverflowError`: troppe chiamate ricorsive contemporaneamente attive.
Il limite dipende dalla JVM e dalla dimensione del suo stack.

Il `Main` della soluzione 2 usa Kahn senza ricorsione: lo stesso caso,
generato temporaneamente per la verifica, è stato completato anche
con `java -Xss256k`. Non si attende più un overflow dello stack.
Le classi DFS conservate nel progetto restano ricorsive.
