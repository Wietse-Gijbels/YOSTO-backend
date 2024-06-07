# YOSTO backend
Welkom op de readme pagina van de backend van onze YOSTO applicatie. 
Hier ga je alle nodige stappen terugvinden over hoe je het project op een correcte manier kan opzetten.
## Stappen ##
- Clone deze repository
- Zorg ervoor dat er op je lokale machine een postgreSQL databank aanwezig is, deze moet de naam `postgres` hebben alsook het wachtwoord moet `postgres` zijn.
- Om de email-service van de applicatie op een correcte manier te laten werken, wordt er gebruik gemaakt van Sendgrid. Hiervoor is een API-key nodig die je bij ons kan verkrijgen
- maar in de `src/main/resources` folder van het project een `application.properties` bestand aan dat dezelfde info bevat als het `application.properties.example` bestand, aan het nieuwe bestand moet je de API-key van Sendgrid toevoegen.
- Daarnaast moeten ook de volgende folders aangemaakt worden: `src/main/resources/static/images` en `src/main/resources/static/images/vragen`
- Hierna moet je ervoor zorgen dat de dependencies van het `pom.xml` bestand correct geïnitialiseerd worden door maven te builden
- Ten slotte run je het project (via IntelliJ kan dit door op de groene `play-button` bovenaan het scherm te klikken)
