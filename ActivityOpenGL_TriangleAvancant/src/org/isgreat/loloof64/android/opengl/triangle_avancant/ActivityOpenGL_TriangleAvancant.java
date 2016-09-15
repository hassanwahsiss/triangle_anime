package org.isgreat.loloof64.android.opengl.triangle_avancant;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class ActivityOpenGL_TriangleAvancant extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 
           La classe GLSurfaceView sert de pont entre
           l'activity et la classe de rendu de scène (GLSurfaceView.Renderer)
        */
        openGLView = new GLSurfaceView(this);
        /* 
           Le signal setRenderer de GLSurfaceView
           est obligatoire pour que le programme fonctionne :
           on lui affecte un GLSurfaceView.Renderer.
           Cette dernière classe s'occupe de définir et rendre
           la scène 3D 
        */
        openGLView.setRenderer(new OpenGLRenderer_TriangleAvancant());
        setContentView(openGLView);
    }
    
    
    /* 
       Méthode que l'on doit redéfinir au moins avec les
       lignes suivantes pour le bon déroulement du programme.
       Il s'agit d'asscocier le cycle de vie de openGLView (qui est une
       GLSurfaceView) avec celui de l'Activity
    */  
    @Override
	protected void onPause() {
		// L'ordre des appels compte !!!
		super.onPause();
		openGLView.onPause();
	}

	/* 
       Méthode que l'on doit redéfinir au moins avec les
       lignes suivantes pour le bon déroulement du programme.
       Il s'agit d'asscocier le cycle de vie de openGLView (qui est une
       GLSurfaceView) avec celui de l'Activity
    */  
	@Override
	protected void onResume() {
		// L'ordre des appels compte !!!
		super.onResume();
		openGLView.onResume();
	}
	
	/* 
	    La classe GLSurfaceView sert de pont entre
        l'activity et la classe de rendu de scène (GLSurfaceView.Renderer)
     */
	private GLSurfaceView openGLView;
}
