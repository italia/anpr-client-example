Dopo aver opportunamente modificato il file XML richiesta di invio con dati presenti nel sistema ANPR di test (vedi chiamata 6001 in [https://www.anpr.interno.it/portale/documents/20182/239162/SPECIFICHE_DI_INTERFACCIA_XSD_15052018.rar/a62bee5c-a447-415d-9d74-801cfa809170](https://www.anpr.interno.it/portale/documents/20182/239162/SPECIFICHE_DI_INTERFACCIA_XSD_15052018.rar/a62bee5c-a447-415d-9d74-801cfa809170)) lanciare il comando di compilazione e test come specificato nel file README del progetto.

Verificare che l'intero XML sia valido e "Well Formatted" con strumenti appositi usando i file XSD di riferimento alla chiamata da validare, altrimenti si otterà come risposta un generico

Esempio verifica XML chiamata WS6001 per Mac:

`Esperanza2:6001 enricosperanza1$ xmllint --noout --schema 6001certificazione.xsd 6001_888013_FREE.req`
`6001_888013_FREE.req:10: element dataDecorrenza: Schemas validity error : Element 'dataDecorrenza': '2018-05-28T10:20:11.543+02:00' is not a valid value of the atomic type 'xs:date'.`
`6001_888013_FREE.req:11: element dataDefinizionePratica: Schemas validity error : Element 'dataDefinizionePratica': '2018-05-28T10:20:11.543+02:00' is not a valid value of the atomic type 'xs:date'.`
`6001_888013_FREE.req:25: element datiControllo: Schemas validity error : Element 'datiControllo': Missing child element(s). Expected is one of ( bolloVirtuale, esenzioneDiritti, diritti ).`
`6001_888013_FREE.req fails to validate`

Apportando le necessarie modifiche (vedi [https://www.w3schools.com/xml/schema_dtypes_date.asp](https://www.w3schools.com/xml/schema_dtypes_date.asp)):
`Esperanza2:6001 enricosperanza1$ xmllint --noout --schema 6001certificazione.xsd 6001_888013_FREE.req`
`6001_888013_FREE.req validates`

Strumenti per la manipolazione e verifica di XML:

[http://xmlsoft.org/index.html](http://xmlsoft.org/index.html)