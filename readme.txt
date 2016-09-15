[opengl] triangle animé-----------------------
Url     : http://codes-sources.commentcamarche.net/source/52805-opengl-triangle-animeAuteur  : cs_loloof64Date    : 13/08/2013
Licence :
=========

Ce document intitulé « [opengl] triangle animé » issu de CommentCaMarche
(codes-sources.commentcamarche.net) est mis à disposition sous les termes de
la licence Creative Commons. Vous pouvez copier, modifier des copies de cette
source, dans les conditions fixées par la licence, tant que cette note
apparaît clairement.

Description :
=============

Ce code, pr&eacute;vu pour les Android dont l'api vaut au moins 3, permet d'affi
cher un triangle rouge sur fond blanc qui avance progressivement vers l'avant de
 l'&eacute;cran, avant de dispara&icirc;tre.
<br />
<br />Pour cela, on utilis
e l'api OpenGL ES 1.0 d'Android (la version 1.5 d'Android ne dispose pas d'Open 
GL ES 2.0 :=( ).
<br />
<br />Je vous poste aussi les liens sur des tutos qui 
m'ont &eacute;t&eacute; tr&egrave;s utiles (le livre rouge disponible sur le sit
e de khronos aussi m'a sembl&eacute; indispensable pour le d&eacute;butant que j
e suis, ainsi que les sp&eacute;cifications OpenGL ES 1.0 =&gt; on y apprend les
 restrictions de la version ES 1.0)
<br />
<br /><a href='http://www.khronos.o
rg/opengles/sdk/1.1/docs/man/' target='_blank'>http://www.khronos.org/opengles/s
dk/1.1/docs/man/</a>
<br /><a href='http://obviam.net/index.php/opengl-es-andro
id-displaying-graphical-elements-primitives/' target='_blank'>http://obviam.net/
index.php/opengl-es-android-displaying-graphical-elements-primitives/</a>
<br /
><a href='http://blog.jayway.com/2009/12/03/opengl-es-tutorial-for-android-part-
i/' target='_blank'>http://blog.jayway.com/2009/12/03/opengl-es-tutorial-for-and
roid-part-i/</a>
<br /><a href='http://www.droidnova.com/android-3d-game-tutori
al-part-ii,328.html' target='_blank'>http://www.droidnova.com/android-3d-game-tu
torial-part-ii,328.html</a>
<br />
<br />L'EDI utilis&eacute; est Eclipse Heli
os.
<br /><a name='source-exemple'></a><h2> Source / Exemple : </h2>
<br /><p
re class='code' data-mode='basic'>
import java.nio.ByteBuffer;
import java.nio
.ByteOrder;
import java.nio.ShortBuffer;

import javax.microedition.khronos.e
gl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import androi
d.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

// Classe qui dé
finit et rend la scène 3D
public class OpenGLRenderer_TriangleAvancant implemen
ts Renderer {
	
	// Lorsque la scène doit être rendue
	/* 
		Appelée en perm
anence
		Tient alors compte des transformations précédentes
		de la scène (tra
nslate, rotate, scale, color, ...)

<ul>	<li>/</li></ul>
	@Override
	public 
void onDrawFrame(GL10 gl) {
		clearGlScreen(gl); // Effacer la zone : cf plus b
as
		moveCamera_ThatIs_MoveObject(gl); // Deplacer la caméra : cf plus bas
		d
rawTriangle(gl); // Seulement là, dessiner le triangle : cf plus bas
	}
	
	//
Effacer la zone en blanc
	private void clearGlScreen(GL10 gl) {
		 /* Le mode 
modèle-vue est le mode qui sera utilisé pour le

<ul>		 <li> rendu des objets.
 Donc ne pas oublier de revenir dans ce mode
</li>		 <li> pour que les prochain
es fonctions puissent directement rendre la scène
</li>		 <li>/</li></ul>
		gl
.glMatrixMode(GL10.GL_MODELVIEW);
		// Rappel : réinitialiser l'état du mode co
urant (modèle-vue)
		gl.glLoadIdentity(); // Réinitialise la caméra
		/*

<u
l>		 <li> Ici on positionne la camera (soit en x=0, y=0 et z=6),
</li>		 <li> o
n définit sa visée (x=0, y=0, z=0) et on définit ce qu'est
</li>		 <li> pour el
le le sens de dessus (x=0, y=1, z=0)
</li>		 <li> 
</li>		 <li> Attention, cet
te commande en elle-même ne suffit pas
</li>		 <li> à garantir une position con
stante de la caméra !!!
</li>		 <li> Appeller glLoadIdentity() auparavant=&gt; 
là on sera sûr de repartir de zéro
</li>		 <li> à chaque fois. (Pourquoi ? ces 
deux fonctions sont des opérations sur la matrice
</li>		 <li> appellée modèle-
vue : si l'opération de &quot;nettoyage de matrice&quot; manque, les effets
</l
i>		 <li> des différents gluLookAt() se cumulent !!! (Avec les erreurs dues à un
 trop grand nombre
</li>		 <li> de cumuls que cela peut entrainer !!!)
</li>		
 <li>/</li></ul>
		GLU.gluLookAt(gl, 0, 0, 6, 0, 0, 0, 0, 1, 0); // intègre des
 appels à glTranslate et glRotate !!!
		// Couleur effacement :  en (rouge, ver
t, bleu, alpha) : 1.0 = intensité maximale
		gl.glClearColor(FULL_INTENSITY, FU
LL_INTENSITY, FULL_INTENSITY, EMPTY_INTENSITY);
		// Effacer le tampon de coule
ur (ie les données du dessin) et le tampon de profondeur
		// (pour gérer les r
ecouvrements d'objets dus à l'axe z)
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL
10.GL_DEPTH_BUFFER_BIT);
	}
	
	// Deplacer la caméra
	private void moveCamer
a_ThatIs_MoveObject(GL10 gl) {
		/* Effectue une translation (x, y, z) 

<ul>
		 <li> (depuis l'origine grâce à glLoadIdentity() de la ligne précédente
</li>
		 <li>/</li></ul>
		gl.glTranslatex(ORIGIN, ORIGIN, currentZTranslationValue);

		currentZTranslationValue+=1000;
	}
	
	/*

<ul>	 <li> La grande différen
ce avec OpenGL classique (ici on est en ES) !!!
</li>	 <li> Il faut utiliser un
 tampon regroupant tous les points de la figure à dessiner
</li>	 <li> Ce tampo
n sera ensuite utiliser par la méthode gl.glVertexPointer pour faire OpenGL
</l
i>	 <li> pointer sur notre structure &quot;en points&quot;, le moment adéquat. U
n appel à gl.DrawArrays permettra alors
</li>	 <li> de &quot;flusher&quot; le d
essin de la figure (le valider).
</li>	 <li>/</li></ul>
	private void buildVer
ticesBuffer() {
		// On alloue la taille de l'ensemble des points en nombre d'o
ctets
		ByteBuffer baseBuffer = ByteBuffer.allocateDirect(triangleVertices.leng
th * SHORTS_SIZE_BYTES);
		// Important !!! L'ordre du tampon doit être natif

		baseBuffer.order(ByteOrder.nativeOrder());
		// Récupération de la version sh
ort du tampon (on aurait pu utiliser des Float :) )
		verticesBuffer = baseBuff
er.asShortBuffer();
		//Stockage des valeurs à partir du tableau de short
		ve
rticesBuffer.put(triangleVertices);
		// On dit que la 1ere valeur du tampon es
t la 1ere valeur utilisable (indice 0, logique :) )
		verticesBuffer.position(S
TART_AT_FIRST_POSITION);
	}
	
	private void drawTriangle(GL10 gl) {
		// Aut
oriser OpenGL à utiliser les tableaux de vertex pour dessiner
		gl.glEnableClie
ntState(GL10.GL_VERTEX_ARRAY);
		/*

<ul>		 <li> Ne pas confondre avec glClea
rColor !!!
</li>		 <li> Ici on définit la couleur de dessin et non d'effacement

</li>		 <li> Couleur : (rouge, vert, bleu, alpha)
</li>		 <li>/</li></ul>
		
gl.glColor4f(FULL_INTENSITY, EMPTY_INTENSITY, EMPTY_INTENSITY, HALF_INTENSITY);

		/*

<ul>		 <li> OpenGL devra maintenant chercher les données de notre tampo
n définissant la figure,
</li>		 <li> pour son prochain dessin
</li>		 <li> TR
IANGLE_COORDS_PER_VERTEX : nombte de coordonnées par vertex (3 ont été choisis p
our notre triangle)
</li>		 <li> GL10.GL_SHORT : on a codé les coordonnées à l'
aide de short
</li>		 <li> START_AT_FIRST_POSITION : index 0 comme valeur de dé
part dans le tampon ( logique :) )
</li>		 <li>/</li></ul>
		gl.glVertexPointe
r(TRIANGLE_COORDS_PER_VERTEX, GL10.GL_SHORT, START_AT_FIRST_POSITION, verticesBu
ffer);
		/*

<ul>		 <li> OpenGL dessine une figure à partir des données charg
ées précédemment
</li>		 <li> GL10.GL_TRIANGLE_STRIP : dessin de triangles conc
aténés (ici, il y en a qu'un car on
</li>		 <li> n'a défini que 3 points, mais 
avec 4, on aurait défini un carré =&gt; doc OpenGL officielle
</li>		 <li> pour
 plus de précision :) )
</li>		 <li> START_AT_FIRST_POSITION : index de départ 
(0 ici, logique :) )
</li>		 <li> VERTICES_NUMBER : nombre de vertices à utilis
er
</li>		 <li>/</li></ul>
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, START_AT_
FIRST_POSITION, VERTICES_NUMBER);
		// Interdire OpenGL à utiliser les tableaux
 de vertex pour dessiner
		// ( Performances++ :) )
		gl.glDisableClientState(
GL10.GL_VERTEX_ARRAY);
	}

	// Lorsque la surface doit être repainte
	// Seu
lement susceptible d'être appelée au besoin
	@Override
	public void onSurfaceC
hanged(GL10 gl, int width, int height) {
		// Définit la zone de la SurfaceView
, qui sera utilisée pour le rendu
		// (ici, la zone entière)
		gl.glViewport(
0, 0, width, height);
		/*

<ul>		 <li> On bascule en mode projection :
</li
>		 <li> les prochaines transformations affecteront, non pas les objets
</li>		
 <li> ou la caméra, mais le mode de vue de la scène (je pense avoir compris le 

</li>		 <li> but du mode projection, mais sans en être sûr =&gt; à vérifier tou
t de même :) )
</li>		 <li>/</li></ul>
		gl.glMatrixMode(GL10.GL_PROJECTION);

		// Efface les dernières transformations
		gl.glLoadIdentity();
		float aspe
ctRatio = (float) width / (float) height;
		/*

<ul>		 <li> Définit le cône q
ui servira à donner l'impression de perspective
</li>		 <li> Il englobe les pla
ns avant et arrière que l'on défini, le plan arrière
</li>		 <li> étant plus gr
and que le plan avant (Cela vous permettra de déterminer/définir
</li>		 <li> l
e sens du cône) (Ne pas hésiter à consulter le livre rouge
</li>		 <li> officie
l d'OpenGL, cela y sera mieux expliqué)
</li>		 <li> 60.0f =&gt; Moitié de l'an
gle du cône (à partir de l'axe z)
</li>		 <li> Une valeur de 60/70 garantie un 
champ de vision humaine.
</li>		 <li> aspectRatio =&gt; Rapport largeur/hauteur
 du cône
</li>		 <li> 0.1f et 10000000 =&gt; 0.1f : plan avant et 100.0 (car de
rnier paramètre) : plan arrière
</li>		 <li>/</li></ul>
		/*

<ul>		 <li> At
tention !!! Dans ce cas, seuls les objets ayant z compris entre -0.1f et -100000
00
</li>		 <li> seront rendus. (Il faut prendre l'opposé des valeurs)
</li>		 
<li>/</li></ul>
		/*

<ul>		 <li> Si l'on souhaite une cône plus précis (nota
mment sur un autre axe que l'axe z)
</li>		 <li> on peut toujours utiliser le m
oins recommandé gl.glFrustum
</li>		 <li> (Vous me connaissez :) Doc officielle
 :) )
</li>		 <li>/</li></ul>
		GLU.gluPerspective(gl,60.0f, aspectRatio, 0.1f
, 10000000);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig
 config) {
		/* 
		Les collections gérant les définitions des points
		n'auro
nt pas à être recrées

<ul>		 <li>/</li></ul>
		buildVerticesBuffer();
	}
	

	private int currentZTranslationValue = 0;
	
	// Stockage des définitions de
s vertices
	private ShortBuffer verticesBuffer;
	
	// Position des vertices d
ans le système
	/*
	 Ici z vaut -1 pour être en adéquation avec
	 la perspect
ive d'observation, définie par
	 l'appel gl.gluPerspective

<ul>	<li>/</li></
ul>
	private short triangleVertices [] = {
			0, 	 1, 0,// point d'index 0 (x,
y,z)
			1,   -1, 0, // point d'index 1 (x,y,z)
		   -1,  -1, 0,// point d'inde
x 2 (x,y,z)
	};
	
	
	// Taille des shorts en octets
	private final static i
nt SHORTS_SIZE_BYTES = 2;
	
	// L'origine en terme de position
	private final
 short ORIGIN = 0;
	
	// Intensité des couleurs
	private final float FULL_INT
ENSITY = 1.0f;
	private final float HALF_INTENSITY = 0.5f;
	private final floa
t EMPTY_INTENSITY = 0.0f;
	
	// Index de depart des &quot;collections&quot;
	
private final int START_AT_FIRST_POSITION = 0;
	
	//Nombre de vertices à affic
her
	private final int VERTICES_NUMBER = 3;
	
	// Nombre de coordonnées défin
ies par vertex (1,2 ou 3)
	private final int TRIANGLE_COORDS_PER_VERTEX = trian
gleVertices.length / VERTICES_NUMBER;
}
</pre>
<br /><a name='conclusion'></a
><h2> Conclusion : </h2>
<br />N'h&eacute;sitez pas &agrave; visiter les tutor
iels dont je vous ai mis les liens !!!
<br />Mon but est juste de proposer un e
xemple, pas d'en expliquer les moindres d&eacute;tails.
<br />
<br />Enfin, de
rni&egrave;re information pour la source pr&eacute;sent&eacute;e ci-dessus : il 
s'agit d'une instance de GLRenderer, &agrave; combiner avec une GLSurfaceView. C
ette GLSurfaceView sera la vue &agrave; utiliser dans l'Activity. Ne pas oublier
 &eacute;galement d'adapter les m&eacute;thodes onPause() et onResume() de l'Act
ivity.  En fait ces deux m&eacute;thodes doivent appeler les m&eacute;thodes res
pectives onPause() et onResume() de la classe h&eacute;rit&eacute;e de GLSurface
View.
<br />
<br />Enfin, notez que l'ordre d&#8217;encha&icirc;nement des op&
eacute;rations pourrait &ecirc;tre imparfait (ne connaissant pas encore bien Ope
nGL &agrave; la base et ses matrices) : je m'en suis aper&ccedil;u avec des exp&
eacute;rimentations ult&eacute;rieures. Si tel est le cas, merci aux experts et 
initi&eacute;s OpenGL de me le signaler. Des modifications pourraient alors &eci
rc;tre apport&eacute;es.
