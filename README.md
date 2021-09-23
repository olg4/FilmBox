# FilmBox 

<h5><b>Olga Baitemirova </b></h5>

### [Projet](/Projet) MOBG5 Android

## Remise1

### Aperçu
**Application en Anglais**<br/>


1. Au démarrage de l'application, un *splash screen* apparaît.<br/>
<img src="/img/file.png" width="30" alt="source files" style="float: left; margin-right: 20px;"/>
[SplashScreen.kt](/Projet/app/src/main/java/com/mobg5/filmbox/SplashScreen.kt)&nbsp;&nbsp;&nbsp;[splash_screen.xml](/Projet/app/src/main/res/drawable/splash_screen.xml)<br/>
<img src="/img/SplashScreen.png" width="200" alt="splash screen"/>

2. Le *splash screen* mène à l'écran de connexion, lequel demande à l'utilisateur d'entrer une adresse mail. <br/>
<img src="/img/file.png" width="30" alt="source files" style="float: left; margin-right: 20px;"/>
[LoginActivity.kt](/Projet/app/src/main/java/com/mobg5/filmbox/ui/login/LoginActivity.kt)&nbsp;&nbsp;&nbsp;[activity_login.xml](/Projet/app/src/main/res/layout/activity_login.xml)<br/>

3. Si un nom d'utilisateur (le format e-mail) est erroné et que l'utilisateur appuie sur ***Sign in*** ou appuie sur ***Enter*** un message Toast apparaît à l'écran.<br/>

4. Une fois que l'utilisateur entre un nom d'utilisateur valide il peut se connecter.<br/>
par exemple:<br/>
<img src="/img/Username.png" width="200" alt="enter username" style="float: right;"/>

5. L'écran de connexion mène à l'écran principal de l'application une fois le nom d'utilisateur validé. Un Toast apparaît lors de la transition.<br/>
<img src="/img/file.png" width="30" alt="source files" style="float: left; margin-right: 20px;"/>
[MainActivity.kt](/Projet/app/src/main/java/com/mobg5/filmbox/MainActivity.kt)&nbsp;&nbsp;&nbsp;[activity_main.xml](/Projet/app/src/main/res/layout/activity_main.xml)<br/>
<img src="/img/Logged.png" width="200" alt="logged"/>

6. L'écran d'information sur l'application et le développeur (About) est accessible depuis le ***Navigation Drawer***.<br/>
<img src="/img/file.png" width="30" alt="source files" style="float: left; margin-right: 20px;"/>
[About.kt](/Projet/app/src/main/java/com/mobg5/filmbox/About.kt)&nbsp;&nbsp;&nbsp;[fragment_about.xml](/Projet/app/src/main/res/layout/fragment_about.xml)<br/>
<img src="/img/NavDrawer.png" width="200" alt="navigation drawer"/>
<img src="/img/AboutFragment.png" width="190" alt="about screen" style="float: right;"/>
<br/>

#### Lint :

> **Android** *2 warnings*
> - **Lint** *2 warnings*
>   - Performances *2 warnings*
>     - Overdraw: Painting regions more than once *2 warnings*
>       - activity_main_nav_header.xml *1 warning*
>         - Possible overdraw: Root element paints background'@color/mainColor' with a theme that also paints a background (inferred theme is '@style/Theme.AppCompat')
>       - fragment_about.xml *1 warning*
>         - Possible overdraw: Root element paints background '@color/colorPrimaryDark' with a theme that also paints a background (inferred theme is '@style/Theme.AppCompat')


#### Build:

> **API 'BaseVariant.getApplicationIdTextResource' is obsolete and has been replaced with 'VariantProperties.applicationId'.**


## Remise 2
Mises à jour du visuel sur l'écran de connexion, avec l'ajout d'une image et désormais si l'utilisateur clique en dehors de l'EditText et du clavier lui-même, le clavier se referme.
Effet coins ronds grâce à la dépendance 
> implementation 'de.hdodenhof:circleimageview:3.1.0'

Et améliorations des fichiers **xml** pour l'écran de connexion et l'écran About.<br/>

<img src="/img/connectionScreen.png" width="200" alt="navigation drawer"/><br/><br/>

#### Room:
- Si l'utilisateur entre pour la première fois un nom (son adresse mail), il s'inscrit et son nom est enregistré dans la base de donnée locale (les noms d'utilisateurs sont uniques, donc ils sont choisis comme clé primaire de la table représentant l'utilisateur).
A l'ajout d'un nouvel utilisateur, une coroutine est créée.

- Si l'utilisateur entre un nom déjà enregistré dans la base de données locale, dans un premier temps, avec les deux premières lettres il peut voir quels noms existent déjà, ensuite il peut sélectionner un des ces noms et se connecter. Dans ce cas de figure, la base de données est mise à jour. Plus précisément, la date de connexion change.
Lors de la mise à jour, une autre coroutine est créee.

<img src="/img/Autocompletetextview.png" width="200" alt="navigation drawer"/><br/><br/>

#### Lint :
Pas de changement par rapport à la première remise.<br/><br/>

## Remise 3
<img src="/img/TMDB.jpg" width="52"/>
<img src="/img/FilmBox.png" width="50"/><br/><br/>

<br/>

La première chose qui change de la [Remise 2](#Remise_2) est la couleur du *splash screen* et de l'*icône de démarrage*.

Ensuite:

<br/>**[The Movie Database API](https://developers.themoviedb.org/3/getting-started/regions)**

> *The Movie Database (TMDb) is a popular, user editable database for movies and TV shows*

**FilmBox** utilise des requêtes **GET**, pour afficher toute une série de films ainsi qu'une partie des informations qui leur sont associées. Et l'utilisateur peut créer des listes en local afin de "collectionner" les films qu'il souhaite.

**Page d'accueil de FilmBox**

<img src="/img/Homepage.jpg" width="200" />

- requêtes vers l'API grâce à **Retrofit**
- requêtes et stockage en local grâce à **Room**

A chaque fois que l'utilisateur souhaite consulter une liste, une requête vers l'API est faite grâce à l'id, ce qui permet de restituer les films. 

Voici à quoi ressemble les tables pour la gestion des listes et des films qu'elles contiennent:

- La table des listes:

<img src="/img/lists_table.png" />

- La table des films:

<img src="/img/movies_table.png" />

<br/><br/>Et enfin, l'application est construite d'après le concept de navigation entre fragments, depuis une activité commune.
<br/>Voici le graphe:

<img src="/img/graph.png"/>


<br/>Ajout de ce qui suit, dans le manifest:

```xml
<application
	android:usesCleartextTraffic="true">
<application/>
```
afin d'éviter l'erreur suivante:

> failure **CLEARTEXT:** communication to api.themoviedb.org not permitted by network security policy


#### Lint:

> **Android** *1 warning*
> - **Lint** *1 warning*
>   - Correctness *1 warning*
>     - Use FragmentContainerView instead of the <fragment> tag
activity_main.xml *1 warning*
>        - Replace the <fragment> tag with FragmentContainerView.

<br/>Si je remplace ***fragment*** par ***FragmentContainerView*** une erreur survient et le **MainActivity** ne peut être créé.
<br/>Et enfin, le warning concernant **Kotlin** serait un bug.