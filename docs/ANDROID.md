# Pixel Sheet Converter per Android

La build 0.13 introduce un selettore grafico accessibile dal pulsante **Tema**. La scelta viene conservata sul telefono e ripristinata agli avvii successivi. Sono disponibili Windows 2000, XP Luna, Aero, KDE Keramik, KDE Oxygen, GNOME Clearlooks, Ubuntu Human, Terminale GNU/Linux e Flubber. Dal medesimo menu si può inoltre importare un tema JSON creato nel Pixel Sheet Theme Studio dal browser. Il flusso resta diviso in tre schermate — Fotocamera, Conversione e Risultato — raggiungibili dalla barra superiore e collegate da passaggi automatici.

I temi cambiano superfici, colori, bordi, profondità, tipografia e pulsanti senza modificare gli algoritmi, i file esportati o il flusso OpenSea. Windows 2000 è il tema iniziale per le nuove installazioni; il tema precedentemente scelto resta attivo dopo la chiusura dell'app.

Il tema mantiene un'impostazione elegante e leggibile: le decorazioni rimangono a bassa opacità, i pannelli utilizzano doppi bordi luminosi e il verde MSN identifica selezione, anteprime e azioni principali senza dominare l'interfaccia.

La fotocamera non usa più fasce ondulate separate: il pannello superiore incorpora il proprio bordo curvo e i controlli inferiori sono contenuti in una nuvola sospesa con fotografia visibile intorno. I pulsanti utilizzano raggi più generosi, da 12 a 16 dp, senza diventare completamente ovali.

Il bordo superiore dispone di due linee ondulate non coincidenti e lascia maggiore respiro sotto i pulsanti. La nuvola inferiore è ondulata sui quattro lati e raccordata da fillet ampi agli angoli.

Il layout conserva bordi sottili, pannelli chiari e comandi con altezza uniforme. Gestisce inoltre le aree riservate alle barre di sistema e al foro della fotocamera Samsung. Le anteprime cambiano altezza in base alla larghezza utile del dispositivo e le etichette principali restano su una sola riga per evitare disallineamenti anche con caratteri di sistema ingranditi.

La modalità Fotocamera è immersiva: l'anteprima CameraX si estende fino ai bordi fisici, eliminando la fascia vuota superiore, mentre i controlli rispettano automaticamente foro della fotocamera e aree di sistema. Zoom, esposizione, flash, scatto, galleria e cambio camera sono sovrapposti all'immagine. Una griglia fotografica 3×3 può essere attivata dal comando **Griglia**. Le barre di sistema possono ricomparire temporaneamente con uno scorrimento dal bordo.

La prima build Android è una versione di prova destinata inizialmente al Samsung Galaxy A36 e ai dispositivi Android 8 o successivi.

## Funzioni della build di prova

- Nove temi selezionabili e persistenti: Windows 2000, XP Luna, Aero, KDE Keramik, KDE Oxygen, GNOME Clearlooks, Ubuntu Human, Terminale GNU/Linux e Flubber.
- Importazione di temi personali `.json` esportati dal Pixel Sheet Theme Studio; il file viene verificato, applicato e conservato sul telefono.
- Anteprima CameraX e scatto diretto.
- Fotocamera posteriore o anteriore.
- Anteprima fotografica immersiva a tutto schermo e griglia 3×3 attivabile.
- Zoom, compensazione EV, flash e tap-to-focus quando supportati dal dispositivo.
- Importazione dalla galleria.
- Navigazione a tre fasi con anteprima separata dell'immagine originale e del risultato.
- Conversione locale e offline con RGB primari, RGB con bianco e nero, CMY e scala di grigi a 8 livelli.
- Anteprime adattive al rapporto d'aspetto dell'immagine, con limiti coerenti con lo spazio disponibile.
- RGB classico, RGB lineare, OKLab e CIELAB Delta E 2000.
- Nessun dithering, Floyd-Steinberg, Floyd serpentino, Atkinson, Bayer 4x4, Sierra Lite, Stucki e Jarvis-Judice-Ninke.
- Calcolo delle dimensioni fisiche mediante passo pixel.
- Statistiche RGB.
- Esportazione PNG e XLSX con riempimenti statici.
- Pubblicazione mobile su OpenSea: il comando **Pubblica su OpenSea** salva il PNG e apre il convertitore web nel browser MetaMask, dove il file può essere selezionato e pubblicato sulla rete Base.
- Watermark opzionale nel PNG: composizione con due dodecaedri, ombra viola e segmenti neri, con sfondo esterno trasparente e facce interne bianche. Il logo viene disposto in basso a destra con dimensione e margine proporzionali all'immagine.

## Installazione sul Samsung Galaxy A36

1. Scaricare `PixelSheetConverter-Android-test.apk` dalla Release o dall'artefatto della GitHub Action.
2. Spostare il file nella cartella `Download` del telefono, se è stato scaricato da PC.
3. Aprire **Archivio > Download** e toccare il file APK.
4. Se richiesto, autorizzare **Archivio** in **Sicurezza e privacy > Altre impostazioni di sicurezza > Installa app sconosciute**.
5. Se il Blocco automatico Samsung impedisce l'installazione, disattivarlo temporaneamente, installare e riattivarlo.
6. Concedere il permesso Fotocamera al primo avvio.

## Importare un tema personale

1. Crea il tema nel Pixel Sheet Theme Studio dal computer e premi **Esporta JSON**.
2. Copia il file `.json` nella cartella Download del telefono.
3. Nell'app premi **Tema**, quindi **Importa tema JSON…**.
4. Seleziona il file. Dopo la verifica, il tema viene applicato e rimane disponibile come tema personale.

## Pubblicare una Pixel Sheet su OpenSea

1. Installa MetaMask sul telefono e configura il wallet sulla rete Base.
2. Scatta o importa un'immagine, genera la conversione e apri la schermata **Risultato**.
3. Premi **Pubblica su OpenSea** e scegli dove salvare il PNG.
4. L'app apre automaticamente il convertitore nel browser MetaMask; se MetaMask non è installato, apre il browser predefinito.
5. Nel convertitore web premi **Apri immagine** e seleziona il PNG appena salvato.
6. Genera l'anteprima, compila titolo e descrizione, quindi controlla e conferma separatamente preparazione e transazione.

Il salvataggio del PNG e l'apertura del sito non creano una NFT e non consumano gas. La NFT viene creata soltanto dopo la conferma esplicita della transazione nel wallet.

## Firma e aggiornamenti

L'APK iniziale è firmato con la chiave di debug generata durante la compilazione ed è destinato al collaudo. Prima della prima release stabile verrà configurata una chiave di firma permanente custodita nei GitHub Actions Secrets. Da quel momento gli aggiornamenti potranno essere installati sopra la versione precedente senza disinstallarla.

## Sviluppo locale

Aprire la cartella `android-app` con Android Studio. Il progetto usa Kotlin, View Binding, CameraX e Android API 35, con `minSdk 26`.
