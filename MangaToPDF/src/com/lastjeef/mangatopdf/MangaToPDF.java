package com.lastjeef.mangatopdf;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class MangaToPDF {

	public MangaToPDF(String manga, int chapter) {

		File folder = new File("temp");

		Downloader downloader = new Downloader("http://www.mangareader.net/"
				+ manga + "/" + chapter + "/");
		int images = downloader.download();
		System.out.println(images + " images downloaded");

		Document document = new Document();
		Image image;
		try {
			PdfWriter.getInstance(document,
					new FileOutputStream(manga.replace("-", " ")
							+ " - Chapter " + chapter + ".pdf"));

			document.open();

			document.addAuthor("MangaToPDF Application");
			document.addTitle(manga.replace("-", " ") + " - Chapter " + chapter);
			document.addCreationDate();
			document.addCreator("Last Jeef");
			document.addSubject("Manga chapter");
			document.setMargins(0, 0, 0, 0);

			for (int i = 1; i < images + 1; i++) {

				image = Image.getInstance("temp/image"
						+ String.format("%03d", i) + ".jpg");

				image.scaleToFit(document.getPageSize().getWidth(), document
						.getPageSize().getHeight());
				document.add(image);
			}

			document.close();
		} catch (Exception e) {
			System.out.println("Unexpected error :D ");
			System.out.println("Press any key to exit");
			Scanner in = new Scanner(System.in);
			in.nextLine();
			System.exit(0);
		}
		deleteFolder(folder);
		System.out.println("Pdf generated successfully");

	}

	public void deleteFolder(File folder) {
		File[] files = folder.listFiles();

		for (File f : files) {
			try {
				f.delete();

			} catch (Exception e) {
				System.out.println("Cannot delete " + f.getName());
			}

		}

	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out
				.println("Enter Manga Name (e.g: Enter shingeki-no-kyojin for Shingeki No Kyojin)");
		String manga = in.nextLine();
		System.out.println("Enter Chapter number (e.g: Enter 23)");
		String chapter = in.nextLine();
		new MangaToPDF(manga, Integer.parseInt(chapter));
	}

}
