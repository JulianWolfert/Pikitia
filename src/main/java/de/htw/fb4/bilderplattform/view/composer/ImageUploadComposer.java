package de.htw.fb4.bilderplattform.view.composer;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Image;

/**
 * 
 * @author Wojciech Konitzer
 *
 */
public class ImageUploadComposer extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = -1800850584259216668L;
	
	private List<String> invalidContentTypes = new ArrayList<String>();
	private List<Image> validImages = new ArrayList<Image>();

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	//TODO: finishing and using ImageServiceImpl
	@Listen("onUpload = #btnUpload")
	public void onUpload$btnUpload(UploadEvent e)// throws InterruptedException
	{
		if (e.getMedias() != null) {
			StringBuilder sb = new StringBuilder("Sie haben folgende Dateien erfolgreich hochgeladen: \n");

			for (Media m : e.getMedias()) {
				if(m.getContentType().equals("image/jpeg") || m.getContentType().equals("image/gif")){
					Image newImage = new Image();
					newImage.setFile(m.getByteData());
					validImages.add(newImage);
				}else{
					sb.append(m.getName());
					sb.append("Folgende Dateien wurden nicht hochgeladen, weil es keine jpg oder gif Dateien sind.");
					sb.append(m.getContentType());
					sb.append(")\n");
					invalidContentTypes.add(sb.toString());
				}
			}
			
			//upload images to server directory
			for(Image image : validImages){
				BusinessCtx.getInstance().getIImageService().saveOrUpdateImage(image, BusinessCtx.getInstance().getUserService().getCurrentlyLoggedInUser());
			}			
			Messagebox.show(sb.toString());			
		} else {
			Messagebox.show("Sie haben keine Dateien hochgeladen!");
		}
	}

}
