import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

public class GUI extends JFrame {
	
	Logic logic;
	
	public GUI() {
		super("Hallo GUI");
		
		logic = new Logic();
		
		setSize(300,300);
        setLocation(800,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        JLabel myText = new JLabel ("ID Tag");
        
        JButton buttonOpen = new JButton("Open mp3 File");
        
        buttonOpen.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {	
				handleFile(open());	
			}
        });
        
        contentPane.add(myText);
        contentPane.add(buttonOpen);
        setContentPane(contentPane);
        
        setVisible(true);
        
	}

	  private String open() {
	        final JFileChooser chooser = new JFileChooser("Open");
	        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
	        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	       	chooser.setFileFilter(mp3FileFilter());
	        chooser.setAcceptAllFileFilterUsed(false);	// "Alle Dateien" Filter deaktivieren
	  
	        final File file = new File("/home");

	        chooser.setCurrentDirectory(file);

	        chooser.addPropertyChangeListener(new PropertyChangeListener() {
	            public void propertyChange(PropertyChangeEvent e) {
	                if (e.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)
	                        || e.getPropertyName().equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
	                    final File f = (File) e.getNewValue();
	                }
	            }
	        });

	        chooser.setVisible(true);
	        final int result = chooser.showOpenDialog(null);

	        String inputVerzStr = "no Dir";
	                
	        if (result == JFileChooser.APPROVE_OPTION) {
	            File inputVerzFile = chooser.getSelectedFile();
	            inputVerzStr = inputVerzFile.getPath();
	        //    System.out.println("Eingabepfad:" + inputVerzStr);
	        }
	        // System.out.println("Abbruch");
	        chooser.setVisible(false);
			return inputVerzStr;
	    } 
	  
	  
	  public FileFilter mp3FileFilter() {
		  
		  final String endung = ".mp3";
	        
		  FileFilter fileFilter = new FileFilter() {
			
			@Override
			public String getDescription() {
				 return endung + " only";
			}
			
			@Override
			public boolean accept(File f) {
				if(f == null) return false;
	            
	            // Ordner anzeigen
	            if(f.isDirectory()) return true;
	            
	            // true, wenn File gewuenschte Endung besitzt
	            return f.getName().toLowerCase().endsWith(endung);
			}
		};
		  return fileFilter;
	  }
				  
			  
	  public void handleFile(String name) {
		  
		  System.out.println("calling handleFile("+name+");");
		 
		  MP3File mp3file = null;
	  
		try {
			mp3file = new MP3File(name);
		} catch (IOException e) {
			System.out.println("GUI.handleFile() - IO Exception");
			e.printStackTrace();
		} catch (TagException e) {
			System.out.println("GUI.handleFile() - Tag Exception");
			e.printStackTrace();
		}

		System.out.println("GUI.handleFile() - mp3file.getID3v2Tag().getAlbumTitle() = "+mp3file.getID3v2Tag().getAlbumTitle() );
		
		  String text = "abc";

		  if (mp3file.hasID3v2Tag() == true) {
			  text = mp3file.getID3v2Tag().getLeadArtist();
			  System.out.println(text);
		  } else {
			  System.out.println("Reading Tag failed");
		  }
		  
		 
	  }
		  
}