package de.htw.fb4.bilderplattform.view.vm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.util.FileUtil;

/**
 * 
 * @author Peter Horn
 * 
 */
public class BannerVM {
	@Wire(".banner_imglist")
	private Div banner_imglist;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		this.test();
	}

	public void test() {
		String imagePath = BusinessCtx.getInstance().getImageService().getImagePath() + File.separator;
		
		List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx.getInstance().getImageService().getBest(5);
		
		int i=0;
		try {
			for (de.htw.fb4.bilderplattform.dao.Image item : imgList) {
				File file = new File(imagePath + item.getPreview_file());			
				byte[] img_data = FileUtil.fileToByte(file);
				Image img_gui = new Image();
				org.zkoss.image.AImage img_preview = new AImage("img"+i,
						new ByteArrayInputStream(img_data));
				i++;
				img_gui.setContent(img_preview);
				this.banner_imglist.appendChild(img_gui);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
