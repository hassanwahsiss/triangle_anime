[opengl] triangle anim�-----------------------
Url     : http://codes-sources.commentcamarche.net/source/52805-opengl-triangle-animeAuteur  : cs_loloof64Date    : 13/08/2013
Licence :
=========

Ce document intitul� � [opengl] triangle anim� � issu de CommentCaMarche
(codes-sources.commentcamarche.net) est mis � disposition sous les termes de
la licence Creative Commons. Vous pouvez copier, modifier des copies de cette
source, dans les conditions fix�es par la licence, tant que cette note
appara�t clairement.

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

// Classe qui d�
finit et rend la sc�ne 3D
public class OpenGLRenderer_TriangleAvancant implemen
ts Renderer {
	
	// Lorsque la sc�ne doit �tre rendue
	/* 
		Appel�e en perm
anence
		Tient alors compte des transformations pr�c�dentes
		de la sc�ne (tra
nslate, rotate, scale, color, ...)

<ul>	<li>/</li></ul>
	@Override
	public 
void onDrawFrame(GL10 gl) {
		clearGlScreen(gl); // Effacer la zone : cf plus b
as
		moveCamera_ThatIs_MoveObject(gl); // Deplacer la cam�ra : cf plus bas
		d
rawTriangle(gl); // Seulement l�, dessiner le triangle : cf plus bas
	}
	
	//
Effacer la zone en blanc
	private void clearGlScreen(GL10 gl) {
		 /* Le mode 
mod�le-vue est le mode qui sera utilis� pour le

<ul>		 <li> rendu des objets.
 Donc ne pas oublier de revenir dans ce mode
</li>		 <li> pour que les prochain
es fonctions puissent directement rendre la sc�ne
</li>		 <li>/</li></ul>
		gl
.glMatrixMode(GL10.GL_MODELVIEW);
		// Rappel : r�initialiser l'�tat du mode co
urant (mod�le-vue)
		gl.glLoadIdentity(); // R�initialise la cam�ra
		/*

<u
l>		 <li> Ici on positionne la camera (soit en x=0, y=0 et z=6),
</li>		 <li> o
n d�finit sa vis�e (x=0, y=0, z=0) et on d�finit ce qu'est
</li>		 <li> pour el
le le sens de dessus (x=0, y=1, z=0)
</li>		 <li> 
</li>		 <li> Attention, cet
te commande en elle-m�me ne suffit pas
</li>		 <li> � garantir une position con
stante de la cam�ra !!!
</li>		 <li> Appeller glLoadIdentity() auparavant=&gt; 
l� on sera s�r de repartir de z�ro
</li>		 <li> � chaque fois. (Pourquoi ? ces 
deux fonctions sont des op�rations sur la matrice
</li>		 <li> appell�e mod�le-
vue : si l'op�ration de &quot;nettoyage de matrice&quot; manque, les effets
</l
i>		 <li> des diff�rents gluLookAt() se cumulent !!! (Avec les erreurs dues � un
 trop grand nombre
</li>		 <li> de cumuls que cela peut entrainer !!!)
</li>		
 <li>/</li></ul>
		GLU.gluLookAt(gl, 0, 0, 6, 0, 0, 0, 0, 1, 0); // int�gre des
 appels � glTranslate et glRotate !!!
		// Couleur effacement :  en (rouge, ver
t, bleu, alpha) : 1.0 = intensit� maximale
		gl.glClearColor(FULL_INTENSITY, FU
LL_INTENSITY, FULL_INTENSITY, EMPTY_INTENSITY);
		// Effacer le tampon de coule
ur (ie les donn�es du dessin) et le tampon de profondeur
		// (pour g�rer les r
ecouvrements d'objets dus � l'axe z)
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL
10.GL_DEPTH_BUFFER_BIT);
	}
	
	// Deplacer la cam�ra
	private void moveCamer
a_ThatIs_MoveObject(GL10 gl) {
		/* Effectue une translation (x, y, z) 

<ul>
		 <li> (depuis l'origine gr�ce � glLoadIdentity() de la ligne pr�c�dente
</li>
		 <li>/</li></ul>
		gl.glTranslatex(ORIGIN, ORIGIN, currentZTranslationValue);

		currentZTranslationValue+=1000;
	}
	
	/*

<ul>	 <li> La grande diff�ren
ce avec OpenGL classique (ici on est en ES) !!!
</li>	 <li> Il faut utiliser un
 tampon regroupant tous les points de la figure � dessiner
</li>	 <li> Ce tampo
n sera ensuite utiliser par la m�thode gl.glVertexPointer pour faire OpenGL
</l
i>	 <li> pointer sur notre structure &quot;en points&quot;, le moment ad�quat. U
n appel � gl.DrawArrays permettra alors
</li>	 <li> de &quot;flusher&quot; le d
essin de la figure (le valider).
</li>	 <li>/</li></ul>
	private void buildVer
ticesBuffer() {
		// On alloue la taille de l'ensemble des points en nombre d'o
ctets
		ByteBuffer baseBuffer = ByteBuffer.allocateDirect(triangleVertices.leng
th * SHORTS_SIZE_BYTES);
		// Important !!! L'ordre du tampon doit �tre natif

		baseBuffer.order(ByteOrder.nativeOrder());
		// R�cup�ration de la version sh
ort du tampon (on aurait pu utiliser des Float :) )
		verticesBuffer = baseBuff
er.asShortBuffer();
		//Stockage des valeurs � partir du tableau de short
		ve
rticesBuffer.put(triangleVertices);
		// On dit que la 1ere valeur du tampon es
t la 1ere valeur utilisable (indice 0, logique :) )
		verticesBuffer.position(S
TART_AT_FIRST_POSITION);
	}
	
	private void drawTriangle(GL10 gl) {
		// Aut
oriser OpenGL � utiliser les tableaux de vertex pour dessiner
		gl.glEnableClie
ntState(GL10.GL_VERTEX_ARRAY);
		/*

<ul>		 <li> Ne pas confondre avec glClea
rColor !!!
</li>		 <li> Ici on d�finit la couleur de dessin et non d'effacement

</li>		 <li> Couleur : (rouge, vert, bleu, alpha)
</li>		 <li>/</li></ul>
		
gl.glColor4f(FULL_INTENSITY, EMPTY_INTENSITY, EMPTY_INTENSITY, HALF_INTENSITY);

		/*

<ul>		 <li> OpenGL devra maintenant chercher les donn�es de notre tampo
n d�finissant la figure,
</li>		 <li> pour son prochain dessin
</li>		 <li> TR
IANGLE_COORDS_PER_VERTEX : nombte de coordonn�es par vertex (3 ont �t� choisis p
our notre triangle)
</li>		 <li> GL10.GL_SHORT : on a cod� les coordonn�es � l'
aide de short
</li>		 <li> START_AT_FIRST_POSITION : index 0 comme valeur de d�
part dans le tampon ( logique :) )
</li>		 <li>/</li></ul>
		gl.glVertexPointe
r(TRIANGLE_COORDS_PER_VERTEX, GL10.GL_SHORT, START_AT_FIRST_POSITION, verticesBu
ffer);
		/*

<ul>		 <li> OpenGL dessine une figure � partir des donn�es charg
�es pr�c�demment
</li>		 <li> GL10.GL_TRIANGLE_STRIP : dessin de triangles conc
at�n�s (ici, il y en a qu'un car on
</li>		 <li> n'a d�fini que 3 points, mais 
avec 4, on aurait d�fini un carr� =&gt; doc OpenGL officielle
</li>		 <li> pour
 plus de pr�cision :) )
</li>		 <li> START_AT_FIRST_POSITION : index de d�part 
(0 ici, logique :) )
</li>		 <li> VERTICES_NUMBER : nombre de vertices � utilis
er
</li>		 <li>/</li></ul>
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, START_AT_
FIRST_POSITION, VERTICES_NUMBER);
		// Interdire OpenGL � utiliser les tableaux
 de vertex pour dessiner
		// ( Performances++ :) )
		gl.glDisableClientState(
GL10.GL_VERTEX_ARRAY);
	}

	// Lorsque la surface doit �tre repainte
	// Seu
lement susceptible d'�tre appel�e au besoin
	@Override
	public void onSurfaceC
hanged(GL10 gl, int width, int height) {
		// D�finit la zone de la SurfaceView
, qui sera utilis�e pour le rendu
		// (ici, la zone enti�re)
		gl.glViewport(
0, 0, width, height);
		/*

<ul>		 <li> On bascule en mode projection :
</li
>		 <li> les prochaines transformations affecteront, non pas les objets
</li>		
 <li> ou la cam�ra, mais le mode de vue de la sc�ne (je pense avoir compris le 

</li>		 <li> but du mode projection, mais sans en �tre s�r =&gt; � v�rifier tou
t de m�me :) )
</li>		 <li>/</li></ul>
		gl.glMatrixMode(GL10.GL_PROJECTION);

		// Efface les derni�res transformations
		gl.glLoadIdentity();
		float aspe
ctRatio = (float) width / (float) height;
		/*

<ul>		 <li> D�finit le c�ne q
ui servira � donner l'impression de perspective
</li>		 <li> Il englobe les pla
ns avant et arri�re que l'on d�fini, le plan arri�re
</li>		 <li> �tant plus gr
and que le plan avant (Cela vous permettra de d�terminer/d�finir
</li>		 <li> l
e sens du c�ne) (Ne pas h�siter � consulter le livre rouge
</li>		 <li> officie
l d'OpenGL, cela y sera mieux expliqu�)
</li>		 <li> 60.0f =&gt; Moiti� de l'an
gle du c�ne (� partir de l'axe z)
</li>		 <li> Une valeur de 60/70 garantie un 
champ de vision humaine.
</li>		 <li> aspectRatio =&gt; Rapport largeur/hauteur
 du c�ne
</li>		 <li> 0.1f et 10000000 =&gt; 0.1f : plan avant et 100.0 (car de
rnier param�tre) : plan arri�re
</li>		 <li>/</li></ul>
		/*

<ul>		 <li> At
tention !!! Dans ce cas, seuls les objets ayant z compris entre -0.1f et -100000
00
</li>		 <li> seront rendus. (Il faut prendre l'oppos� des valeurs)
</li>		 
<li>/</li></ul>
		/*

<ul>		 <li> Si l'on souhaite une c�ne plus pr�cis (nota
mment sur un autre axe que l'axe z)
</li>		 <li> on peut toujours utiliser le m
oins recommand� gl.glFrustum
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
		Les collections g�rant les d�finitions des points
		n'auro
nt pas � �tre recr�es

<ul>		 <li>/</li></ul>
		buildVerticesBuffer();
	}
	

	private int currentZTranslationValue = 0;
	
	// Stockage des d�finitions de
s vertices
	private ShortBuffer verticesBuffer;
	
	// Position des vertices d
ans le syst�me
	/*
	 Ici z vaut -1 pour �tre en ad�quation avec
	 la perspect
ive d'observation, d�finie par
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
	
	// Intensit� des couleurs
	private final float FULL_INT
ENSITY = 1.0f;
	private final float HALF_INTENSITY = 0.5f;
	private final floa
t EMPTY_INTENSITY = 0.0f;
	
	// Index de depart des &quot;collections&quot;
	
private final int START_AT_FIRST_POSITION = 0;
	
	//Nombre de vertices � affic
her
	private final int VERTICES_NUMBER = 3;
	
	// Nombre de coordonn�es d�fin
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
