package org.isgreat.loloof64.android.opengl.triangle_avancant;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

// Classe qui définit et rend la scène 3D
public class OpenGLRenderer_TriangleAvancant implements Renderer {
	
	// Lorsque la scène doit être rendue
	/* 
		Appelée en permanence
		Tient alors compte des transformations précédentes
		de la scène (translate, rotate, scale, color, ...)
	*/
	@Override
	public void onDrawFrame(GL10 gl) {
		clearGlScreen(gl); // Effacer la zone : cf plus bas
		moveCamera_ThatIs_MoveObject(gl); // Deplacer la caméra : cf plus bas
		drawTriangle(gl); // Seulement là, dessiner le triangle : cf plus bas
	}
	
	//Effacer la zone en blanc
	private void clearGlScreen(GL10 gl) {
		 /* Le mode modèle-vue est le mode qui sera utilisé pour le
		 * rendu des objets. Donc ne pas oublier de revenir dans ce mode
		 * pour que les prochaines fonctions puissent directement rendre la scène
		 */
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Rappel : réinitialiser l'état du mode courant (modèle-vue)
		gl.glLoadIdentity(); // Réinitialise la caméra
		/*
		 * Ici on positionne la camera (soit en x=0, y=0 et z=6),
		 * on définit sa visée (x=0, y=0, z=0) et on définit ce qu'est
		 * pour elle le sens de dessus (x=0, y=1, z=0)
		 * 
		 * Attention, cette commande en elle-même ne suffit pas
		 * à garantir une position constante de la caméra !!!
		 * Appeller glLoadIdentity() auparavant=> là on sera sûr de repartir de zéro
		 * à chaque fois. (Pourquoi ? ces deux fonctions sont des opérations sur la matrice
		 * appellée modèle-vue : si l'opération de "nettoyage de matrice" manque, les effets
		 * des différents gluLookAt() se cumulent !!! (Avec les erreurs dues à un trop grand nombre
		 * de cumuls que cela peut entrainer !!!)
		 */
		GLU.gluLookAt(gl, 0, 0, 6, 0, 0, 0, 0, 1, 0); // intègre des appels à glTranslate et glRotate !!!
		// Couleur effacement :  en (rouge, vert, bleu, alpha) : 1.0 = intensité maximale
		gl.glClearColor(FULL_INTENSITY, FULL_INTENSITY, FULL_INTENSITY, EMPTY_INTENSITY);
		// Effacer le tampon de couleur (ie les données du dessin) et le tampon de profondeur
		// (pour gérer les recouvrements d'objets dus à l'axe z)
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	}
	
	// Deplacer la caméra
	private void moveCamera_ThatIs_MoveObject(GL10 gl) {
		/* Effectue une translation (x, y, z) 
		 * (depuis l'origine grâce à glLoadIdentity() de la ligne précédente
		 */
		gl.glTranslatex(ORIGIN, ORIGIN, currentZTranslationValue);
		currentZTranslationValue+=1000;
	}
	
	/*
	 * La grande différence avec OpenGL classique (ici on est en ES) !!!
	 * Il faut utiliser un tampon regroupant tous les points de la figure à dessiner
	 * Ce tampon sera ensuite utiliser par la méthode gl.glVertexPointer pour faire OpenGL
	 * pointer sur notre structure "en points", le moment adéquat. Un appel à gl.DrawArrays permettra alors
	 * de "flusher" le dessin de la figure (le valider).
	 */
	private void buildVerticesBuffer() {
		// On alloue la taille de l'ensemble des points en nombre d'octets
		ByteBuffer baseBuffer = ByteBuffer.allocateDirect(triangleVertices.length * SHORTS_SIZE_BYTES);
		// Important !!! L'ordre du tampon doit être natif
		baseBuffer.order(ByteOrder.nativeOrder());
		// Récupération de la version short du tampon (on aurait pu utiliser des Float :) )
		verticesBuffer = baseBuffer.asShortBuffer();
		//Stockage des valeurs à partir du tableau de short
		verticesBuffer.put(triangleVertices);
		// On dit que la 1ere valeur du tampon est la 1ere valeur utilisable (indice 0, logique :) )
		verticesBuffer.position(START_AT_FIRST_POSITION);
	}
	
	private void drawTriangle(GL10 gl) {
		// Autoriser OpenGL à utiliser les tableaux de vertex pour dessiner
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		/*
		 * Ne pas confondre avec glClearColor !!!
		 * Ici on définit la couleur de dessin et non d'effacement
		 * Couleur : (rouge, vert, bleu, alpha)
		 */
		gl.glColor4f(FULL_INTENSITY, EMPTY_INTENSITY, EMPTY_INTENSITY, HALF_INTENSITY);
		/*
		 * OpenGL devra maintenant chercher les données de notre tampon définissant la figure,
		 * pour son prochain dessin
		 * TRIANGLE_COORDS_PER_VERTEX : nombte de coordonnées par vertex (3 ont été choisis pour notre triangle)
		 * GL10.GL_SHORT : on a codé les coordonnées à l'aide de short
		 * START_AT_FIRST_POSITION : index 0 comme valeur de départ dans le tampon ( logique :) )
		 */
		gl.glVertexPointer(TRIANGLE_COORDS_PER_VERTEX, GL10.GL_SHORT, START_AT_FIRST_POSITION, verticesBuffer);
		/*
		 * OpenGL dessine une figure à partir des données chargées précédemment
		 * GL10.GL_TRIANGLE_STRIP : dessin de triangles concaténés (ici, il y en a qu'un car on
		 * n'a défini que 3 points, mais avec 4, on aurait défini un carré => doc OpenGL officielle
		 * pour plus de précision :) )
		 * START_AT_FIRST_POSITION : index de départ (0 ici, logique :) )
		 * VERTICES_NUMBER : nombre de vertices à utiliser
		 */
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, START_AT_FIRST_POSITION, VERTICES_NUMBER);
		// Interdire OpenGL à utiliser les tableaux de vertex pour dessiner
		// ( Performances++ :) )
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	// Lorsque la surface doit être repainte
	// Seulement susceptible d'être appelée au besoin
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Définit la zone de la SurfaceView, qui sera utilisée pour le rendu
		// (ici, la zone entière)
		gl.glViewport(0, 0, width, height);
		/*
		 * On bascule en mode projection :
		 * les prochaines transformations affecteront, non pas les objets
		 * ou la caméra, mais le mode de vue de la scène (je pense avoir compris le 
		 * but du mode projection, mais sans en être sûr => à vérifier tout de même :) )
		 */
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Efface les dernières transformations
		gl.glLoadIdentity();
		float aspectRatio = (float) width / (float) height;
		/*
		 * Définit le cône qui servira à donner l'impression de perspective
		 * Il englobe les plans avant et arrière que l'on défini, le plan arrière
		 * étant plus grand que le plan avant (Cela vous permettra de déterminer/définir
		 * le sens du cône) (Ne pas hésiter à consulter le livre rouge
		 * officiel d'OpenGL, cela y sera mieux expliqué)
		 * 60.0f => Moitié de l'angle du cône (à partir de l'axe z)
		 * Une valeur de 60/70 garantie un champ de vision humaine.
		 * aspectRatio => Rapport largeur/hauteur du cône
		 * 0.1f et 10000000 => 0.1f : plan avant et 100.0 (car dernier paramètre) : plan arrière
		 */
		/*
		 * Attention !!! Dans ce cas, seuls les objets ayant z compris entre -0.1f et -10000000
		 * seront rendus. (Il faut prendre l'opposé des valeurs)
		 */
		/*
		 * Si l'on souhaite une cône plus précis (notamment sur un autre axe que l'axe z)
		 * on peut toujours utiliser le moins recommandé gl.glFrustum
		 * (Vous me connaissez :) Doc officielle :) )
		 */
		GLU.gluPerspective(gl,60.0f, aspectRatio, 0.1f, 10000000);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		/* 
		Les collections gérant les définitions des points
		n'auront pas à être recrées
		 */
		buildVerticesBuffer();
	}
	
	private int currentZTranslationValue = 0;
	
	// Stockage des définitions des vertices
	private ShortBuffer verticesBuffer;
	
	// Position des vertices dans le système
	/*
	 Ici z vaut -1 pour être en adéquation avec
	 la perspective d'observation, définie par
	 l'appel gl.gluPerspective
	*/
	private short triangleVertices [] = {
			0, 	 1, 0,// point d'index 0 (x,y,z)
			1,   -1, 0, // point d'index 1 (x,y,z)
		   -1,  -1, 0,// point d'index 2 (x,y,z)
	};
	
	
	// Taille des shorts en octets
	private final static int SHORTS_SIZE_BYTES = 2;
	
	// L'origine en terme de position
	private final short ORIGIN = 0;
	
	// Intensité des couleurs
	private final float FULL_INTENSITY = 1.0f;
	private final float HALF_INTENSITY = 0.5f;
	private final float EMPTY_INTENSITY = 0.0f;
	
	// Index de depart des "collections"
	private final int START_AT_FIRST_POSITION = 0;
	
	//Nombre de vertices à afficher
	private final int VERTICES_NUMBER = 3;
	
	// Nombre de coordonnées définies par vertex (1,2 ou 3)
	private final int TRIANGLE_COORDS_PER_VERTEX = triangleVertices.length / VERTICES_NUMBER;
}
