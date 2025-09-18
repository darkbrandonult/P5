# SafetyNet Alerts - Présentation du Projet
## OpenClassrooms Projet 5 - Parcours Développeur d'Application Java

---

## 📋 **Vue d'ensemble du Projet**

**SafetyNet Alerts** est un système d'API REST complet de gestion d'urgence conçu pour fournir des informations de sécurité critiques aux services d'urgence. L'application gère les personnes, les casernes de pompiers et les dossiers médicaux pour permettre une coordination rapide de la réponse d'urgence.

### **Objectifs Clés Atteints**
- ✅ API REST complète avec opérations CRUD intégrales
- ✅ Points de terminaison d'alerte d'urgence pour les premiers intervenants
- ✅ Suite de tests complète avec 88% de couverture de code
- ✅ Application Spring Boot prête pour la production
- ✅ Capacités professionnelles de journalisation et de surveillance

---

## 🏗️ **Architecture Technique**

### **Stack Technologique**
- **Framework**: Spring Boot 2.7.18
- **Version Java**: 21 (LTS)
- **Outil de Build**: Maven 3.6+
- **Tests**: JUnit 5, Mockito, Spring Boot Test
- **Couverture de Code**: JaCoCo 0.8.10
- **Journalisation**: Logback avec SLF4J
- **Format de Données**: JSON (Jackson)

### **Structure du Projet**
```
src/main/java/com/safetynet/alerts/
├── controller/          # Points de terminaison de l'API REST
├── service/            # Couche de logique métier
├── model/              # Modèles de données (Person, Firestation, MedicalRecord)
├── dto/                # Objets de Transfert de Données pour les réponses
└── config/             # Configuration de l'application

src/test/java/
├── controller/         # Tests d'intégration et unitaires des contrôleurs
├── service/            # Tests de la couche service
└── dto/                # Tests de validation des DTO
```

---

## 🎯 **Fonctionnalités Principales Livrées**

### **1. APIs de Gestion des Données**
#### **Gestion des Personnes**
- `GET /persons` - Lister toutes les personnes
- `GET /persons/name/{firstName}/{lastName}` - Obtenir une personne spécifique
- `POST /persons` - Ajouter une nouvelle personne
- `PUT /persons/name/{firstName}/{lastName}` - Mettre à jour une personne
- `DELETE /persons/name/{firstName}/{lastName}` - Supprimer une personne

#### **Gestion des Casernes**
- `GET /firestations` - Lister toutes les casernes de pompiers
- `GET /firestations/address/{address}` - Obtenir une caserne par adresse
- `POST /firestations` - Ajouter un nouveau mappage de caserne
- `PUT /firestations/address/{address}` - Mettre à jour une caserne
- `DELETE /firestations/address/{address}` - Supprimer une caserne

#### **Gestion des Dossiers Médicaux**
- `GET /medicalrecords` - Lister tous les dossiers médicaux
- `GET /medicalrecords/name/{firstName}/{lastName}` - Obtenir un dossier spécifique
- `POST /medicalrecords` - Ajouter un nouveau dossier médical
- `PUT /medicalrecords/name/{firstName}/{lastName}` - Mettre à jour un dossier
- `DELETE /medicalrecords/name/{firstName}/{lastName}` - Supprimer un dossier

### **2. Points de Terminaison d'Alerte d'Urgence**
#### **Informations d'Urgence Critiques**
- `GET /firestation?stationNumber={number}` - Personnes couvertes par la caserne
- `GET /childAlert?address={address}` - Enfants à une adresse spécifique
- `GET /phoneAlert?firestation={number}` - Numéros de téléphone pour la zone de la caserne
- `GET /fire?address={address}` - Résidents et caserne pour une adresse
- `GET /flood/stations?stations={numbers}` - Foyers par caserne de pompiers
- `GET /personInfo?firstName={firstName}&lastName={lastName}` - Détails d'une personne
- `GET /communityEmail?city={city}` - Tous les emails des résidents de la ville

---

## 🧪 **Assurance Qualité & Tests**

### **Réalisation de la Couverture de Tests**
- **Couverture Globale**: **88%** (dépasse l'exigence de 85%)
- **Couverture des Branches**: **85%**
- **Total des Tests**: **133 tests** (tous réussis)
- **Catégories de Tests**: Tests unitaires, Tests d'intégration, Tests de contrôleurs

### **Stratégie de Test**
#### **Tests Unitaires**
- Validation de la logique métier de la couche service
- Construction et validation des objets DTO
- Vérification du comportement des méthodes individuelles

#### **Tests d'Intégration**
- Tests du cycle complet requête/réponse HTTP
- Intégration Contrôleur-Service-Modèle
- Validation du flux de données réel

#### **Gestion des Données de Test**
- Environnements de test isolés
- Données fictives pour des tests cohérents
- Couverture complète des cas limites

### **Exclusions de Couverture (Justifiées)**
- **Classes Modèles**: POJOs simples sans logique métier
- **Classe Application Principale**: Code de démarrage Spring Boot
- **Classes de Configuration**: Composants gérés par le framework

---

## 📊 **Performance & Surveillance**

### **Implémentation de la Journalisation**
- **Journalisation Console**: Environnement de développement
- **Journalisation Structurée**: Format prêt pour la production
- **Niveaux de Log**: INFO pour les opérations, ERROR pour les exceptions
- **Suivi Requête/Réponse**: Piste d'audit HTTP complète

### **Surveillance de l'Application**
- **Spring Boot Actuator**: Vérifications de santé et métriques
- **Point de terminaison**: `/actuator/health` pour la surveillance
- **Préparation Production**: Capacités de surveillance intégrées

---

## 🔧 **Build & Déploiement**

### **Configuration Maven**
#### **Plugins Clés**
- **Plugin Maven Spring Boot**: Packaging de l'application
- **Plugin JaCoCo**: Rapports de couverture de code
- **Plugin Surefire**: Exécution et rapports de tests
- **Plugin Compiler**: Compilation Java 21

#### **Commandes de Build**
```bash
mvn clean compile          # Compiler l'application
mvn test                   # Exécuter tous les tests
mvn jacoco:report         # Générer le rapport de couverture
mvn spring-boot:run       # Démarrer l'application
mvn package               # Créer le JAR exécutable
```

### **Portails de Qualité**
- Tous les tests doivent réussir avant le déploiement
- Exigence minimale de 85% de couverture de code
- Aucune erreur de compilation ou avertissement
- Packaging JAR réussi

---

## 🎨 **Excellence de la Conception API**

### **Principes de Conception RESTful**
- **URLs basées sur les ressources**: Structure de points de terminaison claire et intuitive
- **Sémantique des Méthodes HTTP**: Utilisation appropriée de GET, POST, PUT, DELETE
- **Codes de Statut**: Codes de réponse HTTP appropriés
- **Réponses JSON**: Format de données cohérent

### **Exemples de Structure de Réponse**
#### **Réponse de Couverture de Caserne**
```json
{
  "persons": [
    {
      "firstName": "John",
      "lastName": "Boyd",
      "address": "1509 Culver St",
      "phone": "841-874-6512"
    }
  ],
  "adultCount": 5,
  "childCount": 1
}
```

#### **Réponse d'Alerte d'Urgence**
```json
[
  {
    "phone": "841-874-6512"
  },
  {
    "phone": "841-874-7878"
  }
]
```

---

## 🚀 **Réalisations Techniques**

### **Métriques de Qualité du Code**
- **Architecture Propre**: Séparation des préoccupations
- **Principes SOLID**: Code maintenable et extensible
- **Patterns de Conception**: Couche service, pattern DTO
- **Gestion d'Erreurs**: Gestion gracieuse des exceptions

### **Gestion des Données**
- **Stockage Fichier JSON**: Solution de données légère et portable
- **Validation des Données**: Validation et assainissement des entrées
- **Accès Concurrent**: Opérations thread-safe
- **Intégrité des Données**: Gestion d'état cohérente

### **Meilleures Pratiques de Développement**
- **Contrôle de Version**: Git avec commits significatifs
- **Documentation du Code**: Documentation inline complète
- **Développement Dirigé par les Tests**: Les tests guident l'implémentation
- **Intégration Continue**: Pipeline automatisé de build et test

---

## 📈 **Livrables du Projet**

### **Code Source**
- ✅ Application Spring Boot complète
- ✅ Tous les points de terminaison requis implémentés
- ✅ Suite de tests complète
- ✅ Documentation et commentaires

### **Rapports & Documentation**
- ✅ Rapports de couverture JaCoCo (format HTML)
- ✅ Rapports de tests Surefire
- ✅ Conformité aux spécifications techniques
- ✅ Documentation API

### **Artefacts de Build**
- ✅ Fichier JAR exécutable
- ✅ Structure de projet Maven
- ✅ Fichiers de configuration
- ✅ Données de test et ressources

---

## 🎯 **Satisfaction des Critères d'Évaluation**

### **Exigences Fonctionnelles** ✅
- Toutes les opérations CRUD implémentées
- Points de terminaison d'alerte d'urgence fonctionnels
- Relations de données appropriées maintenues
- Persistance des données de fichier JSON

### **Exigences Techniques** ✅
- Java 21 avec Spring Boot 2.7.18
- Système de build Maven
- Framework de test JUnit 5
- 88% de couverture de code (dépasse l'exigence de 85%)

### **Standards de Qualité** ✅
- Structure de code propre et maintenable
- Couverture de tests complète
- Implémentation de journalisation professionnelle
- Application prête pour la production

### **Documentation** ✅
- Alignement des spécifications techniques
- Commentaires et documentation du code
- Rapports de tests et métriques de couverture
- Instructions de build et déploiement

---

## 🏆 **Points Forts du Succès du Projet**

1. **Objectif de Couverture Dépassé**: Atteint 88% vs requis 85%
2. **Zéro Échec de Test**: 133/133 tests réussis
3. **Implémentation API Complète**: Tous les points de terminaison spécifiés livrés
4. **Qualité Production**: Journalisation, surveillance et gestion d'erreurs professionnelles
5. **Meilleures Pratiques**: Architecture propre, principes SOLID, tests complets

---



### **Considérations de Scalabilité**
- Architecture microservices
- Communication pilotée par les événements
- Mise en cache distribuée
- Capacités d'équilibrage de charge

---

## 📞 **Contact & Dépôt**

**Dépôt du Projet**: [GitHub - P5 SafetyNet Alerts](https://github.com/darkbrandonult/P5)

**Projet**: OpenClassrooms Développeur d'Application Java - Projet 5  
**Date**: Septembre 2025  


