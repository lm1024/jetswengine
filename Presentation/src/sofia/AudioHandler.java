/*
 * Alex Cash and Calum Armstrong
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package sofia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sofia.Audio;

import javafx.scene.*;

/**
 * This class is the handler for audio
 * 
 * @author Alex Cash 
 * @author Calum Armstrong
 * @version 1.5 4 Mar 2015
 */

public class AudioHandler {
	/* Reference to the group on which to add the Audio */
	private Group group;
	
	/* Array List of the Audios on screen */
	private List<Audio> audios;
	
	/** Constructor Method */
	public AudioHandler(Group nGroup) {
		/* Set the group reference */
		this.group = nGroup;
		 
        /* Initialise the audio list */
        this.audios = new ArrayList<Audio>();
	}
	
	 /** Add an audio to a group */
    public void createAudio(double x, double y, double width, File sourceFile, boolean autoPlay, boolean visibleControls, boolean playButtonOnly) {
        audios.add(new Audio(group, x, y, width, sourceFile, autoPlay, visibleControls, playButtonOnly));
    }
    
    /** Play an audio */
    public void playAudio(int audioId) {
        audios.get(audioId).play();
    }
    
    /** Pause an audio */
    public void pauseAudio(int audioId) {
    	audios.get(audioId).pause();
    }
    
    /** Stop an audio */
    public void stopAudio(int audioId) {
    	audios.get(audioId).stop();
    }
    
    /** Scan an audio */
    public void seekAudio(int audioId, double percent) {
    	audios.get(audioId).audioSeek(percent);
    }
    
    /** Clear all the audios currently being handled */
    public void clearAudios() {
        /* Remove all the media views (videos) from the group */
        for(int i = 0; i < audios.size(); i++) {
            audios.get(i).dispose();
            group.getChildren().remove(audios.get(i));
        }
        
        /* Clear the array list */
        audios.clear();
    }
    
}