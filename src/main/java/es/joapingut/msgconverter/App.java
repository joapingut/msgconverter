package es.joapingut.msgconverter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.simplejavamail.converter.EmailConverter;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			String original = args[0];
			System.out.println("Convirtiendo " + original);
			String name = FilenameUtils.getBaseName(original);
			String path = FilenameUtils.getPath(original);
			
			String eml = EmailConverter.outlookMsgToEML(new File(original));
			
			String newpath;
			if (args.length > 1) {
				newpath = args[1];
				if (!Files.isDirectory(new File(newpath).toPath())) {
					throw new IllegalArgumentException("Arg[1] no es un directorio");
				}
			} else {
				newpath = path;
			}
			String newname = FilenameUtils.concat(newpath, name).concat(".eml");
			
			if (Files.exists(new File(newname).toPath())) {
				throw new IllegalArgumentException("Ya existe el archivo de destino " + newname);
			}
			
			FileUtils.write(new File(newname), eml, StandardCharsets.UTF_8);
			System.out.println("Creado archivo " + newname);
		} else {
			System.out.println("ERROR - Debe indicar un archivo como parametro de entrada");
		}
	}

}
