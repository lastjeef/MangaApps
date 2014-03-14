package com.lastjeef.mangatopdf;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class Downloader {

	private String chapterURL;

	public Downloader(String chapterUrl) {
		this.chapterURL = chapterUrl;
	}

	public int download() {
		Document htmlDoc;
		Element htmlElement;
		String source;
		Image img = null;
		File outputfile;
		URL url;
		int pages = 0;
		int i = 1;
		try {
			htmlDoc = Jsoup.connect(chapterURL).get();
			htmlElement = htmlDoc.getElementById("pageMenu");
			pages = htmlElement.children().size();
		} catch (Exception e) {
			System.out.println("This Manga or Chapter doesn't exist");
			System.out.println("Press any key to exit");
			Scanner in = new Scanner(System.in);
			in.nextLine();
			System.exit(0);
		}

		while (i < pages + 1) {
			try {

				htmlDoc = Jsoup.connect(chapterURL + i).get();
				htmlElement = htmlDoc.getElementById("img");

				source = htmlElement.attr("src");

				url = new URL(source);
				img = ImageIO.read(url);
				outputfile = new File("temp/image" + String.format("%03d", i)
						+ ".jpg");
				ImageIO.write((RenderedImage) img, "jpg", outputfile);
				System.out.println("Downloading page " + i + " ...");

				i += 1;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
		System.out.println("Done .");
		return i - 1;
	}

}
