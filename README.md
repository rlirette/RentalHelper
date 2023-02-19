# RentalHelper
Application helping rental mails

## Prérequis
- Installer JDK 15 (l'API utilise la JDK 14)
- Installer maven
- Installer le plugin Lombok
- Installer PostgreSQL
- DBeaver (conseillé)

## Installation
### Runner
- Configurer l'executeur en
  - Sélectionnant le fichier Main
  - Ajoutant les variables d'environnement : mail.username=<email de l'emetteur>;mail.password=<mot de passe de la boite mail>

### DB
- Créer une DB au nom : rentalhelperdb
- Créer le schéma : 
  - batch
  - api

## Première exécution
Lancer l'initiation de la DB en appellant l'URL suivant :
- http://localhost:8081/api/event/init : initialisation des données de la DB

## Executables
- http://localhost:8081/api/event/monthly/mail : envoie de mail du mois
- http://localhost:8081/api/event/daily : mise à jour ponctuel des données 
- http://localhost:8081/api/event/daily/mail : envoie de mail du de mise à jour

## Erreurs
- Server returned HTTP response code: 400 for URL: <URL d'une source de données> : 
  - l'URL n'est pas à jour. A mettre à jour ou a supprimer dans la table **source_ics**
- Pop up "Can't sand email cause : Authentication failed"
  - vérifier la config qui est mise dans la table **source_mail_config** (mail48.lwspanel.com pour lws, smtp.gmail.com pour gmail)
- Pop up "Can't sand email cause : Failed message [...] <adresse email> : Sender adress rejected [...]"
  - Vérifier l'adresse mail de l'emmeteur dans la table **source_mail**
  - En profiter pour vérifier les récepteurs, table **source_mail_content_header_recipient** et les personnes en copie, table **source_mail_content_header_copy**

