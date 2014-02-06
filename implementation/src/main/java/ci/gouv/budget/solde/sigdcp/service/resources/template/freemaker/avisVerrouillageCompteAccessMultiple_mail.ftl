<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<b>Objet : Avis de verrouillage de compte "SIGDCP" pour ${motifVerrouillage}</b>
		<br>Bonjour ${nomPrenomsAgentEtat}
		<p>
			Votre compte vient d'être verrouillé pour échec à trois tentatives de connexion.
			Veuillez examiner les détails de la tentative de connexin :
        </p>
        <p>
        	Date (Jour/Heure) : <b>${dateHeureVerrouillage}</b><br>
            Adresse IP : <b>${adresseIP}</b><br>
            Localisation : <b>${adresseGeographique}</b><br>
        </p>
        <p>
        	<b>N.B :</b> Si vous n'avez pas tenté de vous connecter, il est possible qu'une tierce personne ait tenté d'usurper votre votre identité.
			<br>Nous vous conseillons de redoubler de vigilance dans la gestion de votre mot de passe.
            <p>Si contrairement, vous aviez tenté de vous connecter mais avec certaines difficultés, nous vous conseillons de
               retrouver le premier message qui vous a été adressé par "SIGDCP" après la création de votre compte, ou encore
               utilisez l'une des fonctionnalités suivantes :<br>
               <i><a href="#">- Mot de passe oublié, cliqueze ici.</a></i><br>
               <i><a href="#">- Login oublié, cliqueze ici.</a></i>
            </p>
            <p>
            Toutefois, vous ne pourrez accéder à votre compte après avoir solliciter le service de "Déverrouillage de compte".
			<p>
			Cordialement;<br>
			L'équipe "SIGDCP" de la Direction Générale du Budget et des Finances.
			</p>
	</body>
</html>