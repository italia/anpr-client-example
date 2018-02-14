# Client Java di esempio per il test di connessione ad ANPR

Per l'installazione e l'esecuzione del test di connessione, **dopo aver inserito le credenziali**, eseguire
```
mvn clean install
```
(questo comando esegue tutte le operazioni, compreso l'esecuzione del test di connessione)

**Nota**: è necessario utilizzare un ambiente Java8.
Si può utilizzare la utility `jenv` per impostare il proprio ambiente di sviluppo.
In questo caso, una volta attivata la versione 1.8, richiamare maven con
```
jenv exec mvn clean install
```

Una classe di test della connessione si trova nella cartella `src/test`

# Impostare le credenziali

Per rendere il client utilizzabili, è importante impostare, al primo utilizzo, i certificati con le credenziali di test.
Per personalizzare il certificato bisogna:

- Modificare `src/main/resources/config/FREE_Keystore.properties` inserendo i riferimenti al proprio ceritficato, al codice della postazione e al PIN del certificato.
- Aggiungere il certificato nella cartella `keystore/FREE`

Per ottenere delle credenziali di test, fintanto che non sarà approntato un modulo per il rilascio automatico, si prega di scrivere a [credenziali.anpr.test@developers.italia.it](mailto:credenziali.anpr.test@developers.italia.it).
