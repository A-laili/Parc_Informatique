# Parc_Informatique

# Description
Ce projet vise à implémenter un système d'attribution de machines aux employés en utilisant Java EE (Jakarta EE). Il utilise des technologies telles que JSF (JavaServer Faces), PrimeFaces, Facelets, et EJB (Enterprise JavaBeans), avec GlassFish comme serveur d'application.

# Fonctionnalités

- Gestion des Employés : Ajouter, mettre à jour et supprimer des informations sur les employés, y compris le nom, le prénom et le salaire.
- Gestion des Machines : Ajouter, mettre à jour et supprimer des informations sur les machines, y compris la référence, la marque, la date d'achat, le prix et l'employé associé.
- Filtrage et Tri des Machines : Filtrer la liste des machines par employé et trier par date d'achat.
- Liste des Machines par Employé : Visualiser la liste des machines attribuées à un employé spécifique.
- Graphique d'Acquisition Annuelle : Incorporer un graphique illustrant les machines acquises par année.
- Support Multilingue : Rendre l'interface utilisateur disponible en anglais et en français.
- Authentification Admin : Transformer la page d'index en une page d'authentification en introduisant une entité "Admin" (id, nom d'utilisateur, mot de passe).


# Technologies Utilisées

- Java EE (Jakarta EE)
- JSF, PrimeFaces, Facelets
- EJB (Enterprise JavaBeans)
- GlassFish (Serveur d'Application)
- Base de Données (Entités "Employe" et "Machine")

#  Comment Exécuter le Projet Localement
## Prérequis :
- Java Development Kit 8 (JDK)
- Serveur d'Applications Java EE compatible (GlassFish recommandé)
- Maven (pour la gestion des dépendances)
- Base de Données MySQL (ou autre base de données configurée dans le projet)

## Configuration de la Base de Données :
- Créez une base de données nommée machines_employee (ou choisissez un nom approprié).
- Mettez à jour le fichier persistence.xml dans le répertoire src/META-INF avec les informations de connexion à la base de données.
- 
# Build et Déploiement :
- Utilisez Maven pour construire le projet : mvn clean install.
- Déployez le fichier WAR généré sur votre serveur d'applications Java EE.
- Accès à l'Application :
- Accédez à l'application via l'URL : http://localhost:8080/WebApplication5/faces/machine/MachineFilter.xhtml

# Capture d'écran
## Login
![image](https://github.com/A-laili/Parc_Informatique/assets/147451080/4348f91b-8d67-407d-96e0-b0b601f4990d)




# Auteurs
- Asmaa Laili
- Oussama Errahmi





