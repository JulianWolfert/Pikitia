package de.htw.fb4.bilderplattform.view.composer;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;

/**
 * 
 * @author Wojciech Konitzer
 *
 */
public class ImageUploadComposer extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = -1800850584259216668L;
	private List<String> invalidContentTypes = new ArrayList<String>();

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	//TODO: finishing and using ImageServiceImpl
	@Listen("onUpload = #btnUpload")
	public void onUpload$btnUpload(UploadEvent e)// throws InterruptedException
	{
		if (e.getMedias() != null) {
			StringBuilder sb = new StringBuilder("You uploaded: \n");

			
			
			for (Media m : e.getMedias()) {
				if(m.getContentType().equals("image/jpeg") || m.getContentType().equals("image/gif")){
					
				}else{
					sb.append(m.getName());
					sb.append("Folgende Dateien wurden nicht hochgeladen, weil es keine jpg oder gif Dateien sind.");
					sb.append(m.getContentType());
					sb.append(")\n");
					invalidContentTypes.add(sb.toString());
				}

			}

			Messagebox.show(sb.toString());
		} else {
			Messagebox.show("You uploaded no files!");
		}
	}
}
